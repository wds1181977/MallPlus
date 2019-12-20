package com.tymall.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tymall.R;
import com.tymall.base.BaseFragment;
import com.tymall.callback.LogAndRegCallback;
import com.tymall.model.UserModel;
import com.tymall.okrx2.ApiException;
import com.tymall.okrx2.BaseSubscriber;
import com.tymall.okrx2.ServerApi;
import com.tymall.ui.login.ForgetPwdActivity;
import com.tymall.utils.CommonParametersUtils;
import com.tymall.utils.CustomUtils;
import com.tymall.utils.SharedPreferencesUtils;
import com.tymall.utils.ToastUtils;
import com.tymall.utils.XToastUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录
 */
public class FragmentLogin extends BaseFragment {
    @BindView(R.id.fg_register_code)
    EditText phone;
    @BindView(R.id.fg_register_email)
    EditText etPassword;
    @BindView(R.id.fg_login_error)
    TextView error;

    @BindView(R.id.cb_confirm_password)
    CheckBox cbConfirmPassword;

    LogAndRegCallback callback;
    @Override
    protected void initView() {
        String shareName = SharedPreferencesUtils.getInstance().getShared("shareName");
        phone.setText("-1".equals(shareName) ? "" : shareName);
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

    }

    @OnClick({R.id.fg_register_submit, R.id.fg_login_forget})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fg_register_submit:
                if (CustomUtils.isFastClick()) {
                    doLogin();
                }
                break;
            case R.id.fg_login_forget:
                if (CustomUtils.isFastClick()) {
                    startActivity(new Intent(getActivity(), ForgetPwdActivity.class));
                }
                break;
        }
    }
    private void doLogin() {
        String name = phone.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            ToastUtils.show(cnt.getResources().getString(R.string.please_input_the_email_address_or_telephone));
            return;
        }

        if (TextUtils.isEmpty(pwd)){
            ToastUtils.show(cnt.getResources().getString(R.string.password_et));
            return;
        }


        ServerApi.getInstance().login(getActivity(), name,pwd)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                       showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@NonNull String result) {

                        try {
                            if (CommonParametersUtils.response(cnt, result)) {
                                JSONObject jsonObject = new JSONObject(result);
                                String  data = jsonObject.optString("data");
                                UserModel userModel = JSON.parseObject(data, new TypeReference<UserModel>() {});

                                String uid =userModel.getUserInfo().getId();
                                String uname = userModel.getUserInfo().getPhone();
                                String token = userModel.getToken();
                                callback.onLoginCallBack(uid, uname, token);

                            }
                        } catch (Exception e) {

                        }


                    }


                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();
                        XToastUtils.showRoundRectToast(e.getMessage());

                       hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        hideLoading();

                    }
                });

    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (LogAndRegCallback) context;
    }
}
