package com.tao.androidx_mvvm.viewmodel;

import android.app.Application;
import android.content.Context;
import android.nfc.FormatException;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tao.androidx_mvvm.basis.viewmodel.BaseViewModel;
import com.tao.androidx_mvvm.bean.DistrictsBean;
import com.tao.androidx_mvvm.bean.RResponse;
import com.tao.androidx_mvvm.bean.RegionBean;
import com.tao.androidx_mvvm.model.ModelOfFirst;
import com.tao.androidx_mvvm.net.NetworkClient;
import com.tao.androidx_mvvm.net.api.UserApi;
import com.tao.androidx_mvvm.utils.FilePathProvider;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

import java.io.*;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: tao
 * @time: 2019/9/10
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ViewModelOfSQL extends BaseViewModel<ModelOfFirst> {

    //INSERT INTO `tb_region`(`code`, `name`, `level`, `remark`, `superior_region_id`, `superior_region_name`, `country`) VALUES (111201, '宋庄镇', 3, NULL, 111200, '通州区', 86);
    private static final String text1 = "INSERT INTO `tb_region`(`code`, `name`, `level`, `remark`, `superior_region_id`, `superior_region_name`, `country`) VALUES (";
    private static final String text2 = ", '";
    private static final String text3 = "', 3, NULL, ";
    private static final String text4 = "', 86);";

    public final MutableLiveData<CharSequence> text = new MutableLiveData<>();

    private SoftReference<Context> softReference = null;

    public ViewModelOfSQL(Application application) {
        super(application);
    }

    @Override
    protected ModelOfFirst setModel() {
        return null;
    }

    public void onClick(View view){
        logic();
    }

    public void setContext(Context context){
        softReference = new SoftReference<>(context);
    }

    private void logic() {
        Observable.create(emitter -> {
            try {
                doSomeThing();
            }catch (Exception e){
                e.printStackTrace();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }});

    }

    private void doSomeThing() throws Exception{
        text.postValue("准备开始");
        Log.i("测试",Thread.currentThread().getName());
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        inputStream = softReference.get().getResources().getAssets().open("city.json");
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(isr);
        String jsonLine;
        while ((jsonLine = reader.readLine()) != null) {
                Log.i("测试1",jsonLine);
                stringBuilder.append(jsonLine);
            }
        reader.close();
        isr.close();
        inputStream.close();
        String result =  stringBuilder.toString();
        Log.i("json",result);
        ArrayList<RegionBean> list = new Gson().fromJson(result, new TypeToken<List<RegionBean>>(){}.getType());
        text.postValue("初始化数据完成，开始解析数据");
        File file = FilePathProvider.createPublicFile(softReference.get(),"sql","citySql.txt");
        Log.i("测试file",file.getAbsolutePath());
        OutputStream outputStream = new FileOutputStream(file);
        StringBuilder writeString ;
        int index = 0 ;
        for (RegionBean bean: list){
            index ++ ;
            text.postValue("初始化数据完成，开始解析数据: " + index + " /" + list.size());
            if (true){
                Call<RResponse> responseCall = NetworkClient.getNetworkClient().createApi(UserApi.class).getData(bean.getText(),"1"
                        ,"5a0462df13010170059e2a8c2cb9c59f6");
                RResponse response = responseCall.execute().body();
                Log.i("测试response",response.toString());
                if (response.getDistricts()!=null && response.getDistricts().size() > 0 ){
                    List<DistrictsBean> districtsBeanList = response.getDistricts().get(0).getDistricts();
                    if (districtsBeanList!=null && districtsBeanList.size() > 0){
                        int num = 0;
                        for (DistrictsBean districtsBean : districtsBeanList){
                            num++;
                            if (num > 1)
                                break;
                            writeString = new StringBuilder();
                            writeString.append(text1);
                            writeString.append(Integer.parseInt(bean.getCode()) + num);
                            writeString.append(text2);
                            writeString.append(districtsBean.getName());
                            writeString.append(text3);
                            writeString.append(bean.getCode());
                            writeString.append(text2);
                            writeString.append(bean.getText());
                            writeString.append(text4);
                            writeString.append("\n");
                            String write = writeString.toString();
                            Log.i("write",write);
                            outputStream.write(write.getBytes("gbk"));
                        }
                    }
                }

                Log.i("RResponse",response.toString());
                Thread.sleep(200);
            }
        }
        outputStream.close();
        text.postValue("输出完成，文件路径" +file.getAbsolutePath());
    }
}
