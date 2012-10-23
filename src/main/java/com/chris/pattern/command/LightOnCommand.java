package com.chris.pattern.command;

/**
 * User: chris
 * Date: 12-10-23
 * Time: 下午2:45
 *  开灯命令的对象
 */
public class LightOnCommand implements Command {
    private Receiver receiver;

    public LightOnCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
       receiver.onLight();
    }

    public void undo() {
        receiver.offLight();
    }
}
