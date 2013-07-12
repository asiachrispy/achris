package com.chris.pattern.command;

/**
 * User: chris
 * Date: 12-10-23
 * Time: 下午2:30
 * 指遥控器。
 通过setCommand()方法来获取Command，再由子类“决定”需要的哪个Command。
 通过几个Button方法，来决定什么时候执行相应的事件。接受者作出相应地反映（灯亮，灯灭）。
 这里即可以体会到，遥控器和灯是解耦的。
 */
public class Invoker  {
    private Command onCommand;
    private Command offCommand ;
    private Command undoCommand ;

    public Invoker() {}

    public Invoker(Command onCommand, Command offCommand, Command undoCommand) {
        this.onCommand = onCommand;
        this.offCommand = offCommand;
        this.undoCommand = undoCommand;
    }

    public void setCommand (Command onCommand,Command offCommand, Command resetCommand) {
        this.offCommand = offCommand;
        this.onCommand = onCommand;
        this.undoCommand = resetCommand;
    }

    public void pressedOnButton() {
       this.onCommand.execute();
       offCommand = onCommand;
    }

    public void pressedOffButton() {
        this.offCommand.execute();
        onCommand = offCommand;
    }

    public void undo(){
        this.undoCommand.undo();
    }
}
