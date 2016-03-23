package demo.cn.com.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by rander on 16-3-20.
 */
public class ImgView extends View{
    Bitmap mBitmap;

    public ImgView(Context context) {
        super(context);
    }

    public ImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println("============>>>>>>>>>>>" + getClass().getSimpleName());
        // 绘制位图
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
}
