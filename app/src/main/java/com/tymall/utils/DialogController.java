package com.tymall.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;

import com.aries.ui.widget.alert.UIAlertDialog;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.tymall.R;
import com.tymall.view.DBankDialog;

public class DialogController {


    private static class SingletonHolder {

        private static final DialogController INSTANCE = new DialogController();

    }

    private DialogController (){}

    public static final DialogController getInstance() {

        return SingletonHolder.INSTANCE;



    }

    public  void showDBankDialog(Context context, String title, String content,
                                 String positiveStr,String cancelStr,int btnRes,DBankDialog.OnSubmitClickListener listener){

        DBankDialog dBankDialog = new DBankDialog(context,title,content,positiveStr,cancelStr,btnRes);
        dBankDialog.setSubmitClickListener(listener);
        dBankDialog.show();

    }
    public void showMessageDialog(Activity activity , String msg, String title){
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton(R.string.string_sure_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void showNomalMessageDialog(Context contenxt, String msg, String title){
        new UIAlertDialog.DividerIOSBuilder(contenxt)
                .setTitle(title)
                .setTitleTextColorResource( R.color.colorAlertTitle )
                .setMessage(msg)
                .setMessageTextColorResource( R.color.colorAlertMessage )
                .setNegativeButton(contenxt.getString(R.string.cancel),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButtonTextColorResource(R.color.colorAlertButton )
                .setPositiveButton(contenxt.getString(R.string.string_sure_confirm),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                .setMinHeight(SizeUtil.dp2px(200))
                .setPositiveButtonTextColorResource( R.color.colorAlertButton)
                .create()
                .setDimAmount(0.6f)
                .show();
    }


    public void showMsgDialog(Context contenxt, String msg, String title){
        new AlertDialog.Builder(contenxt)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton(R.string.string_sure_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void showSuccessDialog(Activity activity ,String title,String content,boolean isAutoCancel){
        if(activity == null){
            return;
        }
        final SweetAlertDialog successDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(content);
        successDialog.show();
        if(isAutoCancel) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!activity.isFinishing() && successDialog.isShowing()) {
                        successDialog.dismiss();
                    }
                }
            }, 1500);
        }

    }


    public void showErrorDialog(Activity activity,String title,String cointentText){
        if(activity == null){
            return;
        }
        new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(cointentText)
                .setConfirmText("OK")
                .show();
    }


}
