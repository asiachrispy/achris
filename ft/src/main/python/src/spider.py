#!/usr/bin/python2.6
#coding=utf-8

import os, sys,time
try:
    from cStringIO import StringIO
except ImportError:
    from StringIO import StringIO
#from htmlparser import CHtmlParser
import pycurl
#from libs import getConnection
import hashlib
import logging
#from models import CJob, CTask, CListTask
import time
import lxml.html as H
import urllib, urllib2
import re
import json
#from config import CConfig
import cookielib
#import chardet
import MySQLdb as mysql
import re
import socket
import random
from proxy import PROXY_IP
#from dajielibs.spider import urldownload,urldownloadcookie,urlsdownloadcookie
socket.setdefaulttimeout(10)

def timer(msecs):
    print 'Timer callback msecs:', msecs

class DJSpider():
    
    def __init__(self,config_file_path,POOL):
        
        self.max_multi = 20
        self.config_file_path = config_file_path
        self.POOL = POOL
    
    @staticmethod
    def download(urls):
        
        #max_wait_time = 5
        #init
        m = pycurl.CurlMulti()
        #m.setopt(pycurl.M_PIPELINING, 1)
        #m.setopt(pycurl.M_TIMERFUNCTION, timer)
        m.handles = []
        for url,headers in urls:
            c = pycurl.Curl()
            # save info in standard Python attributes
            c.url = url
            c.body = StringIO()
            c.http_code = -1
            # pycurl API calls
            c.setopt(c.URL, c.url)
            c.setopt(pycurl.MAXREDIRS, 5)#最大重定向次数
            c.setopt(pycurl.CONNECTTIMEOUT, 10)#链接超时
            c.setopt(pycurl.TIMEOUT, 20)#请求超时
            c.setopt(c.WRITEFUNCTION, c.body.write)
            #px = random.randint(1,len(PROXY_IP))
