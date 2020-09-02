package com.example.common.view;

import android.util.SparseArray;

import androidx.lifecycle.ViewModel;

/**
 * @Author: tao
 * @ClassName: DataBindingConfig
 * @Time: 2020/8/29 8:48
 * @E-mail: 1462320178@qq.com
 * @version: 1.0
 * @Description: java类作用描述
 * @Exception: 无
 */
public class DataBindingConfig {
    private int layout;

    private int vmVariableId;

    private ViewModel stateViewModel;

    private SparseArray<Object> bindingParams = new SparseArray<>();

    public DataBindingConfig(int layout, int vmVariableId, ViewModel stateViewModel) {
        this.layout = layout;
        this.vmVariableId = vmVariableId;
        this.stateViewModel = stateViewModel;
    }

    public int getLayout() {
        return layout;
    }

    public int getVmVariableId() {
        return vmVariableId;
    }

    public ViewModel getStateViewModel() {
        return stateViewModel;
    }

    public SparseArray getBindingParams() {
        return bindingParams;
    }

    public DataBindingConfig addBindingParam(int variableId, Object object) {
        if (bindingParams.get(variableId) == null) {
            bindingParams.put(variableId, object);
        }
        return this;
    }
}
