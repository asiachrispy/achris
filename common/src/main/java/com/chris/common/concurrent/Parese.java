package com.chris.common.concurrent;


import com.dajie.common.util.DateUtil;

import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

public class Parese {

    public static final String DAY_M = DateUtil.formatDate(new Date(), "yyyy-MM-dd-HH-mm");
    public static final String PREFIX = "curl \"http://www.dajie.com/sms/internal/mt?&type=o&mobile=";
    public static final String MOB_CLOUMN = "电话";
    public static final String MSG_CLOUMN = "短信内容";

    public static void main(String[] args)
    {
       //String[] argss = {"E:\\dajie\\infra\\smessage\\webapp\\src\\main\\java\\com\\dajie\\smessage\\operation\\tinfo.txt"};
       try {
           parese(args);
       } catch (Exception e) {
           System.out.println("error:" + e.getMessage());
       }
    }

    private static boolean parese(String[] args) throws IOException
    {
        String[] pathArr = new String[2];
        if (args != null) {
            if (args.length == 1) {
                pathArr[0] = args[0];
                pathArr[1] = args[0] + ".finished.txt";
            } else if (args.length == 2){
                pathArr = args;
            } else {
                System.out.println("Usage: ./parese.sh srcTxt [desTxt]");
                return false;
            }

            File srcf = new File(pathArr[0]);
            File desf = new File(pathArr[1]);

            // 原始文件
            if (!srcf.exists()) {
                System.out.println(args[0] + " src file not exists.");
                return false;
            }

            // 目标文件
            if (desf.exists()) {
                desf.renameTo(new File(desf.getAbsolutePath() + "." + DAY_M + ".txt"));
            }
            desf.createNewFile();

            geneFile(pathArr);

        } else {
            System.out.println("Usage: ./parese.sh srcTxt [desTxt]");
            return false;
        }
        return true;
    }
    /**
     * @param args  args[0]:mobile; args[1]:msgContent
     */
    private static void geneFile(String[] args)
    {
        try {
            FileReader fr = new FileReader(args[0]);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(args[1]);
            BufferedWriter bw = new BufferedWriter(fw);
            String myreadline;
            int size = -1;

            String logFileName = DAY_M + ".log";
            String[] items = new String[10];
            int mob_index = 0;
            int msg_index = 0;

            System.out.println("==============start");
            while (br.ready()) {
                size++;
                // 读取第一行数据 判断 “电话” 和“短信内容”这2个字段的索引位置
                if (size == 0) {
                    myreadline = br.readLine();
                    items = myreadline.split("\t");

                    for (int i = 0; i < items.length ; i++) {
                        if (MOB_CLOUMN.equals(items[i])) {
                            mob_index = i;
                        } else if (MSG_CLOUMN.equals(items[i])) {
                            msg_index = i;
                        }
                    }
                    continue;
                }

                System.out.println("num:" + size);
                // 读取一行
                myreadline = br.readLine();
                items = myreadline.split("\t")  ;

                // 写入文件
                System.out.println("msg:" + items[msg_index]);
                bw.write( PREFIX + items[mob_index] + "&msg=" + URLEncoder.encode(items[msg_index], "GB2312") + "\" >> " + logFileName);
                bw.newLine();
                bw.write("echo \"---" + size + "---" + items[mob_index] + "\" >> " + logFileName);
                bw.newLine();
            }
            // exit
            bw.write("exit 0;");
            bw.newLine();
            // 刷新该流的缓冲
            bw.flush();
            bw.close();
            br.close();
            fw.close();
            fr.close();
            System.out.println("==============end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
