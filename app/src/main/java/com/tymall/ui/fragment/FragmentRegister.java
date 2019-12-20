package com.tymall.ui.fragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.tymall.utils.CommonParametersUtils;
import com.tymall.utils.CustomUtils;
import com.tymall.utils.XToastUtils;
import com.tymall.view.MyCheckBox;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 注册
 */
public class FragmentRegister extends BaseFragment {
    @BindView(R.id.fg_register_email)
    EditText email;
    @BindView(R.id.fg_register_pwd)
    EditText etPassword;
    @BindView(R.id.fg_register_repwd)
    EditText etRePassword;
    @BindView(R.id.fg_register_paypwd)
    EditText etPayPassword;
    @BindView(R.id.fg_register_repaypwd)
    EditText etRePaypassword;
    @BindView(R.id.fg_register_code)
    EditText code;

    @BindView(R.id.ll_check)
    LinearLayout llCheck;

    @BindView(R.id.create_cb)
    MyCheckBox checkBox;

    @BindView(R.id.create_agree)
    TextView createAgree;

    @BindView(R.id.cb_confirm_password_one)
    CheckBox cbConfirmPasswordOne;

    @BindView(R.id.cb_confirm_password_two)
    CheckBox cbConfirmPasswordTwo;

    @BindView(R.id.cb_confirm_password_three)
    CheckBox cbConfirmPasswordThree;

    @BindView(R.id.cb_confirm_password_four)
    CheckBox cbConfirmPasswordFour;

    private  boolean isLoading = false;


    LogAndRegCallback callback;

    @Override
    protected void initView() {
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(email.getText().toString().trim())) {
                    email.setSelection(email.getText().toString().length());
                }
            }
        });
        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etPayPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etRePaypassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        cbConfirmPasswordOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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


        cbConfirmPasswordTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                etRePassword.setSelection(etRePassword.getText().toString().length());
            }
        });


        cbConfirmPasswordThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPayPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPayPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                etPayPassword.setSelection(etPayPassword.getText().toString().length());
            }
        });


        cbConfirmPasswordFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etRePaypassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etRePaypassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                etRePaypassword.setSelection(etRePaypassword.getText().toString().length());
            }
        });


    }

    @OnClick({R.id.fg_register_submit, R.id.ll_check, R.id.create_agree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fg_register_submit:
                if (CustomUtils.isFastClick()) {
                    if (TextUtils.isEmpty(email.getText().toString().trim())) {
                        XToastUtils.showRoundRectToast(cnt.getResources().getString(R.string.please_input_the_email_address_or_telephone));
                        return;
                    }
                    if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                        XToastUtils.showRoundRectToast(cnt.getResources().getString(R.string.please_enter_your_password));
                        return;
                    }
                    if (TextUtils.isEmpty(etRePassword.getText().toString().trim())) {
                        XToastUtils.showRoundRectToast(cnt.getResources().getString(R.string.please_confirm_the_login_password));
                        return;
                    }




                    if (!etPassword.getText().toString().trim().equals(etRePassword.getText().toString().trim())) {
                        XToastUtils.showRoundRectToast(cnt.getResources().getString(R.string.inconsistent_login_password));
                        return;
                    }



                    if (etPassword.getText().toString().trim().length() < 8) {
                       XToastUtils.showRoundRectToast(cnt.getResources().getString(R.string.password_cannot_be_less_than_digits_login));
                        return;
                    }


                    if (!checkBox.isChecked()) {
                        XToastUtils.showRoundRectToast(cnt.getResources().getString(R.string.please_agree_to_the_service_and_privacy_policy_first));
                        return;
                    }
                    doRegister();
                }
                break;

            case R.id.ll_check:
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                } else {
                    checkBox.setChecked(true);
                }
                break;
            case R.id.create_agree:
//                startActivity(new Intent(cnt, CustomWebViewActivity.class)
//                        .putExtra("aid", "9")
//                        .putExtra("title", cnt.getResources().getString(R.string.registration_agreement))
//                );
                break;
        }

    }

    private void doRegister() {

        if(isLoading){
            return;
        }
        isLoading = true;

        ServerApi.getInstance().register(getActivity(),
                email.getText().toString()
                , etPassword.getText().toString()
                , etRePassword.getText().toString()
                , code.getText().toString())
                .flatMap(new Function<String, Observable<String>>() {
                    @Override
                    public Observable<String> apply(String response) throws Exception {
                        if (!CommonParametersUtils.response(cnt, response)) {
                            return Observable.empty();
                        } else {

                          return ServerApi.getInstance().login(getContext(),email.getText().toString(),etPassword.getText().toString());

                        }
                    }
                }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSubscribe(@android.support.annotation.NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(@android.support.annotation.NonNull String result) {
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
                        hideLoading();
                        XToastUtils.showRoundRectToast(e.getMessage());
                        isLoading = false;

                    }

                    @Override
                    public void onComplete() {
                        hideLoading();
                        isLoading = false;


                    }
                });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (LogAndRegCallback) context;
    }
}
