package com.chris.crawler.index.core.similary;

/**
 * User: zhong.huang
 * Date: 13-5-24
 */
public class TestCos {

    public static void main(String[] args) {
        try {
//            URL url2 = new URL("http://www.ruanyifeng.com/blog/2013/03/cosine_similarity.html");
//            URL url3 = new URL("http://www.ruanyifeng.com/blog/2013/03/tf-idf.html");
//            String v1 = Jsoup.parse(url2, 300).getElementsByTag("body").text();
//            String v2 = Jsoup.parse(url3, 300).getElementsByTag("body").text();
//
//            Document doc1 = new DocumentImpl(v1);
//            Document doc2 = new DocumentImpl(v2);
//            CosSimilary cos = new CosSimilary();
//            System.out.println(cos.calculateSimilary(doc1, doc2));

            String v1 = "最后来看getNextLexeme()，从最终的分词结果集中取出分词结果，输出";
            String v2 = "compound()合并数量词，将相邻的数量词切分结果进行合并 如果取出来的词是停用词，则过滤掉，不输出";

            IDoc idoc1 = new IDocImpl(v1);
            IDoc idoc2 = new IDocImpl(v2);
            ICosSimilary icos = new ICosSimilary();
            System.out.println(icos.similary(idoc1, idoc2));
        } catch (Exception e) {
        }

    }
}
