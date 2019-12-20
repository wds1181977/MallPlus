package com.tymall.view;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.tymall.R;


public class DBankDialog extends Dialog {

    private static Button submit;
    private static TextView cancle;
    private static TextView content_tv;
    private static TextView title_tv;


    public DBankDialog(Context context, String title, String content,String positiveStr,String cancelStr,int btnRes) {
        super(context, R.style.BottomDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_dbank_message, null);  //通过LayoutInflater获取布局
        setContentView(view);


        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.85);

        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        lp.width = width;
        win.setAttributes(lp);
        content_tv = view.findViewById(R.id.dialog_content);
        submit = view.findViewById(R.id.dialog_password_submit);
        cancle = view.findViewById(R.id.dialog_password_cancle);
        title_tv = view.findViewById(R.id.dialog_title);
        content_tv.setText(TextUtils.isEmpty(content) ? "": content);
        title_tv.setText(title==null ? context.getString(R.string.eos_input_error_notice): title);
        submit.setText(TextUtils.isEmpty(positiveStr) ? context.getString(R.string.string_sure_confirm): positiveStr);
        cancle.setText(TextUtils.isEmpty(cancelStr) ? context.getString(R.string.cancle): cancelStr);
        if(btnRes == -1) {
            submit.setBackgroundResource(R.drawable.shape_radio_btn);
        }else {
            submit.setBackgroundResource(btnRes);

        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(submitClickListener != null) {
                    submitClickListener.onSubmitClick();
                }
                dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnSubmitClickListener {
        void onSubmitClick();
    }


    public OnSubmitClickListener submitClickListener;

    public void setSubmitClickListener(OnSubmitClickListener submitClickListener) {
        this.submitClickListener = submitClickListener;
    }




}


