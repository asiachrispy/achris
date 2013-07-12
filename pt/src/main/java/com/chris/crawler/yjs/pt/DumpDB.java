package com.chris.crawler.yjs.pt;

import com.chris.crawler.yjs.pt.util.JDBCUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * User: zhong.huang
 * Date: 13-4-25
 */
public class DumpDB {

    public void run() {
        try {
            JDBCUtil dev = JDBCUtil.getInstance();

            dev.init("192.168.10.11", 3316, "DB_SEARCH", "recruit", "uc1q2w3e4r");
            String sql = "select * from tb_yjs_pt_crawler ";
            Statement dev_stmt = dev.getConnection().createStatement();
            ResultSet dev_rs = dev_stmt.executeQuery(sql);

            JDBCUtil pro = JDBCUtil.getInstance();
            pro.init("10.10.32.191", 3343, "DB_SEARCH", "invite", "invite-dajie");
            sql = "insert into tb_yjs_pt_crawler(md5_url,url,title,citycn,cityen,create_date) value(?,?,?,?,?,?) ";
            PreparedStatement pro_stmt = dev.getConnection().prepareStatement(sql);
            int size = 0;
            while (dev_rs.next()) {
                pro_stmt.setString(1, dev_rs.getString("md5_url"));
                pro_stmt.setString(2, dev_rs.getString("url"));
                pro_stmt.setString(3, dev_rs.getString("title"));
                pro_stmt.setString(4, dev_rs.getString("citycn"));
                pro_stmt.setString(5, dev_rs.getString("cityen"));
                pro_stmt.setDate(6, dev_rs.getDate("create_date"));
                pro_stmt.execute();
                size++;
            }

            System.out.println("total size " + size);
            pro_stmt.close();
            dev_stmt.close();
            dev_rs.close();
            dev.close();
            pro.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DumpDB dump = new DumpDB();
        dump.run();
    }
}
