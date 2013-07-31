package com.dajie.common.util.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: tao.li
 * Date: 13-6-20
 * Time: 下午5:44
 * To change this template use File | Settings | File Templates.
 */
public final class ExceptionIgnoredProxyFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionIgnoredProxyFactory.class);
    private static final ExceptionIgnoredProxyFactory INSTANCE = new ExceptionIgnoredProxyFactory();
    private final Map<Object, Object> proxyCache = new HashMap<Object, Object>();
    private final DefaultValueMapper defaultValueMapper;

    public static ExceptionIgnoredProxyFactory getInstance() {
        return INSTANCE;
    }

    public ExceptionIgnoredProxyFactory(DefaultValueMapper defaultValueMapper) {
        this.defaultValueMapper = defaultValueMapper;
    }

    private ExceptionIgnoredProxyFactory() {
        this(new DefaultValueMapper());
    }

    public synchronized <T> T create(T t) {
        if (t == null) {
            return t;
        }
        T result = (T) this.proxyCache.get(t);
        if (result == null) {
            result = createProxy(t);
            this.proxyCache.put(t, result);
        }
        return result;

    }

    private <T> T createProxy(T t) {
        Class targetCls = t.getClass();
        if (Modifier.isFinal(targetCls.getModifiers())) {
            return createProxyUseInterface(targetCls, t);
        } else {
            return createProxyUseSubClass(targetCls, t);
        }
    }

    private <T> T createProxyUseInterface(Class targetCls, T t) {
        LOGGER.info("create proxy use interfaces.");
        Class[] interfaceClses = targetCls.getInterfaces();
        if (interfaceClses == null || interfaceClses.length == 0) {
            LOGGER.warn("the object {} have no interface, can not create proxy, use self .", t);
            return t;
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(interfaceClses);
        enhancer.setCallback(new ExceptionIgnoredCallback(t));
        return (T) enhancer.create();

    }

    private <T> T createProxyUseSubClass(Class targetCls, T t) {
        LOGGER.info("create proxy use interfaces.");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetCls);
        enhancer.setCallback(new ExceptionIgnoredCallback(t));
        return (T) enhancer.create();
    }

    private class ExceptionIgnoredCallback implements MethodInterceptor {
        private final Object targetObject;

        private ExceptionIgnoredCallback(Object targetObject) {
            this.targetObject = targetObject;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            try {
                return method.invoke(this.targetObject, objects);
            } catch (Throwable t) {
                LOGGER.error("failed to run method {} using args {}.",
                        method, Arrays.toString(objects), t);
                return defaultValueMapper.getDefaultValueOfClass(method.getReturnType());
            }
        }
    }
}
