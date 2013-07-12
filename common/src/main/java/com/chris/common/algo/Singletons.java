package com.chris.common.algo;

public class Singletons {

    private static Singletons singleton = new Singletons();

    private Singletons() {
    }

    public static Singletons getInstance() {
        return singleton;
    }

    public static void main(String[] args) {
        Singletons.getInstance();
        Singletons.getInstance();
    }
}
