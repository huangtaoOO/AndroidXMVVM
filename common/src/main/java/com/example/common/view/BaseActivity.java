package com.example.common.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import com.example.common.utils.LogUtil;
import com.example.common.bean.MessageEvens;
import java.lang.ref.SoftReference;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: tao
 * @ClassName: BaseActivity
 * @Time: 2020/8/28 23:40
 * @E-mail: 1462320178@qq.com
 * @version: 1.0
 * @Description: java类作用描述
 * @Exception: 无
 */
public abstract class BaseActivity extends AppCompatActivity {

    private AtomicBoolean needHandler = new AtomicBoolean(false);
    private SoftReference<ArrayDeque<Object>> queueReference;

    public int getTag(){
        return hashCode();
    }


    public synchronized void dispatchMessage(Object object){
        if (object instanceof MessageEvens){
            if (((MessageEvens<?>) object).getType() == MessageEvens.PROMPTLY){
                handlerMsg(((MessageEvens<?>) object).getData());
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
                        needHandler.set(false);
                        break;
                    } else {
                        handlerMsg(o);
                    }
                }
            }else {
                needHandler.set(false);
                LogUtil.e("BaseActivity","可能存在信息丢失");
            }
        }
    }

    /**
     * 处理全局传递进来的msg
     * @param object MessageEvens
     */
    public abstract void handlerMsg(Object object);

}
