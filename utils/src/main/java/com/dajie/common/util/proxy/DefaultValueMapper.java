package com.dajie.common.util.proxy;


import java.util.Map;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: tao.li
 * Date: 13-6-20
 * Time: 下午5:45
 * To change this template use File | Settings | File Templates.
 */
public class DefaultValueMapper {
    private static final Map<Class, Object> DEFAULT_MAPPER;

    static {
        DEFAULT_MAPPER = new HashMap<Class, Object>();
        DEFAULT_MAPPER.put(Integer.TYPE, -1);
        DEFAULT_MAPPER.put(Long.TYPE, -1l);
        DEFAULT_MAPPER.put(Double.TYPE, -1.0);
        DEFAULT_MAPPER.put(Boolean.TYPE, false);
    }

    private final Map<Class, Object> defaultValueMap;

    public DefaultValueMapper(Map<Class, Object> defaultValueMap) {
        this.defaultValueMap = defaultValueMap;
    }

    public DefaultValueMapper() {
        this(DEFAULT_MAPPER);
    }

    public Object getDefaultValueOfClass(Class cls) {
        return this.defaultValueMap.get(cls);
    }
}
