package com.dajie.crawler.yjs.ft.test;

import com.chris.crawler.yjs.ft.model.FTJob;
import com.chris.crawler.yjs.ft.model.FTJobURL;
import com.dajie.common.util.StringUtil;
import com.chris.crawler.yjs.ft.dao.FTJobDao;
import com.chris.crawler.yjs.ft.dao.FTJobURLDao;
import com.chris.crawler.yjs.ft.dao.impl.FTJobDaoImpl;
import com.chris.crawler.yjs.ft.dao.impl.FTJobURLDaoImpl;
import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: zhong.huang
 * Date: 13-4-24
 */
public class TestPTJobDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestPTJobDao.class);

    FTJobDao dao = Guice.createInjector().getInstance(FTJobDaoImpl.class);
    FTJobURLDao udao = Guice.createInjector().getInstance(FTJobURLDaoImpl.class);
    FTJob job = new FTJob();
    FTJobURL ujob = new FTJobURL();

    @Before
    public void before() {
        job.setYjsUrl("http://www.yingjiesheng.com/job-101-566-682.html");
        job.setName("宝马中国财务部招实习生");
        job.setApplyUrl("http://www.yingjiesheng.com/");
        job.setContent("content");
        job.setCreateDate("2013-05-06 15:25:160");
        job.setDegree("14,16,13");
        job.setMajor("");
        job.setPlace("9999");
        job.setRecruitType("1");
        job.setReferUrl("http://gaoxiaojob.com/zhaopin/gaoxiaojiaoshi/20130329/92685.html");
        job.setScheduleBegin("2013-04-17 00:00:00");
        job.setYjsMajor("物理,天文地理,力学,化学化工");

        ujob.setUrl("http://www.yingjiesheng.com/job-101-566-681.html");
        ujob.setMd5sum(StringUtil.md5(ujob.getUrl()));
        ujob.setStatus(1);
        ujob.setRecruitType(1);

    }

    @Test
    public void testInsert() {
        dao.insert(job);
    }

    @Test
    public void testInsertUjob() {
        udao.insert(ujob);
    }

    @Test
    public void testExist() {
        LOGGER.info(udao.exist(ujob.getMd5sum()) + "");
    }

}
