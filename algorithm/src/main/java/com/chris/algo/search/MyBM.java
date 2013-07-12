package com.chris.algo.search;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Fast string search (indexOf) using the Boyer-Moore
 * algorithm.
 * <p/>
 * use:
 * import com.mindprod.boyer.Boyer;
 * <p/>
 * Boyer b = new Boyer("dogcatwombat");
 * int where = b.indexOf("cat");
 * or
 * int where = Boyer.indexOf("dogcatwombat","cat");
 * <p/>
 * Boyer-Moore is about twice as fast as String.indexOf when
 * the string you are searching in is 2K or over and the
 * pattern you are searching for is 4 characters or longer.
 * <p/>
 * String.indexOf is particularly slow when the pattern begins
 * with a common letter such as "e". Boyer-Moore is fastest
 * when the pattern is long and composed only of uncommon
 * letters, e.g. "z" or "^". If you use a char[] instead of
 * String for your text to be searched, it will run an
 * additional 33% faster.
 * <p/>
 * You don't have to worry which is faster. Boyer automatically
 * reverts to String.indexOf when that would be faster.
 * <p/>
 * Boyer currently does only case sensitive searches.
 * I think it could be done by passing a boolean to analysePattern.
 * to say whether case sensitive or insensitive searches wanted.
 * You would need to store two flavours of previous pattern.
 * <p/>
 * May be copied and used freely for any purpose but military.
 * <p/>
 * Roedy Green
 * Canadian Mind Products
 * #327 - 964 Heywood Avenue
 * Victoria, BC Canada V8V 2Y5
 * tel: (250) 361-9093
 * mailto:roedyg@mindprod.com
 * http://mindprod.com
 * <p/>
 * version 1.4 2002 July 20 by David Gentzel <gentzel@pobox.com>
 * - fix bug in indexOfViaTextArray, was ignoring non-zero index.
 * version 1.3 2001 August 13 by Roedy Green
 * - clean up JavaDoc
 * - set different breakEvenLenPat and breakEvenLenText based on debugging setting.
 * - removed the null constructor.
 * - rename pat to patternArray
 * <p/>
 * version 1.2 2001 August 13 by Jonathan Ellis
 * - added index argument to indexOf functions, to allow you to start search
 * part way through the string.
 * - setPattern is no longer public; this cuts down on the number of overloaded fns I had to write for the above
 * (why would anyone want to make n+1 fn calls instead of n, in the first place?)
 * - lenPat and lenText now instance variables again; I am anal about not duplicating code where possible
 * <p/>
 * version 1.1 1999 January 10
 * - use simple String.indexOf for short patterns and texts
 * - lazy evaluation of skip[] array, to avoid work of calculating it.
 * - more comments.
 * - lenPat and lenText now local variables.
 * - more efficient code to catch the degenerate cases of null and 0-length strings.
 * - unravel main loop slightly to avoid extra charAt.
 * - now throw NullPointerExceptions on null arguments.
 * - also support searches of char arrays.
 * <p/>
 * version 1.0 1999 January 8
 *
 * @author Roedy Green
 */

/*
Futures:
- search given an InputStream
*/

public class MyBM {

    /**
     * true if debugging.  Includes test harness main method, and forces use of Boyer algorithm even on small cases.
     */
    private static final boolean DEBUGGING = true;

    /**
     * Copyright message not displayed.
     */
    private static final String EmbeddedCopyright =
        "copyright (c) 1999-2005 Roedy Green, Canadian Mind Products, http://mindprod.com";

    /**
     * Pattern length under which might as well use String.indexOf in place of the Boyer algorithm.
     */
    private static final int breakEvenLenPat = DEBUGGING ? 1 : 4;

    /**
     * Text length under which might as well use String.indexOf in place of the Boyer algorithm.
     */
    private static final int breakEvenLenText = DEBUGGING ? 1 : 2048;

    /**
     * what we search for, the pattern.
     */
    private String pattern;

    /**
     * Previous pattern, used to avoid reanalysing needlessly.
     */
    private String prevPattern;

    /**
     * store pattern as a char array for efficient access.
     */
    private char[] patternArray;

    /**
     * what we search in, in inefficient String form.
     */
    private String text;

