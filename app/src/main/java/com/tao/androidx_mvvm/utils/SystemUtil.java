package com.tao.androidx_mvvm.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author: tao
 * @time: 2019/6/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class SystemUtil {
    /**
     * 获取当前进程名
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;

                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //获得屏幕的宽高
    public static int[] getScreenParams(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm!=null){
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        return new int[]{outMetrics.widthPixels,outMetrics.heightPixels};
    }

    public static int getScreenWidth(Context context){
        return getScreenParams(context)[0];
    }

    public static int getScreenHeight(Context context){
        return getScreenParams(context)[1];
    }

    public static Rect getWindowVisibleDisplayFrame(Window window){
        Rect rect = new Rect();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect;
    }

    public static int getWindowVisibleWidth(Window window){
        Rect rect = getWindowVisibleDisplayFrame(window);
        return rect.right - rect.left;
    }

    public static int getWindowVisibleHeight(Window window){
        Rect rect = getWindowVisibleDisplayFrame(window);
        return rect.bottom - rect.top;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
