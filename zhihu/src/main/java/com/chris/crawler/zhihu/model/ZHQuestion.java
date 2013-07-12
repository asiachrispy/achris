package com.chris.crawler.zhihu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public class ZHQuestion implements Serializable {
    private int id; // dajie qid
    private int uid;
    private int qid;// zhihu-zhqid
    private String tags;
    private String title;
    private String detail;
    private int answerCount;
    private Date pubDate;
    private Date createDate;

    private List<ZHAnswer> answers;


    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public List<ZHAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ZHAnswer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "ZHQuestion{" +
            ", qid=" + qid +
            ", tags='" + tags + '\'' +
            ", title='" + title + '\'' +
            ", detail='" + detail + '\'' +
            ", answerCnt=" + answerCount +
            ", pubDate=" + pubDate +
            '}';
    }
}
