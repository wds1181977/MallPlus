package com.tymall.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tymall.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 单例模式下popupwindow
 */
public class CustomPopUpWindow {
    private PopupWindow mPopupWindow;
    private Context mContexct;

    private static CustomPopUpWindow instance;

    private CustomPopUpWindow(Context context) {
        this.mContexct = context;
    }

    public static CustomPopUpWindow getInstance(Context context) {
        if (instance == null) {
            synchronized (CustomPopUpWindow.class) {
                if (instance == null) {
                    instance = new CustomPopUpWindow(context);
                }
            }
        }
        return instance;
    }

    public void showListPopwindow(String title, LinkedHashMap<String, String> map) {
        final List idList = new ArrayList();
        List valueList = new ArrayList();
        //遍历map中的键
        for (String key : map.keySet()) {
            System.out.println("Key = " + key);
            idList.add(key);
        }
        //遍历map中的值
        for (String value : map.values()) {
            System.out.println("Key = " + value);
            valueList.add(value);
        }
        final View view = LayoutInflater.from(mContexct).inflate(R.layout.pop_addwalletaddr, null);
        final ConstraintLayout cl = view.findViewById(R.id.pop_cl);
        TextView tv = view.findViewById(R.id.pop_title);
        Button btCancle = view.findViewById(R.id.pop_cancle);
        if ("cn".equals(CacheUtils.getLanguage(mContexct))){
            btCancle.setText("取消");
        }else if ("en".equals(CacheUtils.getLanguage(mContexct))){
            btCancle.setText("Cancel");
        }else if ("ko".equals(CacheUtils.getLanguage(mContexct))){
            btCancle.setText("취소");
        }
        ListView lv = view.findViewById(R.id.pop_lv);
        // 动态控制弹出框高度
        int height = 0;
        if (idList.size() <= 2) {
            height = CustomUtils.dp2px(160);
        } else {
            height = CustomUtils.dp2px(idList.size() * 70);
        }
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        params.bottomToTop = R.id.pop_cancle;
        params.setMarginStart(CustomUtils.dp2px(16));
        params.setMarginEnd(CustomUtils.dp2px(16));
        int margin = CustomUtils.dp2px(16);
        params.setMargins(margin, 0, margin, margin / 2);
        cl.setLayoutParams(params);
        if ("".equals(title)) {
            tv.setVisibility(View.GONE);
        }
        tv.setText(title);
        ArrayAdapter adapter = new ArrayAdapter(mContexct, android.R.layout.simple_list_item_1, valueList);
        lv.setAdapter(adapter);
        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (CustomUtils.isFastClick()) {
                    dismiss();
                    _OnItemOnClickListener.onItemClick(idList.get(position).toString());
                }
            }
        });
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setAnimationStyle(R.style.custom_popwindow_anim_style);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#50000000"));
        mPopupWindow.setBackgroundDrawable(dw);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setContentView(view);
    }

    /**
     * @param view
     */
    public void show(View view) {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    OnItemOnClickListener _OnItemOnClickListener;

    public void set_OnItemOnClickListener(OnItemOnClickListener _OnItemOnClickListener) {
        this._OnItemOnClickListener = _OnItemOnClickListener;
    }

    public interface OnItemOnClickListener {
        void onItemClick(String key);
    }
}