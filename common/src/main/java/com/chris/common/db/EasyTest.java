package com.chris.common.db;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

public class EasyTest {
    public static void main(String[] args) {
        // Mongo m = new Mongo();
        // Mongo m = new Mongo( "localhost" );
        try {
            Mongo m = new Mongo("localhost", 27017);
            for (String s : m.getDatabaseNames()) {
                System.out.println("dbs : " + s);
            }

            DB db = m.getDB("py");
            // boolean auth = db.authenticate(myUserName, myPassword);

            // for all collections
            Set<String> colls = db.getCollectionNames();
            for (String s : colls) {
                System.out.println("collections : " + s);
            }

            // for one collection
            DBCollection coll = db.getCollection("author");
            DBObject obj = coll.findOne();
            System.out.println(obj);

            // find a DBObject
            BasicDBObject doc = new BasicDBObject();
            doc.put("name", "huang.zhong");
            obj = coll.findOne(doc);
            System.out.println(obj);

            // for more info
            doc = new BasicDBObject();
            doc.put("name", "MongoDB");
            doc.put("type", "database");
            doc.put("age", "6");
            doc.put("count", 1);

            BasicDBObject info = new BasicDBObject();
            info.put("x", 203);
            info.put("y", 102);

            doc.put("info", info);
            coll.insert(doc);

            // for all
            DBCursor cur = coll.find();
            while (cur.hasNext()) {
                System.out.println("find all:" + cur.next());
            }

            // for age
            BasicDBObject query = new BasicDBObject();
            // query.put("age", new BasicDBObject("$gt", 20).append("$lte", 36));//20
            // < age <= 36

            query.put("age", new BasicDBObject("$lt", 26));

            cur = coll.find(query);
            System.out.println("count : " + cur.count());
            while (cur.hasNext()) {
                System.out.println("---" + cur.next());
            }

            // for index
            coll.createIndex(new BasicDBObject("name", "MongoDB"));
            List<DBObject> list = coll.getIndexInfo();

            for (DBObject o : list) {
                System.out.println(o);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
