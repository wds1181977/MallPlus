package com.tymall.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tymall.R;

/**
 * Created by zoujiamin on 2019/1/14.
 * 更新弹窗
 */

public class UpdateDialog extends Dialog implements View.OnClickListener {
    private DialogOnClickListener listener;
    private Context context;
    private Button btYes;
    private Button btNo;
    private TextView tvTitle;
    private TextView tvContent;

    public UpdateDialog(@NonNull Context context) {
        super(context, R.style.dialog2);
        this.context = context;
        inflateView();
    }

    private void inflateView() {
        setContentView(R.layout.dialog_update);
        btYes = findViewById(R.id.bt_yes);
        btNo = findViewById(R.id.bt_no);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        btYes.setOnClickListener(this);
        btNo.setOnClickListener(this);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setContent(String str) {
        tvContent.setText(str);
    }

    public void setCancleIsVisibility(int updateFlag) {
        if (updateFlag == 1) {
            btNo.setVisibility(View.GONE);
        } else {
            btNo.setVisibility(View.VISIBLE);
        }
    }

    public void setDialogOnClickListener(DialogOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_yes:
                if (listener != null) {
                    listener.Determine("");
                }
                break;

            case R.id.bt_no:
                if (listener != null) {
                    listener.Cancel();
                }
                break;
        }
    }
}
