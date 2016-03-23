package demo.cn.com.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by rander on 16-3-16.
 */
public class CycleProgress extends View{

    int colorId = android.R.color.holo_blue_dark;

    int colorId2 = android.R.color.holo_orange_dark;

    int mProgress = 0;

    int curColorId = colorId;

    int speed = 20;

    public CycleProgress(Context context) {
        this(context,null);
    }

    public CycleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CycleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    if(mProgress == 360)
                    {
                        mProgress = 0;
                        int temp = colorId;
                        colorId = colorId2;
                        colorId2 = temp;
                    }
                    mProgress++;

                    try {
                        Thread.sleep(speed);
                        postInvalidate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    Paint mPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        int centre = getWidth()/2;
        Log.e("TAG"," ========>>>>>>> " + centre);
        int radius = centre - 100;
        mPaint.setStrokeWidth(200);
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        mPaint.setColor(getResources().getColor(colorId)); // 设置圆环的颜色
        canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
        mPaint.setColor(getResources().getColor(colorId2)); // 设置圆环的颜色
        canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
    }
}
