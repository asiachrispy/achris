package com.chris.crawler.yjs.ft.model;

import com.dajie.common.dubbo.util.StringUtil;

import java.io.Serializable;

/**
 * User: zhong.huang
 * Date: 13-4-23
 */
public class FTJob implements Serializable {
    private Integer id;
    private String name;
    private String yjsUrl;
    private String scheduleBegin;
    //laiyuan
    private String referUrl;
    private String recruitType;
    //wangshen
    private String applyUrl;
    private String major;
    private String yjsMajor;
    private String degree;
    private String place;
    private String content;
    private String createDate;

    public FTJob() {
    }

    public FTJob(Integer id, String name, String yjsUrl, String scheduleBegin, String referUrl, String recruitType, String applyUrl, String major, String yjsMajor, String degree, String place, String content, String createDate) {
        this.id = id;
        this.name = name;
        this.yjsUrl = yjsUrl;
        this.scheduleBegin = scheduleBegin;
        this.referUrl = referUrl;
        this.recruitType = recruitType;
        this.applyUrl = applyUrl;
        this.major = major;
        this.yjsMajor = yjsMajor;
        this.degree = degree;
        this.place = place;
        this.content = content;
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYjsUrl() {
        return yjsUrl;
    }

    public void setYjsUrl(String yjsUrl) {
        this.yjsUrl = yjsUrl;
    }

    public String getScheduleBegin() {
        return scheduleBegin;
    }

    public void setScheduleBegin(String scheduleBegin) {
        this.scheduleBegin = scheduleBegin;
    }

    public String getReferUrl() {
        return referUrl;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }

    public String getRecruitType() {
        return recruitType;
    }

    public void setRecruitType(String recruitType) {
        this.recruitType = recruitType;
    }

    public String getApplyUrl() {
        return applyUrl;
    }

    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getYjsMajor() {
        return yjsMajor;
    }

    public void setYjsMajor(String yjsMajor) {
        this.yjsMajor = yjsMajor;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean validate() {
        if (StringUtil.isNotEmpty(content) && StringUtil.isNotEmpty(name)
            && StringUtil.isNotEmpty(scheduleBegin)
            && recruitType.equals("1")) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "FTJob{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", yjsUrl='" + yjsUrl + '\'' +
            ", scheduleBegin='" + scheduleBegin + '\'' +
            ", referUrl='" + referUrl + '\'' +
            ", recruitType='" + recruitType + '\'' +
            ", applyUrl='" + applyUrl + '\'' +
            ", major='" + major + '\'' +
            ", yjsMajor='" + yjsMajor + '\'' +
            ", degree='" + degree + '\'' +
            ", place='" + place + '\'' +
            ", createDate='" + createDate + '\'' +
            ", content='" + content + '\'' +
            '}';
    }
}
