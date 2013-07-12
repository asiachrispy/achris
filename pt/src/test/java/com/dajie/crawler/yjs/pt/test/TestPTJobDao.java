package com.dajie.crawler.yjs.pt.test;

import com.chris.crawler.yjs.pt.dao.impl.PTJobDaoImpl;
import com.chris.crawler.yjs.pt.model.PTJob;
import com.dajie.common.util.StringUtil;
import com.chris.crawler.yjs.pt.dao.PTJobDao;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;

/**
 * User: zhong.huang
 * Date: 13-4-24
 */
public class TestPTJobDao {

    PTJobDao dao = Guice.createInjector().getInstance(PTJobDaoImpl.class);
    PTJob job = new PTJob();

    @Before
    public void before() {
        job.setUrl("http://www.yingjiesheng.com/job-101-566-681.html");
        job.setMd5Url(StringUtil.md5(job.getUrl()));
        job.setTitle("宝马中国财务部招实习生");
        job.setCityCN("beijing");
        job.setCityEN("北京");
    }

    @Test
    public void testInsert() {
        dao.insert(job);
    }

    @Test
    public void testExist() {
        System.out.print(dao.exist(job.getMd5Url()));
    }

    @Test
    public void testDelete() {
        System.out.print(dao.deleteByMd5Url(job.getMd5Url()));
    }

    @Test
    public void testGet() {
        System.out.print(dao.getByDate("2013-04-25").size());
    }

}
