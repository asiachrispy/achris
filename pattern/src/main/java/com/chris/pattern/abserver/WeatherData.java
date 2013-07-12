package com.chris.pattern.abserver;

import java.util.ArrayList;
import java.util.List;

/**
 * User: chris
 * Date: 12-9-15
 * Time: 下午8:20
 */
public class WeatherData implements Subject{
    private List<Observer> observers = null;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
        observers = new ArrayList<Observer>();
    }

    public void register(Observer observer) {
        observers.add(observer);
    }

    public void remove(Observer observer) {
        int i = observers.indexOf(observer);
        if (i >= 0) {
            observers.remove(i); }
    }

    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer)observers.get(i);
            observer.update(temperature, humidity, pressure);
        }
    }

    public void measurementsChanged() {
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
}

