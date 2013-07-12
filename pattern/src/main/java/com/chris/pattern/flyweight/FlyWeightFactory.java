package com.chris.pattern.flyweight;

import java.util.Hashtable;

/**
 * User: chris
 * Date: 12-11-1
 * Time: 下午4:19
 */
public class FlyWeightFactory {
    private Hashtable flyweights = new Hashtable();

    public FlyWeight getFlyweight(Object key) {
        FlyWeight flyweight = null;
        if (flyweights.containsKey(key)) {
            flyweight = (FlyWeight) flyweights.get(key);
        } else {
            flyweight = new ConcreateFlyWeight(key);
            flyweights.put(key, flyweight);
        }

        return flyweight;
    }

    public int getFlyWeightsSize()
    {
        return flyweights.size();
    }
}
