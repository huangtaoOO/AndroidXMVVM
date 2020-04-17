package com.example.base.bean;

import java.io.Serializable;

/**
 * @author: tao
 * @time: 2019/4/20
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class MessageEvens<T> implements Serializable {
    //立刻处理此消息
    public final static int PROMPTLY = 0 ;
    //当activity显示的时候处理
    public final static int SHOW = 1;

    private int type;

    private T data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        if (type!=PROMPTLY && type!= SHOW){
            this.type = SHOW;
        }else {
            this.type = type;
        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) throws Exception {
        if (Serializable.class.isAssignableFrom(data.getClass())){
            this.data = data;
        }else {
            throw new Exception("data 对象没有实现Serializable接口");
        }
    }
}