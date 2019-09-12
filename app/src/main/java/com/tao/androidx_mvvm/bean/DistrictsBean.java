package com.tao.androidx_mvvm.bean;

import android.content.Intent;

import java.util.List;
import java.util.Map;

/**
 * @author: tao
 * @time: 2019/9/10
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class DistrictsBean {


    /**
     * citycode : 0311
     * adcode : 130181
     * name : 辛集市
     * center : 115.217451,37.92904
     * level : district
     * districts : [{"citycode":"0311","adcode":"130181","name":"南智邱镇","center":"115.189,37.8098","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"王口镇","center":"115.282,37.7806","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"和睦井乡","center":"115.276,37.886","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"辛集镇","center":"115.147,37.9477","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"前营乡","center":"115.371,37.9802","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"辛集经济开发区","center":"115.242,37.983","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"田家庄乡","center":"115.151,37.898","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"天宫营乡","center":"115.452,38.0862","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"新垒头镇","center":"115.205,37.962","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"新城镇","center":"115.343,37.88","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"张古庄镇","center":"115.312,38.1034","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"小辛庄乡","center":"115.243,38.1312","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"马庄乡","center":"115.25,37.7887","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"旧城镇","center":"115.337,37.9961","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"位伯镇","center":"115.206,38.0912","level":"street","districts":[]},{"citycode":"0311","adcode":"130181","name":"中里厢乡","center":"115.346,38.1182","level":"street","districts":[]}]
     */

    private String citycode;
    private String adcode;
    private String name;
    private String center;
    private String level;
    private List<DistrictsBean> districts;

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<DistrictsBean> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictsBean> districts) {
        this.districts = districts;
    }

    @Override
    public String toString() {
        return "DistrictsBean{" +
                "citycode='" + citycode + '\'' +
                ", adcode='" + adcode + '\'' +
                ", name='" + name + '\'' +
                ", center='" + center + '\'' +
                ", level='" + level + '\'' +
                ", districts=" + districts +
                '}';
    }
}
