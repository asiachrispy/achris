package com.chris.postman;


import com.dajie.common.util.StringUtil;

import java.util.List;

/**
 * 我们把日志抽象成信件对象
 * 信件有不同的方式传输：
 * 1. 短信
 * 2. 电子邮件
 * User: zhong.huang
 * Date: 12-10-16
 * Time: 上午11:07
 */
public abstract class Letter {
    // 接收者的地址
    private List<String> recipients;

    // 文本内容
    private String content;

    // 发送信件的方式
    private Channel channel;

    public Letter(Channel channel, String content, List<String> recipients) {
        this.channel = channel;
        this.content = content;
        this.recipients = recipients;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean validate() {
        if (getRecipients() == null || getRecipients().isEmpty()
            || StringUtil.isEmpty(getContent())) {
            return false;
        }
        return true;
    }
}
