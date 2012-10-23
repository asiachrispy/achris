package com.chris.pattern.decorator;

/**
 * User: chris
 * Date: 12-9-15
 * Time: 下午9:39
 */
public class DarkRoast extends Beverage {
    public DarkRoast() {
        description = "Dark Roast Coffee";
    }

    public double cost() {
        return .99;
    }
}