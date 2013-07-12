# -*- coding:utf-8 -*-
import urllib
import ConfigParser
import logging
import os
from pyquery import PyQuery as pq
from AppInfo import AppInfo
from MAJOR_DICT import MAJOR_DICT
from DAJIE_MAJOR_DICT import DAJIE_MAJOR_DICT
from CITY_DICT import CITY_DICT
from MysqlUtil import MysqlUtil
from Util import Util

# 解析app
class AppParser():
    config = ConfigParser.ConfigParser()
    config.readfp(open('ini.conf'))
    filter_sites = str(config.get('app','filter_site')).split(';')

    yjs_key = 'yingjiesheng'
    yjs_home = "http://www.yingjiesheng.com"
    refer_default = '--'
    publish_date = config.get("app","publish_date") #抓取指定时间之内的数据

    def __init__(self, htmlContent):
        #file = open('job-001-501-654.html')
        #htmlContent = file.read()
        #htmlContent = htmlContent.decode('gbk')
        #htmlContent = htmlContent.encode('utf-8')
        self.htmlContent = htmlContent
        #print htmlContent
        #self.detail = pq(urllib.urlopen(r'file:///D:\pywork\crawl\job-001-516-901.html').read(),parser='html')
        #self.detail = pq(filename='job-001-508-982.html',parser='html') #job-001-516-901.html  job-001-510-421.html job-001-508-982.html

        self.detail = pq(htmlContent,parser='html')

        logging.basicConfig(filename = os.path.join(os.getcwd(), 'log.txt'), level = logging.INFO, filemode = 'a', format = '%(asctime)s - %(levelname)s: %(message)s')


    def replaceP(self,content):
        content = content.replace('<p></p>','').replace('<p> </p>','').replace('<P></P>','').replace('<br/><br/>','<br/>')
        return content

    '''
        替换闭合标签 <p></p>
        字符串处理方式
    '''
    def replaceTag(self,content,startTag, endTag):
        p = startTag
        sp_len = len(startTag)
        ep_len = len(endTag)
        index = content.find(p)
        tmp = content
        while index != -1:
            end = tmp.find(endTag)
            if end > index:
                link = tmp[index + sp_len:end]
                if len(link.strip()) == 0:
                    content = content.replace(tmp[index:end + ep_len],"",1)
            tmp = tmp[end + sp_len:]
            index = tmp.find(p)
        return content

    '''
        删除项目内容中包含应届生的连接
        字符串处理方式
    '''
    ##todo fix
    def replaceBR(self,content):
        br = '<br />'
        index = content.find(br)
        tmp = content
        while index != -1:
            print tmp
            print content
            end = tmp.find(br,index + 6)
            if end >= index + 6:
                link = tmp[index + 6:end]
                if len(link.strip()) == 0:
                    content = content.replace(tmp[index:end],"",1)
            tmp = tmp[index + 6:]
            index = tmp.find(br)
        return content

    '''
        删除项目内容中包含应届生的连接
        *字符串处理方式
    '''
    def replaceYJSURL(self,content):
        index = content.find('<a ')
        tmp = content
        while index != -1:
            end = tmp.find('</a>')
            if end > index:
                link = tmp[index:end+4]
                for site in self.filter_sites:
                    if link.find(site) >= 0:
                        content = content.replace(link,link.split('>')[1].split('<')[0])
                        break
                tmp = tmp[end+4:]
                index = tmp.find('<a ')
            else:
                break
        return content
    '''
        删除项目内容中包含应届生的连接
        *pyquery处理方式
    '''
    def _delYJSLink(self, htmlCont):
        try:
            dt = pq(htmlCont, parser='html')
            htmlCont = dt.contents()
            if not htmlCont or len(htmlCont) == 0:
                print '_delYJSLink htmlCont.contents is empty.'
            else :
                links = htmlCont.find('a')
                for i in range(0,len(links)):
                    href = links.eq(i).attr('href')
                    if href and href.find(self.yjs_key) >= 0:
                        links.eq(i).removeAttr('href')
        except Exception,data:
            print "_delYJSLink>>>", Exception,":",data
        return htmlCont.html()

        # 解析 title
    def getName(self):
        name = self.detail('title').html()
        if not name:
            name = self.detail('h1').eq(0).html()
            if not name:
                name = ''
            else:
                name = name.split(']')[1]
        else:
            name = name.split('_')[0].split(']')[1]
        return name

    # 解析项目内容
    def getJobIntro(self,p_idx):
        # for content
        indexs = []
        key = "<p"
        start = 0
        cont = ''
        jobIntro = self.detail('div').filter('.jobIntro').html()
        if jobIntro != None:
            while True:
                index = jobIntro.find(key, start, len(jobIntro))
                if index == -1:
                   break
                indexs.append(index)
                start = index + 1
            if len(indexs) > p_idx:
                cont = jobIntro[indexs[0]:indexs[len(indexs) - int(p_idx)]]
                if cont and cont.find(self.yjs_key) > 0:
                    cont = self.replaceYJSURL(cont)
                    cont = self.replaceP(cont)
                return cont
            elif len(indexs) == p_idx:
                if jobIntro[0:indexs[0]].replace('\t','').replace('\n','').endswith(';</script>'):
                    index = jobIntro.find("<pre>", start, len(jobIntro))
                    if index < 0:
                        index = jobIntro.find("<table>", start, len(jobIntro))
                    cont = jobIntro[0:index].replace('\t','').replace('\n','')
                    if cont and cont.find(self.yjs_key) > 0:
                        cont = self.replaceYJSURL(cont)
                    return cont
        ## 如果上面解析不了，则继续下面的方式解析
        key = '<div class="jobIntro">'
        sindex = self.htmlContent.find(key)
        eindex = self.htmlContent.find('href="http://bbs.yingjiesheng.com/thread-1100509-1-1.html"')
        p_index = self.htmlContent.rfind('<p',sindex,eindex)
        if sindex > 0 and eindex > 0:
            try:
                cont =  self.htmlContent[sindex + len(key):p_index].decode('gbk') #eindex - 42
                cont = cont.encode('utf-8')
            except:
                cont =  self.htmlContent[sindex + len(key):p_index] #eindex - 42
        if cont and cont.replace('\t','').replace('\n','').endswith(';</script>'):
            return ''
        cont = self.replaceYJSURL(cont)
        return cont

    '''解析项目专业'''
    def getMajor(self,majors):
        s = set()
        if len(majors):
            for major in majors.split(','):
                if MAJOR_DICT.get(major):
                    s.add(MAJOR_DICT[major])
        if len(s) == 0:
            majors = self.detail('div').filter('.relHotJob').eq(0).find('a')
            for i in range(0, len(majors)):
                major = unicode(majors.eq(i).text()).encode('utf-8')
                if MAJOR_DICT.get(major):
                    s.add(DAJIE_MAJOR_DICT[MAJOR_DICT[major]])
        return ','.join(v for v in s)

    '''解析项目专业'''
    def getYJSMajor(self):
        try :
            majors = self.detail('div').filter('.relHotJob').eq(0).find('a').text()
            values = []
            if majors and len(majors) > 0:
                majors = ','.join(majors.split(' '))
                return majors
            else:
                tc = len('<div class="relHotJob">')
                start = self.htmlContent.find('<div class="relHotJob">')
                end = self.htmlContent.find('<div class="relHotJob">', start + tc)
                if start > 0 and end > 0:
                    try:
                        majors = self.htmlContent[start + tc:end-5].decode('gbk')
                        majors = majors.encode('utf-8')
                    except:
                        majors = self.htmlContent[start + tc:end-5]
                    for a in majors.split('</a>'):
                        if len(a.split('">')) > 1:
                            values.append(a.split('">')[1])
                return ','.join(values)
        except Exception,data:
            print Exception,":",data


    ''' 解析项目学历'''
    def getDegree(self):
        try :
            degree=[]
            degrees = {'博士研究生':'10','硕士研究生':'12','其他':'16','本科':'13','大专':'14'}
            cont = self.detail('div').filter('#showMain').text()
            if not cont:
                cont = self.detail('div').filter('.jobDetails').text()
            if cont:
                try:
                    cont = unicode(cont).encode('utf-8')
                except:
                    cont = unicode(cont).encode('gbk')
            else:
                start = self.htmlContent.find('<div class="jobIntro">')
                end = self.htmlContent.find('<div class="fillSlotBottom">')
                cont = self.htmlContent[start:end]
                try:
                    cont = cont.decode('gbk')
                    cont = cont.encode('utf-8')
                except:
                    pass
            for dg in degrees.keys():
                if cont.find(dg) >= 0:
                    degree.append(degrees[dg])
            return ','.join(degree)
        except Exception,data:
            print Exception,":",data

    ''' 解析网申url '''
    def getHApplyUrl(self):
        try :
            a_link = self.detail('div').filter('.jobIntro').find('a')
            apply_url = a_link.attr('href')
            if apply_url and apply_url.startswith('http://my.yingjiesheng.com/index.php/personal/applyjob.htm'):
                login_d = pq(url=apply_url,parser='html')
                apply_url = login_d('td:last').find('a').eq(1).text()
            if apply_url and apply_url.startswith('#') :
                apply_url = a_link.attr('onclick').split('\'')[1]
            if not apply_url:
                apply_url = ''
            return apply_url
        except Exception,data:
            print Exception,":",data

    ''' 解析网申url '''
    def getApplyUrl(self):
        a_link = self.detail('div').filter('.jobIntro').find('a').eq(0)
        apply_url = a_link.text()
        if not apply_url or len(apply_url.lstrip()) == 0 or apply_url.find('http://') < 0:
            apply_url = ''
        return apply_url

    ''' 解析所有项目信息 '''
    def getInfo(self,appInfo):
        # for base info
        print "getQZBase"
        res = self.getQZBase(appInfo)
        if res == 0:
            print "getSXBase"
            res = self.getSXBase(appInfo)
            if res == -1: #todo if res <= 0
                print 'failed of publish_time..'
                return -1
        elif res == -1:
             print 'failed of publish_time ..'
             return -1

        # for apply_url
        appInfo.apply_url = self.getApplyUrl()
        print "refer_url=" + appInfo.refer_url

        # for content 
        p_idx = 3
        if not appInfo.apply_url:
            p_idx = 2
            intro =  self.getJobIntro(p_idx)
            if intro and len(intro) > 0:
                appInfo.content = intro
            else :
                print 'failed of Intro..'
                ##return -2  todo
        # for name
        name = self.getName()
        if name and len(name) > 0:
            appInfo.name = name
            print "name=" + name
        else:
            print 'failed of title ...'
            return -2

        # for yjs major
        appInfo.yjs_major = '' #self.getYJSMajor()
        #print "yjs_major=" + appInfo.yjs_major

        # for major
        appInfo.major = '' #self.getMajor(appInfo.yjs_major)
        #print "major=" + appInfo.major

        # for degree
        appInfo.degree = self.getDegree()
        #print "degree=" + appInfo.degree
        return 1

    def getQZBase(self, appInfo):
        try :
            baseKey = [r'发布时间',r'工作地点',r'来源',r'职位类型',r'全职',r'实习',r'其它']
            flag = 0
            for fd in self.detail("li[class='floatl']"):
                base = unicode(pq(fd).find('span').html()).encode('utf-8')
                text = unicode(pq(fd).text()).encode('utf-8')
                if base and base.find(baseKey[0]) >= 0:
                    pub = text.split('：')[1].strip()
                    if pub  >= self.publish_date:
                        appInfo.schedule_begin = pub
                        flag = flag + 1
                    else:
                        print "schedule_begin = ",pub," <  ",self.publish_date
                        return -1
