package com.chris.algo.vista;

/**
 * 先验概率计算
 * <p/>
 * <h3>先验概率计算</h3>
 * <p/>
 * P(c<sub>j</sub>)=N(C=c<sub>j</sub>)<b>/</b>N <br>
 * <p/>
 * 其中，N(C=c<sub>j</sub>)表示类别c<sub>j</sub>中的训练文本数量；
 * <p/>
 * N表示训练文本集总数量。
 */

public class PriorProbability {
    private static TrainingDataManager tdm = new TrainingDataManager();

    /**
     * 先验概率
     * @param c 给定的分类
     * @return 给定条件下的先验概率
     */
    public static float calculatePc(String c) {
        float ret = 0F;
        float Nc = tdm.getTrainingFileCountOfClassification(c);
        float N = tdm.getTrainingFileCount();
        ret = Nc / N;
        return ret;
    }
}