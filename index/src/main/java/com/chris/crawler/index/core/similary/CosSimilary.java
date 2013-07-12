package com.chris.crawler.index.core.similary;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-24
 */
public class CosSimilary {
    /**
     * 计算文档相似度
     *
     * @param doci 准备比较的文档
     * @param docj 样例文档
     * @return
     */
    public double calculateSimilary(Document doci, Document docj) {
        Map<String, Integer> ifreq = doci.documentFreq();//文档词项词频
        Map<String, Integer> jfreq = docj.documentFreq();

        double ijSum = 0;
        Iterator<Map.Entry<String, Integer>> it = ifreq.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            if (jfreq.containsKey(entry.getKey())) {// 某一个词在另一文档中是否出现
                double iw = weight(entry.getValue());
                double jw = weight(jfreq.get(entry.getKey()));
                ijSum += (iw * jw);
            }
        }

        double iPowSum = powSum(doci);
        double jPowSum = powSum(docj);
        return ijSum / (iPowSum * jPowSum);
    }

    /**
     * @param document
     * @return
     */
    private double powSum(Document document) {
        Map<String, Integer> mapfreq = document.documentFreq();
        Collection<Integer> freqs = mapfreq.values();

        double sum = 0;
        for (int f : freqs) {// 词在文档中出现的次数
            double dw = weight(f);
            sum += Math.pow(dw, 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * 计算词项特征值
     *
     * @param wordFreq
     * @return
     */
    private double weight(float wordFreq) {
        return Math.sqrt(wordFreq);
    }
}
