package com.tymall.weight;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tymall.R;

/**
 * Created by bzzt on 2018/10/17 0022.
 */
public class MyTitleBar extends RelativeLayout {
    private ImageView back;
    private TextView titleValue;
//    private TextView leftLayout;
    private TextView rightLayout;
//    private TextView titleView;
    private ImageView rightImg;
    private ConstraintLayout titlelayout;

    public MyTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_title_bar, this);
        titlelayout = findViewById(R.id.titlelayout);
        back = findViewById(R.id.titlebar_back);
        titleValue = findViewById(R.id.titlebar_title);
        rightLayout = findViewById(R.id.titlebar_right_tv);
        rightImg = findViewById(R.id.titlebar_right_img);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
        parseStyle(context, attrs);
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTitleBar);
            String title = ta.getString(R.styleable.MyTitleBar_titleBarTitle);
            titleValue.setText(title);
            String rightTitle = ta.getString(R.styleable.MyTitleBar_titleBarRightStr);
            rightLayout.setText(rightTitle);
            rightImg.setVisibility(GONE);
//            Drawable rightDrawable = ta.getDrawable(R.styleable.MyTitleBar_titleBarRightImage);
//            if (null != rightDrawable) {
//                rightImg.setImageDrawable(rightDrawable);
//            }
            /*String leftTitle = ta.getString(R.styleable.MyTitleBar_titleBarLeftStr);
            leftLayout.setText(leftTitle);

            Drawable leftDrawable = ta.getDrawable(R.styleable.MyTitleBar_titleBarLeftImage);
            if (null != leftDrawable) {
                leftLayout.setCompoundDrawables(leftDrawable, null, null, null);
            }

            Drawable background = ta.getDrawable(R.styleable.MyTitleBar_titleBarBackground);
            if (null != background) {
                titlelayout.setBackgroundDrawable(background);
            }*/
            ta.recycle();
        }
    }

    public void setLeftBtnClickListener(OnClickListener onClickListener) {
        back.setOnClickListener(onClickListener);
    }

    public void setRightBtnClickListener(OnClickListener onClickListener) {
        rightLayout.setOnClickListener(onClickListener);
    }
    public void setRightImgClickListener(OnClickListener onClickListener) {
        rightImg.setOnClickListener(onClickListener);
    }

    public void setTitleBarBackground(int color) {
        titlelayout.setBackgroundColor(color);
    }


    public void setRigtBtnColor(int color){
        rightLayout.setTextColor(color);
    }

    public void setTitle(String title) {
        titleValue.setText(title);
    }

    public void setRightImg(int id) {
        rightImg.setVisibility(VISIBLE);
        rightImg.setImageResource(id);
    }

    public void setRightTitle(String rightTitle) {
        rightLayout.setText(rightTitle);
    }

    /*public void setLeftTitle(String leftTitle) {
        leftLayout.setText(leftTitle);
    }

    public void setRightTitle(String rightTitle) {
        rightLayout.setText(rightTitle);
    }*/
}
