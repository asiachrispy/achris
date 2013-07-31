package com.chris.algo.clazz;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordList {

    private final static Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+'?[a-zA-Z]*");

    private Set<String> parse(File file) throws IOException {
        Preconditions.checkNotNull(file, "File is null.");
        InputStream stream = new FileInputStream(file);
        Set<String> words = new HashSet<String>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(stream);
            String nextLine = null;
            Iterable<String> possibleWords = null;
            Matcher matcher = null;
            final Splitter splitter = Splitter.onPattern("[\\s]+").omitEmptyStrings().trimResults();
            while (scanner.hasNextLine()) {
                nextLine = scanner.nextLine();
                possibleWords = splitter.split(nextLine);

                for (String word : possibleWords) {
                    matcher = WORD_PATTERN.matcher(word);
                    if (matcher.matches()) {
                        words.add(word.toLowerCase());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return words;
    }

    public static void main(String[] args) throws Exception {
        File stopWords = new File("C:\\Users\\zhong.huang\\Documents\\GitHub\\achris\\algorithm\\dictionary\\stopwords.txt");
        System.out.println(stopWords.getCanonicalPath());
        System.out.println(stopWords.getAbsolutePath());
        WordList stopWordList = new WordList();
        Set<String> words = stopWordList.parse(stopWords);
        for (String stopWord : words) {
            System.out.println(stopWord);
        }

        System.out.println("Total: " + words.size());
    }
}
