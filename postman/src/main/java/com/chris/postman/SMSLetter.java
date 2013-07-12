package com.chris.postman;

import java.util.List;

/**
 * 短信信件
 * User: zhong.huang
 * Date: 12-10-16
 * Time: 上午11:10
 */
public class SMSLetter extends Letter {

    public SMSLetter(String content, List<String> recipients) {
        super(Channel.SMS, content, recipients);
    }
}
