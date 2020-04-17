package com.tao.androidx_mvvm.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.base.utils.DimenUtil;
import com.tao.androidx_mvvm.R;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * @author: tao
 * @time: 2019/9/4
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 默认宽高100dp
 */
public class RingView extends View {

    private Paint paint;

    public RingView(Context context) {
        super(context);
        initPaint();
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public RingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint(){
        paint = new Paint();
        paint.setStrokeWidth(DimenUtil.dp2px(getContext(),3));
        paint.setTextSize(DimenUtil.sp2px(getContext(),50));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        switch (modeWidth){
            case MeasureSpec.EXACTLY:
                //精确测量，无需处理
                break;
            case MeasureSpec.AT_MOST:
                //有建议的最大值，建议不超过
                width = Math.max(width, DimenUtil.dp2px(getContext(),100));
                break;
            case MeasureSpec.UNSPECIFIED:
                //玩命测量
                width = DimenUtil.dp2px(getContext(),100);
                break;
        }
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        switch (modeHeight){
            case MeasureSpec.EXACTLY:
                //精确测量，无需处理
                break;
            case MeasureSpec.AT_MOST:
                //有建议的最大值，建议不超过
                height = Math.max(width,DimenUtil.dp2px(getContext(),100));
                break;
            case MeasureSpec.UNSPECIFIED:
                //玩命测量
                height = DimenUtil.dp2px(getContext(),100);
                break;
        }
        setMeasuredDimension(width,height);
    }

    /**
     * 是否可以触摸旋转
     */
    private boolean isTouchable = false;

    /**
     * 设置是否可以触发旋转
     * @param touchable
     */
    public void setTouchable(boolean touchable){
        isTouchable = touchable;
    }

    /**
     * 偏移量，用来控制旋转
     */
    private int rotationAngle = 0;

    /**
     * 实际上下左右位置
     * 兼容padding
     */
    private int left ;
    private int right ;
    private int top ;
    private int bottom ;

    /**
     * 圆心的位置
     */
    private int centerX;
    private int centerY;



    /**
     * 外大圆半径
     */
    private int radiusAbroad1;
    /**
     * 外小圆半径
     */
    private int radiusAbroad2;

    /**
     * 外大圆
     */
    private RectF rectFAbroad1 ;

    /**
     * 外小圆
     */
    private RectF rectFAbroad2 ;

    /**
     * 内大圆
     */
    private RectF rectFInside1;
    /**
     * 内小圆
     */
    private RectF rectFInside2;

    /**
     * 内大圆半径
     */
    private int radiusInside1;
    /**
     * 内小圆半径
     */
    private int radiusInside2;


    /**
     * 外圆环间距
     */
    private int outerRingSpacing = 0 ;

    /**
     * 内圆环间距
     */
    private int interiorRingSpacing = 0;

    /**
     * 两圆间距
     */
    private int circularSpacing = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("onLayout","left = " + l + " right = " + r + " top = " + t + " bottom = " + b);
        //设置实际位置
        this.left = l + getPaddingLeft();
        this.right = r - getPaddingRight();
        this.top = t + getPaddingTop();
        this.bottom = b - getPaddingBottom();

        //设置圆心的位置
        centerX = (this.right - this.left)/2 + this.left;
        centerY = (this.bottom - this.top)/2 + this.top;

        //设置外大圆的半径
        radiusAbroad1 =  Math.min(this.right - this.left , this.bottom- this.top) / 2;
        //设置外大圆的范围
        if (rectFAbroad1 == null){
            rectFAbroad1 = new RectF(centerX - radiusAbroad1 ,centerY - radiusAbroad1,
                    centerX + radiusAbroad1 ,centerY + radiusAbroad1);
        }else {
            rectFAbroad1.set(centerX - radiusAbroad1 ,centerY - radiusAbroad1,
                    centerX + radiusAbroad1 ,centerY + radiusAbroad1);
        }
        //设置外圆的间距，也就是外环的粗细
        outerRingSpacing = outerRingSpacing == 0 ? DimenUtil.dp2px(getContext(),6):outerRingSpacing;
        //设置外小圆的半径
        radiusAbroad2 = radiusAbroad1 - DimenUtil.dp2px(getContext(),outerRingSpacing);
        //设置外小圆的范围
        if (rectFAbroad2 == null){
            rectFAbroad2 = new RectF(centerX - radiusAbroad2 ,centerY - radiusAbroad2,
                    centerX + radiusAbroad2 ,centerY + radiusAbroad2);
        }else {
            rectFAbroad2.set(centerX - radiusAbroad2 ,centerY - radiusAbroad2,
                    centerX + radiusAbroad2 ,centerY + radiusAbroad2);
        }
        //如果内圆的数据不为空的话，则设置内圆所需要的数据
        if (dataInside!=null){
            circularSpacing = circularSpacing ==0? DimenUtil.dp2px(getContext(),6):circularSpacing;
            radiusInside1 = radiusAbroad2 - circularSpacing;
            if (rectFInside1 == null){
                rectFInside1 = new RectF(centerX - radiusInside1 ,centerY - radiusInside1,
                        centerX + radiusInside1 ,centerY + radiusInside1);
            }else {
                rectFInside1.set(centerX - radiusInside1 ,centerY - radiusInside1,
                        centerX + radiusInside1 ,centerY + radiusInside1);
            }

            interiorRingSpacing = interiorRingSpacing == 0 ?DimenUtil.dp2px(getContext(),6):interiorRingSpacing;

            radiusInside2 = radiusInside1 - interiorRingSpacing;
            if (rectFInside2 == null){
                rectFInside2 = new RectF(centerX - radiusInside2 ,centerY - radiusInside2,
                        centerX + radiusInside2 ,centerY + radiusInside2);
            }else {
                rectFInside2.set(centerX - radiusInside2 ,centerY - radiusInside2,
                        centerX + radiusInside2 ,centerY + radiusInside2);
            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dataAbroad != null){
            //绘制外环的大圆扇形
            int current = rotationAngle;
            for (RingBean bean : dataAbroad){
                paint.setColor(bean.getColor());
                int own = ( bean.getSize() / abroadTotal) * 360;
                canvas.drawArc(rectFAbroad1,current, own,true,paint);
                current = current + own;
            }
            //绘制外环的小圆
            paint.setColor(getContext().getResources().getColor(R.color.white));
            canvas.drawArc(rectFAbroad2,0, 360,true,paint);
            if (dataInside != null){
                //绘制内环的大圆扇形
                current = rotationAngle;
                for (RingBean bean : dataInside){
                    paint.setColor(bean.getColor());
                    int own = ( bean.getSize() / insideTotal) * 360;
                    canvas.drawArc(rectFInside1,current, own,true,paint);
                    current = current + own;
                }
                //绘制内环的小圆
                paint.setColor(getContext().getResources().getColor(R.color.white));
                canvas.drawArc(rectFInside2,0, 360,true,paint);
            }

        }else {
            //TODO 设置成灰色
            paint.setColor(getContext().getResources().getColor(R.color.colorPrimary));
            canvas.drawArc(rectFAbroad1,rotationAngle, 90,true,paint);
            paint.setColor(getContext().getResources().getColor(R.color.white));
            canvas.drawArc(rectFAbroad2,rotationAngle,90,true,paint);
        }
        //绘制中间 总数的显示
        int space = DimenUtil.dp2px(getContext(),3);
        int positionY = (int) (centerY - space - totalSize);
        int positionR = (int) calculateThePointOnTheCircle(centerX,centerY,radiusAbroad2,positionY,true);
        int positionL = (int) calculateThePointOnTheCircle(centerX,centerY,radiusAbroad2,positionY,false);
        //设置字体为粗体
        paint.setTextSize(totalSize);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        paint.setTypeface(font);
        //判断绘制字体所需要的空间
        int need = (int) paint.measureText(TextUtils.isEmpty(totalOfDisplays)?"0":totalOfDisplays);
        if ((positionR- positionL) > need){
            canvas.drawText(TextUtils.isEmpty(totalOfDisplays)?"0":totalOfDisplays,(centerX - (need/2F)),centerY-space,paint);
        }else {
            //绘制不完使用..替代
            int textPosition = 0;
            for (int i = 1 ; i <totalOfDisplays.length() ;i++){
                if (paint.measureText(totalOfDisplays.substring(0,i))
                        > (positionR- positionL)){
                    textPosition = i-2;
                    break;
                }
            }
            String string = totalOfDisplays.substring(0,textPosition) + "..";
            need = (int) paint.measureText(TextUtils.isEmpty(string)?"0":string);
            canvas.drawText(string,(centerX - (need/2F)),centerY-space,paint);
        }
        Typeface fontNormal = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        paint.setTypeface(fontNormal);
        paint.setTextSize(explainSize);
        //绘制中间 描述文字的显示
        positionY = (int) (centerY + space + explainSize);
        positionR = (int) calculateThePointOnTheCircle(centerX,centerY,radiusAbroad2 , positionY ,true);
        positionL = (int) calculateThePointOnTheCircle(centerX,centerY,radiusAbroad2,positionY,false);
        need = (int) paint.measureText(TextUtils.isEmpty(explain)?"无数据":explain);
        if ((positionR- positionL) > need){
            canvas.drawText(TextUtils.isEmpty(explain)?"无数据":explain,(centerX - (need/2F)),centerY-space,paint);
        }else {
            int textPosition = 0;
            for (int i = 1 ; i <explain.length() ;i++){
                if (paint.measureText(explain.substring(0,i))
                        > (positionR- positionL)){
                    textPosition = i-2;
                    break;
                }
            }
            String string = explain.substring(0,textPosition) + "..";
            need = (int) paint.measureText(TextUtils.isEmpty(string)?"0":string);
            canvas.drawText(string,(centerX - (need/2F)),centerY-space,paint);
        }
    }

    /**
     * 外环的数据
     */
    private List<RingBean> dataAbroad;

    /**
     * 外环的总数
     */
    private int abroadTotal;

    /**
     * 设置外环的数据
     * @param list 数据列表
     */
    public void setDataAbroad(List<RingBean> list){
        dataAbroad = list;
        abroadTotal = 0 ;
        for (RingBean bean:list)
            abroadTotal += bean.size;
    }

    /**
     * 内环数据
     */
    private List<RingBean> dataInside;

    /**
     * 内环总数
     */
    private int insideTotal;

    public void setDataInside(List<RingBean> list){
        if (dataAbroad == null)
            throw new NullPointerException("请先调用setDataAbroad方法设置外环数据");
        dataInside = list;
        insideTotal = 0 ;
        for (RingBean bean:list)
            insideTotal += bean.size;
    }

    /**
     * 显示的总数
     */
    private String totalOfDisplays;

    /**
     * 设置显示的总数
     * @param totalOfDisplays 显示的总数
     */
    public void setTotalOfDisplays(String totalOfDisplays){
        this.totalOfDisplays = totalOfDisplays;
    }

    private float totalSize ;

    /**
     * 设置总数的字体大小
     * @param sp
     */
    public void setTotalTextSize(float sp){
        this.totalSize = DimenUtil.sp2px(getContext(),sp);
    }

    /**
     * 说明
     */
    private String explain ;

    /**
     * 设置说明
     * @param explain 说明
     */
    public void setExplain(String explain){
        this.explain = explain;
    }

    /**
     * 说明文字体大小设置
     */
    private float explainSize ;

    /**
     * 设置说明文字的大小
     * @param sp sp
     */
    private void explainSize(int sp){
        this.explainSize = DimenUtil.sp2px(getContext(),sp);
    }

    public static class RingBean{
        /**
         * 显示的文本
         */
        private String text;

        /**
         * 数字大小
         */
        private int size ;

        /**
         * 颜色
         */
        private @ColorRes int color ;

        /**
         * 自定义字段，防止加需求
         */
        private SoftReference<Object> tag;

        public RingBean(String text, int size, int color) {
            this.text = text;
            this.size = size;
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public Object getTag() {
            return tag!=null?tag.get():null;
        }

        public void setTag(Object tag) {
            this.tag = new SoftReference<Object>(tag);
        }
    }

    /**
     * 根据圆的标准方程 求直线Y过圆心（x,y）边上的一个解
     * @param x 圆心（x,y）
     * @param y 圆心（x,y）
     * @param radius 半径
     * @param position 直线Y = position
     * @param isPlus true 返回正解
     * @return 一个解
     */
    private float calculateThePointOnTheCircle(float x, float y, float radius, float position,boolean isPlus) {
        if (isPlus){
            return (float) (Math.sqrt(Math.pow(radius,2) - Math.pow((position - y),2))+x);
        }else {
            return (float) (-Math.sqrt(Math.pow(radius,2) - Math.pow((position - y),2))+x);
        }
    }

    /**
     * 获取触点相当于中心点的角度
     * @param x 触点 x坐标
     * @param y 触点 y坐标
     * @return 角度
     */
    public float getAngleForPoint(float x, float y) {

        double tx = x - centerX, ty = y - centerY;
        double length = Math.sqrt(tx * tx + ty * ty);
        double r = Math.acos(ty / length);

        float angle = (float) Math.toDegrees(r);

        if (x > centerX)
            angle = 360f - angle;

        // add 90° because chart starts EAST
        angle = angle + 90f;

        // neutralize overflow
        if (angle > 360f)
            angle = angle - 360f;
        return angle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isTouchable){
            if (event.getAction() == MotionEvent.ACTION_MOVE){
                float a = getAngleForPoint(event.getX(),event.getY());
                rotationAngle = (int) a;
                invalidate();
            }
            return true;
        }else {
            return super.onTouchEvent(event);
        }
    }
}
