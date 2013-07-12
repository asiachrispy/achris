package com.chris.common.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;

public class NIOServer {
    public static int BLOCK = 4096;
    public static int PORT = 1234;
    private Selector selector;
    private String filename = "bigfile.dat"; // a big file
    private ByteBuffer clientBuffer = ByteBuffer.allocate(BLOCK);
    private CharsetDecoder decoder;


    public NIOServer() {
        decoder = Charset.forName("UTF-8").newDecoder();
    }

    public void listen(int port) {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            selector = Selector.open();
            server.socket().bind(new InetSocketAddress(port));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);
            for (; ;) {
                selector.select();//阻塞
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();//必须移除，否则重复执行
                    handleKey(key);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 处理事件
    private void handleKey(SelectionKey key) throws IOException {
        if (key.isAcceptable()) { // 接收请求
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel channel = server.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) { // 读信息
            SocketChannel channel = (SocketChannel) key.channel();
            clientBuffer.clear();
            int count = channel.read(clientBuffer);
            if (count > 0) {
                clientBuffer.flip();
                CharBuffer charBuffer = decoder.decode(clientBuffer);
                System.out.println("Client >>" + charBuffer.toString());
                SelectionKey wKey = channel.register(selector,
                    SelectionKey.OP_WRITE);
                wKey.attach(new HandleClient());
            } else {
                channel.close();
            }
        } else if (key.isWritable()) { // 写事件
            SocketChannel channel = (SocketChannel) key.channel();
            HandleClient handle = (HandleClient) key.attachment();
            ByteBuffer block = null;
            while ((block = handle.readBlock()) != null) {
                channel.write(block);
            }
            handle.close();
            channel.close();
        }
    }

    // 处理与客户端的交互
    public class HandleClient {
        private FileChannel channel;
        private ByteBuffer buffer;

        public HandleClient() throws IOException {
            this.channel = new FileInputStream(filename).getChannel();
            this.buffer = ByteBuffer.allocate(BLOCK);
        }

        public ByteBuffer readBlock() {
            try {
                buffer.clear();
                int count = channel.read(buffer);
                buffer.flip();
                if (count <= 0)
                    return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer;
        }

        public void close() {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            NIOServer server = new NIOServer();
            server.listen(PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
