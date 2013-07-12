package com.chris.common.design;

// 观察者模式定义了一种一对多的依赖关系，让多个观察者对需昂同时监听某一个主题对象。
//这个主题对象在状态上发生变化时，会通知所有观察者对象，使它们能够自动更新自己。
//1， 观察者（具体执行操作的对象，有多个）
//2， 被观察者（顾名思义是被观察的对象，如果该对象发生某些变化则通知观察者执行对应的操） 

public class TestObserver {

 public static void main(String[] args) {
  Children child=new Children();
  Observer parent=new Teacher();
  Observer mother=new Mother();
  Children.attach(parent);
		Children.attach(mother);
  child.setState("fight");
  child.notifyObs();
  child.setState("scholarship");
  child.notifyObs();

 }

}
