package com.tao.androidx_mvvm.bean;

/**
 * @author: tao
 * @time: 2019/9/10
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class RegionBean  {


    /**
     * code : 130283
     * text : 迁安市
     */

    private String code;
    private String text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "RegionBean{" +
                "code='" + code + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
