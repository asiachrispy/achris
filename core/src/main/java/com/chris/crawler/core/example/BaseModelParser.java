package com.chris.crawler.core.example;

import com.chris.crawler.core.parser.Parser;

import java.util.Date;
import java.util.Map;

/**
 * User: zhong.huang
 * Date: 13-5-29
 */
public class BaseModelParser extends Parser<BaseModel> {
    @Override
    protected BaseModel handler(Map<String, Object> map) {
        BaseModel model = new BaseModel();
        String filedName = null;
        String v = null;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            filedName = entry.getKey();
            if (filedName.equals("title")) {
                model.setTitle((String) entry.getValue());
            } else if (filedName.equals("content")) {
                v = (String) entry.getValue();
                v = v.substring(v.indexOf(">") + 1, v.length() - 6);
                model.setContent(v);
             } else if (filedName.equals("tags")) {
                model.setTags(((String) entry.getValue()).replaceAll(" ", ","));
            }
        }
        model.setCreateDate(new Date());
        return model;
    }
}
