package com.chris.postman;

import com.chris.postman.exception.ChannelException;

/**
 * User:tao.li
 * Date: 12-10-19
 * Time: 上午10:35
 */
public final class SenderManager implements PostMan {
    private static final SenderManager INSTANCE = new SenderManager();
    private final PostMan smsPostMan = new SMSPostMan();
    private final PostMan emailPostMan = new EmailPostMan();

    private SenderManager() {
    }

    public static SenderManager getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean send(Letter letter) throws ChannelException {
        if (letter == null) {
            return false;
        }

        switch (letter.getChannel()) {
            case SMS:
                return this.smsPostMan.send(letter);
            case EMAIL:
                return this.emailPostMan.send(letter);
        }
        throw new ChannelException("channel " + letter.getChannel() + " not fount");
    }
}
