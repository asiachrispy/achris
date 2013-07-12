package com.chris.crawler.zhihu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public class ZHAnswer implements Serializable {
    private int id;
    private int uid;
    private int aid;
    private int qid;// dajie qid
//    private int zhqid;// zhihu qid
    private String content;
    private Date pubDate;
    private Date createDate;
    private List<ZHComment> comments;

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

//    public int getZhqid() {
//        return zhqid;
//    }
//
//    public void setZhqid(int zhqid) {
//        this.zhqid = zhqid;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public List<ZHComment> getComments() {
        return comments;
    }

    public void setComments(List<ZHComment> comments) {
        this.comments = comments;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "ZHAnswer{" +
            ", qid=" + qid +
            ", aid=" + aid +
            ", content='" + content + '\'' +
            ", pubDate=" + pubDate +
            '}';
    }
}
