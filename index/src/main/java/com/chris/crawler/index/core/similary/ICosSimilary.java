package com.chris.crawler.index.core.similary;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-24
 */
public class ICosSimilary {
    /**
     * 计算文档相似度
     *
     * @param doci 准备比较的文档
     * @param docj 样例文档
     * @return
     */
    public double similary(IDoc doci, IDoc docj) {
        Map<String, Integer> ifreq = doci.getDF();//文档词项词频
        Map<String, Integer> jfreq = docj.getDF();

        double ijSum = 0;
        Iterator<Map.Entry<String, Integer>> it = ifreq.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            if (jfreq.containsKey(entry.getKey())) {// 某一个词在另一文档中是否出现
                ijSum += (entry.getValue() * jfreq.get(entry.getKey()));  //x1*y1 + x2*y2
            }
        }

        double iPowSum = powSum(doci);
        double jPowSum = powSum(docj);
        return ijSum / (iPowSum * jPowSum);
    }

    /**
     * x2 + y2 + z2
     * @param document
     * @return
     */
    private double powSum(IDoc document) {
        Map<String, Integer> mapfreq = document.getDF();
        Collection<Integer> freqs = mapfreq.values();

        double sum = 0;
        for (int f : freqs) {// 词在文档中出现的次数
            sum += Math.pow(f, 2);
        }
        return Math.sqrt(sum);
    }
}
