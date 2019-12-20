package com.tymall.ui.login;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.tymall.R;
import com.tymall.base.XBaseActivity;
import com.tymall.model.HttpModel;
import com.tymall.utils.CustomDialog;
import com.tymall.utils.CustomPopUpWindow;
import com.tymall.utils.OkHttpUtils;
import com.tymall.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码
 */
public class ForgetPwdActivity extends XBaseActivity {
    @BindView(R.id.forget_pwd_type)
    TextView pwdType;

    @BindView(R.id.forget_key)
    EditText key;
    @BindView(R.id.forget_pwd_new)
    EditText etNewPwd;
    @BindView(R.id.forget_pwd_confirm)
    EditText etConfirm;

    @BindView(R.id.cb_confirm_password_one)
    CheckBox cbConfirmPasswordOne;

    @BindView(R.id.cb_confirm_password_two)
    CheckBox cbConfirmPasswordTwo;

    private CustomPopUpWindow customPopUpWindow;

    /**
     * 1登录密码
     * 2 交易密码
     */
    private String password_type = "1";

    @Override
    protected void initView() {
        customPopUpWindow = CustomPopUpWindow.getInstance(this);
        pwdType.setText(cnt.getResources().getString(R.string.password_type) + cnt.getResources().getString(R.string.login_pwd));
        etNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        etConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());

        cbConfirmPasswordOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etNewPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etNewPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                etNewPwd.setSelection(etNewPwd.getText().toString().length());
            }
        });


        cbConfirmPasswordTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                etConfirm.setSelection(etConfirm.getText().toString().length());
            }
        });


    }

    @OnClick({R.id.forget_pwd_type, R.id.forget_pwd_sub})
    public void onClick(View v) {
        if (v.getId() == R.id.forget_pwd_type) {
            showPopWindow();
        } else if (v.getId() == R.id.forget_pwd_sub) {
            doSubmit();
        }
    }

    /**
     * 修改密码操作
     */
    private void doSubmit() {
        String private_key = key.getText().toString();
        String new_pass = etNewPwd.getText().toString().trim();
        String confirm_pass = etConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(private_key)) {
            ToastUtils.show(cnt.getResources().getString(R.string.please_enter_the_private_key));
            return;
        }

        if (TextUtils.isEmpty(new_pass)) {
            ToastUtils.show(cnt.getResources().getString(R.string.please_enter_a_new_password));
            return;
        }

        if (TextUtils.isEmpty(confirm_pass)) {
            ToastUtils.show(cnt.getResources().getString(R.string.please_confirm_the_new_password));
            return;
        }

        if (!new_pass.equals(confirm_pass)) {
            ToastUtils.show(cnt.getResources().getString(R.string.two_password_entries_are_inconsistent));
            return;
        }

        if (new_pass.length() < 8) {
            ToastUtils.show(cnt.getResources().getString(R.string.password_cannot_be_less_than_digits));
            return;
        }

        CustomDialog.getInstance(ForgetPwdActivity.this).show();
        Map<String, String> params = new HashMap<>();
        params.put("password_type", password_type);
        params.put("private_key", private_key);
        params.put("new_pass", new_pass);
        params.put("confirm_pass", confirm_pass);
        OkHttpUtils http = new OkHttpUtils(ForgetPwdActivity.this, HttpModel.ForgetPwd, params, new OkHttpUtils.NetCallBack() {
            @Override
            public void httpCallback(String result) {
                if (ForgetPwdActivity.this != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                CustomDialog.getInstance(ForgetPwdActivity.this).dismiss();
                                JSONObject obj = new JSONObject(result);
                                int code = obj.optInt("code");
                                String msg = obj.optString("msg");
                                if (code == 1) {
                                    finish();
                                }
                                ToastUtils.show(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void httpCallbackFailed(String result) {

            }
        });
        http.doPost();
    }

    /**
     * 密码类型选择
     */
    private void showPopWindow() {
        final LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("1", cnt.getResources().getString(R.string.login_pwd));
        map.put("2", cnt.getResources().getString(R.string.transaction_pwd));
        customPopUpWindow.showListPopwindow(cnt.getResources().getString(R.string.password_type_new), map);
        customPopUpWindow.set_OnItemOnClickListener(new CustomPopUpWindow.OnItemOnClickListener() {
            @Override
            public void onItemClick(String id) {
                pwdType.setText(cnt.getResources().getString(R.string.password_type) + map.get(id).toString());
                password_type = id;
            }
        });
        customPopUpWindow.show(pwdType);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }
}
