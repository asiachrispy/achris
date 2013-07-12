package com.chris.crawler.zhihu.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * User: zhong.huang
 * Date: 13-5-15
 */
public class LogItem implements Serializable {
    private int id; // logitem id
    private int qid;
    private Date createDate;

    @Override
    public String toString() {
        return "LogItem{" +
            "id=" + id +
            ", qid=" + qid +
            ", createDate=" + createDate +
            '}';
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
