package demo.cn.com.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.ToggleButton;

import demo.cn.com.demo.view.CanvasSaveAndRestoreView;
import demo.cn.com.demo.view.CanvasView;
import demo.cn.com.demo.view.ImgView;
import demo.cn.com.demo.view.ToggleLinearLayout;


public class MainActivity extends Activity {
    ToggleLinearLayout toll;
    ImgView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(imgView);

    /*    toll = (ToggleLinearLayout)findViewById(R.id.tll);
        toll.setPopUpWindowClickListener(new ToggleLinearLayout.OnPopUpWindowClickListener() {
            @Override
            public void onPopupWindowClick() {
                Toast.makeText(MainActivity.this,"Yes I am clicked ",1).show();
            }
        });

        toll.setOnListViewItemClick(new ToggleLinearLayout.OnListViewItemClick() {
            @Override
            public void onItemClick(int index) {
                Toast.makeText(MainActivity.this,index+"",0).show();
            }
        });*/
;
    }

}
