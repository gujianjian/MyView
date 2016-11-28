package com.example.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * Created by joy on 2016/11/28.
 */

public class ClockView extends View {
    private int mCircleX;
    private int mCircleY;
    private int mRaduis;
    private Paint mPaintCircle;
    private Paint mPaintScale;
    private Paint mPaintScaleSmall;
    private Paint mPaint;
    private int mPadding;
    private int mPointEndLength;
    private int mLineWidth;
    private int mLineLittleWidth;


    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void init() {
        mLineWidth = 30;
        mLineLittleWidth = 20;
        mPadding = 10;
        mPaintCircle = new Paint();
        mPaintCircle.setAntiAlias(true);
        mPaintCircle.setDither(true);
        mPaintCircle.setStyle(Paint.Style.FILL);
        mPaintCircle.setColor(Color.WHITE);

        mPaintScale = new Paint();
        mPaintScale.setAntiAlias(true);
        mPaintScale.setDither(true);
        mPaintScale.setStrokeWidth(1.5f);
        mPaintScale.setColor(Color.BLACK);

        mPaintScaleSmall = new Paint();
        mPaintScaleSmall.setDither(true);
        mPaintScaleSmall.setAntiAlias(true);
        mPaintScaleSmall.setStrokeWidth(1);
        mPaintScaleSmall.setColor(Color.DKGRAY);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(18);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int result = 1000;//设置最小值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        boolean a = widthMode == MeasureSpec.AT_MOST;
        boolean b = widthMode == MeasureSpec.UNSPECIFIED;
        boolean c = heightMode == MeasureSpec.AT_MOST;
        boolean d = heightMode == MeasureSpec.UNSPECIFIED;
        if ((widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED)
                && (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED)) {

            try {
                throw new NoDeterMineSizeException("至少制定一个宽度或者高度");
            } catch (NoDeterMineSizeException e) {
                e.printStackTrace();
            }
        } else {
            if (widthMode == MeasureSpec.EXACTLY) {
                result = Math.min(width, result);
            }
            if (heightMode == MeasureSpec.EXACTLY) {
                result = Math.min(result, height);
            }
        }
        setMeasuredDimension(result, result);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRaduis = (Math.min(w, h)) / 2;
        mCircleX = mRaduis;
        mCircleY = mRaduis;
        mPointEndLength = mRaduis / 6;//指针尾部长度
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.save();
        canvas.translate(mCircleX, mCircleY);
        drawCircle(canvas);
        drawScale(canvas);
        drawPaintPoint(canvas);

        canvas.restore();
        postInvalidateDelayed(1000);
    }

    public void drawCircle(Canvas canvas) {
        canvas.drawCircle(0, 0, mRaduis, mPaintCircle);
    }

    public void drawScale(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                String text = ((i / 5 == 0) ? 12 : (i / 5)) + "";
                Rect rect = new Rect();
                mPaintScale.getTextBounds(text, 0, text.length(), rect);
                canvas.drawLine(0, -mRaduis + mPadding, 0, -mRaduis + mPadding + mLineWidth, mPaintScale);
                canvas.save();
                canvas.translate(0, (rect.bottom - rect.top) - mRaduis + mPadding + mLineWidth + 10);
                canvas.rotate(-6 * i);
                canvas.drawText(text, -(rect.right - rect.left) / 2, (rect.bottom - rect.top) / 2, mPaint);
                canvas.restore();

            } else {
                canvas.drawLine(0, -mRaduis + mPadding, 0, -mRaduis + mPadding + mLineLittleWidth, mPaintScaleSmall);
            }


            canvas.rotate(6);

        }
        canvas.restore();
    }

    public void drawPaintPoint(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        int angelHour = (hour % 12) * 360 / 12;
        int angelMinute=minute*360/60;
        int angelSecond=second*360/60;

        angelHour+=30*angelMinute/360;
        angelMinute+=6*angelSecond/360;
        canvas.save();
        canvas.rotate(angelHour);

        RectF rectF=new RectF(-5,-mRaduis*0.6f,5,mPointEndLength);
        Paint pHour=new Paint();
        pHour.setAntiAlias(true);
        pHour.setStyle(Paint.Style.FILL);
        pHour.setStrokeWidth(5);
        pHour.setColor(Color.BLACK);

        canvas.drawRoundRect(rectF,5,5,pHour);
        canvas.restore();

        canvas.save();
        canvas.rotate(angelMinute);
        RectF rectF2=new RectF(-5,-mRaduis*0.8f,5,mPointEndLength);
        Paint pMiunte=new Paint();
        pMiunte.setAntiAlias(true);
        pMiunte.setStyle(Paint.Style.FILL);
        pMiunte.setStrokeWidth(3);
        pMiunte.setColor(Color.BLACK);

        canvas.drawRoundRect(rectF2,3,3,pMiunte);
        canvas.restore();


        canvas.save();
        canvas.rotate(angelSecond);
        RectF rectF3=new RectF(-5,-mRaduis*0.7f,5,mPointEndLength);
        Paint pSecond=new Paint();
        pSecond.setAntiAlias(true);
        pSecond.setStyle(Paint.Style.FILL);
        pSecond.setStrokeWidth(3);
        pSecond.setColor(Color.RED);

        canvas.drawRoundRect(rectF3,3,3,pSecond);
        canvas.restore();


        canvas.drawCircle(0,0,10,pSecond);
    }
}
