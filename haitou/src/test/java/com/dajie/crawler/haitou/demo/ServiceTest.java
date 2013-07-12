package com.dajie.crawler.haitou.demo;

import com.chris.crawler.haitou.model.HTxjh;
import com.chris.crawler.haitou.service.HTxjhService;
import com.chris.crawler.haitou.service.impl.HTxjhServiceImpl;
import com.google.inject.Guice;
import org.junit.Test;

/**
 * User: zhong.huang
 * Date: 13-5-31
 */
public class ServiceTest {

    HTxjhService service = Guice.createInjector().getInstance(HTxjhServiceImpl.class);


    @Test
    public void testHT() {
        HTxjh ht = new HTxjh();
        service.getUpdateCount("af1fb1337239d8800a1226125ac86c8b");
    }
}
