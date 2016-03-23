package demo.cn.com.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by rander on 16-3-20.
 */
public class CanvasSaveAndRestoreView extends View{
    public CanvasSaveAndRestoreView(Context context) {
        this(context, null);
    }

    public CanvasSaveAndRestoreView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CanvasSaveAndRestoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    Paint mPaint;
    RectF mRectF;
    int mScreenWidth;
    int mScreenHeight;
    int cx;
    int cy;
    private void init() {
        initPaint();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWidth = w;
        mScreenHeight = h;
        cx = mScreenWidth/2;
        cy = mScreenHeight/2;
        mRectF = new RectF();
        mRectF.left = cx - 100;
        mRectF.right = cx + 100;
        mRectF.top = cy - 150;
        mRectF.bottom = cy + 150;
    }

    private static final int MSG = 0x123;
    private int translateX = 0;

    Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case MSG:
                    translateX++;
                    break;
            }
            invalidate();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawArc(mRectF,0,90,true,mPaint);
//        canvas.save();
////        System.out.println("======>>>>>>1: " + canvas.getWidth() + " " + canvas.getHeight());
//        canvas.translate(400, 400);
////        System.out.println("======>>>>>>2: " + canvas.getWidth() + " " + canvas.getHeight());
//        canvas.drawCircle(0,0,100,mPaint);
//        canvas.drawLine(0,0,150,150,mPaint);
//        canvas.restore();
////        canvas.restore();
//        mHandler.sendEmptyMessage(MSG);
        canvas.drawRect(0, 0, 150, 150, mPaint);   // Draw a rectangle with default settings
        canvas.save();                  // Save the default state

        mPaint.setColor(Color.parseColor("#000009FF"));     // Make changes to the settings
        canvas.drawRect(15, 15, 120, 120, mPaint); // Draw a rectangle with new settings

        canvas.save();                  // Save the current state
        mPaint.setColor(Color.parseColor("#800009FF"));
        canvas.drawRect(30, 30, 90, 90, mPaint);   // Draw a rectangle with new settings

        canvas.restore();               // Restore previous state
        canvas.drawRect(45, 45, 60, 60, mPaint);   // Draw a rectangle with restored settings

        canvas.restore();               // Restore original state
        canvas.drawRect(60,60,30,30,mPaint);   // Draw a rectangle with restored settings
        super.onDraw(canvas);
    }
}
