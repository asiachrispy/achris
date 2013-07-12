package com.chris.common.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;


/** 
 */
public class TransferFile {
    
    private String srcFile;
    
    List<String> files = new ArrayList<String>();
    ByteBuffer byteBuffer;
   
    public TransferFile(String srcFile)
    {
    	this.srcFile = srcFile;
        createFileNames();
    }
    
    private void createFileNames()
    {
        for (int i = 11; i < 20; i++)
        {
            files.add(i + "");
        }
    }  
    
    public List<String> getFiles()
    {
        return this.files;
    }

    public String getSrcFile() {
        return srcFile;
    }

    public void setSrcFile(String srcFile) {
        this.srcFile = srcFile;
    }
    
    public void saveFiles() throws FileNotFoundException
    {
        String tempFile;
        FileChannel fiChannel = null;
        for (int i = 0; i < this.files.size(); i++) 
        {
            tempFile = "/home/asiachris/workspace/yoyoDemo/src/test-files/" + (String)files.get(i) + ".txt";
            System.out.println("begin create thread for " + tempFile);
            
             fiChannel = new FileInputStream(this.srcFile).getChannel();
            
            WriteFileThread writeFileThread = new WriteFileThread("writeFile ["+i+"]", fiChannel, tempFile);
            writeFileThread.start();   
        }
        
    }

    public static void main(String[] args) throws Exception 
    {
        final String srcFile = "/home/asiachris/workspace/yoyoDemo/src/test-files/test.txt";
        TransferFile transferFile = new TransferFile(srcFile);
        transferFile.saveFiles();        
    }
}
