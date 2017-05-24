package com.bfgirl.administrator.bfgirl.Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator。 on 2017/5/21.
 */

public class BaseModels implements Serializable{
    /**
     * GANK妹纸
     */
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<GankResults> getResults() {
        return results;
    }

    public void setResults(List<GankResults> results) {
        this.results = results;
    }

    private List<GankResults> results;

    public String getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(String showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(String showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    /**
     * 花瓣妹纸
     */
    private String showapi_res_code;
    private String showapi_res_body;
}
