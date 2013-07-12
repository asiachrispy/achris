# -*- coding:utf-8 -*-
import sys
import MySQLdb

class MysqlUtil:
    def __init__(self,host,port,user,password,charset="utf8"):
        self.host=host
        self.port=port
        self.user=user
        self.password=password
        self.charset=charset
        try:
            self.conn=MySQLdb.connect(host=self.host,user=self.user,passwd=self.password,port=self.port)
            self.conn.ping(True)
            self.conn.set_character_set(self.charset)
            self.cur = self.conn.cursor()
            
        except MySQLdb.Error as e:
            print("Mysql Error %d: %s" % (e.args[0], e.args[1]))

    def selectDb(self,db):
        try:
            self.conn.select_db(db)
            self.cur.execute("SET NAMES utf8")
            self.cur.execute("SET CHARACTER_SET_CLIENT=utf8")
            self.cur.execute("SET CHARACTER_SET_RESULTS=utf8")
            self.conn.commit()
        except MySQLdb.Error as e:
            print("Mysql Error %d: %s" % (e.args[0], e.args[1]))

    def query(self,sql):
        try:
            n=self.cur.execute(sql)
            return n
        except MySQLdb.Error as e:
            print("Mysql Error:%s \n SQL:%s" %(e,sql))

    def queryRow(self,sql):
        self.query(sql)
        result = self.cur.fetchone()
        return result

    def queryAll(self,sql):
        self.query(sql)
        result=self.cur.fetchall()
        desc =self.cur.description
        d = []
        for inv in result:
            _d = {}
            for i in range(0,len(inv)):
                if str(inv[i]) == "None":
                    continue
                _d[desc[i][0]] = str(inv[i])
            d.append(_d)
        return d

    def insert(self,p_table_name,p_data):
        try:
            keys = ','.join(p_data.keys())
            for key in p_data.keys():
                if isinstance(p_data[key],unicode):
                    p_data[key] = unicode(p_data[key]).encode('utf-8')
                #else:
                p_data[key] = '\"' + p_data[key].replace('"','\\"').replace("'","\\'") + '\"'
            values = ','.join(p_data.values())
            real_sql = "INSERT INTO " + p_table_name + " (%s) VALUES(%s)" % (keys,values)
            return self.query(real_sql)
        except MySQLdb.Error as e:
            real_sql = "INSERT INTO " + p_table_name + " (%s) VALUES(%s)" % (keys,values.encode('utf-8'))
            print("Mysql Error %d: %s" % (e.args[0], e.args[1]))
            return self.query(real_sql)

    '''update table set key='''''
    def update(self,p_table_name,p_data,where):
        try:
            values = ''
            for key in p_data.keys():
                if isinstance(p_data[key],unicode):
                    p_data[key] = unicode(p_data[key]).encode('utf-8')
                if isinstance(p_data[key],str):
                    p_data[key] = '\"' + p_data[key].replace('"','\\"').replace("'","\\'") + '\"'
                values = values + key + "=" + str(p_data[key]) + ","
            real_sql = "update " + p_table_name + " set " + values[0:len(values) - 1] + where
            return self.query(real_sql)
        except MySQLdb.Error as e:
            print("Mysql Error %d: %s" % (e.args[0], e.args[1]))

    def getLastInsertId(self):
        return self.cur.lastrowid

    def rowcount(self):
        return self.cur.rowcount

    def commit(self):
        self.conn.commit()

    def close(self):
        self.cur.close()
        self.conn.close()

if __name__ == '__main__' :
    mq = MysqlUtil("192.168.10.11",3316,"recruit","uc1q2w3e4r")
    mq.selectDb('DB_CLUB_TEST')#'DB_WORKSHOP')
    p_data = {'url':'dddaa'}
    uid="a"
    mq.update("tb_yjs_app_url",p_data," where md5sum=\"a166be60a3efd7858bd2ce766c4f5a30\""  )
