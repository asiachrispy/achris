package com.chris.common.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 13-7-12
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class NIOClient {
    public static int SIZE = 100;

    public static void main(String[] args) throws IOException {
        ExecutorService exec = Executors.newFixedThreadPool(SIZE);
        for (int index = 0; index < SIZE; index++) {
            exec.execute(new Download(index));
        }
        exec.shutdown();
    }
}

class Download implements Runnable {
    private int index;
    private static int port = 1234;
    private static Charset charset = Charset.forName("UTF-8");

    public Download(int index) {
        this.index = index;
    }

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();
            SocketChannel client = SocketChannel.open();
            client.configureBlocking(false);
            Selector selector = Selector.open();
            client.register(selector, SelectionKey.OP_CONNECT);
            client.connect(new InetSocketAddress("localhost", port));
            ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
            int total = 0;
            FOR:
            for (; ;) {
                selector.select();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isConnectable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        if (channel.isConnectionPending())
                            channel.finishConnect();
                        channel.write(charset.newEncoder().encode(
                            CharBuffer.wrap("Hello from " + index)));
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        int count = -1;
                        CharsetDecoder decoder = charset.newDecoder();
                        while ((count = channel.read(buffer)) > 0) {
                            total += count;
                            buffer.flip();
                            System.out.print(decoder.decode(buffer).toString());
                            buffer.clear();
                        }
                        channel.close();
                        client.close();
                        break FOR;
                    }
                }
            }
            double last = (System.currentTimeMillis() - start) * 1.0 / 1000;
            System.out.println("Thread " + index + " downloaded " + total
                + "bytes in " + last + "s.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}