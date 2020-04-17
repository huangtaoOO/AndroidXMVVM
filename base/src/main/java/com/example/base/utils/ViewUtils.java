package com.example.base.utils;

import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

/**
 * @author: tao
 * @time: 2019/6/12
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ViewUtils {

    @BindingAdapter("typeface")
    public static void setTypeface(final TextView view, @Nullable final Typeface typeface){
        if (SystemUtil.isMainThread()){
            view.setTypeface(typeface);
        }else {
            view.post(() -> view.setTypeface(typeface));
        }
    }

    @BindingAdapter("textColor")
    public static void setTextColor(final TextView view, @Nullable final ColorStateList colors){
        if (colors == null){
            return;
        }
        if (SystemUtil.isMainThread()){
            view.setTextColor(colors);
        }else {
            view.post(() -> view.setTextColor(colors));
        }
    }

    @BindingAdapter("selection")
    public static void setSelection(final EditText editText, @Nullable final Integer index){
        if (index == null){
            return;
        }
        if (SystemUtil.isMainThread()){
            editText.setSelection(index);
        } else {
            editText.post(()-> editText.setSelection(index));
        }
    }

    /**
     * 1 LEFT_CROP,
     * 2 TOP_CROP,
     * 3 RIGHT_CROP,
     * 4 BOTTOM_CROP
     * @param view
     * @param cropType
     */
    @BindingAdapter("cropType")
    public static void setCropType(final ImageView view, @Nullable final Integer cropType){
        if (cropType == null){
            return;
        }
        if (SystemUtil.isMainThread()){
            setCropTypeOnUIThread(view, cropType);
        }else {
            view.post(() -> {
                setCropTypeOnUIThread(view, cropType);
            });
        }
    }

    public static void setCropTypeOnUIThread(final ImageView view, final int cropType){
        if (view.getDrawable() == null){
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setCropTypeOnUIThread(view,cropType);
                }
            },40);
            return;
        }
        final Matrix matrix = view.getImageMatrix();
        final int dwidth = view.getDrawable().getIntrinsicWidth();
        final int dheight = view.getDrawable().getIntrinsicHeight();
        final int vwidth = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
        final int vheight = view.getHeight() - view.getPaddingTop() - view.getPaddingBottom();
        float scale = 1;
        float dx = 0, dy = 0;
        if (cropType == 4){
            if (dwidth * vheight > vwidth * dheight) {
                scale = (float) vheight / (float) dheight;
                dx = (vwidth - dwidth * scale) * 0.5f;
            } else {
                scale = (float) vwidth / (float) dwidth;
                dy = (vheight - dheight * scale);
            }
        }else if (cropType == 3){
            if (dwidth * vheight > vwidth * dheight) {
                scale = (float) vheight / (float) dheight;
                dx = (vwidth - dwidth * scale);
            } else {
                scale = (float) vwidth / (float) dwidth;
                dy = (vheight - dheight * scale) * 0.5f;
            }
        }else if (cropType == 2){
            if (dwidth * vheight > vwidth * dheight) {
                scale = (float) vheight / (float) dheight;
                dx = (vwidth - dwidth * scale) * 0.5f;
            } else {
                scale = (float) vwidth / (float) dwidth;
            }
        } else if (cropType == 1){
            if (dwidth * vheight > vwidth * dheight) {
                scale = (float) vheight / (float) dheight;
            } else {
                scale = (float) vwidth / (float) dwidth;
                dy = (vheight - dheight * scale) * 0.5f;
            }
        }
        matrix.setScale(scale, scale);
        matrix.postTranslate(Math.round(dx), Math.round(dy));
        view.invalidate();
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImageOnTheInternet(final ImageView imageView, final String url) {
        if (null != url && url.startsWith("http")) {
            imageView.post(() ->
                Glide.with(imageView.getContext()).load(url).into(imageView));
        }
    }
}
