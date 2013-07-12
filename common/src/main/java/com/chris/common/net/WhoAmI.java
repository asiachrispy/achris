package com.chris.common.net;

/**
 * User: zhong.huang
 * Date: 12-8-20
 * Time: 下午12:09
 */

import java.net.*;

public class WhoAmI {
    public static void main(String[] args) throws Exception {
        args = new String[]{"ZHONG-HUANG"};
        if (args.length != 1) {
            System.err.println("Usage: WhoAmI MachineName");
            System.exit(1);
        }
        InetAddress a = InetAddress.getByName(args[0]);
        System.out.println(a);
        System.out.println(InetAddress.getLocalHost());
        System.out.println(InetAddress.getByName("ZHONG-HUANG"));
        System.out.println(InetAddress.getByName(null));
        System.out.println(InetAddress.getByName("127.0.0.1"));

    }
} ///:~

