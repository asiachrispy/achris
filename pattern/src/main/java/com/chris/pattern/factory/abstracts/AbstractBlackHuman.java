package com.chris.pattern.factory.abstracts;

/**
 * User: chris
 * Date: 12-10-18
 * Time: 下午3:28
 */
public abstract class AbstractBlackHuman implements Human {

    @Override
    public void talk(String content) {
    }

    @Override
    public void putOn(String cloth) {
    }

    @Override
    public String getColor() {
        return "Black";
    }
}