#            c.setopt(pycurl.PROXY,'http://' + PROXY_IP[px])
            #c.setopt(pycurl.PROXY,'http://122.72.12.52:8118') ##代理
            c.setopt(pycurl.HTTPHEADER, ["User-Agent:Mozilla/5.0+(compatible;+Googlebot/2.1;++http://www.google.com/bot.html)"])
            c.setopt(pycurl.USERAGENT,"Mozilla/5.0+(compatible;+Googlebot/2.1;++http://www.google.com/bot.html)")
            
            if headers:
                #headers = eval(headers)
                hh = []
                for k,v in headers.items():
                    hh.append( k+':'+v )
                if hh:
                    c.setopt(pycurl.HTTPHEADER, hh)
            m.handles.append(c)
            m.add_handle(c)
        
        #download        
        #begin = time.time()
        num_handles = len(m.handles)
        while num_handles:
            while 1:
                ret, num_handles = m.perform()
                if ret != pycurl.E_CALL_MULTI_PERFORM:
                    break
            # currently no more I/O is pending, could do something in the meantime
            # (display a progress bar, etc.)
            #print m.info_read()
             
            #end = time.time()
            #if (end-begin) > (len(urls)*max_wait_time):
            #   break
            #else:
            m.select(1.0)
        
        #uninit
        for c in m.handles:
            # save info in standard Python attributes
            c.http_code = c.getinfo(c.HTTP_CODE)
            # pycurl API calls
            m.remove_handle(c)
            c.close()
        m.close()
        
        #get datas
        datas = {}
        for c in m.handles:
            data = c.body.getvalue()
            
            datas[c.url] = data
            
            #c.http_code
            c.close()
        
        return datas
    
    @staticmethod
    def downloadCookie(urls,login_data,login_url,referer_url=''):
        
        # Set up objects
        dev_null = StringIO()
        slurpp = pycurl.Curl()
        
        # Request login page
        slurpp.setopt(pycurl.USERAGENT, "Mozilla/5.0+(compatible;+Googlebot/2.1;++http://www.google.com/bot.html)")
        slurpp.setopt(pycurl.FOLLOWLOCATION, 1)
        slurpp.setopt(pycurl.WRITEFUNCTION, dev_null.write)
        slurpp.setopt(pycurl.COOKIEFILE, '')
        #slurpp.setopt(pycurl.COOKIEJAR, '')
        #slurpp.setopt(pycurl.COOKIE, session)
        slurpp.setopt(pycurl.URL, login_url)
        if referer_url:
            slurpp.setopt(pycurl.REFERER,referer_url)
        else:
            pass
        slurpp.perform()
        
        # Log in to site
        if login_data:
            slurpp.setopt(pycurl.POSTFIELDS, login_data)
            slurpp.setopt(pycurl.POST, 1)
            slurpp.perform()
        
        datas = {}
        # Download relevant data
        for url,headers in urls:
            
            slurpp.body = StringIO()
            slurpp.setopt(pycurl.HTTPGET, 1)
            slurpp.setopt(pycurl.URL, url)
            slurpp.setopt(pycurl.WRITEFUNCTION, slurpp.body.write)
            
            if headers:
                #headers = eval(headers)
                hh = []
                for k,v in headers.items():
                    hh.append( k+':'+v )
                if hh:
                    slurpp.setopt(pycurl.HTTPHEADER, hh)
            else:
                pass
            
            slurpp.perform()
            
            datas[url] = slurpp.body.getvalue()
        
        # Clean up and close out
        dev_null.close()
        slurpp.close()
        
        return datas
    
    def __parseTasks(self,urls,hosts):
        
        tasks={}
        tasks_cookie={}
        for url,values in urls.items():
            
            hostid = values['hostid']
            extdata = values['extdata']
            host = hosts[hostid]
            loginurl = host['loginurl']
            if loginurl:
                tasks_cookie[url] = {'hostid':hostid,'extdata':extdata}
            else:
                tasks[url] = {'hostid':hostid,'extdata':extdata}
        
        return tasks,tasks_cookie
    
    def __newTask(self,url,table,debug=0):
        
        b_new = True
        if debug:
            pass
        else:
            conn = self.POOL.connection()
            cursor = conn.cursor(mysql.cursors.DictCursor)
            key = self.__getKey(url)
            sql = 'select * from ' + table + ' where guid=%s'
            parmars = (key,)
            n = cursor.execute(sql,parmars)
            if n:
                b_new = False
            else:
                if debug:
                    pass
                else:
                    now = time.strftime('%Y-%m-%d %X')
                    sql = 'insert into '+ table + ' (guid,url,date) values(%s,%s,%s)'
                    parmars = (key,url,now)
                    cursor.execute(sql,parmars)
                    conn.commit()
            
            cursor.close()
            conn.close()
        return b_new
    
    def __getKey(self,url):
        
        m1 = md5.new()
        m1.update(url)
        dest1 = m1.hexdigest()
        return dest1
    
    def getProxyListTaskResult(self,table,url):
        
        '''
            for proxy download
        '''
        
        model = CListTask(self.POOL,table)
        set = model.get(url)
        
        return set
    
    def setListTask(self,table,url):
        
        '''
            for proxy download ,when the urls is exist will pass,or not will set into the table
        '''
        
        model = CListTask(self.POOL,table)
        model.add(url)
    
    def __saveTasks(self,tasks,list,confs,table):
        
        model = CTask(self.POOL,table)
        
        for url in tasks:
            
            dpfl = confs['dpfl']
            if dpfl:
                pass
            else:
                dpfl = confs['dpflo']
            
            value = '{"hostid":"%d","extdata":"%s","dpfl":"%d","enterprise":"%d"}' % (list['host_id'],list['extdata'],dpfl,confs['enterprise'])
            model.set('url',url)
            model.set('info',value)
            model.save()
    
    def _getItemTasks(self,table_task_done,url,html,list,confs,debug=0):
        
        b_have_new_task = True#该页中是否含有新任务
        tasks = []
        
        cases = list['cases']
        if cases:
            cases = cases.split(',')
        else:
            cases = []
        xpath = list['xpath']
        filters = list['filter']
        if filters:
            filters = filters.split(',')
        else:
            filters = []
        format = list['format']
        if format:
            format = format.split(';')
        else:
            format = []
        
        tasks_temp = {}
        
        parser = CHtmlParser(html,url)
        
        tasks_temp = parser.getTasks(cases,xpath,filters)#text:url
        del(parser)
            
        for url_item in tasks_temp.values():
            
            if self.__newTask(url_item,table_task_done,debug):
                for item in format:
                    items = item.split('=>')
                    url_item = re.sub(items[0],items[1],url_item)
                tasks.append(url_item)
            else:
                pass
        
        #是否有新任务
        if len(tasks):
            pass
        else:
            b_have_new_task = False
        
        return tasks,b_have_new_task
    
    def _getItemTasks_reg(self,table_task_done,url,html,t_list,confs,debug=0):
        
        b_have_new_task = True#该页中是否含有新任务
        tasks = []
        tasks_temp = []
        
        format = t_list['format']
        if format:
            format = format.split(';')
        else:
            format = []
        
        rule = re.compile(t_list['reg_exp'])
        tasks_temp = rule.findall(html)
        tasks_temp = list(set(tasks_temp))
        
        for url_item in tasks_temp:
            if self.__newTask(url_item,table_task_done,debug):
                for item in format:
                    items = item.split('=>')
                    url_item = re.sub(items[0],items[1],url_item)
                tasks.append(url_item)
            else:
                pass

        #是否有新任务
        if len(tasks):
            pass
        else:
            b_have_new_task = False
        
        return tasks,b_have_new_task
    
    def getTask(self,hlists,debug=0):
        
        '''
            通过lists获取任务
        '''
        
        configer = CConfig(self.config_file_path)
        table_task = configer.getModuleTable('task')
        table_task_done = configer.getModuleTable('taskrecord')
        table_list_task = configer.getModuleTable('tasklist')
        del configer
        
        tasks_total = []
        
        for host_name,confs in hlists.items():
            if confs['status'] or debug:#有效或者测试
                
                lists = confs['lists']
                urls = []
                
                for tlist in lists:
                    #process url for replaces
                    url_base = tlist['url']
                    replaces = tlist['replaces']
                    if replaces:
                        replaces = replaces.strip(',').strip()
                    if replaces:
                        url_base = url_base % tuple(replaces.split(','))
                    else:
                        pass
                    #process url for page
                    #modified by guang
                    page = confs['page']
                    startpage = 1
                    endpage = 4
                    page_range = page.split(',')
                    
                    if len(page_range) == 2:
                        startpage = int(page_range[0])
                        endpage = startpage + int(page_range[1])
                    else:
                        endpage = int(page_range[0]) + 1
                    
                    urls += [url_base.replace('(*page*)',str(current_page)) for current_page in range(startpage, endpage)]
                    
                htmls = {}
                for tlist in lists:
                    #process url for replaces
                    url_base = tlist['url']
                    replaces = tlist['replaces']
                    if replaces:
                        replaces = replaces.strip(',').strip()
                    if replaces:
                        url_base = url_base % tuple(replaces.split(','))
                    else:
                        pass
                    #process url for page
                    #modified by guang
                    page = confs['page']
                    startpage = 1
                    endpage = 4
                    page_range = page.split(',')
                    
                    if len(page_range) == 2:
                        startpage = int(page_range[0])
                        endpage = startpage + int(page_range[1])
                    else:
                        endpage = int(page_range[0]) + 1
                        
                    for i in range(startpage, endpage):
                    #end mod   
                        url = url_base.replace('(*page*)',str(i))
                        
                        logging.info('process %s' % (url,))
                        
                        html = ''
                        if confs['use_proxy']:#使用代理
                            if debug:
                                headers = tlist['headers']
                                if headers:
                                    headers = eval(headers)
                                else:
                                    headers = {}
                                
                                #modify by guang 2010/6/12
                                if tlist.get('need_login',0):
                                    if htmls.has_key(url):
                                        pass
                                    else:
                                        htmls = urlsdownloadcookie(urls,confs['loginurl'],confs['logindata'],confs['refererurl'],headers)
                                    html = htmls.get(url,'')
                                else:
                                    if tlist.get('cookie_url',''):
                                        html = urldownloadcookie(url,tlist['cookie_url'],None,confs['refererurl'],headers)
                                    else:
                                        html = urldownload(url,headers)
                                #end mod
                            else:
                                list_task_result_proxy = self.getProxyListTaskResult(table_list_task,url)
                                if list_task_result_proxy:
                                    html = list_task_result_proxy['result']
                                else:#将该页加入list下载任务
                                    if debug:
                                        pass
                                    else:
                                        self.setListTask(table_list_task,url)
                        else:
                            
                            headers = tlist['headers']
                            if headers:
                                headers = eval(headers)
                            else:
                                headers = {}
                                
                            if debug:
                                #modify by guang 2010/6/12
                                if tlist.get('need_login',0):
                                    if htmls.has_key(url):
                                        pass
                                    else:
                                        htmls = urlsdownloadcookie(urls,confs['loginurl'],confs['logindata'],confs['refererurl'],headers)
                                    html = htmls.get(url,'')
                                else:
                                    if tlist.get('cookie_url',''):
                                        html = urldownloadcookie(url,tlist['cookie_url'],None,confs['refererurl'],headers)
                                    else:
                                        html = urldownload(url,headers)
                                #end mod                                
                            else:
                                if htmls.has_key(url):
                                    pass
                                else:
                                    urls_temp = [(x,headers) for x in urls]
                                    
                                    if tlist.get('need_login', 0):
                                        htmls = DJSpider.downloadCookie(urls_temp,confs['logindata'],confs['loginurl'],None)
                                    elif tlist.get('cookie_url', ''):
                                        htmls = DJSpider.downloadCookie(urls_temp,None,tlist['cookie_url'],None)
                                    else:
                                        htmls = DJSpider.download(urls_temp)
                                html = htmls.get(url,'')
                        
                        if html:
                            tasks = []
                            b_have_new_task = True
                            if confs['dpfl']:#从列表页下载职位
                                if url_base.find('(*page*)') != -1:
                                    tasks.append(url)
                                else:
                                    if i == 1:
                                        tasks = [url,]
                                    else:
                                        pass
                            else:
                                try:
                                    if tlist['use_reg_exp']:
                                        tasks,b_have_new_task = self._getItemTasks_reg(table_task_done,url,html,tlist,confs,debug)
                                    else:
                                        tasks,b_have_new_task = self._getItemTasks(table_task_done,url,html,tlist,confs,debug)
                                except Exception,e:
                                    logging.debug('parse %s err!(%s)' % (url,e))
                            
                            tasks_total += tasks
                            if not b_have_new_task:#如果当前列表页中没有新任务
                                break
                            else:
                                if debug:
                                    pass
                                else:
                                    self.__saveTasks(tasks,tlist,confs,table_task)
                        else:
                            pass
            else:
                pass
        return list(set(tasks_total))
