package com.chris.crawler.haitou.model;

import com.dajie.crawler.core.parser.ParserModel;

import java.util.Date;

/**
 * User: zhong.huang
 * Date: 13-5-30
 */
public class HTxjh extends ParserModel {

    private int id;
    private String url;
    private String urlMd5;
    private String title;
    private int city;
    private int school;
    private String address;
    private String content;
    private int updateCount;

    private Date scheduleDate;
    private Date publishDate;
    private Date createDate;
    private Date updateDate;

    public HTxjh() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMd5 = urlMd5;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getSchool() {
        return school;
    }

    public void setSchool(int school) {
        this.school = school;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "HTxjh{" +
            "id=" + id +
            ", url='" + url + '\'' +
            ", urlMd5='" + urlMd5 + '\'' +
            ", title='" + title + '\'' +
            ", city='" + city + '\'' +
            ", school='" + school + '\'' +
            ", address='" + address + '\'' +
            ", updateCount=" + updateCount +
            ", scheduleDate=" + scheduleDate +
            ", publishDate=" + publishDate +
            '}';
    }
}
