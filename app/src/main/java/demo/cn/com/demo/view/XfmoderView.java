package demo.cn.com.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import demo.cn.com.demo.R;

/**
 * Created by rander on 16-3-22.
 */
public class XfmoderView extends LinearLayout implements Runnable{


    public XfmoderView(Context context) {
        this(context, null);
    }

    public XfmoderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("=========>>>>>>>>>>>>>");
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                y = (int) event.getY();
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                toX = (int) event.getX();
                toY = (int) event.getY();
                mPath.lineTo(toX,toY);
                break;
            case MotionEvent.ACTION_UP:
                new Thread(this).start();
                break;
        }
        invalidate();
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mBitmap = Bitmap.createBitmap(width,height-mBottomLayout.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.save();
        mCanvas.translate(0,mBottomLayout.getHeight());
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
        mCanvas.restore();
    }

    int x;
    int y;
    int toX;
    int toY;

    Paint mPaint;
    Bitmap mBitmap;
    Canvas mCanvas;
    Path mPath;
    Bitmap mBgBitmap;
    boolean isCompleted = false;
    LinearLayout mBottomLayout;

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(40);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();

        mBgBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.beauty);
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater lf = LayoutInflater.from(getContext());
        mBottomLayout = (LinearLayout) lf.inflate(R.layout.bottom_layout, null);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        mBottomLayout.setLayoutParams(lp);
        mBottomLayout.setGravity(Gravity.TOP);
        addView(mBottomLayout);
        mBottomLayout.findViewById(R.id.btn_clear).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isCompleted = true;
                postInvalidate();
            }
        });
        mBottomLayout.findViewById(R.id.btn_reset).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isCompleted = false;
                mPath.reset();
                postInvalidate();
            }
        });
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawBitmap(mBgBitmap, 0, mBottomLayout.getHeight(), null);
        if(isCompleted)
        {
            return;
        }
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mPaint.setColor(Color.parseColor("#ffffffff"));
        mCanvas.drawPath(mPath, mPaint);
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

//    @Override
//        protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(mBgBitmap, 0, 0, null);
//        if(isCompleted)
//        {
//            return;
//        }
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//        mPaint.setColor(Color.parseColor("#ffffffff"));
//        mCanvas.drawPath(mPath, mPaint);
//        canvas.drawBitmap(mBitmap,0,0,null);
//    }

    @Override
    public void run() {
        int w = getWidth();
        int h = getHeight();

        float wipeArea = 0;
        float totalArea = w * h;
        Bitmap bitmap = mBitmap;
        int[] mPixels = new int[w * h];

        // 获得Bitmap上所有的像素信息
        bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);

        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                int index = i + j * w;
                if (mPixels[index] == 0)
                {
                    wipeArea++;
                }
            }
        }

        if (wipeArea > 0 && totalArea > 0)
        {
            int percent = (int) (wipeArea * 100 / totalArea);


            if (percent > 60)
            {
                // 清除掉图层区域
                isCompleted = true;
                postInvalidate();

            }

        }

    }
}
