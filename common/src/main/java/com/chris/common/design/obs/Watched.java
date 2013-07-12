package com.chris.common.design.obs;

import java.util.Observable;

public class Watched extends Observable {
    private String data = "";

    /**
     * 业务方法，一旦执行某个操作，则通知观察者 ,所有观察者分别去各自处理自己的业务
     */
    public void doBusiness(String data) {
        if (!this.data.equals(data)) {
            this.data = data;
            System.out.println(" data: " + data);
            setChanged();
        }
        notifyObservers();
    }
}
