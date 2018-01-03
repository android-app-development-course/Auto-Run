package com.example.al.auto_run.customview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by windy on 2017/12/30.
 */

public class Painter {
    private Canvas mCanvas;

    private static Painter INSTANCE;

    private Painter(Canvas canvas){
        mCanvas = canvas;
    }

    public static Painter build(Canvas canvas){
        if (INSTANCE == null){
            INSTANCE = new Painter(canvas);
        }
        return INSTANCE;
    }

    public void drawRoundRect(float left, float top, float width, float height, float round, Paint paint){
        RectF rectF = new RectF(left, top, left + width, top + height);
        //去除底部圆角
        Paint tPaint = new Paint(paint);
        tPaint.setStrokeWidth(30);
        drawLine(left, top + height - 10, left + width, top + height - 10, tPaint);
        mCanvas.drawRoundRect(rectF, round, round, paint);
    }

    public void drawLine(float x, float y, float x1, float y1, Paint paint){
        mCanvas.drawLine(x, y, x1, y1, paint);
    }

    public void drawText(String text, float x, float y, Paint paint){
        mCanvas.drawText(text, x, y, paint);
    }
}
