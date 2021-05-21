package com.example.aidlservice.view;

/**
 * @author: tao
 * @time: 2020/10/23
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class Particle {
    //x
    private float x ;
    //Y
    private float y ;
    //半径
    private float radius ;
    //当前移动距离
    private float offSet;

    private float offSetX;

    private float offSetY;

    private float direction;
    //速度
    private float speed;
    //角度
    private double angle;
    //最大移动距离
    private float maxOffSet = 500F;
    //透明度
    private int alpha ;

    private int colour;

    public Particle(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public Particle(float x, float y, float radius, float offSetX, float offSetY, float direction,
                    float speed, double angle, float maxOffSet) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.direction = direction;
        this.speed = speed;
        this.angle = angle;
        this.maxOffSet = maxOffSet;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public float getOffSetX() {
        return offSetX;
    }

    public void setOffSetX(float offSetX) {
        this.offSetX = offSetX;
    }

    public float getOffSetY() {
        return offSetY;
    }

    public void setOffSetY(float offSetY) {
        this.offSetY = offSetY;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public float getMaxOffSet() {
        return maxOffSet;
    }

    public void setMaxOffSet(float maxOffSet) {
        this.maxOffSet = maxOffSet;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getAlpha() {
        return alpha;
    }

    public float getOffSet() {
        return offSet;
    }

    public void setOffSet(float offSet) {
        this.offSet = offSet;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "x=" + x +
                ", y=" + y +
                ", radius=" + radius +
                ", offSet=" + offSet +
                ", offSetX=" + offSetX +
                ", offSetY=" + offSetY +
                ", direction=" + direction +
                ", speed=" + speed +
                ", angle=" + angle +
                ", maxOffSet=" + maxOffSet +
                ", alpha=" + alpha +
                '}';
    }
}
