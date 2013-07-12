# -*- coding:utf-8 -*-
__author__ = 'zhong.huang'
from MysqlUtil import MysqlUtil
from AppParser import AppParser
import ConfigParser
import time
import sys

class Copy2YJS():
    config = ConfigParser.ConfigParser()
    config.readfp(open('ini.conf'))
    src_env = "test"
    dest_env = "product"
    #startID = config.get('app','start_ID') #拷贝数据的起始位置，需要记录每次的其实点

    ## create src mysql connect
    src_mq = MysqlUtil(config.get(src_env,'db_host'),int(config.get(src_env,'db_port')),config.get(src_env,'db_user'),config.get(src_env,'db_password'))
    src_mq.selectDb(config.get(src_env,'db_name'))
    ## create destination mysql connect
    dest_mq = MysqlUtil(config.get(dest_env,'db_host'),int(config.get(dest_env,'db_port')),config.get(dest_env,'db_user'),config.get(dest_env,'db_password'))
    dest_mq.selectDb(config.get(dest_env,'db_name'))

    def updateContent(self):
        appParser = AppParser('<html></html>')
        apps = self.src_mq.queryAll("select id,content from tb_yjs_project where id=134")
        for row in apps:
            cont = appParser.replaceP(row['content'])
            self.src_mq.queryAll("update tb_yjs_project set content=%s ,update_date=update_date where id = %d" % (cont, int(row['id'])))
        print "update row ",len(apps)

    def deleteDumpData(self):
     self.dest_mq.query("DELETE a FROM tb_yjs_project AS a, tb_yjs_project AS b WHERE a.yjs_url=b.yjs_url AND a.id<b.id ")
     self.dest_mq.commit()

    def getMaxIDByTab(self,tab):
        return self.dest_mq.queryRow("SELECT MAX(id) as size FROM " + tab)[0]

    def getDataByTab(self,tab,start_ID,start):
        return self.src_mq.queryAll("select * from " + tab + " where id >= " + str(start_ID) + " order by id  LIMIT "+ str(start) + ",1000 ")

    def addDataProject(self,tab,p_data):
        self.dest_mq.insert(tab,p_data)
        self.dest_mq.commit()

    def cp2YJS(self):
        tab = "tb_yjs_project"
        ## max_id  + 1
        max = self.getMaxIDByTab(tab)
        if max == None:
            startID = 1
        else :
            startID = max + 1
        print "start to copy host=",self.config.get(self.src_env,'db_host')," to ",self.config.get(self.dest_env,'db_host')," and start id is",startID
        total = 0
        start = 0

        print ">"*5,"start tb_yjs_project time ",time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))
        p_data = self.getDataByTab(tab, startID, start)
        while(len(p_data) > 0):
            total = total + len(p_data)
            for row in p_data:
                self.addDataProject(tab,row)
            start = start + 1000
            p_data = self.getDataByTab(tab, startID,  start)
        print ">"*5,"total =",total,"  end time ",time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))
        #########
        total = 0
        start = 0
        tab = "tb_yjs_app_url"
        max = self.getMaxIDByTab(tab)
        if max == None:
            startID = 1
        else :
            startID = max + 1
        print ">"*5,"start tb_yjs_app_url time ",time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))
        p_data = self.getDataByTab(tab, startID, start)
        while(len(p_data) > 0):
            total = total + len(p_data)
            for row in p_data:
                self.addDataProject(tab,row)
            start = start + 1000
            p_data = self.getDataByTab(tab, startID, start)
        print ">"*5,"total= ",total,"  end time ",time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))

    def close(self):
        self.dest_mq.close()
        self.src_mq.close()

if __name__ == "__main__":
    print "usage python2.6 cp2YJS.py [bg] "
    pro = Copy2YJS()
    if len(sys.argv) == 2:
        bg = sys.argv[1]
        if bg == 'bg':
            while(True):
                pro.cp2YJS()
                print ">>>>>>>>please wait 1 hour...."
                time.sleep(1*60*60)
    else :
        pro.cp2YJS()