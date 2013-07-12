package com.chris.common.concurrent;

/** 
* Writer倒不用怎么改动 
*/ 
public class Writer2 extends Thread { 
  
    private BufferInterruptibly buff; 
  
    public Writer2(BufferInterruptibly buff) { 
        this.buff = buff; 
    } 
  
    @Override 
    public void run() { 
        buff.write(); 
    } 
  
} 