#                elif base and base.find(baseKey[1]) >= 0:
#                    places = ','.join(text.split('：')[1].strip().split(' '))
#                    citys = []
#                    for city in places.split(','):
#                        if CITY_DICT.has_key(city):
#                            citys.append(CITY_DICT[city])
#                    appInfo.place = ','.join(citys)
#                    flag = flag + 1
                elif base and base.find(baseKey[2]) >= 0:
                    flag = flag + 1
                    if pq(fd).find('a'):
                         rurl = self.yjs_home + pq(fd).find('a').attr('onclick').split('\'')[1]
                         dt = pq(url=rurl,parser='html')
                         rurl = dt('a').text()
                         #if has yingjiesheng or not startwith http://
                         if rurl.startswith('http://') and rurl.find(self.yjs_key) == -1:
                             appInfo.refer_url = rurl
                         else:
                             appInfo.refer_url = self.refer_default
                    else :
                        appInfo.refer_url = self.refer_default
            return flag
        except Exception,data:
            print Exception,":",data
            return 0

    # TODO fixed
    def getSXBase(self,appInfo):
        baseKey = [r'发布时间',r'工作地点',r'来源',r'职位类型',r'全职',r'实习',r'其它']
        tc =len("<li class=\"floatl\"><span>")
        s = self.htmlContent.find("<li class=\"floatl\"><span>")
        e = self.htmlContent.find("<li class=\"floatl\"><span>",s + tc )
        kv = (self.htmlContent[s + tc:e]).split('</span>')
        try :
            #if isinstance(kv[0], unicode):
            try:
                kv[0] = kv[0].decode('gbk')
                kv[0] = kv[0].encode('utf-8')
            except:
                pass
            #kv[0] = self._coding(kv[0])
            if kv[0].find(baseKey[0]) >= 0:
                pub = kv[1].split('<')[0].strip()
                if pub  >= self.publish_date:
                    appInfo.schedule_begin = pub
                else:
                    print "schedule_begin = ",pub," <  ",self.publish_date
                    return -1
            else :
                return 0
            ##
            s = self.htmlContent.find("<li class=\"floatl\"><span>",e + tc)
            kv = (self.htmlContent[e + tc:s]).split('</span>')
            try:
                kv[0] = kv[0].decode('gbk')
                kv[0] = kv[0].encode('utf-8')
            except:
                pass
