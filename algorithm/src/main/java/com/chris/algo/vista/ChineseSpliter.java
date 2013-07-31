package com.chris.algo.vista;
import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

/**
 * 中文分词器
 */

public class ChineseSpliter {
    private static IKSegmentation ikSeg = null;

    public ChineseSpliter() {
    }

    /**
     * 对给定的文本进行中文分词
     *
     * @param content 给定的文本
     * @return 分词完毕的文本
     */
    public static Set<String> split(String content) {
        Set<String> result = new HashSet<String>();
        ikSeg = new IKSegmentation(new StringReader(content), true);
        try {
            Lexeme lexeme = null;
            String txt = null;
            while ((lexeme = ikSeg.next()) != null) {
                txt = lexeme.getLexemeText();
                result.add(txt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}