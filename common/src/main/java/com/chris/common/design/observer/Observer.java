package com.chris.common.design.observer;

/**
 * User: chris
 * Date: 12-9-15
 * Time: 下午8:16
 */
public interface Observer {
    public void update(float temp, float humidity, float pressure);
}
