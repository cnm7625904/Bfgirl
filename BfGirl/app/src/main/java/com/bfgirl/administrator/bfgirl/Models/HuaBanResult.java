package com.bfgirl.administrator.bfgirl.Models;

import java.io.Serializable;

/**
 * Created by tckj03 on 2017/5/23.
 */

public class HuaBanResult implements Serializable
{
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String title;
    private String thumb;
    private String url;
}
