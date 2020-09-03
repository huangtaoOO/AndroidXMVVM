package com.example.common.view;

import androidx.databinding.ViewDataBinding;

import com.example.common.viewmodel.RxJavaViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * @author: tao
 * @time: 2020/9/3
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public abstract class BaseLoadingActivity<VDB extends ViewDataBinding,VM extends RxJavaViewModel>
        extends BaseDataBingActivity<VDB ,VM>{

    private QMUITipDialog loadDialog;

    @Override
    protected void setDefaultObservers() {
        super.setDefaultObservers();
        mViewModel.loading.observe(this, it->{
        });
    }

    protected final void showLoadDialog(){
        if (loadDialog == null){
            loadDialog = new QMUITipDialog.Builder(BaseLoadingActivity.this)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在加载")
                    .create();
        }
        loadDialog.show();
    }

    protected final void dismissLoadDialog(){
        if (loadDialog!=null){
            loadDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadDialog();
    }
}
