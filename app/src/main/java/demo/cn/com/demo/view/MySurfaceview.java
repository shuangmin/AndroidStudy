package demo.cn.com.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by rander on 16-3-22.
 */
public class MySurfaceview extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    SurfaceHolder mSurfaceHolder;

    Canvas mCanvas;

    Thread t;

    Paint mPaint;

    boolean isRunning;

    public MySurfaceview(Context context) {
        this(context, null);
    }

    public MySurfaceview(Context context, AttributeSet attrs) {
        this(context, attrs, -2);
    }

    public MySurfaceview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);

        //设置常量
        this.setKeepScreenOn(true);
    }

    @Override
    public void run() {
        while (isRunning) {
            System.out.println("======>>>>>>>>");
            draw();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(getMeasuredWidth(),getMeasuredHeight()) - getPaddingLeft();
        mRectF = new RectF(0,0,width,width);
        setMeasuredDimension(width,width);
    }

    private void draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();
            if (mCanvas != null) {
//                mCanvas.drawOval(mRectF,mPaint);
//                int startAngle = 0;
//                int perAngle = 360/6;
//                int sweepAngle = startAngle + perAngle;
//                int[] colorIds = {Color.parseColor("#FF00FF00"),Color.parseColor("#FF0000FF")};
//                for(int i = 0 ; i < 6; i++)
//                {
//                    mPaint.setColor(colorIds[i%colorIds.length]);
//                    System.out.println("===>>>>" + startAngle + " " + sweepAngle);
//                    mCanvas.drawArc(mRectF, startAngle, sweepAngle, true, mPaint);
//                    startAngle += perAngle;
//                }

                mCanvas.drawRect(0,0,100,100,mPaint);
                isRunning = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }

    RectF mRectF;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#FFFF0000"));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        isRunning = true;
        t = new Thread(MySurfaceview.this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }
}
