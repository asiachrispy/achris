package com.chris.pattern.command;

/**
 * User: chris
 * Date: 12-10-23
 * Time: 下午2:49
 * 空对象,null object
 */
public class ResetCommand implements Command {
    private Receiver receiver;

    public ResetCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        receiver.reset();
    }

    public void undo() {
        System.out.println("do nothing！");
    }
}
