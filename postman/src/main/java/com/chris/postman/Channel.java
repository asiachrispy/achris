package com.chris.postman;

/**
 * 邮递方式
 * User: zhong.huang
 * Date: 12-10-16
 * Time: 下午1:41
 */
public enum Channel {
    NONE("none"),
    EMAIL("email"),
    SMS("sms");

    private String type;

    private Channel(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static Channel getBusinessByType(String type) {
        for (Channel consumer : Channel.values()) {
            if (type.equals(consumer.getType())) {
                return consumer;
            }
        }
        return Channel.NONE;
    }

}
