package com.chris.common.design;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 11-12-1
 * Time: 下午7:25
 * To change this template use File | Settings | File Templates.
 */
class Mother implements Observer{
 public void update(Children child){
  if(child.getState().equals("fight")){
   System.out.println(" 告诉Mother，他和别人打架了");
  }else if(child.getState().equals("scholarship")){
   System.out.println("告诉 Mother,他得到了奖学金");
  }
 }
}
