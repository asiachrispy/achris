package com.chris.postman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信邮递员
 * User: zhong.huang
 * Date: 12-10-16
 * Time: 下午3:07
 */
public class SMSPostMan extends AbstractPostMan<SMSLetter> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SMSPostMan.class);
    public static final String MONITOR_TYPE = "e";

    public SMSPostMan() {
        super(Channel.SMS);
    }

    @Override
    protected boolean doSend(SMSLetter letter) {
        for (String recipient : letter.getRecipients()) {
            LOGGER.info("SMSPostMan send sms content [{}] to [{}] .", new Object[]{letter.getContent(), recipient});
            //TODO LOGGER.info("SMSPostMan send sms {} .", SMSUtil.sendSMS(MONITOR_TYPE, recipient, letter.getContent()));
            LOGGER.info("SMSPostMan send sms {} .", "SUCCESS");
        }
        return true;
    }
}
