package com.chris.pattern.command;

/**
 * User: chris
 * Date: 12-10-23
 * Time: 下午2:45
 *  关灯命令的对象
 */
public class LightOffCommand implements Command {
    private Receiver receiver;

    public LightOffCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
       receiver.offLight();
    }

    public void undo() {
        receiver.onLight();
    }
}
