package com.chris.common.design.obs;

import java.util.Observer;

public class Tester {
    static private Watched watched;

    public static void main(String[] args) {
        watched = new Watched();

        Observer mob = new MailObserver();
        Observer nob = new NoticeObserver();

        watched.addObserver(mob);
        watched.addObserver(nob);


        watched.doBusiness("In C, we create bugs.");
        watched.doBusiness("In Java, we inherit bugs.");
        watched.doBusiness("In Java, we inherit bugs.");
        watched.doBusiness("In Visual Basic, we visualize bugs.");
    }
}

