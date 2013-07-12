package com.chris.pattern.abserver;

/**
 * User: chris
 * Date: 12-9-15
 * Time: 下午8:17
 */
public interface Subject {
    void register(Observer observer);
    void remove(Observer observer);
    void notifyObservers();
}
