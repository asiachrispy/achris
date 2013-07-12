package com.chris.pattern.command;

/**
 * User: chris
 * Date: 12-10-23
 * Time: 下午2:42
 * 由它执行最后的动作
 */
public class Receiver {

    public Receiver() {
    }

    public void onLight(){
        System.out.println("The light is open!") ;
    }

    public void offLight(){
        System.out.println("The light is close!") ;
    }

    public void reset(){
        System.out.println("The light is reset!") ;
    }


}
