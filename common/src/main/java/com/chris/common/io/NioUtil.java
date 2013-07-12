package com.chris.common.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NioUtil {

    // 将此通道的文件区域直接映射到内存中。
    public static ByteBuffer readFile(String filename) throws Exception {
        FileChannel fiChannel = new FileInputStream(filename).getChannel();
        MappedByteBuffer mBuf;
        mBuf = fiChannel.map(FileChannel.MapMode.READ_ONLY, 0, fiChannel.size());
        fiChannel.close();
        fiChannel = null;

        return mBuf;

    }

    //将缓冲区的字节序列写入通道，此通道链接到一个文件。
    public static void saveFile(ByteBuffer src, String filename)
        throws IOException {
        FileChannel foChannel = new FileOutputStream(filename).getChannel();
        foChannel.write(src);
        foChannel.close();
        foChannel = null;
    }

    //将一个通道中的区域文件写到另外一个文件通道，
    public static void saveFile(FileChannel fiChannel, String filename)
        throws IOException {
        MappedByteBuffer mBuf;
        mBuf = fiChannel.map(FileChannel.MapMode.READ_ONLY, 0, fiChannel.size());

        saveFile(mBuf, filename);//new add
        //FileChannel foChannel = new FileOutputStream(filename).getChannel();
        //foChannel.write(mBuf);

        fiChannel.close();
        //foChannel.close();

        fiChannel = null;
        //foChannel = null;
    }

    public static void main(String[] args) throws Exception {
        final String suffix = ".txt";
        final String filename = "/home/asiachris/workspace/yoyoDemo/src/hey";
        final String srcFile = filename + suffix;
        final String backupFile = filename + "-" + System.currentTimeMillis()
            + suffix;
        ByteBuffer byteBuffer = NioUtil.readFile(srcFile);
        NioUtil.saveFile(byteBuffer, backupFile);
        byteBuffer = null;

    }

}
