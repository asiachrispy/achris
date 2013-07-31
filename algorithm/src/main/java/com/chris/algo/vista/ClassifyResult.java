package com.chris.algo.vista;

/**
 * 分类结果
 */

public class ClassifyResult {
    public double priority = 0.00d;//分类的概率
    public String classification;//分类

    public ClassifyResult() {
    }

    public double getPriority() {
        return priority;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}