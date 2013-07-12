# -*- coding:utf-8 -*-

from pyquery import PyQuery as pq
from MysqlUtil import MysqlUtil
from Util import Util
from spider import DJSpider as spd
from AppParser import AppParser
from AppInfo import AppInfo
import ConfigParser
import urllib
import time
import sys

yjs_home = "http://www.yingjiesheng.com"
# 加载配置文件
config = ConfigParser.ConfigParser()
config.readfp(open('ini.conf'))
env = config.get('app', 'env')
max_repeat = config.get('app', 'max_repeat')
html_path = config.get('app', 'html_path')

# print config.get(env,'db_host'),int(config.get(env,'db_port')),config.get(env,'db_user'),config.get(env,'db_password')
# 连接mysqldb
mq = MysqlUtil(config.get(env, 'db_host'), int(config.get(env, 'db_port')), config.get(env, 'db_user'),
               config.get(env, 'db_password'))
mq.selectDb(config.get(env, 'db_name'))


class Main():
    # if save app_url success then return 1 or return 0
    def addAppUrl(self, app_url, recruit_type):
        md5sum = Util.urlMd5(app_url)
        count = mq.queryRow("select count(id) from tb_yjs_app_url where md5sum='" + md5sum + "'")
        if count and int(count[0]) > 0:
            print '>>>>>>>>>>>>>>>crawl has save the url %s' % (app_url)
            return 0
        else:
            # save yjs_url
            p_data = {'md5sum': md5sum, 'url': app_url, 'recruit_type': str(recruit_type)}
            mq.insert("tb_yjs_app_url", p_data)
            mq.commit()
            return 1

    # if save app_url success then return 1 or return 0
    def updateAppUrl(self, app_url, status):
        md5sum = Util.urlMd5(app_url)
        p_data = {'status': status}
        mq.update("tb_yjs_app_url", p_data, " where md5sum='" + md5sum + "'")
        mq.commit()

    def download(self, app_url):
        # download yjs_url

        tp_urls = []
        tp_urls.append([app_url, None])
        dic = spd.download(tp_urls)
        html = dic[app_url]

        #html = Util.doanloadPage(app_url,"gb2312")

        # save app html
        path = html_path + app_url.split('/')[3]
        #path = app_url.split('/')[3]
        #        Util.saveFile(path,html)
        return html

    def addAppProject(self, appInfo):
        p_data = {'name': appInfo.name, 'yjs_url': appInfo.yjs_url, 'apply_url': appInfo.apply_url,
                  'schedule_begin': appInfo.schedule_begin, 'refer_url': appInfo.refer_url,
                  'recruit_type': appInfo.recruit_type, 'major': appInfo.major, 'yjs_major': appInfo.yjs_major,
                  'degree': appInfo.degree, 'place': appInfo.place, 'content': appInfo.content,
                  'create_date': time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))}
        mq.insert("tb_yjs_project", p_data)
        mq.commit()

    def updateAppProject(self, appInfo):
        p_data = {'name': appInfo.name, 'apply_url': appInfo.apply_url,
                  'schedule_begin': appInfo.schedule_begin, 'refer_url': appInfo.refer_url,
                  'recruit_type': appInfo.recruit_type, 'major': appInfo.major, 'yjs_major': appInfo.yjs_major,
                  'degree': appInfo.degree, 'place': appInfo.place, 'content': appInfo.content}
        mq.update("tb_yjs_project", p_data, " where yjs_url='" + appInfo.yjs_url + "'")
        mq.commit()

    def parserApp(self, html, app_url):
        try:
            appParser = AppParser(html)
            appInfo = AppInfo()
            appInfo.yjs_url = app_url
            res = appParser.getInfo(appInfo)
            if res == -1 or res == -2: #res <= 0:
                return None ## -1 and -2
            return appInfo
        except Exception, data:
            print Exception, ":", data
            return None

    def start(self, type, start, max_page):
        total_count = 0
        for i in range(int(start), int(max_page) + 1):
            src = "http://www.yingjiesheng.com/commend-%s-%s.html/" % (type, i)
            print "src=", src

            if src.find("fulltime"):
                recruit_type = "1"
            else:
                recruit_type = "2"

            appUrls = AppParser.getAppUrls(src)
            ## repeat to get app urls
            if len(appUrls) == 0:
                if type == 'fulltime':
                    src = "http://www.yingjiesheng.com/commend_job/fulltime_%s.html" % (i)
                else:
                    src = "http://www.yingjiesheng.com/commend_job/parttime_%s.html" % (i)
                appUrls = AppParser.getAppUrls(src)

            ##　parser app urls
            for app_url in appUrls:
                print ">>>>>>>>>>>>>>>" + app_url
                total_count = total_count + 1
                # how to save to db
                if self.addAppUrl(app_url, recruit_type) > 0:
                    html = self.download(app_url)
                    if html != None and html != '':
                        appInfo = self.parserApp(html, app_url)
                        if appInfo:#todo and len(appInfo.content) > 0:
                            appInfo.recruit_type = recruit_type
                            self.addAppProject(appInfo)
                        else:
                            self.updateAppUrl(app_url, 1)
            time.sleep(0.1)
        print "total_count=", total_count

    '''查询db中解析失败的url，并重新下载和解析'''

    def startDB(self):
        total_count = 0
        srcs = mq.queryAll(
            "select url,recruit_type from tb_yjs_app_url where status=1 AND create_Date  >= LEFT(DATE_ADD(create_Date,INTERVAL -2 DAY),10) order by id  desc")
        for tup in srcs:
            app_url = tup['url']
            print ">>>>>>>>>>>>>>>", app_url
            total_count = total_count + 1
            html = self.download(app_url)
            if html != None and html != '':
                appInfo = self.parserApp(html, app_url)
                if appInfo and len(appInfo.content) > 0:
                    # parser success
                    appInfo.recruit_type = tup['recruit_type']
                    self.addAppProject(appInfo)
                    self.updateAppUrl(app_url, 0)
            time.sleep(0.1)

        print "total_count=", total_count
        return total_count

    def destroy(self):
        mq.close()

    def run(self):
        ##fulltime
        fu_start = config.get('app', 'fu_start')
        max_page = config.get('app', 'fu_max_page')
        print ">>>>>>>>fulltime start =", time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))
        self.start('fulltime', fu_start, max_page)
        print ">>>>>>>>fulltime end =", time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))

        ##parttime
        pt_start = config.get('app', 'pt_start')
        max_page = config.get('app', 'pt_max_page')
        print ">>>>>>>>parttime start =", time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))
        #self.start('parttime',pt_start,max_page)
        print ">>>>>>>>parttime end =", time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))

        ## db
        print ">>>>>>>>db start =", time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))
        for i in range(int(max_repeat)):
            if self.startDB() == 0:
                break
            else:
                print '>>>>>>>>start db again!'
        print ">>>>>>>>db end =", time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))


if __name__ == '__main__':
    pro = None
    if len(sys.argv) == 2:
        copy = sys.argv[1]
        if copy == 'cp':
            from cp2YJS import Copy2YJS

            pro = Copy2YJS()
        ### for main
    main = Main()
    while (True):
        main.run()
        if pro:
            print ">>>>>>>>Copy2YJS..."
            pro.cp2YJS()
        print ">>>>>>>>please wait 1 hour...."
        time.sleep(1 * 60 * 60) #sleep 1 hour
        print ">>>>>>>>do crawl again...."
    main.destroy()