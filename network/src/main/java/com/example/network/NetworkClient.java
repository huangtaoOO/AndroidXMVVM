package com.example.network;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: tao
 * @time: 2020/3/30
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class NetworkClient {

    private volatile static NetworkClient INSTANCE = null;

    private static Retrofit retrofit;
    private static final int TIME_OUT = 60*5;

    /**
     * 私有化构造方法
     */
    private NetworkClient(String ... url){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Log.i("HttpLog",message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                //.cookieJar(new CookieJarImpl(new PersistentCookieStore(BaseApplication.getInstance())))
                .build();
        Retrofit.Builder builder = new Retrofit.Builder();
        if (url == null || url.length < 1){
            builder.baseUrl("https://www.wanandroid.com/");
        }else {
            builder.baseUrl(url[0]);
        }
        retrofit = builder.client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static NetworkClient getNetworkClient (String ... url){
        if (INSTANCE == null){
            synchronized (NetworkClient.class){
                if (INSTANCE == null){
                    INSTANCE = new NetworkClient(url);
                    return INSTANCE;
                }
            }
        }
        return INSTANCE;
    }

    public static void resetNullClient(){
        INSTANCE = null;
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

