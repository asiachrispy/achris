package com.chris.crawler.core.example;

import com.chris.crawler.core.parser.ParserModel;
import com.chris.crawler.core.parser.Tag;

import java.util.Date;

/**
 * User: zhong.huang
 * Date: 13-5-29
 */
public class BaseModel extends ParserModel {

    @Tag(required = true, clazz = "zm-tag-editor-labels zg-clear", tagType = Tag.TYPE.TEXT)
    private String tags;

    @Tag(required = true, id = "zh-question-title", tagType = Tag.TYPE.TEXT)
    private String title;

    @Tag(required = false, id = "zh-question-detail", tagType = Tag.TYPE.HTML)
    private String content;

    private Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public BaseModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
            "tags='" + tags + '\'' +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            '}';
    }
}
