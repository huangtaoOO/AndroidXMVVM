package com.example.aidlservice.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
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

    private int radius = 1;
    //粒子总数
    private final int PARTICLE_SIZE = 1000;
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
            if(it.getOffSet() >it.getMaxOffSet()){
                it.setOffSet(0);
                it.setSpeed(random.nextInt(5)+1);
            }
            it.setAlpha(random.nextInt(256));
            it.setX(centerX + (float) Math.cos(it.getAngle())*(radius + it.getOffSet()));
            if (it.getY() > centerY){
                it.setY((float) Math.sin(it.getAngle())*(radius + it.getOffSet()) + centerY);
            }else {
                it.setY((float) (centerY - Math.sin(it.getAngle() )* (radius+it.getOffSet())));
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

        path.addCircle(centerX,centerY,radius,Path.Direction.CCW);
        pathMeasure.setPath(path,false);
        float nextX = 0f;
        float nextY = 0f;
        Particle p;
        particleList.clear();
        for (int i = 0 ; i < PARTICLE_SIZE; i ++ ){
            pathMeasure.getPosTan(i / (float)PARTICLE_SIZE * pathMeasure.getLength(), pos, tan);
            nextX = pos[0]+random.nextInt(6) - 3f; //X值随机偏移
            nextY=  pos[1]+random.nextInt(6) - 3f;//Y值随机偏移
            p = new Particle(nextX,nextY,random.nextInt(5));
            p.setSpeed(random.nextInt(5)+1);
            p.setAlpha(random.nextInt(256));
            p.setAngle(Math.acos(((pos[0] - centerX) / radius)));
            p.setColour(getColor());

            particleList.add(p);
        }
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        //canvas.drawPath(path,paint);
        for (Particle particle : particleList){
            paint.setAlpha(particle.getAlpha());
            paint.setColor(particle.getColour());
            canvas.drawCircle(particle.getX(), particle.getY(), particle.getRadius(), paint);
        }
    }

    private int getColor(){
        int i = random.nextInt(11);
        switch (i) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.DKGRAY;
            case 2:
                return Color.GRAY;
            case 3:
                return Color.LTGRAY;
            case 4:
                return Color.WHITE;
            case 5:
                return Color.RED;
            case 6:
                return Color.GREEN;
            case 7:
                return Color.BLUE;
            case 8:
                return Color.YELLOW;
            case 9:
                return Color.CYAN;
            case 10:
                return Color.MAGENTA;
            default:
                return Color.WHITE;
        }
    }
}
