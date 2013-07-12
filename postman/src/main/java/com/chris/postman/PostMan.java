package com.chris.postman;

import com.chris.postman.exception.ChannelException;

/**
 * 邮递员，传送信件的人
 * User: zhong.huang
 * Date: 12-10-16
 * Time: 下午12:27
 */
public interface PostMan<T extends Letter> {
    boolean send(T letter) throws ChannelException;
}