    /**
     * what we search in, alternate more efficient char[] form.
     */
    private char[] textArray;

    /**
     * how much we can skip to right
     * based on letter we find in the text corresponding to the
     * end of the pattern after we find a mismatch.
     * Best to look at how it is used to understand it.
     */
    private int[] skip;

    /**
     * length of pattern
     */
    int lenPat = 0;

    /**
     * length of text to search
     */
    int lenText = 0;

    /**
     * constructor that also
     * sets text to search in for subsequent indexOf searches.
     * Pattern provided later with indexOf.
     *
     * @param text String to search in. may be "" but not null.
     */
    public MyBM(String text) {
        setText(text);
    }

    /**
     * constructor that also
     * sets text to search in for subsequent indexOf searches.
     * Pattern provided later with indexOf.     *
     *
     * @param text char array to search in. may be 0-length but not null.
     */
    public MyBM(char[] text) {
        setText(text);
    }

    /**
     * Calculate how many chars you can skip
     * to the right if you find a mismatch.
     * It depends on what character is at
     * the end of the word when you find a mismatch.
     * We must match the pattern, char by char, right to left.
     * Only called after degenerate cases,
     * (e.g. null, zero-length and 1-length Pattern) are eliminated.
     */
    private final void analysePattern() {
        if (pattern.equals(prevPattern)) {
            return;
        }
        // get pattern in fast-to-access charArray form
        patternArray = pattern.toCharArray();
        // Calculate how many slots we can skip to the right
        // depending on which char is at the end of the word
        // when we find a match.
        // Recycle old array if possible.
        if (skip == null) skip = new int[256];
        for (int i = 0; i < 256; i++) {
            skip[i] = lenPat;
        } // end for

        for (int i = 0; i < lenPat - 1; i++) {
            // The following line is the key to the whole algorithm.
            // It also deals with repeating letters in the pattern.
            // It works conservatively, considering only the last
            // instance of repeating letter.
            // We exclude the last letter of the pattern, because we are
            // only concerned with what to do on a mismatch.
            skip[patternArray[i] & 0xff] = lenPat - i - 1;
        } // end for
        prevPattern = pattern;
    } // end analysePattern

    /**
     * Search for given pattern in string.
     *
     * @param text    String to search in. May be "" but not null.
     * @param pattern String to search for. May be "" but not null.
     * @return 0-based offset in text, just like String.indexOf.
     *         -1 means not found.
     */
    public static int indexOf(String text, String pattern) {
        return MyBM.indexOf(text, pattern, 0);
    } // end indexOf

    /**
     * Search for given pattern in string.
     *
     * @param text    String to search in. May be "" but not null.
     * @param pattern String to search for. May be "" but not null.
     * @param index   index at which to start search
     * @return 0-based offset in text, just like String.indexOf.
     *         -1 means not found.
     */
    public static int indexOf(String text, String pattern, int index) {
        return new MyBM(text).indexOf(pattern, index);
    } // end indexOf

    /**
     * Search for given pattern in char array
     *
     * @param text    char array to search in. May be "" but not null.
     * @param pattern String to search for. May be "" but not null.
     * @param index   index at which to start search
     * @return 0-based offset in text, just like String.indexOf.
     *         -1 means not found.
     */
    public static int indexOf(char[] text, String pattern, int index) {
        return new MyBM(text).indexOf(pattern, index);
    } // end indexOf

    /**
     * Search for given pattern in char array
     *
     * @param text    char array to search in. May be "" but not null.
     * @param pattern String to search for. May be "" but not null.
     * @return 0-based offset in text, just like String.indexOf.
     *         -1 means not found.
     */
    public static int indexOf(char[] text, String pattern) {
        return MyBM.indexOf(text, pattern, 0);
    } // end indexOf

    /**
     * Search for given pattern in string.
     * Text must have been set previously by the constructor or setText.
     *
     * @param pattern String to search for. May be "" but not null.
     * @param index   index at which to start search
     * @return 0-based offset in text, just like String.indexOf.
     *         -1 means not found.
     */
    public int indexOf(String pattern, int index) {
        setPattern(pattern);
        return indexOf(index);
    } // end indexOf

