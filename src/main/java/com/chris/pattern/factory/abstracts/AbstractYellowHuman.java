package com.chris.pattern.factory.abstracts;

/**
 * User: chris
 * Date: 12-10-18
 * Time: 下午3:28
 */
public abstract class AbstractYellowHuman implements Human {

    @Override
    public void talk(String content) {
        System.out.println(content);
    }

    @Override
    public void putOn(String cloth) {
        System.out.println(cloth);
    }

    @Override
    public String getColor() {
        return "Yellow";
    }

}
