package com.tao.androidx_mvvm.net.api;

import com.tao.androidx_mvvm.bean.PublicNumBean;
import com.tao.androidx_mvvm.bean.ResponseBean;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @POST("/user/login")
    Flowable<ResponseBean> login(@Query("username") String username, @Query("password") String password);
}