    /**
     * Search for given pattern in string.
     * Text must have been set previously by the constructor or setText.
     *
     * @param pattern String to search for. May be "" but not null.
     * @return 0-based offset in text, just like String.indexOf.
     *         -1 means not found.
     */
    public int indexOf(String pattern) {
        return indexOf(pattern, 0);
    } // end indexOf

    /**
     * Search for given pattern in String or char array.
     * Presume Pattern and Text have been previously set.
     *
     * @param index index at which to start search
     * @return 0-based offset in text, just like String.indexOf.
     *         -1 means not found.
     */
    protected final int indexOf(int index) {
        if (text != null) {
            return indexOfViaText(index);
        } else {
            return indexOfViaTextArray(index);
        }
    } // end indexOf

    /**
     * Search for given pattern in String.
     * Presume Pattern and Text have been previously set.
     *
     * @param index index at which to start search
     * @return 0-based offset in text, just like String.indexOf.
     *         -1 means not found.
     */
    private final int indexOfViaText(int index) {
        // Deal with cases that don't rate the full
        // Boyer-Moore treatment.
        if ((lenText <= breakEvenLenText / 2 || lenPat <= breakEvenLenPat)) {
            // this way we are consistent with
            // String.indexOf for "".indexOf("")
            // which is -1 in JDK 1.1
            // and 0 in JDK 1.2. See Bug Parade entry 4096273.
            // "".indexOf("abc") is always -1
            return text.indexOf(pattern, index);
        } // end if

        analysePattern();

        // At this point we have the pattern, and have skip[] calculated
        // We are commited to calculating the indexOf via Boyer-Moore.

        // tforward works left to right through the text, skipping depending
        // on what char it found in the text corresponding to the end of the pattern,
        // not to the place of the mismatch.
        char testChar = 0;
        final int lastPatChar = patternArray[lenPat - 1];
        outer:
        for (int tforward = index + lenPat - 1; tforward < lenText; tforward += skip[testChar & 0xff]) {
            // compare working right to left through both pattern and text
            testChar = text.charAt(tforward);
            if (testChar != lastPatChar) {
                continue outer;
            }
            // step back through pattern
            // step back through text
            inner:
            for (int tback = tforward - 1, pback = lenPat - 2; pback >= 0; tback--, pback--) {
                if (text.charAt(tback) != patternArray[pback]) continue outer;
            } // end inner for
            // we stepped all the way back through the pattern comparing
            // without finding a mismatch. We found it!
            return tforward - lenPat + 1;
        } // end outer for
        // stepped through entire text without finding it.
        return -1;
    } // end indexOf

    /**
     * Search for given pattern in charArray.
     * presume Pattern and Text have been previously set.
     *
     * @param index index at which to start search
     * @return 0-based offset in text, just like String.indexOf.
     *         -1 means not found.
     */
    private final int indexOfViaTextArray(int index) {
        // Deal with cases that don't rate the full
        // Boyer-Moore treatment.
        // !! Actually this has a different break even point to cover the overhead
        // of going back to String. We probably should adjust slightly.
        if ((lenText <= breakEvenLenText / 2 || lenPat <= breakEvenLenPat)) {
            // this way we are consistent with
            // String.indexOf for "".indexOf("")
            // which is -1 in JDK 1.1
            // and 0 in JDK 1.2
            // "".indexOf("abc") is always -1
            return new String(textArray).indexOf(pattern, index);
        } // end if

        analysePattern();

        // At this point we have the pattern, and have skip[] calculated
        // We are commited to calculating the indexOf via Boyer-Moore.

        // tforward works left to right through the text, skipping depending
        // on what char it found in the text corresponding to the end of the pattern,
        // not to the place of the mismatch.
        char testChar = 0;
        final int lastPatChar = patternArray[lenPat - 1];
        outer:
        for (int tforward = index + lenPat - 1; tforward < lenText; tforward += skip[testChar & 0xff]) {
            // compare working right to left through both pattern and text
            testChar = textArray[tforward];
            if (testChar != lastPatChar) {
                continue outer;
            }
            // step back through pattern
            // step back through text
            inner:
            for (int tback = tforward - 1, pback = lenPat - 2; pback >= 0; tback--, pback--) {
                if (textArray[tback] != patternArray[pback]) continue outer;
            } // end inner for
            // we stepped all the way back through the pattern comparing
            // without finding a mismatch. We found it!
            return tforward - lenPat + 1;
        } // end outer for
        // stepped through entire text without finding it.
        return -1;
    } // end indexOf

