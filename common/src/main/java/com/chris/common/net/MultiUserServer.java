package com.chris.common.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiUserServer extends Thread {

    private static final Logger SocketLog = LoggerFactory.getLogger(MultiUserServer.class);
    private Socket client;

    public MultiUserServer(Socket c) {
        this.client = c;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream());
            // Mutil User but can't parallel
            while (true) {
                String str = in.readLine();
                System.out.println(str);
                System.out.println("receive message: " + str);
                out.println("has receive....");
                out.flush();
                if (str.equals("end"))
                    break;
            }
            client.close();
        } catch (IOException ex) {
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 1122;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        ServerSocket server = new ServerSocket(port);
        System.out.println("the server socket application is created!");
        while (true) {
            // transfer location change Single User or Multi User
            MultiUserServer mu = new MultiUserServer(server.accept());
            mu.start();
        }
    }
}