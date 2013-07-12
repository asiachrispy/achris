package com.chris.pattern.decorator;

/**
 * User: chris
 * Date: 12-9-15
 * Time: 下午9:39
 */
public class HouseBlend extends Beverage {
    public HouseBlend() {
        description = "House Blend Coffee";
    }

    public double cost() {
        return .89;
    }
}