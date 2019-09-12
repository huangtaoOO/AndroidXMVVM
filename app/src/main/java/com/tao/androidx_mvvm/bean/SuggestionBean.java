package com.tao.androidx_mvvm.bean;

import java.util.List;

/**
 * @author: tao
 * @time: 2019/9/10
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class SuggestionBean {
    private List<String > keywords;
    private List<String> cities;

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }
}
