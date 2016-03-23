package demo.cn.com.demo.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import demo.cn.com.demo.R;

/**
 * Created by rander on 16-3-15.
 */
public class ToggleLinearLayout extends LinearLayout implements View.OnClickListener {
    public ToggleLinearLayout(Context context) {
        this(context, null);
    }

    public ToggleLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ToggleLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    ToggleItem[] items;
    String[] titles = {"Hello", "Alpha", "Go"};

    PopupWindow mPopupWindow;

    ListView listView;

    private void init(Context context) {
        mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        items = new ToggleItem[3];

        TextView tv = new TextView(context);
        for (int i = 0; i < items.length; i++) {
            items[i] = new ToggleItem();
            items[i].toggleButton = new ToggleButton(context);
            items[i].toggleButton.setGravity(Gravity.CENTER);
            items[i].title = titles[i];
            items[i].toggleButton.setText(items[i].title);
            items[i].toggleButton.setOnClickListener(this);
        }

        LayoutParams ivLp = new LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
//        line.setLayoutParams(ivLp);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        for (int i = 0; i < items.length; i++) {
            addView(items[i].toggleButton, lp);
            if (i != items.length - 1) {
                ImageView line = new ImageView(context);
                line.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
                addView(line, ivLp);
            }
        }

        initPopupWindow();
    }

    public interface OnPopUpWindowClickListener
    {
        void onPopupWindowClick();
    }

    private OnPopUpWindowClickListener listener;

    public void setPopUpWindowClickListener(OnPopUpWindowClickListener listener) {
        this.listener = listener;
    }

    private Context mContext;

    private void initPopupWindow() {
        initListView();
        TextView textView = new TextView(getContext());
        textView.setText("I am a popup Window");
        View contentView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toggle_item,null);
        mPopupWindow = new PopupWindow(listView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_red_dark)));
        mPopupWindow.setOutsideTouchable(true);
        contentView.findViewById(R.id.potext_text).setOnClickListener(this);
//        mPopupWindow.setAnimationStyle(R.anim.popup_anim);
    }

    public interface OnListViewItemClick
    {
        void onItemClick(int index);
    }

    private OnListViewItemClick onListViewItemClick;

    public void setOnListViewItemClick(OnListViewItemClick onListViewItemClick) {
        this.onListViewItemClick = onListViewItemClick;
    }

    private void initListView() {
        listView = new ListView(getContext());
        String[] strs = {"1","2","3","4","5"};
        listView.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_expandable_list_item_1,strs));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListViewItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.potext_text:
            {
                if(listener != null)
                {
                    listener.onPopupWindowClick();
                }
                break;
            }
            default:
            {
                if (mPopupWindow.isShowing())
                    mPopupWindow.dismiss();
                mPopupWindow.showAsDropDown(v, 0, 0);
                break;
            }
        }

    }
}

