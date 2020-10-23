package com.example.aidlservice.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: tao
 * @time: 2020/10/23
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class ParticleView extends View {

    //view的宽度
    private float viewWidth;
    //view的高度
    private float viewHeight;

    //view的中心点
    private float centerX;
    private float centerY;

    //画笔
    private Paint paint = new Paint();
    //随机生成器
    private Random random = new Random();

    //粒子总数
    private final int PARTICLE_SIZE = 300;
    //粒子
    private List<Particle> particleList = new ArrayList<>(PARTICLE_SIZE);
    //动画
    private ValueAnimator animator;

//    private val pathMeasure = PathMeasure()//路径，用于测量扩散圆某一处的X,Y值
//    private var pos = FloatArray(2) //扩散圆上某一点的x,y
//    private val tan = FloatArray(2)//扩散圆上某一点切线

    private Path path = new Path();
    private PathMeasure pathMeasure = new PathMeasure();
    private float[] pos = new float[2];
    private float[] tan = new float[2];

    public ParticleView(Context context) {
        super(context);
        init();
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        animator = ValueAnimator.ofFloat(0F,1F);
        animator.setDuration(2000);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(it -> {
            updateParticle((Float)it.getAnimatedValue());
            invalidate();
        });
    }

    private void updateParticle(Float animatedValue) {
        for (Particle it : particleList){
//            if (it.getY() - centerY > it.getMaxOffSet()){
//                it.setY(random.nextInt((int) it.getMaxOffSet())+centerY);
//                it.setX(random.nextInt((int) viewWidth));
//                it.setSpeed(random.nextInt(15)+5);
//                it.setAlpha(random.nextInt(256));
//            }
//            it.setY(it.getY()+it.getSpeed());


            if(it.getOffSet() >it.getMaxOffSet()){
                it.setOffSet(0);
                it.setSpeed(random.nextInt(5)+1);
            }
            it.setAlpha(random.nextInt(256));
            it.setX(centerX + (float) Math.cos(it.getAngle())*(280f + it.getOffSet()));
            if (it.getY() > centerY){
                it.setY((float) Math.sin(it.getAngle())*(280F + it.getOffSet()) + centerY);
            }else {
                it.setY((float) (centerY - Math.sin(it.getAngle() )* (280+it.getOffSet())));
            }
            it.setOffSet(it.getSpeed() + it.getOffSet());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;
        centerX = (float)(w/2);
        centerY = (float)(h/2);
        Log.i("onSizeChanged","viewHeight = " + viewHeight);
        Log.i("onSizeChanged","viewWidth = " + viewWidth);
        Log.i("onSizeChanged","centerX = " + centerX);
        Log.i("onSizeChanged","centerY = " + centerY);

        path.addCircle(centerX,centerY,280F,Path.Direction.CCW);
        pathMeasure.setPath(path,false);
        float nextX = 0f;
        float nextY = 0f;
        Particle p;
        for (int i = 0 ; i < PARTICLE_SIZE; i ++ ){
            pathMeasure.getPosTan(i / (float)PARTICLE_SIZE * pathMeasure.getLength(), pos, tan);
            nextX = pos[0]+random.nextInt(6) - 3f; //X值随机偏移
            nextY=  pos[1]+random.nextInt(6) - 3f;//Y值随机偏移
            //angle=acos(((pos[0] - centerX) / 280f).toDouble())
            p = new Particle(nextX,nextY,random.nextInt(5));
            p.setSpeed(random.nextInt(5)+1);
            p.setAlpha(random.nextInt(256));
            p.setAngle(Math.acos(((pos[0] - centerX) / 280f)));

//            p = new Particle(random.nextInt((int)viewWidth),centerY,random.nextInt(5));
//            p.setY(random.nextInt((int) p.getMaxOffSet())+centerY);
//            p.setSpeed(random.nextInt(15)+5);
//            p.setAlpha(random.nextInt(256));
            particleList.add(p);
        }
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        for (Particle particle : particleList){
            paint.setAlpha(particle.getAlpha());
            canvas.drawCircle(particle.getX(), particle.getY(), particle.getRadius(), paint);
            Log.e("onDraw",particle.toString());
        }
    }
}
