package com.chris.pattern.command;

/**
 * User: chris
 * Date: 12-10-23
 * Time: 下午2:58
 */
public class Client {

    public static void main(String[] args){
        //receiver 真正执行命令的实体
        Receiver light = new Receiver() ;

        //command   构建需要执行的按钮
        Command lightOff = new LightOffCommand(light);
        Command lightOn = new LightOnCommand(light);
        Command lightReset = new ResetCommand(light);

        //control 遥控器
        Invoker control = new Invoker();  // 构建一个遥控器
        control.setCommand(lightOn , lightOff, lightReset); // 给遥控器设置需要的按钮

        // 调用者控制遥控器的按钮
        control.pressedOnButton();
        control.pressedOffButton();

        control.undo() ;
    }
}
