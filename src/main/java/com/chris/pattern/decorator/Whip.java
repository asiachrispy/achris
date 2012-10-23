package com.chris.pattern.decorator;

/**
 * User: chris
 * Date: 12-9-15
 * Time: 下午9:41
 */
public class Whip extends CondimentDecorator {
    Beverage beverage;

    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        return beverage.getDescription() + ", whip";
    }

    public double cost() {
        return .18 + beverage.cost();
    }
}
