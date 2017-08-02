package com.wanpu.tantandemo.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wanpu.tantandemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 */

public class ScanPersonView extends View {
    private Context context;
    private Paint bgPaint;
    private Paint scanPaint;
    private boolean threadRunning;
    private int Angle = 0;
    private Paint arcPaint;
    private List<ScanRound> views = new ArrayList<>();
    private ScanThread scanThread;
    private int width;
    private int iconOffset = 0;
    private int iconTime = -1;

    Rect src = new Rect();
    Rect icon = new Rect();


    public ScanPersonView(Context context) {
        super(context, null);
        this.context = context;
    }

    public ScanPersonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scanPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setColor(getResources().getColor(R.color.colorTheme));
        scanThread = new ScanThread(this);
    }


    public void startScan() {
        threadRunning = true;
        scanThread.start();
    }

    public void stopScan() {
        threadRunning = false;
        if (scanThread != null)
            scanThread.interrupt();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w > h)
            w = h;
        else if (h > w)
            h = w;
        width = w;

        src.top = 0;
        src.left = 0;
        src.right = width;
        src.bottom = width;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.radar_bg), null, src, bgPaint);
        for (int i = 0; i < views.size(); i++) {
            arcPaint.setStrokeWidth(views.get(i).getStrokeWidth());
            arcPaint.setAlpha(views.get(i).getAlpha());
            canvas.drawCircle(width / 2, width / 2, views.get(i).getRadius(), arcPaint);
        }
        canvas.rotate(Angle, width / 2, width/ 2);
        canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.radar), null, src, scanPaint);
        canvas.rotate(-Angle, width / 2, width / 2);

        icon.left = width * 3 / 8 - iconOffset;
        icon.top = width * 3 / 8 - iconOffset;
        icon.right = (width / 2) + width / 8 + iconOffset;
        icon.bottom = (width / 2) + width / 8 + iconOffset;
        canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.scan_icon), null, icon, scanPaint);
    }


    ScanRound scanRound;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (threadRunning&&isPointRange(event.getX(), event.getY())) {
                    scanRound = new ScanRound();
                    scanRound.setRadius(getWidth() / 8);
                    scanRound.setStrokeWidth(8);
                    scanRound.setAlpha(255);
                    views.add(scanRound);
                    iconTime = 0;
                    iconOffset = 0;
                }
                break;
        }

        return true;
    }

    protected class ScanThread extends Thread {
        ScanPersonView view;

        public ScanThread(ScanPersonView view) {
            this.view = view;
        }

        @Override
        public void run() {
            while (threadRunning) {
                view.post(new Runnable() {
                    public void run() {
                        Angle = Angle + 1;
                        view.invalidate();
                        if (Angle == 360)
                            Angle = 0;
                        for (int i = 0; i < views.size(); i++) {
                            float r = views.get(i).getRadius();
                            r = r + 1;
                            views.get(i).setRadius(r);
                            float w = 8 * (1 - r / (getWidth() / 2));
                            int alpha = (int) (255 * (1 - r / (getWidth() / 2)));
                            views.get(i).setStrokeWidth(w);
                            views.get(i).setAlpha(alpha);
                            if (r > getWidth() / 2) {
                                views.remove(i);
                                i = i - 1;
                            }
                        }

                        if (iconTime == -1) {
                            iconOffset = 0;
                        } else if (iconTime <= 25) {
                            iconOffset = iconOffset + 1;
                        } else if (iconOffset <= 50) {
                            iconOffset = iconOffset - 1;
                        }
                        if (iconOffset == 0)
                            iconTime = -1;
                        if (iconTime != -1)
                            iconTime++;
                    }
                });


                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 判断是否在头像区域内
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isPointRange(float x, float y) {
        float xValue = Math.abs(width / 2 - x);
        float yValue = Math.abs(width / 2 - y);
        double r = Math.sqrt(xValue * xValue + yValue * yValue);
        if (r < width / 4)
            return true;
        return false;
    }

    private class ScanRound {
        private float strokeWidth;
        private float radius;
        private int alpha;

        public float getStrokeWidth() {
            return strokeWidth;
        }

        public void setStrokeWidth(float strokeWidth) {
            this.strokeWidth = strokeWidth;
        }

        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }
    }
}
