package com.chris.common.io;

import java.nio.channels.FileChannel;

/** */

/**
 */
public class WriteFileThread extends Thread {

    private FileChannel fiChannel;
    private String fileName;

    public WriteFileThread(String name) {
        super(name);
    }

    public WriteFileThread(String name, FileChannel fiChannel, String fileName) {
        this(name);
        this.fiChannel = fiChannel;
        this.fileName = fileName;
    }

    public void setFiChannel(FileChannel fiChannel) {
        this.fiChannel = fiChannel;
    }

    public FileChannel getFiChannel() {
        return fiChannel;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void run() {
        System.out.println("in Thread: " + this.getName());
        try {
            NioUtil.saveFile(this.fiChannel, this.fileName);
        } catch (Exception e) {
            System.out.println("The file is not founded: " + fileName);
            e.printStackTrace();
        }
        System.out.println("end Thread: " + this.getName());
    }
}