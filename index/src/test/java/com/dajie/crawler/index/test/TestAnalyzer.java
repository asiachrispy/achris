package com.dajie.crawler.index.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;

import java.io.*;

public class TestAnalyzer {

    public static void main(String[] args) {
        filter();
//		test();
    }

    public static void filter() {
        try {
            File file = new File("/home/asiachris/lucene_work/textfile/cmd1.txt");
            FileReader stopWords = new FileReader(
                "/home/asiachris/lucene_work/textfile/stopWords.txt");
            Reader reader = new FileReader(file);
            Analyzer a = new MyAnalyzer();
            TokenStream tokenStream = a.tokenStream("", reader);

            TermAttribute termAtt = (TermAttribute) tokenStream
                .getAttribute(TermAttribute.class);
            TypeAttribute typeAtt = (TypeAttribute) tokenStream
                .getAttribute(TypeAttribute.class);

            OffsetAttribute offsetAtt = (OffsetAttribute) tokenStream
                .getAttribute(OffsetAttribute.class);

            PositionIncrementAttribute posAtt = (PositionIncrementAttribute) tokenStream
                .getAttribute(PositionIncrementAttribute.class);
            int n = 0;
            System.out
                .println("termAtt       typeAtt       offsetAtt       posAtt");
            while (tokenStream.incrementToken()) {
                n++;
                System.out.println(termAtt.term() + " " + typeAtt.type() + " ("
                    + offsetAtt.startOffset() + "," + offsetAtt.endOffset()
                    + ")   " + posAtt.getPositionIncrement());
            }
            System.out.println("== 共有词条 " + n + " 条 ==");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test() {
        String value = new String(
            "I'm a 学生 student. these are apples,and that's U.S.A STUDENT!");
        // 输入流
        StringReader s = new StringReader(value);
        // 标准分词
        TokenStream tokenStream = new StandardTokenizer(Version.LUCENE_CURRENT,
            s);
        // 标准过滤
        tokenStream = new StandardFilter(tokenStream);
        // 大小写过滤
        tokenStream = new LowerCaseFilter(tokenStream);

        TermAttribute termAtt = (TermAttribute) tokenStream
            .getAttribute(TermAttribute.class);

        TypeAttribute typeAtt = (TypeAttribute) tokenStream
            .getAttribute(TypeAttribute.class);

        OffsetAttribute offsetAtt = (OffsetAttribute) tokenStream
            .getAttribute(OffsetAttribute.class);

        PositionIncrementAttribute posAtt = (PositionIncrementAttribute) tokenStream
            .getAttribute(PositionIncrementAttribute.class);

        System.out
            .println("termAtt       typeAtt       offsetAtt       posAtt");
        try {
            while (tokenStream.incrementToken()) {
                System.out.println(termAtt.term() + " " + typeAtt.type() + " ("
                    + offsetAtt.startOffset() + "," + offsetAtt.endOffset()
                    + ")   " + posAtt.getPositionIncrement());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
 * 
 * termAtt typeAtt offsetAtt posAtt i'm <APOSTROPHE> (0,3) 1 a <ALPHANUM> (4,5)
 * 1 学 <CJ> (6,7) 1 student <ALPHANUM> (9,16) 1 these <ALPHANUM> (18,23) 1 are
 * <ALPHANUM> (24,27) 1 apples <ALPHANUM> (28,34) 1 and <ALPHANUM> (35,38) 1
 * that <APOSTROPHE> (39,45) 1 my <ALPHANUM> (46,48) 1 student <ALPHANUM>
 * (49,56)
 */