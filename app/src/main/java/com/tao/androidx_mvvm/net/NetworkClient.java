package com.tao.androidx_mvvm.net;

import com.tao.androidx_mvvm.Constant;
import com.tao.androidx_mvvm.basis.BaseApplication;
import com.tao.androidx_mvvm.net.cookie.CookieJarImpl;
import com.tao.androidx_mvvm.net.cookie.PersistentCookieStore;
import com.tao.androidx_mvvm.utils.LogUtil;
import com.tao.androidx_mvvm.utils.NetworkUtils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author: tao
 * @time: 2019/1/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 网络请求客户端
 */
public class NetworkClient {

    private static NetworkClient INSTANCE = null;

    private static Retrofit retrofit;
    private static final int TIME_OUT = 60*5;

    /**
     * 私有化构造方法
     */
    private NetworkClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> LogUtil.i("HttpLog",message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(BaseApplication.getInstance())))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static NetworkClient getNetworkClient (){
        return INSTANCE == null? INSTANCE = new NetworkClient() : INSTANCE;
    }

    /**
     * 获取api
     * @param service
     * @param <T>
     * @return
     */
    public <T> T createApi(Class<T> service){
        return retrofit.create(service);
    }

}
