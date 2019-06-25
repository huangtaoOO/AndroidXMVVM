package com.tao.androidx_mvvm.utils;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author: tao
 * @time: 2019/6/25
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class GSonUtils {

    /** 转换到对象 */
    public static @Nullable <T> T parse2Object(String objectStr, Class<T> objectClz) {
        try {
            return new Gson().fromJson(objectStr, objectClz);
        } catch (Exception ignored) {}
        return null;
    }

    /** 转换到Json字符串 */
    public static String toString(Object object) {
        return new Gson().toJson(object);
    }

    /** 转换到列表 */
    public static @Nullable
    <T> List<T> parse2List(String listStr, Type type) {
        if (TextUtils.isEmpty(listStr)) {
            return null;
        }
        try {
            Gson gson = new Gson();
            List<T> list = gson.fromJson(listStr, type);
            return new java.util.ArrayList<>(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
