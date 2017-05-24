package com.bfgirl.administrator.bfgirl.Models;

import java.io.Serializable;

/**
 * Created by Administrator。 on 2017/5/21.
 */

public class GankResults implements Serializable {
private String _id;//": "591a4a02421aa92c794632c8",
    private String createdAt;//": "2017-05-16T08:38:26.35Z",
    private String desc;//": "5-16",

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    private String publishedAt;//": "2017-05-16T12:10:38.580Z",
    private String  source;//": "chrome",
    private String type;//": "福利",
    private String url;//": "http://ww1.sinaimg.cn/large/610dc034ly1ffmwnrkv1hj20ku0q1wfu.jpg",
    private String used;//": true,
    private String who;//": "daimajai"
}
