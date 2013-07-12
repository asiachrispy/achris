package com.chris.postman;

import java.util.Properties;

/**
 * User: zhong.huang
 * Date: 12-10-19
 * Time: 下午4:38
 */
public final class EmailServer {
    private static final EmailServer INSTANCE = new EmailServer();

    // 发送邮件的服务器的IP和端口
    public static final String MAILSERVERHOST = "58.68.246.241";

    public static final String MAILSERVERPORT = "25";

    // 邮件发送者的地址
    public static final String FROMADDRESS = "noreply@dajie-inc.com";

    // 登陆邮件发送服务器的用户名和密码
    public static final String USERNAME = "";
    public static final String PASSWORD = "";


    private String mailServerHost;
    private String mailServerPort;
    private String fromAddress;
    private String userName;
    private String password;

    // 是否需要身份验证
    private boolean validate;

    public static EmailServer getINSTANCE() {
        return INSTANCE;
    }

    private EmailServer() {
        this.mailServerHost = MAILSERVERHOST;
        this.mailServerPort = MAILSERVERPORT;
        this.fromAddress = FROMADDRESS;
        this.userName = USERNAME;
        this.password = PASSWORD;
        this.validate = false;
    }

    /**
     * 获得邮件会话属性
     */
    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", getMailServerHost());
        p.put("mail.smtp.port", getMailServerPort());
        p.put("mail.smtp.auth", validate ? "true" : "false");
        return p;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
