package com.chris.pattern.decorator;

/**
 * User: chris
 * Date: 12-9-15
 * Time: 下午9:38
 */
public class Espresso extends Beverage{

    public Espresso() {
        description = "Espresso";
    }

    public double cost() {
        return 1.99;
    }
}
