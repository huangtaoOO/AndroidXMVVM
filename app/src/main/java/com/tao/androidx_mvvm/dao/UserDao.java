package com.tao.androidx_mvvm.dao;

import com.example.base.utils.GSonUtils;
import com.example.base.utils.SharedPreferencesUtils;
import com.tao.androidx_mvvm.bean.UserBean;

/**
 * @author: tao
 * @time: 2019/6/25
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class UserDao {
    /** SharedPreferences缓存Key--已登录帐号 */
    private final static String SP_KEY_USER = "Androidx_MVVM_User_Info";

    /** 缓存已登录帐号用户对象，使用SharedPreferences缓存 */
    public static void saveUserForm(UserBean userBean) {
        String value = GSonUtils.toString(userBean);
        SharedPreferencesUtils.saveSharedPreferences(SP_KEY_USER, value);
    }

    /** 获取已登录帐号用户对象，使用SharedPreferences缓存 */
    public static UserBean getUserForm() {
        String accountFormStr = SharedPreferencesUtils.getSharedPreferences().getString(SP_KEY_USER, null);
        return GSonUtils.parse2Object(accountFormStr, UserBean.class);
    }
}
