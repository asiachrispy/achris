package com.chris.crawler.yjs.pt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * User: zhong.huang
 * Date: 12-12-14
 * Time: 上午11:07
 */
public class JDBCUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JDBCUtil.class);
    private String url = null;
    private Connection connection = null;

    public static final String MOUSE = "@";
    private static final JDBCUtil jdbc = new JDBCUtil();

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static JDBCUtil getInstance() {
        return jdbc;
    }

    public void init(String host, int port, String dbName, String user, String password) {
        try {
            url = "jdbc:mysql://" + host + ":" + port + "/" + dbName + "?autoCommit=true&autoReconnect=true&useUnicode=true&tinyInt1isBit=false&zeroDateTimeBehavior=round&characterEncoding=UTF-8&yearIsDateType=false&zeroDateTimeBehavior=convertToNull";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void executeUpdate(String sql) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
//            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;

    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JDBCUtil jdbc = JDBCUtil.getInstance();
        jdbc.init("10.10.32.237", 3301, "DB_USER", "dj_t", "da@jie.123");
        jdbc.executeQuery("select * from tb_user_email where uid < 10;");
        jdbc.close();
    }
}
