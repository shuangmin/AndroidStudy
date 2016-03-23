package demo.cn.com.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import demo.cn.com.demo.R;

/**
 * Created by rander on 16-3-17.
 */
public class CanvasView extends View {
    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //解析自定义属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CanvasView, defStyleAttr, 0);
        final int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CanvasView_maxRadius:
                    maxRadius = typedArray.getInteger(attr, DEFAULT_RADIUS);
                    break;
            }
        }
        typedArray.recycle();
        init();
    }

    private Paint mPaint;
    /**
     * 屏幕宽
     */
    private int mScreenWith;
    /**
     * 屏幕高
     */
    private int mScreenHeight;
    /**
     * 园心x,y
     */
    private int cx;
    private int cy;
    /**
     * 半径，动态变化
     */
    private int mRadius;
    /**
     * 最大半径
     */
    private int maxRadius;
    /**
     * 用户没设的时候默认最大半径
     */
    private static final int DEFAULT_RADIUS = 200;

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWith = w;
        mScreenHeight = h;
        cx = mScreenWith / 2;
        cy = mScreenHeight / 2;
    }

    private static final int MSG = 0x7986;
    /**
     * 是否需要递增
     */
    private boolean isIncrease = true;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG:
                    /**是递增*/
                    if (isIncrease) {
                        /**超过最大值就递减*/
                        if (mRadius > maxRadius) {
                            isIncrease = false;
                        } else {
                            mRadius++;
                        }
                    } else {
                        /**小于0就递增*/
                        if (mRadius < 0) {
                            isIncrease = true;
                        } else {
                            mRadius--;
                        }
                    }
                    invalidate();
                    break;
            }
        }
    };
    /**
     * 波浪数量
     */
    private final int WAVE_COUNT = 10;

    @Override
    protected void onDraw(Canvas canvas) {
//
//        RectF rectF = new RectF();
//        for(int i=0;i<WAVE_COUNT;i++)
//        {
////            canvas.drawCircle(cx, cy, mRadius - 20 * i, mPaint);
//            rectF.top = cy - 100;
//            rectF.left = cx - 100;
//            rectF.bottom = cy + 100;
//            rectF.right = cx + 100;
//
//            canvas.drawArc(rectF,0f,90f,true,mPaint);
//            canvas.save();
//        }
//        canvas.rotate(45);
//
//        canvas.drawLine(0,0,100,100,mPaint);
//
//        canvas.restore();
//        handler.sendEmptyMessage(MSG);

//        drawSaveAndRestore(canvas);
//        drawClip(canvas);
//        drawText(canvas);
//        drawPoint(canvas);
//        drawRouncRect(canvas);
        myDrawRegin(canvas);
        super.onDraw(canvas);
    }

    private void myDrawRegin(Canvas canvas) {
        Region region = new Region();
        region.set(new Rect(0,0,100,200));
        RegionIterator iterator = new RegionIterator(region);
        Rect r = new Rect();
        while (iterator.next(r))
        {
            System.out.println("======>>>>>>>>");
            canvas.drawRect(r,mPaint);
        }
    }

    private void drawRouncRect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);
        paint.setStrokeWidth(15);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawRoundRect(new RectF(0, 0, 200, 400), 100f, 100f, paint);
    }


    private void drawPoint(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setDither(true);
        paint.setStrokeWidth(15);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
//        canvas.drawPoint(100,100,paint);
        canvas.drawLine(downX,downY,moveX,moveY,paint);
    }

    int downX = 0;
    int downY = 0;
    int moveX = 0;
    int moveY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = x;
                moveY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    private static final String DRAW_TEXT = "一二三四";

    private void drawText(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        paint.setDither(true);
        paint.setStrokeWidth(3);
        paint.setTextSize(100);
        paint.setAntiAlias(true);
        //测量文本高
        Rect textBounds = new Rect();
        paint.getTextBounds(DRAW_TEXT, 0, DRAW_TEXT.length(), textBounds);
        int textH = textBounds.height();


        paint.setStyle(Paint.Style.STROKE);
        canvas.drawText(DRAW_TEXT, 0, 100 + textH * 0, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(DRAW_TEXT, 0, 100 + textH * 1 + 50, paint);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText(DRAW_TEXT, 0, 100 + textH * 2 + 100, paint);
    }

    private void drawClip(Canvas canvas) {
        canvas.drawColor(Color.YELLOW);
        canvas.clipRect(new Rect(100, 100, 200, 200));
        canvas.restore();
        canvas.drawColor(Color.GREEN);
        mPaint.setColor(Color.RED);
        canvas.drawLine(0, 0, 120, 120, mPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int w = wm.getDefaultDisplay().getWidth();
        int h = wm.getDefaultDisplay().getHeight();
        setMeasuredDimension(w, h);
    }

    private void drawSaveAndRestore(Canvas canvas) {
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        mPaint.setColor(Color.RED);
        //在(20,20)坐标处画一个半径为20的圆
        canvas.drawCircle(20, 20, 20, mPaint);
        mPaint.setColor(Color.BLUE);
        //给整个画布画一个框
        canvas.drawRect(0, 0, w, h, mPaint);
        mPaint.setColor(Color.GREEN);
        //保存之前的状态
        canvas.save();
        //将坐标系平移到(100,100)的位置
        canvas.skew(1.732f, 0);
//        //画三条线
//        canvas.drawLine(w / 2, 0, 0, h / 2, mPaint);
//        canvas.drawLine(w / 2, 0, w / 2, h, mPaint);
//        canvas.drawLine(w / 2, 0, w, h / 2, mPaint);
//        canvas.restore();
        canvas.restore();
        mPaint.setColor(Color.parseColor("#ffffff00"));
        canvas.drawCircle(30, 30, 30, mPaint);
    }
}