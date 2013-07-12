package com.chris.crawler.zhihu.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

/**
 * User: zhong.huang
 * Date: 13-5-10
 */
public class ZHLog implements Serializable {
    private int id;
    private int endId;
    private int startId;
    private Date updateDate;
    private LinkedList<LogItem> logItems;

    public ZHLog() {
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<LogItem> getLogItems() {
        return logItems;
    }

    public void setLogItems(LinkedList<LogItem> logItems) {
        this.logItems = logItems;
    }

    @Override
    public String toString() {
        return "ZHLog{" +
            "endId=" + endId +
            ", startId=" + startId +
            ", logItems=" + logItems +
            '}';
    }

    public int getEndId() {
        return endId;
    }

    public void setEndId(int endId) {
        this.endId = endId;
    }

    public int getStartId() {
        return startId;
    }

    public void setStartId(int startId) {
        this.startId = startId;
    }
}
