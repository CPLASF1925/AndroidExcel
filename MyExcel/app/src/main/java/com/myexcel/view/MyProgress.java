package com.myexcel.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.myexcel.R;


/**
 * 名称：范围进度条 没做动画效果
 * 创建人：邹安富
 * 创建时间：2018/3/2
 * 详细说明：
 */
public class MyProgress extends View {
    private Paint backPaint;
    private Paint fillPaint;

    private int backColor;
    private int fillColor;

    private float layout_width;
    private float layout_height;
    private int maxProgress;
    private int startProgress;
    private int endProgress;

    public MyProgress(Context context) {
        super(context);
    }

    public MyProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setStartProgress(int startProgress) {
        this.startProgress = startProgress;
    }

    public void setEndProgress(int endProgress) {
        this.endProgress = endProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public void setStardEndProgress(int start, int endProgress) {
        this.startProgress = start;
        this.endProgress = endProgress;
        invalidate();
    }

    public void clearProgress() {
        this.startProgress = 0;
        this.endProgress = 0;
        invalidate();
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyProgress);

        layout_height = a.getDimension(R.styleable.MyProgress_mpLayoutHeight, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics()));
        layout_width = a.getDimension(R.styleable.MyProgress_mpLayoutWidth, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, context.getResources().getDisplayMetrics()));
        maxProgress = a.getInteger(R.styleable.MyProgress_mpMaxValues, 100);
        startProgress = a.getInteger(R.styleable.MyProgress_mpStartValues, 0);
        endProgress = a.getInteger(R.styleable.MyProgress_mpEndValues, 0);
        backColor = a.getColor(R.styleable.MyProgress_mpBackGroundColor, getResources().getColor(R.color.white));
        fillColor = a.getColor(R.styleable.MyProgress_mpFillGroundColor, getResources().getColor(R.color.red));


        backPaint = new Paint();
        backPaint.setAntiAlias(true);
        backPaint.setColor(backColor);
        backPaint.setDither(true);//防抖动
        backPaint.setStyle(Paint.Style.FILL);

        fillPaint = new Paint();
        fillPaint.setAntiAlias(true);
        fillPaint.setColor(fillColor);
        fillPaint.setDither(true);
        fillPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int measureView_width = measureView(widthMode, widthSize, layout_width);
        int measureView_height = measureView(heightMode, heightSize, layout_height);
        setMeasuredDimension(measureView_width, measureView_height);
    }


    private int measureView(int mode, int size, float defaultSize) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                if (size > defaultSize) {
                    size = (int) Math.ceil(defaultSize);
                }
                return MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
            case MeasureSpec.EXACTLY:
                return MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        }
        return -1;
    }


    private static final String TAG = "MyProgress";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, layout_width, layout_height, backPaint);

        double startReta = (double) startProgress / maxProgress;
        double endReta = (double) endProgress / maxProgress;

        float fillStartWidth = (float) (startReta * layout_width);
        float fillEndWidth = (float) (endReta * layout_width);

        Log.i(TAG, "onDraw: startProgress==" + startProgress);
        Log.i(TAG, "onDraw: endProgress==" + endProgress);
        Log.i(TAG, "onDraw: maxProgress==" + maxProgress);

        Log.i(TAG, "onDraw: endReta==" + endReta);
        Log.i(TAG, "onDraw: fillStartWidth==" + fillStartWidth);
        Log.i(TAG, "onDraw: fillEndWidth==" + fillEndWidth);

        canvas.drawRect(fillStartWidth, 0, fillEndWidth, layout_height, fillPaint);

    }
}
