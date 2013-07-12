package com.chris.crawler.yjs.pt.model;

import java.io.Serializable;

/**
 * User: zhong.huang
 * Date: 13-4-23
 */
public class PTJob implements Serializable {
    private String url;
    private String md5Url;
    private String cityEN;
    private String cityCN;
    private String title;
    private String createDate;

    public PTJob() {
    }

    public PTJob(String url, String md5Url, String cityEN, String cityCN, String title) {
        this.url = url;
        this.md5Url = md5Url;
        this.cityEN = cityEN;
        this.cityCN = cityCN;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCityEN() {
        return cityEN;
    }

    public void setCityEN(String cityEN) {
        this.cityEN = cityEN;
    }

    public String getCityCN() {
        return cityCN;
    }

    public void setCityCN(String cityCN) {
        this.cityCN = cityCN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMd5Url() {
        return md5Url;
    }

    public void setMd5Url(String md5Url) {
        this.md5Url = md5Url;
    }

    @Override
    public String toString() {
        return "PTJob{" +
            "url='" + url + '\'' +
            ", cityEN='" + cityEN + '\'' +
            ", title='" + title + '\'' +
            '}';
    }
}
