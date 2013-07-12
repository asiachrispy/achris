package com.chris.common.design;

import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 11-12-1
 * Time: 下午7:25
 * To change this template use File | Settings | File Templates.
 */ //一个孩子需要老师和家长的监护，当孩子有事情发生时，会被通知，然后去处理事情！
//那么事件是如何被通知的呢，就是在孩子身上安置观察者！
class Children{
 static private Vector<Observer> obs;
 static private String state=null;
 static{
  obs=new Vector<Observer>();
 }
 public static void attach(Observer o){
  obs.addElement(o);
 }
 public static void detach(Observer o){
  obs.removeElement(o);
 }
 public void setState(String str){
  state=str;
 }
 public String getState(){
  return state;
 }
 public void notifyObs(){
  for(Observer o:obs){
   o.update(this);
  }
 }
}
