package com.chris.common.db;

import com.mongodb.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: zhong.huang
 * Date: 11-10-31
 * Time: 下午7:00
 * To change this template use File | Settings | File Templates.
 */
public class TestMogoDB {
    private final static Log log = LogFactory.getLog(TestMogoDB.class);

    private static final String MONGO_DB_IP = "192.168.1.91";
    private static final int MONGO_DB_PORT = 27017;
    private static final String MONGO_DB_NAME = "test";

    private static DB getConn() {
        Mongo m = null;
        DB db = null;
        try {
            m = new Mongo(MONGO_DB_IP, MONGO_DB_PORT);
            db = m.getDB(MONGO_DB_NAME);
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return db;

    }

    private void getCollections() {
        Set<String> colls = TestMogoDB.getConn().getCollectionNames();
        for (String s : colls) {
            log.info(s);
        }
    }

    private DBCollection getCollection(String dbName) {
        DBCollection dbCollection = TestMogoDB.getConn().getCollection(dbName);
        log.info("Name:" + dbCollection.getName());
        log.info("Full Name:" + dbCollection.getFullName());

        return dbCollection;
    }

    private void find(DBCollection col) {
        DBCursor cur = col.find();
        DBObject obj = null;
        while (cur.hasNext()) {
            obj = cur.next();
            log.info("obj : " + obj);
        }
    }

    private void insert(DBCollection col)
    {
        BasicDBObject doc = new BasicDBObject();
        doc.put("name", "chris");
        doc.put("city", "china");
        doc.put("time", new Date());
        col.insert(doc);
    }

    private void findOne(DBCollection col) {
        BasicDBObject cond = new BasicDBObject();
        cond.put("name", "sara");
        DBObject obj = col.findOne(cond);
        log.info("obj : " + obj);
    }


    public static void main(String[] args) {
        TestMogoDB t = new TestMogoDB();
        t.getCollections();

        DBCollection col = t.getCollection("foo");

        t.insert(col);
        t.find(col);
        t.findOne(col);
    }

}
