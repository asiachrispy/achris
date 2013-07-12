package com.chris.postman;

import com.dajie.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * 电子邮件邮递员
 * User: zhong.huang
 * Date: 12-10-16
 * Time: 下午12:26
 */
public class EmailPostMan extends AbstractPostMan<EmailLetter> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailPostMan.class);

    private EmailServer emailServer = EmailServer.getINSTANCE();

    public EmailPostMan() {
        super(Channel.EMAIL);
    }

    @Override
    protected boolean doSend(EmailLetter letter) {
        Session sendMailSession = null;
        Properties pro = emailServer.getProperties();

        if (emailServer.isValidate()) {
            EmailAuthenticator authenticator = new EmailAuthenticator(emailServer.getMailServerHost(), emailServer.getPassword());
            sendMailSession = Session.getDefaultInstance(pro, authenticator);
        } else {
            sendMailSession = Session.getInstance(pro);
        }

        LOGGER.info("EmailPostMan start to send letter");
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);

            // 创建邮件发送者地址
            Address from = new InternetAddress(emailServer.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);

            // 创建邮件的接收者地址，并设置到邮件消息中
            Address[] to = letter.getRealRecipients();
            mailMessage.setRecipients(Message.RecipientType.TO, to);

            // 设置邮件消息的主题
            mailMessage.setSubject(letter.getSubject());

            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());

            // 设置邮件消息的主要内容
            mailMessage.setText(letter.getContent());

            // 处理附件
            if (StringUtil.isNotEmpty(letter.getAttachment())) {
                //后面的BodyPart将加入到此处创建的Multipart中
                Multipart mp = new MimeMultipart();
                MimeBodyPart mbp = new MimeBodyPart();
                //得到数据源
                FileDataSource fds = new FileDataSource(letter.getAttachment());
                //得到附件本身并至入BodyPart
                mbp.setDataHandler(new DataHandler(fds));
                //得到文件名同样至入BodyPart
                mbp.setFileName(fds.getName());
                mp.addBodyPart(mbp);
                mailMessage.setContent(mp);
            }

            // 发送邮件
            Transport.send(mailMessage);

            LOGGER.info("EmailPostMan send mail letter success!");
            return true;
        } catch (MessagingException ex) {
            LOGGER.error("send mail exception ", ex.getMessage());
            ex.printStackTrace();
        }

        return false;
    }
}
