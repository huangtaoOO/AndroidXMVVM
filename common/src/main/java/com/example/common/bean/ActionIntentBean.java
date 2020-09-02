package com.example.common.bean;

import android.content.Intent;

import java.io.Serializable;

/**
 * @Author: tao
 * @ClassName: ActionIntentBean
 * @Time: 2020/9/2 22:14
 * @E-mail: 1462320178@qq.com
 * @version: 1.0
 * @Description: java类作用描述
 * @Exception: 无
 */
public class ActionIntentBean implements Serializable {

    private final ActionEnum action;

    private final Intent intent;

    public ActionIntentBean(ActionEnum action, Intent intent) {
        this.action = action;
        this.intent = intent;
    }

    public ActionEnum getAction() {
        return action;
    }

    public Intent getIntent() {
        return intent;
    }

    @Override
    public String toString() {
        return "ActionIntentBean{" +
                "action=" + action +
                ", intent=" + intent +
                '}';
    }
}
