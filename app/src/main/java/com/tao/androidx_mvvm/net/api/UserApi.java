package com.tao.androidx_mvvm.net.api;

import com.tao.androidx_mvvm.bean.PublicNumBean;
import com.tao.androidx_mvvm.bean.RResponse;
import com.tao.androidx_mvvm.bean.ResponseBean;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;


/**
 * @author: tao
 * @time: 2019/1/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public interface UserApi {

    /**
     * 测试，获取公众号列表
     * @return
     */
    @GET("wxarticle/chapters/json")
    Flowable<ResponseBean<ArrayList<PublicNumBean>>> wxarticle();

    @GET("https://www.wanandroid.com/wxarticle/chapters/json")
    Call<String> wxarticl();

    @POST("/user/login")
    Flowable<ResponseBean> login(@Query("username") String username, @Query("password") String password);

    @GET("v3/config/district")
    Call<RResponse> getData(@Query("keywords") String keywords,@Query("subdistrict") String subdistrict
            ,@Query("key") String key);
}
