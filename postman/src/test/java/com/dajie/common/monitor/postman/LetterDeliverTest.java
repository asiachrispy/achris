package com.dajie.common.monitor.postman;

import com.chris.postman.EmailLetter;
import com.chris.postman.SMSLetter;
import com.chris.postman.SenderManager;
import com.chris.postman.exception.ChannelException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 12-10-16
 * Time: 下午5:17
 */
public class LetterDeliverTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LetterDeliverTest.class);

    @Test
    public void testSendSMS() throws ChannelException {
        List list = new ArrayList<String>();
        list.add("18600419135");
        SMSLetter letter = new SMSLetter("content:chris test", list);
        SenderManager.getInstance().send(letter);
    }

    @Test
    public void testSendEmail() throws ChannelException {
        List list = new ArrayList<String>();
        list.add("zhong.huang@dajie-inc.com");
        String content = "<html>  \n" +
            "  \n" +
            "        <head>  \n" +
            "  \n" +
            "        </head>  \n" +
            "        <body>  \n" +
            "            <div id=\"main\" style=\"width:966px; height:330px; text-align:left;  \n" +
            "                margin:3px 26px; background-color:#F5F5F5; border:1px  \n" +
            "                #E1E1E1 solid;\">  \n" +
            "                <b>  \n" +
            "                    userName:{0}  \n" +
            "                    <br />  \n" +
            "                    userPass:{1}  \n" +
            "                    <br />  \n" +
            "                    <a href=\"http://www.google.com\">去Google看看</a>  \n" +
            "                </b>  \n" +
            "                <span style=\"color: #ff0000;\"><img src=\"cid:{2}\" /></span>  \n" +
            "            </div>  \n" +
            "        </body>  \n" +
            "    </html>  ";//"content:chris is processing monitor test!"
        EmailLetter letter = new EmailLetter("title:monitor test", content, list, "");
        SenderManager.getInstance().send(letter);
    }

    @Test
    public void testT() {
        System.out.println("chris");
        System.console().writer();
        System.out.println(System.console().readLine());
    }
}
