package com.tymall.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tymall.R;

/**
 * Created by zoujiamin on 2018/11/28.
 * 忘记密码提示弹窗
 */

public class ForgetPasswordDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView tvTitle;
    private Button btGotIt;


    private OnYesClickListener yesClickListener;

    public void setYesClickListener(OnYesClickListener yesClickListener) {
        this.yesClickListener = yesClickListener;
    }

    public interface OnYesClickListener {
        void onYesClickListener();
    }


    public ForgetPasswordDialog(Context context) {
        super(context, R.style.dialog2);
        this.context = context;
        inflateView();
    }


    private void inflateView() {
        setContentView(R.layout.dialog_signature);
        tvTitle = findViewById(R.id.tv_title);
        btGotIt = findViewById(R.id.bt_determine);
        btGotIt.setOnClickListener(this);
        tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

//    /**
//     * 设置按钮文本
//     *
//     * @param str
//     */
//    public void setButtonContent(String str) {
//        btGotIt.setText(str);
//    }
//
//
//    /**
//     * 设置标题
//     *
//     * @param title
//     */
//    public void setTitle(String title) {
//        tvTitle.setText(title);
//    }
//
//
//    public void setContent(String content) {
//
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_determine:
               if (yesClickListener!=null){
                   yesClickListener.onYesClickListener();
               }
                break;
        }
    }
}