    /**
     * Set pattern to use for subsequent indexOf searches.
     * Note this is protected.  Clients of this package
     * set the pattern using indexOf.
     *
     * @param pattern String to search for. May be "" but not null..
     */
    protected void setPattern(String pattern) {
        if (pattern == null) {
            throw new NullPointerException();
        }
        this.pattern = pattern;
        lenPat = pattern.length();
    }

    /**
     * Set text to search in for subsequent indexOf searches.
     *
     * @param text String to search in. May be "" but not null.
     */
    public void setText(String text) {
        if (text == null) {
            throw new NullPointerException();
        }
        this.text = text;
        lenText = text.length();
        // we don't yet have this is efficient char[] form.
        this.textArray = null;
    }

    /**
     * Set text to search in for subsequent indexOf searches.
     *
     * @param text char array to search in. May be empty but not null.
     *             This is more efficient that providing the equivalent String.
     */
    public void setText(char[] text) {
        if (text == null) {
            throw new NullPointerException();
        }
        // we have efficient char[] form, but not the string form.
        this.textArray = text;
        lenText = textArray.length;
        this.text = null;
    }

    /**
     * test harness
     *
     * @param args command line arguments.  Not used.
     */
    public static void main(String[] args) {
        if (DEBUGGING) {
            System.out.println(MyBM.indexOf("dogcatwombat", "cat"));
            System.out.println("dogcatwombat".indexOf("cat"));

            System.out.println(MyBM.indexOf("crtcamccmcarogcatwombat", "cat"));
            System.out.println("crtcamccmcarogcatwombat".indexOf("cat"));

            System.out.println(MyBM.indexOf("dogcatwombat", ""));
            System.out.println("dogcatwombat".indexOf(""));

            System.out.println(MyBM.indexOf("", ""));
            System.out.println("".indexOf(""));

            System.out.println(MyBM.indexOf("", "abcde"));
            System.out.println("".indexOf("abcde"));

            System.out.println(MyBM.indexOf("dogcatwombat", "cow"));
            System.out.println("dogcatwombat".indexOf("cow"));

            String s = "create table foo (created_date datetime default sysdate not null)";
            System.out.println(s.indexOf("create", 10));
            System.out.println(MyBM.indexOf(s, "create", 10));

            try {

                // O P E N
                // Any file of sample characters
                File f = new File("C:/temp/temp.txt");
                int size = (int) f.length();
                FileReader fr;
                fr = new FileReader(f);

                // R E A D
                char[] ca = new char[size];
                int charsRead = fr.read(ca);
                s = new String(ca);

                long start;
                long stop;
                int result = 0;

                start = System.currentTimeMillis();
                for (int i = 0; i < 1000; i++) {
                    // Need to make different so optimiser will actually do
                    // the work repeatedly.
                    // search for strings like "efficiency9" that probably won't be there.
                    result = MyBM.indexOf(ca, "efficiency" + i % 10);
                }
                System.out.println("Boyer.indexOf(char[]): " + result);
                stop = System.currentTimeMillis();
                System.out.println("Elapsed:"
                    + (stop - start));

                // benchmark Boyer.indexOf
                start = System.currentTimeMillis();

                System.out.println("Boyer.indexOf(String): " + result);
                stop = System.currentTimeMillis();
                System.out.println("Elapsed:"
                    + (stop - start));

                // Benchmark String.indexOf
                start = System.currentTimeMillis();
                for (int i = 0; i < 1000; i++) {
                    result = s.indexOf("efficiency" + i % 10);
                }
                System.out.println("String.indexOf: " + result);
                stop = System.currentTimeMillis();
                System.out.println("Elapsed:"
                    + (stop - start));

                // C L O S E
                fr.close();

            } catch (IOException e) {
                return;
            }

        } // end if debugging
    } // end main
} // end class Boyer