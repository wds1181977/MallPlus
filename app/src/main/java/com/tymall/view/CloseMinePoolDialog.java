package com.tymall.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tymall.R;

/**
 * Created by zoujiamin on 2018/12/25.
 * 关闭矿池提示框
 */

public class CloseMinePoolDialog extends Dialog implements View.OnClickListener {
    private DialogOnClickListener listener;
    private Context context;
    private Button btYes;
    private Button btNo;
    private TextView tvTitle;
    private TextView tvContent;

    public CloseMinePoolDialog(@NonNull Context context) {
        super(context, R.style.dialog2);
        this.context = context;
        inflateView();
    }

    private void inflateView() {
        setContentView(R.layout.dialog_mine_pool);
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


    /**
     * 设置确定按钮文字显示
     *
     * @param content
     */
    public void setButtonYesContent(String content) {
        btYes.setText(content);
    }


    /**
     * 设置取消按钮显示or隐藏状态
     * 默认是显示的
     *
     * @param
     */
    public void setButtonNoStatus(boolean status) {
        if (status) {
            btNo.setVisibility(View.VISIBLE);
        } else {
            btNo.setVisibility(View.GONE);
        }
    }

    public void setContent(String str) {
        tvContent.setText(Html.fromHtml(str));
    }

    public void setContentNewText(String str) {
        tvContent.setText(str);
    }

    public void setBtYesBackGroundJinSe(int backGround) {
        btYes.setBackgroundResource(backGround);
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
