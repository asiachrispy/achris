#coding=utf-8
#!/usr/bin/python2.6

##########################################
#########  author:alex  ##################
######### create:2010-01-04 ##############
######### dajie html parser ##############
##########################################

from lxml import *
import lxml.html as H
import chardet
import re
import urlparse

def checkEncoding(str):
    
    '''
        check the string's encoding
    '''
    
    msg = chardet.detect(str)
    
    return msg.get('encoding','utf-8')

class CParser():
    
    def __init__(self,html,url):
        
        html = self.__getCleanHtml(html,url)
        self.html = html
        self.doc = H.fromstring(html)
    
    def __getCleanHtml(self,html,url):
        
        #encoding = checkEncoding(html)
        
        try:
            html = html.decode('utf-8')
        except:
            try:
                html = html.decode('gbk')
            except:
                try:
                    html = html.decode('gb2312')
                except:
                    pass
        try:
            html = H.html_to_xhtml(html)
        except:
            pass
        
        html = html.replace('&nbsp;','')
        html = re.sub('encoding','',html)
        html = re.sub('charset','',html)
        html = html.replace('<br>','')
        
        return html

class CHtmlParser(CParser):
    
    def __init__(self,html,url):
        
        '''
            create a html parser: url,html(unicode),doc
        '''
        
        CParser.__init__(self,html,url)
        
        self.url = url
        
    def getXPathByTag(self,tag,attrs,split='/'):
        
        '''
            get the xpath
            parmars: tag:   [div/table/tr/td...]
                     attrs: {'class/id/style...':'xx'}
                     split: ['//' or '/']
            
            return xpath like this: //div[@class='test' and @id='test']
        '''
        
        xpath = ''
        if len(attrs):
            attrs = ' and '.join('@'+k+"='"+v+"'" for k,v in attrs.items())
            xpath = '%s%s[%s]' % (split,tag,attrs)
        else:
            xpath = '%s%s' % (split,tag,)
        return xpath
    
    def getElements(self,xpath):
        
        '''
            get the elements by xpath
            parmars:    xpath:
            return : elements
        '''
        
        elements = []
        try:
            elements = self.doc.xpath(xpath)
        except Exception,e:
            #print e
            pass
        return elements
    
    def getElement(self,elements,no='0'):
        
        '''
            get the element from elements list
            parmars:    no: the element num
            return : element
        '''
        
        try:
            no = int(no)
        except:
            no = 0
        
        try:
            element = elements[no]
        except:
            element = None
        
        return element
    
    def getValue(self,element):
        
        '''
            get the element's text
        '''
        
        value = ''
        try:
            value = unicode(element.text_content())
            
            value = value.encode('utf-8')
            value = value.strip()
        except:
            pass
        return value
    
    def getHtmlValue(self,element):
        
        '''
            get the element's html content
        '''
        
        value = ''
        try:
            value = H.tostring(element,encoding='utf-8')
            value = value.strip()
        except:
            pass
        return value
    
    def getAttr(self,element,attr):
        
        '''
            get element's attr value
        '''
        
        value = ''
        if attr=='tail':
            value = element.tail
        else:
            value = element.attrib.get(attr,'')
        
        try:
            value = value.encode('utf-8')
        except:
            pass
        return value
    
    def filterValue(self,html,crash_strings):
        
        '''
            wash the data
            parmars:    html: value what will be washed
                        crash_strings: a list of string,this string will be replace
            return : the washed string
        '''
        
        try:
            value = html.encode('utf-8')
        except:
            value = html
        for item in crash_strings:
            #value = value.replace(item,'')
            value = re.sub(item,'',value)
        
        value = value.strip()
        return value
    
    def _getFullLink(self,url):
        
        '''
            get the full link
        '''
        
        url_full = urlparse.urljoin(self.url,url)
        
        return url_full
    
    def getTasks(self,cases,xpath='//a',filters=[]):
        
        tasks = {}
        
        i = 0
        elements = self.getElements(xpath)
        for element in elements:
            try:
                href = element.attrib.get('href','')
                
                for filter in filters:
                    href = href.replace(filter,'')
                
                href = self._getFullLink(href)
                text = unicode(element.text_content()).encode('utf-8')
                b_rule = True
                href_for_check = href
                
                for case in cases:
                    if href_for_check.find(case) != -1:
                        href_for_check = href_for_check.replace(case,'')
                    else:
                        b_rule = False
                        break
                
                if b_rule:
                    if tasks.has_key(text):
                        text += str(i)
                    tasks[text] = href
                else:
                    pass
            except:
                pass
            
            i += 1
        return tasks

if __name__ == '__main__':
    
    html_file = open('123.html','r')
    html = html_file.read()
    html_file.close()
    
    tester = CHtmlParser(html,'http://bbs.whnet.edu.cn/cgi-bin/bbsdoc?board=Job')
    #xpath = tester.getXPathByTag('div',{'id':'normalPostType'},'//')
    #xpath += tester.getXPathByTag('div',{'class':'unitBox'},'/')
    #xpath += tester.getXPathByTag('fieldset',{},'/')
    #xpath += tester.getXPathByTag('ul',{"class":"infoLst"},'/')
    #xpath += tester.getXPathByTag('li',{},'/')
    
    #xpath = tester.getXPathByTag('table',{'class':'jobInfoItems'},'//')
    #xpath += tester.getXPathByTag('tr',{},'/')
    #xpath += tester.getXPathByTag('td',{'width':'38%'},'/')
    xpath = "//a"
    elements = tester.getElements(xpath)
    element = tester.getElement(elements,'0')
    print tester.getValue(element)
