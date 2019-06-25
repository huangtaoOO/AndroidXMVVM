package com.tao.androidx_mvvm.dao;

import com.tao.androidx_mvvm.bean.User;
import com.tao.androidx_mvvm.utils.GSonUtils;
import com.tao.androidx_mvvm.utils.SharedPreferencesUtils;

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
    public static void saveUserForm(User user) {
        String value = GSonUtils.toString(user);
        SharedPreferencesUtils.saveSharedPreferences(SP_KEY_USER, value);
    }

    /** 获取已登录帐号用户对象，使用SharedPreferences缓存 */
    public static User getUserForm() {
        String accountFormStr = SharedPreferencesUtils.getSharedPreferences().getString(SP_KEY_USER, null);
        return GSonUtils.parse2Object(accountFormStr, User.class);
    }
}
