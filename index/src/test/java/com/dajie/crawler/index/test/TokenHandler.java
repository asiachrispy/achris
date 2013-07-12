package com.dajie.crawler.index.test;

import org.junit.Test;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.IOException;
import java.io.StringReader;

/**
 * User: zhong.huang
 * Date: 13-5-24
 */
public class TokenHandler {

    @Test
    public void testA() {
        String str = "营销、沟通、传播三者之间的关系和区别？";
        StringReader reader = new StringReader(str);
        IKSegmentation ik = new IKSegmentation(reader, true);// 当为true时，分词器进行最大词长切分
        Lexeme lexeme = null;
        try {
            while ((lexeme = ik.next()) != null) {
                System.out.println(lexeme.getLexemeText());
            }
        } catch (IOException e) {
        }
    }

}
