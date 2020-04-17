package com.example.base.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by JayPhone on 2018/2/1.
 * 尺寸转换工具
 */
public class DimenUtil {
    /**
     * 将dp转换为px
     */
    public static int dp2px(Context context, int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 将dp转换为px
     */
    public static float dp2px(Context context, float dpValue){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    /**
     * 将sp转换为px
     */
    public static float sp2px(Context context, float spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

}
