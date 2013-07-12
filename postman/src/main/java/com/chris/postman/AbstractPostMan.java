package com.chris.postman;

import com.chris.postman.exception.ChannelException;

/**
 * User:tao.li
 * Date: 12-10-19
 * Time: 上午10:41
 */
abstract class AbstractPostMan<T extends Letter> implements PostMan<T> {
    private final Channel acceptChannel;

    protected abstract boolean doSend(T letter);

    public AbstractPostMan(Channel acceptChannel) {
        this.acceptChannel = acceptChannel;
    }

    @Override
    public final boolean send(T letter) throws ChannelException {
        if (letter == null ) {
           return false;
        }

        if (this.acceptChannel == letter.getChannel()) {
            if (letter.validate()) {
                return doSend(letter);
            }
            return false;
        }

        throw new ChannelException("not accept channel " + letter.getChannel());
    }
}
