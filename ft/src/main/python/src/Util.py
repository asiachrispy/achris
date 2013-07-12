# -*- coding:utf-8 -*-

import hashlib
import pycurl
import cStringIO
import urllib2
import gzip
import os

class Util:
    @staticmethod
    def urlMd5(url):
        md = hashlib.md5()
        md.update(url)
        return md.hexdigest()
    
    @staticmethod
    def saveFile(path,value):
        file = open(path,'w')
        file.write(value)
        file.close()

    @staticmethod
    def download(url, encoding):
        buf = cStringIO.StringIO()
        c = pycurl.Curl()
        c.setopt(c.URL, url)
        c.setopt(c.WRITEFUNCTION, buf.write)
        c.setopt(c.CONNECTTIMEOUT, 5)
        c.setopt(c.TIMEOUT, 8)
        #c.setopt(c.PROXY, 'http://inthemiddle.com:8080')
        c.perform()
        content = buf.getvalue();
#        print c.getinfo(pycurl.ENCODING)
        print "Content-type:", c.getinfo(c.HTTP_CONTENT_DECODING)
        content =  gzip.GzipFile(fileobj = cStringIO.StringIO(content)).read()
        return content.decode(encoding)

    @staticmethod
    def doanloadPage(url,encoding):
        html = "<html></html>"
        try:
    #        proxy = urllib2.ProxyHandler({'http':'http://101.44.1.22:80'})
    #        opener = urllib2.build_opener(proxy, urllib2.HTTPHandler)
    #        urllib2.install_opener(opener)

            headers = {'User-Agent':'Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6'}
            req = urllib2.Request(url = url, headers = headers)
            html = urllib2.urlopen(req).read()
            # for gzip
            if html != None and html[:6] == '\x1f\x8b\x08\x00\x00\x00':
                html = gzip.GzipFile(fileobj = cStringIO.StringIO(html)).read()
                try :
                    html = html.decode("gb2312")
                except Exception,data:
                    try :
                        html = html.decode("gbk")
                    except Exception,data:
                        try :
                            html = html.decode("utf-8")
                        except Exception,data:
                            html = "<html></html>"
            else :
                try :
                    html =  html.decode("gb2312")
                except Exception,data:
                    html = "<html></html>"
        except Exception,data:
            pass
        return html

if __name__ == '__main__' :
#    Util.saveFile('a.a','abc')
    url = "http://www.yingjiesheng.com/shanghai/ptjob.html";
#    url = "http://www.yingjiesheng.com/shanghai-moreptjob-1.html";
#    url ="http://www.yingjiesheng.com/job-001-571-120.html";

    print Util.doanloadPage(url,"gb2312") #job-001-508-982.
#    print Util.download(url, "gb2312");
#    print 'http://www.yingjiesheng.com/job-001-519-375.html'.split('/')[3]
#    import  time
#    print time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))