package com.chris.crawler.zhihu.convert;

import com.chris.crawler.zhihu.util.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * 将之前抓取的问题qid导入到新的表中，以便排重使用
 * User: zhong.huang
 * Date: 13-5-15
 */
public class DumpOldQidToCrawler {
    static String ask_url = "jdbc:mysql://10.10.32.164:3313/DB_ASK?autoCommit=true&autoReconnect=true&useUnicode=true&tinyInt1isBit=false&zeroDateTimeBehavior=round&characterEncoding=UTF-8&yearIsDateType=false&zeroDateTimeBehavior=convertToNull";
    //"jdbc:mysql://192.168.10.11:3316/DB_ASK?autoCommit=true&autoReconnect=true&useUnicode=true&tinyInt1isBit=false&zeroDateTimeBehavior=round&characterEncoding=UTF-8&yearIsDateType=false&zeroDateTimeBehavior=convertToNull";
    static String ask_name = "invite";
    //"recruit";
    static String ask_pwd = "invite-dajie";
    //"uc1q2w3e4r";

    static String crawler_url = "jdbc:mysql://10.10.32.139:3317/DB_CRAWLER?autoCommit=true&autoReconnect=true&useUnicode=true&tinyInt1isBit=false&zeroDateTimeBehavior=round&characterEncoding=UTF-8&yearIsDateType=false&zeroDateTimeBehavior=convertToNull";
    //"jdbc:mysql://192.168.10.11:3316/DB_CRAWLER?autoCommit=true&autoReconnect=true&useUnicode=true&tinyInt1isBit=false&zeroDateTimeBehavior=round&characterEncoding=UTF-8&yearIsDateType=false&zeroDateTimeBehavior=convertToNull";
    static String crawler_name = "bootcamp";
    //"recruit";
    static String crawler_pwd = "uc1q2w3e4r";
    //"uc1q2w3e4r";

    static JDBCUtil util = new JDBCUtil();
    static Connection ask = util.getConnection(ask_url, ask_name, ask_pwd);
    static Connection crawler = util.getConnection(crawler_url, crawler_name, crawler_pwd);

    private LinkedList<String> getAsk(String minQid) {
        LinkedList<String> qids = new LinkedList<String>();
        ResultSet set = null;
        try {
            String sql = "select qid from tb_question_spider where qid > # AND create_date<'2013-01-01 00:00:00' order by qid asc limit 1000 ".replace("#", minQid);
            set = util.executeQuery(ask, sql);
            while (set.next()) {
                qids.add(set.getInt(1) + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            util.closeResultSet(set);
        }
        return qids;
    }

    private void insertCrawler(List<String> qids) {
        StringBuffer sql = new StringBuffer("INSERT INTO tb_crawler_zhihu_question (uid, qid, tags, content, detail, answer_count) VALUES ");
        for (String qid : qids) {
            sql.append("(0,#qid,'','','',0),".replace("#qid", qid));
        }
        String v = sql.toString();
        System.out.println(v);
        util.executeUpdate(crawler, v.substring(0, v.length() - 1));
    }

    public void run() {
        String minQid = "19891930";//"19891939";
        LinkedList<String> qids = getAsk(minQid);
        while (qids.size() > 0) {
            insertCrawler(qids);
            minQid = qids.getLast();
            qids = getAsk(minQid);   //20362407
        }
    }

    public static void main(String[] args) {
        DumpOldQidToCrawler dump = new DumpOldQidToCrawler();
        dump.run();
        util.closeConnection(ask);
        util.closeConnection(crawler);
    }
}
