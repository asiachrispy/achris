package com.chris.algo.vista;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 训练集管理器
 */

public class TrainingDataManager {
    private String[] trainingFileClassifications;//训练语料分类集合
    private File trainingTextDir;//训练语料存放目录
    private static String defaultPath = "E:\\temp\\Reduced";
    private static Map<String, Integer> C_n = new HashMap<String, Integer>();
    private static int N = 0;

    public TrainingDataManager() {
        trainingTextDir = new File(defaultPath);
        if (!trainingTextDir.isDirectory()) {
            throw new IllegalArgumentException("训练语料库搜索失败！ [" + defaultPath + "]");
        }
        this.trainingFileClassifications = trainingTextDir.list();


        int n = 0;
        for (int i = 0; i < trainingFileClassifications.length; i++) {
            n = getTrainingFileCountOfClassificationN(trainingFileClassifications[i]);
            C_n.put(trainingFileClassifications[i], n);
            N += n;
        }
    }

    /**
     * 返回训练文本类别，这个类别就是目录名
     *
     * @return 训练文本类别
     */
    public String[] getTrainingClassifications() {
        return this.trainingFileClassifications;
    }

    /**
     * 根据训练文本类别返回这个类别下的所有训练文本路径（full path）
     *
     * @param classification 给定的分类
     * @return 给定分类下所有文件的路径（full path）
     */
    public String[] getFilesPath(String classification) {
        File classDir = new File(trainingTextDir.getPath() + File.separator + classification);
        String[] ret = classDir.list();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = trainingTextDir.getPath() + File.separator + classification + File.separator + ret[i];
        }
        return ret;
    }

    /**
     * 返回给定路径的文本文件内容
     *
     * @param filePath 给定的文本文件路径
     * @return 文本内容
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */

    public static String getText(String filePath) throws IOException {
        InputStreamReader isReader = new InputStreamReader(new FileInputStream(filePath), "GBK");
        BufferedReader reader = new BufferedReader(isReader);
        String aline;
        StringBuilder sb = new StringBuilder();
        while ((aline = reader.readLine()) != null) {
            sb.append(aline).append("\n");
        }

        isReader.close();
        reader.close();
        return sb.toString();
    }

    /**
     * 返回训练文本集中所有的文本数目
     *
     * @return 训练文本集中所有的文本数目
     */
    public int getTrainingFileCount() {
//        int ret = 0;
//        for (int i = 0; i < trainingFileClassifications.length; i++) {
//            ret += getTrainingFileCountOfClassification(trainingFileClassifications[i]);
//        }
        return N;
    }

    /**
     * 返回训练文本集中在给定分类下的训练文本数目
     *
     * @param classification 给定的分类
     * @return 训练文本集中在给定分类下的训练文本数目
     */

    public int getTrainingFileCountOfClassificationN(String classification) {
        File classDir = new File(trainingTextDir.getPath() + File.separator + classification);
        return classDir.list().length;
    }

    public int getTrainingFileCountOfClassification(String classification) {
        return C_n.get(classification);
    }

    /**
     * 返回给定分类中包含关键字／词的训练文本的数目
     *
     * @param classification 给定的分类
     * @param key            给定的关键字／词
     * @return 给定分类中包含关键字／词的训练文本的数目
     */
    public int getCountContainKeyOfClassification(String classification, String key) {
        int ret = 0;
        try {
            String[] filePath = getFilesPath(classification);
            String text = null;
            for (int j = 0; j < filePath.length; j++) {
                text = getText(filePath[j]);
                if (text.contains(key)) {      //todo
                    ret++;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TrainingDataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TrainingDataManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}