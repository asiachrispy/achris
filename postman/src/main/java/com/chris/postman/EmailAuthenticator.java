package com.chris.postman;

/**
 * User: zhong.huang
 * Date: 12-10-16
 * Time: 上午11:35
 */

import javax.mail.*;

public class EmailAuthenticator extends Authenticator {
    String userName = null;
    String password = null;

    public EmailAuthenticator() {
    }

    public EmailAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}