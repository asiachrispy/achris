package com.chris.pattern.decorator;

/**
 * User: chris
 * Date: 12-9-15
 * Time: 下午9:34
 */
public abstract class Beverage {
     String description = "Unknown Beverage";;
    
    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
