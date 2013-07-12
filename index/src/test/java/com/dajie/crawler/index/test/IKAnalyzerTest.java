package com.dajie.crawler.index.test;


import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;


/**
 * IKAnalyzer 分词器测试
 *
 * @author Luxh
 */
public class IKAnalyzerTest {

    @Test
    public void testIKAnalyzer() throws Exception {

        String keyWord = "2012年欧洲杯四强赛";

        IKAnalyzer analyzer = new IKAnalyzer();

        //使用智能分词
//        analyzer.setUseSmart(true);


        //打印分词结果
        //printAnalysisResult(analyzer, keyWord);

    }

    /**
     * 打印出给定分词器的分词结果
     *
     * @param analyzer 分词器
     * @param keyWord  关键词
     * @throws Exception
     */
//    private void printAnalysisResult(Analyzer analyzer, String keyWord) throws Exception {
//        System.out.println("当前使用的分词器：" + analyzer.getClass().getSimpleName());
//        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(keyWord));
//        tokenStream.addAttribute(CharTermAttribute.class);
//        while (tokenStream.incrementToken()) {
//            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
//            System.out.println(new String(charTermAttribute.buffer()));
//        }
//    }
}