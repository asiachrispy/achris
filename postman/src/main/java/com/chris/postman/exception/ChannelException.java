package com.chris.postman.exception;

/**
 * User: zhong.huang
 * Date: 12-10-16
 * Time: 下午1:51
 */
public class ChannelException extends Exception{

    public ChannelException() {
    }

    public ChannelException(String message) {
        super(message);
    }

    public ChannelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChannelException(Throwable cause) {
        super(cause);
    }
}
