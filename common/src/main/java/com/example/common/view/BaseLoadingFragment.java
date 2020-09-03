package com.example.common.view;

import androidx.databinding.ViewDataBinding;

import com.example.common.viewmodel.RxJavaViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * @Author: tao
 * @ClassName: BaseLoadingFragment
 * @Time: 2020/9/3 20:46
 * @E-mail: 1462320178@qq.com
 * @version: 1.0
 * @Description: java类作用描述
 * @Exception: 无
 */
public abstract class BaseLoadingFragment<VDB extends ViewDataBinding , VM extends RxJavaViewModel>
        extends BaseDataBingFragment<VDB,VM> {

    private QMUITipDialog loadDialog;

    @Override
    protected void setDefaultObservers() {
        super.setDefaultObservers();
        mViewModel.loading.observe(getViewLifecycleOwner(),it -> {
            if (it!=null && it){
                showLoadDialog();
            }else {
                dismissLoadDialog();
            }
        });
    }

    protected final void showLoadDialog(){
        if (loadDialog == null){
            loadDialog = new QMUITipDialog.Builder(getContext())
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
    public void onDestroy() {
        super.onDestroy();
        dismissLoadDialog();
    }
}
