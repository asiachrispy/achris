package com.chris.common.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * User: zhong.huang
 * Date: 13-6-18
 */
public class TCPBIOServer {
    public static void main(String[] args) throws Exception {
        int port = 9527;
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Server listen on port: " + port);
        Socket socket = ss.accept();
        BufferedReader in = new BufferedReader(new
            InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {
            String line = in.readLine();
            if (line == null) {
                Thread.sleep(100);
                continue;
            }
            if ("quit".equalsIgnoreCase(line.trim())) {
                in.close();
                out.close();
                ss.close();
                System.out.println("Server has been shutdown!");
                System.exit(0);
            } else {
                System.out.println("Message from client: " + line);
                out.println("Server response：" + line);
                Thread.sleep(100);
            }
        }
    }
}
