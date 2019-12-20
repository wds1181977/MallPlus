package com.tymall.ui.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;
import com.tymall.R;
import com.tymall.utils.TextAnimatorUtil;
import com.tymall.utils.XToastUtils;
import com.tymall.view.ForgetPasswordDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by wds on 2018/8/8.
 */

public class TxPasswordDialogFragment extends DialogFragment {

    private TextView tvForgetPassword;
    private CheckBox cbConfirmPassword;
    private EditText etPassword;
    private CompositeDisposable compositeDisposable; //OKgo Rxjava  关闭页面停止加载

    private AnimationSet mModalInAnim;

    private LinearLayout mDialogView;


    public static TxPasswordDialogFragment getInstance(Bundle bundle) {
        TxPasswordDialogFragment mPasswordDialogFragment = new TxPasswordDialogFragment();
        if (mPasswordDialogFragment != null) {
            mPasswordDialogFragment.setArguments(bundle);
        }
        return mPasswordDialogFragment;
    }

    public interface PasswordInputListener {
        void password(String pwd);
    }

    PasswordInputListener mListener = null;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            if (activity instanceof PasswordInputListener) {
                mListener = ((PasswordInputListener) activity);
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SignListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_password_fragment, null);
        etPassword = view.findViewById(R.id.dialog_password_et);
        Button submit = view.findViewById(R.id.dialog_password_submit);
        TextView cancle = view.findViewById(R.id.dialog_password_cancle);
        tvForgetPassword = view.findViewById(R.id.tv_forget_password);
        cbConfirmPassword = view.findViewById(R.id.cb_confirm_password);

        etPassword.setFocusable(true);
        etPassword.setFocusableInTouchMode(true);
        etPassword.requestFocus();
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        cbConfirmPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                etPassword.setSelection(etPassword.getText().toString().length());
            }
        });


        addDisposable(RxView.clicks(tvForgetPassword)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    showForgetPasswordDialog();
                    dismiss();

                }));

        addDisposable(RxView.clicks(cancle)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    dismiss();

                }));

        addDisposable(RxView.clicks(submit)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    String pwd = etPassword.getText().toString().trim();
                    if(TextUtils.isEmpty(pwd)){
                        XToastUtils.showRoundRectToast(getActivity().getResources().getString(R.string.string_input_pwd_et));
                        TextAnimatorUtil.showTextAnimator(etPassword);
                        return;
                    }
                    if(mListener != null){
                        mListener.password(pwd);
                    }
                    dismiss();
                }));

        builder.setView(view);
        return builder.create();

    }




    /**
     * 忘记密码提示弹框
     */
    private void showForgetPasswordDialog() {
        ForgetPasswordDialog dialog = new ForgetPasswordDialog(getContext());
        dialog.show();
        dialog.setYesClickListener(new ForgetPasswordDialog.OnYesClickListener() {
            @Override
            public void onYesClickListener() {
                dialog.dismiss();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        try {

        } catch (Exception e) {

        }


    }


    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            //dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


