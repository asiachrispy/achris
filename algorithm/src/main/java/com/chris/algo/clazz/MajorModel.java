package com.dajie.algo.clazz;

/**
 * User: zhong.huang
 * Date: 13-7-26
 */
public class MajorModel {
    private int id;
    private String value;
    private MajorModel parent;

    public MajorModel() {
    }

    public MajorModel(int id) {
        this.id = id;
    }

    public boolean isMain() {
        return parent.getId() == 0 ? true : false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MajorModel getParent() {
        return parent;
    }

    public void setParent(MajorModel parent) {
        this.parent = parent;
    }
}
