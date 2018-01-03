package com.example.al.auto_run.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.al.auto_run.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by windy on 2017/12/28.
 */

public class HistogramCharView extends View {
    private final int WEEKNUM = 7;
    private final int STROKE_WIDTH = 5;

    private Painter mPainter;
    //字体颜色
    private int mTextColor;
    //字体大小
    private int mTextSize;
    //图表颜色
    private int mGramColor;

    private float mDistance;
    //屏幕宽高
    private int mWidth;
    private int mHeight;

    private Paint mTextPaint;
    private Paint mGramPaint;

    //0点至当前时间各个小时步数记录数据
    private List<Integer> mStepNums = new ArrayList<>();
    //根据所给数据，计算所得的单位步数占总步数的百分比，用于计算柱形的高度
    private List<Float> mDegrees = new ArrayList<>();
    //X/时间轴
    private List<String> mWeekDays = new ArrayList<>();

    public HistogramCharView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistogramCharView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HistogramCharView, defStyleAttr, 0);

        initAttrs(typedArray);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight() - 20;
        Log.v("CHANGE", mWidth + " " + mHeight);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        int width, height;
//
//        if (widthMode == MeasureSpec.EXACTLY){
//            width = widthSize;
//        } else{
//             width = mWidth;
//        }
//
//        if (heightMode == MeasureSpec.EXACTLY){
//            height = heightSize;
//        } else {
//            float ascent = mTextPaint.ascent();
//            float descent = mTextPaint.descent();
//            height = (int) (-ascent + descent) + getPaddingBottom() + getPaddingTop();
//        }
//        Log.v("MEASURE", width + " " + height);
//        setMeasuredDimension(width, height);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPainter = Painter.build(canvas);

        //画出x,y轴
        float x = 100;
        float y = 100;
        float x1 = 100;
        float y1 = mHeight - 100;
        float x2 = x1 + (mWidth - 200);
        float y2 = y1;
        mTextPaint.setStrokeWidth(STROKE_WIDTH);
        mPainter.drawLine(x, y, x1, y1, mTextPaint);
        mPainter.drawLine(x1, y1, x2, y2, mTextPaint);

        //求出间隔，并画出x轴值
        mDistance = (x2 - x1) / WEEKNUM;
        float x3 = x1 + 25;
        float y3 = y2 + 65;
        for (int i = 0; i < WEEKNUM; i++){
            mPainter.drawText(mWeekDays.get(i), x3, y3, mTextPaint);
            x3 += mDistance;
        }

        //画柱形图
        if (mDegrees.size() != 0) {
            float yHeight = y1 - y;
            float x4 = x1; //矩形leftX
            for (int i = 0; i < WEEKNUM; i++) {
                float gramHeight = yHeight * mDegrees.get(i);
                float y4 = y1 - gramHeight; //矩形topY
                mPainter.drawRoundRect(x4 + 20, y4, mDistance - 40, gramHeight, 20, mGramPaint);

                String text = mStepNums.get(i).toString();
                float x5 = x4 + (mDistance - mTextPaint.measureText(text)) / 2;//字体的起始x坐标
                float y5 = y4 - 15;
                mPainter.drawText(text, x5, y5, mTextPaint);
                x4 += mDistance;
            }
        }

        //画本周步数总数
        Paint paint = new Paint();
        paint.setStrokeWidth(30);
        paint.setTextSize(70);
       // paint.setStyle(Style.);
        mPainter.drawText("本周总步数 : " + getSum(mStepNums), x + 20, y + 80, paint);
    }

    private void init(){
        mWeekDays.add("周日");
        mWeekDays.add("周一");
        mWeekDays.add("周二");
        mWeekDays.add("周三");
        mWeekDays.add("周四");
        mWeekDays.add("周五");
        mWeekDays.add("周六");

//        mStepNums.add(100);
//        mStepNums.add(120);
//        mStepNums.add(140);
//        mStepNums.add(100);
//        mStepNums.add(80);
//        mStepNums.add(60);
//        mStepNums.add(160);
    }

    public void setData(List<Integer> listNumber){
        mStepNums.clear();
        mStepNums.addAll(listNumber);
        //计算各步数占比
        float sum = getSum(mStepNums);
        for (int i = 0; i < WEEKNUM; i++){
            mDegrees.add(mStepNums.get(i) / sum);
            Log.v("DEGREE", (mStepNums.get(i) / sum) + "");
        }
    }

    private void initAttrs(TypedArray typedArray){
        int size =  typedArray.getIndexCount();
        for (int i = 0; i < size; i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.HistogramCharView_xtextSize : {
                    mTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                                                                getResources().getDisplayMetrics()));
                }break;
                case R.styleable.HistogramCharView_xtextColor : {
                    mTextColor = typedArray.getColor(attr, Color.BLACK);
                }break;
                case R.styleable.HistogramCharView_gramBackground : {
                    mGramColor = typedArray.getColor(attr, Color.BLUE);
                }break;
            }
        }
        typedArray.recycle();

        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        mGramPaint = new Paint();
        mGramPaint.setColor(mGramColor);
    }

    private float getSum(List<Integer> list){
        float cnt = 0;
        for (Integer integer : list){
            cnt += integer.intValue();
        }
        return cnt;
    }
}
