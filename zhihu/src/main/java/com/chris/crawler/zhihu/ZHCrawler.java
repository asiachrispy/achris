package com.chris.crawler.zhihu;

import com.chris.crawler.zhihu.model.*;
import com.chris.crawler.zhihu.parser.*;
import com.chris.crawler.zhihu.service.ZHAnswerService;
import com.chris.crawler.zhihu.service.ZHLogService;
import com.chris.crawler.zhihu.service.ZHQuestionService;
import com.chris.crawler.zhihu.service.impl.ZHCommentServiceImpl;
import com.chris.crawler.zhihu.service.impl.ZHLogServiceImpl;
import com.chris.crawler.zhihu.spider.ZHSpider;
import com.dajie.common.config.AppConfigs;
import com.dajie.common.monitor.postman.EmailLetter;
import com.dajie.common.monitor.postman.SenderManager;
import com.dajie.common.monitor.postman.exception.ChannelException;
import com.chris.crawler.zhihu.service.ZHCommentService;
import com.chris.crawler.zhihu.service.impl.ZHAnswerServiceImpl;
import com.chris.crawler.zhihu.service.impl.ZHQuestionServiceImpl;
import com.chris.crawler.zhihu.spider.Spider;
import com.google.inject.Guice;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;


/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public class ZHCrawler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZHCrawler.class);

    private static final String LOG_HOME = "http://www.zhihu.com/log/questions?start=#&offset=30&_xsrf=7547a995c9c840db8bf28202107a5fe6";
    private static final String Q_HOME = "http://www.zhihu.com/question/#";
    private static final String LOG_URL = "http://www.zhihu.com/log/questions";
    private static final Integer END_QID = Integer.valueOf(AppConfigs.getInstance().get(ZHConstant.CONFIG_KEY_END_QID));

    private Parser<List<ZHComment>> cParser = new ZHCParser();
    private Parser<List<ZHAnswer>> aParser = new ZHAParser((ZHCParser) cParser);
    private Parser<ZHLog> logParser = new ZHLParser();
    private Parser<ZHQuestion> qParser = new ZHQParser((ZHAParser) aParser);
    private Spider spider = Guice.createInjector().getInstance(ZHSpider.class);

    // FOR SERVICE
    private ZHQuestionService questionService = Guice.createInjector().getInstance(ZHQuestionServiceImpl.class);
    private ZHAnswerService answerService = Guice.createInjector().getInstance(ZHAnswerServiceImpl.class);
    private ZHCommentService commentService = Guice.createInjector().getInstance(ZHCommentServiceImpl.class);
    private ZHLogService logService = Guice.createInjector().getInstance(ZHLogServiceImpl.class);

    public void run(int logItemStartId) {

        //int end = logService.getStart(LOG_ID);// 上一次抓取的起始logitem id作为结束logitem的条件

        // 从首页抓取，获取起始 logitem id,和结束的logitem id,
        // 并以结束的logitem id作为起始 logitem id,发起post请求，并重复以上步骤，
        // 并以上一次抓取的开始 logitem id作为结束的logitem id
        long startTime = System.currentTimeMillis();
        long endTime = 0L;

        ZHLog logs = new ZHLog();
        try {
            String logHtml = "";

            if (logItemStartId != 0) {
                logHtml = spider.getHtmlWithLogin(LOG_HOME.replace("#", logItemStartId + ""), ZHConstant.ENCODING_UTF8);
            } else {
                logHtml = spider.getHtmlWithLogin(LOG_URL, ZHConstant.ENCODING_UTF8);
            }
            Document doc = Jsoup.parse(logHtml);
            logParser.parser(doc, logs);
            int startId = logs.getStartId();

            ZHQuestion question = null;
            List<ZHAnswer> answers = null;
            List<ZHComment> comments = null;
            Integer qid = null;
            String qUrl = "";
            String qHtml = "";
            int istartId = startId;
            int insertFlag = 0;
            long itemStartTime = 0l;
            while (logs.getLogItems().size() > 0) {
                LOGGER.info("the start logItem id is {}.", istartId);
                // 由于找不到 end logitem id,所以使用qid来作为结束的条件
//            if (logs.getEndId() == end) {
//                logs.setStartId(startId);
//                LOGGER.info("the end logItem id is {}.", end);
//                break;
//            }

                // for single question
                for (LogItem log : logs.getLogItems()) {
                    itemStartTime = System.currentTimeMillis();
                    qid = log.getQid();
                    if (qid.equals(END_QID)) {//
                        LOGGER.info("the end qid id is {}.", qid);
                        break;
                    }
                    if (questionService.exist(qid)) {
                        LOGGER.warn("question qid={} exists.", qid);
                        continue;
                    }

                    qUrl = Q_HOME.replace("#", qid + "");
                    qHtml = spider.getHtml(qUrl, ZHConstant.ENCODING_UTF8);
                    LOGGER.info(">>> q_url = {}", qUrl);

                    doc = Jsoup.parse(qHtml);
                    question = new ZHQuestion();
                    qParser.parser(doc, question);
                    question.setQid(qid);
                    question.setCreateDate(log.getCreateDate());

                    // for answers
                    answers = new LinkedList<ZHAnswer>();
                    aParser.parser(doc, answers);

                    //question.setAnswers(answers);
                    question.setAnswerCount(answers.size());
                    insertFlag = questionService.insert(question);
                    LOGGER.debug(">>>> question = {}", question.toString());
                    if (insertFlag == 1) {
                        // for answer
                        for (ZHAnswer answer : answers) {
                            answer.setQid(qid);
                            comments = new LinkedList<ZHComment>();
                            cParser.parser(answer.getAid() + "", comments);

                            //answer.setComments(comments);
                            insertFlag = answerService.insert(answer);
                            LOGGER.debug(">>>> answer = {}", answer.toString());
                            if (insertFlag == 1) {
                                // for comments
                                for (ZHComment comment : comments) {
                                    comment.setAid(answer.getAid());
                                    comment.setQid(qid);
                                    comment.setCid(0);
                                    insertFlag = commentService.insert(comment);
                                    LOGGER.debug(">>>> comment = {}", comment.toString());
                                    if (insertFlag == 0) {
                                        LOGGER.warn("insert comment failed.");
                                    }
                                }
                            } else {
                                LOGGER.warn("insert answer aid={} failed.", qid);
                            }
                        }
                    } else {
                        LOGGER.warn("insert question qid={} failed.", qid);
                    }

                    endTime = System.currentTimeMillis();
                    LOGGER.info("get and parser one question used {} millis. ", (endTime - itemStartTime));
                }
                istartId = logs.getEndId();
                endTime = System.currentTimeMillis();
                LOGGER.info("get and parser 20 pages used {} millis. ", (endTime - startTime));
                logHtml = spider.getHtmlWithLogin(LOG_HOME.replace("#", istartId + ""), ZHConstant.ENCODING_UTF8);
                doc = Jsoup.parse(logHtml);
                logParser.parser(doc, logs);
            }
            sendEmail("知乎问答抓取完毕", ZHConstant.getEmailPersons());
        } catch (Exception e) {
            LOGGER.info("ZHCrawler exception ", e);
            sendEmail("知乎问答抓取异常", ZHConstant.getEmailOwner());
        }
        logService.update(logs);
    }

    public static void main(String[] args) {
        ZHCrawler crawler = new ZHCrawler();
        int start = 0;
        if (args.length == 1) {
            start = Integer.valueOf(args[0]);
        }
        crawler.run(start);
        System.exit(0);
    }

    private void sendEmail(String title, List<String> persons) {
        EmailLetter letter = new EmailLetter(title, "知乎问答抓取完毕", persons, "");
        try {
            SenderManager.getInstance().send(letter);
        } catch (ChannelException e) {
            LOGGER.error("sendEmail exception >>>", e);
        }
    }
}
