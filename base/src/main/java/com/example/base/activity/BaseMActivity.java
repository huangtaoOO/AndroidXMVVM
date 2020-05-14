package com.example.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.example.base.BR;
import com.example.base.bean.MessageEvens;
import com.example.base.utils.LogUtil;
import com.example.base.viewmodel.BaseViewModel;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.SoftReference;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author: tao
 * @time: 2019/2/24
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseMActivity<VM extends BaseViewModel,VDB extends
        ViewDataBinding> extends AppCompatActivity {
    
    private AtomicBoolean needHandler = new AtomicBoolean(false);
    private SoftReference<ArrayDeque<Object>> queueReference;

    protected VDB binding;
    protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeBinding();
        onBinding();
        initUI();
        setDefaultObservers();
    }

    protected void setDefaultObservers() {
        viewModel.startIntent.observe(this, o -> {
            if ( o instanceof Intent ){
                boolean needFinish = ((Intent) o).getBooleanExtra("needFinish",false);
                int requestCode = ((Intent) o).getIntExtra("requestCode",
                        this.getClass().getCanonicalName()==null?-1:this.getClass().getCanonicalName().length());
                startActivityForResult((Intent) o,requestCode);
                if (needFinish){
                    finish();
                }
            }
        });
    }

    /**
     * 设置布局文件id
     * @return  layout id
     */
    protected abstract int getLayoutId();

    protected void doBeforeBinding(){}

    protected void onBinding(){
        if (!BaseViewModel.class.isAssignableFrom(setViewModel())){
            Log.e("BaseMvvmActivity","setViewModel() 的返回值不满足 BaseViewModel ") ;
            throw new ClassCastException("setViewModel() 的返回值不满足 BaseViewModel ");
        }
        viewModel = new ViewModelProvider(this).get(setViewModel());
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setLifecycleOwner(this);
        binding.setVariable(BR.viewModel,viewModel);
    }

    /**
     * 设置ViewModel
     * @return viewModel
     */
    @NotNull
    protected abstract Class<VM> setViewModel();

    /**
     * 初始化ui的方法
     */
    protected abstract void initUI();

    public void changeIntoLightStatusBar(boolean isLight){
        if (isLight){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else{
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    public synchronized void dispatchMessage(Object object){
        if (object instanceof MessageEvens){
            if (((MessageEvens) object).getType() == MessageEvens.PROMPTLY){
                handlerMsg(((MessageEvens) object).getData());
            }else {
                if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED){
                    handlerMsg(object);
                }else {
                    if (queueReference==null){
                        queueReference = new SoftReference<>(new ArrayDeque<>(4));
                    }
                    if (queueReference.get()!=null){
                        queueReference.get().offer(object);
                    }
                    needHandler.set(true);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needHandler.get()){
           if (queueReference.get()!=null){
               while (true){
                   Object o = queueReference.get().poll();
                   if (o == null){
                       break;
                   } else {
                       handlerMsg(o);
                   }
               }
           }else {
               LogUtil.e("BaseMvvmActivity","可能存在信息丢失");
           }
        }
    }

    /**
     * 处理全局传递进来的msg
     * @param object MessageEvens
     */
    public abstract void handlerMsg(Object object);

    public int getTag(){
        return hashCode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.unsubscribe();
    }
}
