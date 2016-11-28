package com.example.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by joy on 2016/11/28.
 */

public class ClockView extends View {
    private int mCircleX;
    private int mCircleY;
    public ClockView(Context context) {
        super(context);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int result=700;
        int width= MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
//        boolean a=widthMode==MeasureSpec.AT_MOST;
//        boolean b=widthMode==MeasureSpec.UNSPECIFIED;
//        boolean c=heightMode==MeasureSpec.AT_MOST;
//        boolean d=heightMode==MeasureSpec.UNSPECIFIED;
        if((widthMode==MeasureSpec.AT_MOST||widthMode==MeasureSpec.UNSPECIFIED)
                &&(heightMode==MeasureSpec.AT_MOST||heightMode==MeasureSpec.UNSPECIFIED)){

            try{
                throw new NoDeterMineSizeException("至少制定一个宽度或者高度");
            } catch (NoDeterMineSizeException e) {
                e.printStackTrace();
            }
        }else{
            if(widthMode==MeasureSpec.EXACTLY){
                result=Math.min(width,result);
            }
            if(heightMeasureSpec==MeasureSpec.EXACTLY){
                result=Math.min(result,height);
            }
        }
        setMeasuredDimension(result,result);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    public void drawCircle(Canvas canvas){
//        canvas.drawCircle();
    }
}
