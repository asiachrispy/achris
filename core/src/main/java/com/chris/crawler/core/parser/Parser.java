package com.chris.crawler.core.parser;

import com.dajie.common.dubbo.util.StringUtil;
import com.chris.crawler.core.exception.ParserException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public abstract class Parser<T extends ParserModel> {

    /**
     * 按照标注解析原始内容，
     *
     * @param doc
     * @param clazz
     * @return
     */
    public T parser(Document doc, Class clazz) throws ParserException {
        Field[] fields = clazz.getDeclaredFields();
        Tag tag = null;
        String tClazz = "";
        String id = "";
        String value = "";
        boolean required = true;
        Map<String, Object> map = new HashMap<String, Object>();
        Element element = null;
        for (Field f : fields) {
            tag = f.getAnnotation(Tag.class);
            tClazz = tag.clazz();
            id = tag.id();
            required = tag.required();

            if (tag.tagType().equals(Tag.TYPE.TEXT)) {
                if (StringUtil.isNotEmpty(id)) {
                    element = doc.getElementById(id);
                } else if (StringUtil.isNotEmpty(tClazz)) {
                    element = doc.getElementsByAttributeValue("class",tClazz).first();//doc.getElementsByClass(tClazz).first();
                }
                if (element != null) {
                    value = element.text();
                }
            } else {
                if (StringUtil.isNotEmpty(id)) {
                    element = doc.getElementById(id);
                } else if (StringUtil.isNotEmpty(tClazz)) {
                    element = doc.getElementsByClass(tClazz).first();
                }
                if (element != null) {
                    value = element.html();
                }
            }

            if (StringUtil.isEmpty(value) && required) {
                throw new ParserException(String.format(" required field [%s] is null or empty.", f.getName()));
            }
            map.put(f.getName(), value);
        }
        T t = handler(map);
        return t;
    }

    /**
     * 由调用者自己解析原始内容，并构造最终的Model
     *
     * @param map
     * @return
     */
    abstract protected T handler(Map<String, Object> map);

}
