package com.dajie.common.util;

import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;


/**
 * User: haluo
 * Date: Jan 19, 2010
 * Time: 2:28:01 PM
 */
public class SearchUtil {
    private final static Logger log = LoggerFactory.getLogger(SearchUtil.class);

    /**
     * @param urlString 能够放回 well-formated XML 的URL.
     * @param beanClass 与URL放回的xml对应的bean,
     *                  这个bean需要用XStreamAlias annotation标识bean的相应字段转成XML后结点的名字
     * @param <T>       对应于bean的类型
     * @return 放回一个bean instance
     */
    public static <T> T getBeanFromUrl(String urlString, Class<T> beanClass) {
        String xml = HttpClientUtil.getResponse(urlString);
        if (xml == null || xml.isEmpty()) {
            return null;
        }
        try {
            return getBeanFromXml(xml, beanClass);
        } catch (Exception e) {
            log.error("Parse xml to bean error. The name of the bean is" + beanClass.getName() + ", and the url is " + urlString, e);
            return null;
        }
    }

    /**
     * @param xml       well-formated XML
     * @param beanClass xml对应的bean,
     *                  这个bean需要用XStreamAlias annotation标识bean的相应字段转成XML后结点的名字
     * @param <T>       对应于bean的类型
     * @return 放回一个bean instance
     */
    public static <T> T getBeanFromXml(String xml, Class<T> beanClass) {
        XStream xstream = new XStream();
        xstream.processAnnotations(beanClass);
        return (T) xstream.fromXML(xml);
    }

    public static <T> T getBeanFromXmlFile(String xmlFilePath, Class<T> beanClass) {
        XStream xstream = new XStream();
        xstream.processAnnotations(beanClass);
        try {
            return (T) xstream.fromXML(new FileInputStream(xmlFilePath));
        } catch (Exception e) {
            log.error("{}", e);
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getBeanFromXmlInputStream(InputStream inputStream, Class<T> beanClass) {
        XStream xstream = new XStream();
        xstream.processAnnotations(beanClass);
        try {
            return (T) xstream.fromXML(inputStream);
        } catch (Exception e) {
            log.error("{}", e);
            e.printStackTrace();
            return null;
        }
    }

}