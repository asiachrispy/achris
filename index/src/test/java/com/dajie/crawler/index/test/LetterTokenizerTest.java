package com.dajie.crawler.index.test;

import org.apache.lucene.analysis.LetterTokenizer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

//只要读取到非字符的符号，就分词
public class LetterTokenizerTest {

    public static void main(String[] args) {
        Reader reader = new StringReader("That's a 者和world,I wonder why.");

        LetterTokenizer ct = new LetterTokenizer(reader);
        try {

            while (ct.incrementToken()) {
                System.out.println(ct.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
/**
 * 在分词过程中，遇到了单引号，就把单引号之前的作为一个词条返回。
 *
 * 可以验证一下，把构造的Reader改成下面的形式：
 *
 * Reader reader = new StringReader("ThatisaworldIwonderwhy.");
 *
 * 输出结果为：
 *
 * (ThatisaworldIwonderwhy,0,22)
 *
 * 没有非字符的英文字母串就可以作为一个词条，一个词条长度的限制为255个字符，可以在CharTokenizer抽象类中看到定义：
 *
 * private static final int MAX_WORD_LEN = 255;
 */
