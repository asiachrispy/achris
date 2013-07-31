package com.chris.algo.vista;

import java.util.*;


/**
 * 朴素贝叶斯分类器
 */

public class BayesClassifier {
    private TrainingDataManager tdm;//训练集管理器
    private static double zoomFactor = 10.0f;

    /**
     * 默认的构造器，初始化训练集
     */
    public BayesClassifier() {
        tdm = new TrainingDataManager();
    }

    /**
     * 计算给定的文本属性向量X在给定的分类Cj中的类条件概率
     * <p/>
     * <code>ClassConditionalProbability</code>连乘值
     *
     * @param X  给定的文本属性向量
     * @param Cj 给定的类别
     * @return 分类条件概率连乘值，即<br>
     */
    float calcProd(Set<String> X, String Cj) {
        float ret = 1.0F;
        // 类条件概率连乘
        String Xi = null;
        for (String txt : X) {
            Xi = txt;
            //因为结果过小，因此在连乘之前放大10倍，这对最终结果并无影响，因为我们只是比较概率大小而已
            ret *= ClassConditionalProbability.calculatePxc(Xi, Cj) * zoomFactor;
        }

        // 再乘以先验概率
        ret *= PriorProbability.calculatePc(Cj);
        return ret;
    }

    /**
     * 去掉停用词
     *
     * @param oldWords 给定的文本
     * @return 去停用词后结果
     */
    public Set<String> dropStopWords(Set<String> oldWords) {
        Set<String> result = new HashSet<String>();
        for (String txt : oldWords) {
            if (!StopWordsHandler.IsStopWord(txt)) {//不是停用词
                result.add(txt);
            }
        }
        return result;
    }

    /**
     * 对给定的文本进行分类
     *
     * @param text 给定的文本
     * @return 分类结果
     */

    @SuppressWarnings("unchecked")

    public String classify(String text) {
        Set<String> terms = ChineseSpliter.split(text);//中文分词处理(分词后结果可能还包含有停用词）
        terms = dropStopWords(terms);//去掉停用词，以免影响分类

        String[] Classes = tdm.getTrainingClassifications();//分类
        float priority = 0.0F;
        List<ClassifyResult> crs = new ArrayList<ClassifyResult>();//分类结果
        String Ci = null;
        ClassifyResult cr = null;
        for (int i = 0; i < Classes.length; i++) {
            Ci = Classes[i];//第i个分类
            priority = calcProd(terms, Ci);//计算给定的文本属性向量terms在给定的分类Ci中的分类条件概率

            //保存分类结果
            cr = new ClassifyResult();
            cr.classification = Ci;//分类
            cr.priority = priority;//关键字在分类的条件概率
            System.out.println("In process.");
            System.out.println(Ci + "：" + priority);
            crs.add(cr);
        }

        //对最后概率结果进行排序

        java.util.Collections.sort(crs, new Comparator() {
            public int compare(final Object o1, final Object o2) {
                final ClassifyResult m1 = (ClassifyResult) o1;
                final ClassifyResult m2 = (ClassifyResult) o2;
                final double ret = m1.priority - m2.priority;
                if (ret < 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        //返回概率最大的分类
        return crs.get(0).classification;
    }


    public static void main(String[] args) {

        String text = "　新浪财经评论员老艾[微博]认为，今日大盘走的让大家颇为纠结，上午的一波直线拉升一度让大家亢奋，但午后新增量能未能跟上，再次缓缓滑落。上涨原因是央行逆回购170亿结束5周零操作，缓解市场资金压力。央妈终于不再像上次那样袖手旁观，7月份很快也要过去了，钱荒问题会得以缓解。创业板在早盘大幅杀跌，但很快引来资金抄底，收了一根长下影线，近期从机构及资金动向来看，对创业板不是很有利，毕竟累计涨幅太高，资金有套现冲动也很正常。但目前来看，权重及周期股仍难有作为，市场热点仍得由中小盘概念股发起，相信在经过回调之后，一些长期有发展潜力的个股仍会再次受到资金追捧，目前趁着回调大浪淘沙，正是精选个股的好机会，为下一波行情做好准备。。";

        BayesClassifier classifier = new BayesClassifier();//构造Bayes分类器
        String result = classifier.classify(text);//进行分类
        System.out.println("此项属于[" + result + "]");

    }

}