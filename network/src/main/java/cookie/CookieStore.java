package cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * @author: tao
 * @time: 2019/7/1
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public interface CookieStore {

    /**  添加cookie */
    void add(HttpUrl httpUrl, Cookie cookie);

    /** 添加指定httpurl cookie集合 */
    void add(HttpUrl httpUrl, List<Cookie> cookies);

    /** 根据HttpUrl从缓存中读取cookie集合 */
    List<Cookie> get(HttpUrl httpUrl);

    /** 获取全部缓存cookie */
    List<Cookie> getCookies();

    /**  移除指定httpurl cookie集合 */
    boolean remove(HttpUrl httpUrl, Cookie cookie);

    /** 移除所有cookie */
    boolean removeAll();
}
