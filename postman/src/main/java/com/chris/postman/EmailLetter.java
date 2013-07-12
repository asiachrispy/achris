package com.chris.postman;

import com.dajie.common.util.StringUtil;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.List;

/**
 * 电子邮件信件
 * User: zhong.huang
 * Date: 12-10-16
 * Time: 上午11:09
 */
public class EmailLetter extends Letter {
    // 邮件主题
    private String subject;
    // 附件路径
    private String attachment;

    public EmailLetter(String subject, String content, List<String> recipients) {
        super(Channel.EMAIL, content, recipients);
        this.subject = subject;
    }

    public EmailLetter(String subject, String content, List<String> recipients, String attachment) {
        super(Channel.EMAIL, content, recipients);
        this.subject = subject;
        this.attachment = attachment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Address[] getRealRecipients() throws AddressException {
        List<String> recipients = getRecipients();
        if (recipients != null && recipients.size() > 0) {
            Address[] addresses = new InternetAddress[recipients.size()];

            int i = 0;
            for (String add : recipients) {
                addresses[i++] = new InternetAddress(add);
            }
            return addresses;
        }
        return null;
    }

    @Override
    public boolean validate() {
        if (StringUtil.isEmpty(getSubject()) || !super.validate()) {
            return false;
        }
        return true;
    }
}
