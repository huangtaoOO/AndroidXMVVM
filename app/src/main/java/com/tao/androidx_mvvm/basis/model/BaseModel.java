package com.tao.androidx_mvvm.basis.model;


import com.tao.androidx_mvvm.basis.net.BaseSubscriber;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseModel {

    public void getData(int code, BaseSubscriber subscriber , Object... parameter){
        //TODO 调用网络请求前的操作
        requestInNet(code, subscriber ,parameter);
    }

    /**
     * 网络请求
     * @param code 请求对应的code用于区分不同的请求
     * @param subscriber 回调
     * @param parameter 可变参数列表 存放请求所需参数
     */
    protected abstract void requestInNet(int code,BaseSubscriber subscriber ,Object... parameter);
}