#            if kv[0].find(baseKey[1]) >=0:
#                places = ','.join(kv[1].split('<')[0].strip().split(' ')).decode('gbk')
#                places = places.encode('utf-8')
#                ##
#                citys = []
#                for city in places.split(','):
#                    if CITY_DICT.has_key(city):
#                        citys.append(CITY_DICT[city])
#                appInfo.place = ','.join(citys)
            ##
            e = self.htmlContent.find("<li class=\"floatl\"><span>",s + tc )
            kv = self.htmlContent[s + tc:e].split('\'')
            if len(kv) >= 2:
                appInfo.refer_url = self.yjs_home +  (kv[1]).decode('gbk')
                dt = pq(url=appInfo.refer_url,parser='html')
                rurl = dt('a').text()
                #if has yingjiesheng or not startwith http://
                if rurl.startswith('http://') and rurl.find(self.yjs_key) == -1:
                    appInfo.refer_url = rurl
                else:
                    appInfo.refer_url = self.refer_default
            else :
                appInfo.refer_url = self.refer_default
        except Exception,data :
            print "exception >>>>" ,data

    ''' 解析全职和实习项目url'''
    @staticmethod
    def getAppUrls(purl):
        s = []
        d = pq(url=purl, parser='html')
        for href in d('table').find('a'):
            href = d(href).attr('href')
            if href.startswith('/job-'):
                s.append("http://www.yingjiesheng.com" + href)
        return s

if __name__ == '__main__' :
    app = AppParser('<html></html>')
    appInfo = AppInfo()
    print app.getAppUrls("http://www.yingjiesheng.com/commend-parttime-1.html/") #commend-parttime
#    print app.replaceYJSURL('实行网络化<a href="http://topic.yingjiesheng.com/guanlixue/guanli/" class="word_link" target="_blank">1234</a>1234<a href="http://topic.aa.com"><br />24<br /><br /></a>')
#    print app.replaceP('a<p a="aaa">b</p>24 <p></p>a<p>bbbbbbbbb</p><p>  </p>aa<P> </P><br/> <br/>')
    #app.getInfo(appInfo)
    #app.getBase(appInfo)
    #print app.getApplyUrl()
    #print app.getYJSMajor()
    #print app.getName()
    #print app.getDegree()
    #print app.getMajor()
#    print app.getJobIntro(2)
    
