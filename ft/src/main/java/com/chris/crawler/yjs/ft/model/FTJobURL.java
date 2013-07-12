package com.chris.crawler.yjs.ft.model;

import java.io.Serializable;

/**
 * User: zhong.huang
 * Date: 13-4-23
 */
public class FTJobURL implements Serializable {
    private int id;
    private String md5sum;
    private String url;
    private int status;
    private int recruitType;
    private String createDate;

    public FTJobURL() {
    }

    public FTJobURL(int id, String md5sum, String url, int status,int recruitType, String createDate) {
        this.id = id;
        this.md5sum = md5sum;
        this.url = url;
        this.status = status;
        this.recruitType = recruitType;
        this.createDate = createDate;
    }

    public int getRecruitType() {
        return recruitType;
    }

    public void setRecruitType(int recruitType) {
        this.recruitType = recruitType;
    }

    @Override
    public String toString() {
        return "FTJobURL{" +
            "id=" + id +
            ", md5sum='" + md5sum + '\'' +
            ", url='" + url + '\'' +
            ", status=" + status +
            ", recruitType=" + recruitType +
            ", createDate='" + createDate + '\'' +
            '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
