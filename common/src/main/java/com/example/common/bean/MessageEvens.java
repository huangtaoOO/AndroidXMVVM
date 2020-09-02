package com.example.common.bean;

import androidx.annotation.Nullable;

import java.io.Serializable;

/**
 * @author: tao
 * @time: 2019/4/20
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class MessageEvens<T extends Serializable> implements Serializable {
    //立刻处理此消息
    public final static int PROMPTLY = 0 ;
    //当activity显示的时候处理
    public final static int SHOW = -1;

    private final int type;

    private final T data;

    public MessageEvens(int type) {
        this.type = type;
        this.data = null;
    }

    public MessageEvens(int type,@Nullable T data) {
        this.type = type;
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "MessageEvens{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}