package com.chris.crawler.zhihu.model;

import java.io.Serializable;
import java.util.Date;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public class ZHComment implements Serializable {
    private int id;
    private int uid;
    private int qid;
    //    private int zhqid;
    private int aid;
    private int cid;
    //    private int zhaid;
    private String content;
    private Date pubDate;
    private Date createDate;

//    public int getZhqid() {
//        return zhqid;
//    }
//
//    public void setZhqid(int zhqid) {
//        this.zhqid = zhqid;
//    }
//
//    public int getZhaid() {
//        return zhaid;
//    }
//
//    public void setZhaid(int zhaid) {
//        this.zhaid = zhaid;
//    }


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
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

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

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

    @Override
    public String toString() {
        return "ZHComment{" +
            ", qid=" + qid +
            ", aid=" + aid +
            ", content='" + content + '\'' +
            ", createDate=" + createDate +
            '}';
    }
}
