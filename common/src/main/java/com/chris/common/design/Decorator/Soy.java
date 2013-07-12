package com.chris.common.design.Decorator;

/**
 * User: chris
 * Date: 12-9-15
 * Time: 下午9:41
 */
public class Soy extends CondimentDecorator {
    Beverage beverage;

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getDescription() {
        return beverage.getDescription() + ", Soy";
    }

    public double cost() {
        return .10 + beverage.cost();
    }
}
