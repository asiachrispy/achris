package com.chris.pattern.command;

/**
 * User: chris
 * Date: 12-10-23
 * Time: 下午2:28
 * 电信号
 * 命令对象的execute方法执行了接受者（Receiver）的一个或者几个action，
 * 在由调用者control确定什么时候执行execute。
 */
public interface Command {
    public void execute();
    public void undo() ;
}
