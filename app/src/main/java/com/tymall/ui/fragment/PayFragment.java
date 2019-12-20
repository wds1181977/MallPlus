package com.tymall.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tymall.R;
import com.tymall.app.Constant;
import com.tymall.ui.mall.DBMallManager;
import com.tymall.utils.TextAnimatorUtil;
import com.tymall.utils.ToastUtils;
import com.tymall.view.ForgetPasswordDialog;




/**
 * @author wds
 * @time 2019/2/25
 */
public class PayFragment extends BottomSheetDialogFragment {

    TextView amountTv;
    ImageView closeDialog;
    EditText dialogPasswordEt;
    CheckBox cbConfirmPassword;
    TextView tvForgetPassword;
    Button btnConfirmPwd;
    String pid;
    String currencyId;
    String r2;
    String symbol;
    String type;
    DBMallManager.DBMallPayParams payParams = null;
    boolean isfromMall = false;
    private BottomSheetBehavior mBehavior;


    public static PayFragment getInstance(Bundle bundle) {
        PayFragment mPayFragment = new PayFragment();
        if (mPayFragment != null) {
            mPayFragment.setArguments(bundle);
        }
        return mPayFragment;
    }

    public static PayFragment newInstance() {

        Bundle args = new Bundle();

        PayFragment fragment = new PayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface PayObjListener {
        public void paySuccessObj(DBMallManager.DBMallPayParams payParams);
    }
    PayObjListener mPayObjListener = null;

    public void setPayObjListener(PayObjListener mListener) {
        this.mPayObjListener = mListener;
    }

    public interface PayListener {
        void paySuccess(String pid, String currency_id, String pay_password, String R2,String type);
    }
    PayListener mListener = null;
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            if (activity instanceof PayListener) {
                mListener = ((PayListener) activity);
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SignListener");
        }
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.fragment_pay_dialog, null);
        tvForgetPassword = view.findViewById(R.id.tv_forget_password);
        closeDialog = view.findViewById(R.id.close_dialog);
        btnConfirmPwd = view.findViewById(R.id.btn_confirm_pwd);
        cbConfirmPassword = view.findViewById(R.id.cb_confirm_password);
        dialogPasswordEt = view.findViewById(R.id.dialog_password_et);
        amountTv = view.findViewById(R.id.amount);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgetPasswordDialog();
            }
        });

        btnConfirmPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOnClick();
            }
        });


        cbConfirmPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dialogPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    dialogPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                dialogPasswordEt.setSelection(dialogPasswordEt.getText().toString().length());
            }
        });
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);

        return dialog;

    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            int pay_state = this.getArguments().getInt(Constant.EOS_PAY_STATE,-1);
            if(pay_state == 201){
                btnConfirmPwd.setBackgroundResource(R.drawable.shape_radio_eos_btn);
                String  amount = this.getArguments().getString(Constant.PAY_CREATE_ETH);
                amount = new StringBuilder().append(TextUtils.isEmpty(amount) ? "" : amount).append(" ").append("ETH").toString();
                amountTv.setText(amount);
            }else  if(pay_state == 200) {
                //  {"actualPrice":"1119","currencyId":"11","symbol":"DBM"}
                //{"actualPrice":"30.28","pid":"1506","R2":"de95af432f824a9129a807db3f1e2e74","currencyId":"11","symbol":"USDB"}
                btnConfirmPwd.setBackgroundResource(R.drawable.shape_radio_btn_mall);
                String json = this.getArguments().getString(Constant.PAY_JSON);
                JsonObject jsonObject = (JsonObject) new JsonParser().parse(json);
                String actualPrice = null;
                if(jsonObject.get("actualPrice") != null) {
                    actualPrice   =jsonObject.get("actualPrice").getAsString();
                }
                if(jsonObject.get("pid") != null) {
                    pid = jsonObject.get("pid").getAsString();
                }
                if(jsonObject.get("currencyId") != null) {
                    currencyId = jsonObject.get("currencyId").getAsString();
                }
                if(jsonObject.get("R2") != null) {
                    r2 = jsonObject.get("R2").getAsString();
                }
                if(jsonObject.get("symbol") != null) {
                    symbol = jsonObject.get("symbol").getAsString();
                }
                if(jsonObject.get("type") != null) {
                    type = jsonObject.get("type").getAsString();
                }
                String amount = new StringBuilder().append(TextUtils.isEmpty(actualPrice) ? "" : actualPrice).append(" ").append(TextUtils.isEmpty(symbol) ? "" : symbol).toString();
                amountTv.setText(amount);
            }else  if(pay_state == 300) {
                String  amount = this.getArguments().getString(Constant.PAY_AMOUNT);
                amount = new StringBuilder().append(TextUtils.isEmpty(amount) ? "" : amount).append(" ").append("DBM").toString();
                amountTv.setText(amount);
            }else  if (pay_state == 203){
                btnConfirmPwd.setBackgroundResource(R.drawable.shape_radio_btn_mall);
                isfromMall = true;
                 payParams = ( DBMallManager.DBMallPayParams) this.getArguments().getSerializable(Constant.MALL_PAY_PARAMS);
                String amount = new StringBuilder().append(TextUtils.isEmpty(payParams.getAmount()) ? "" : payParams.getAmount()).append(" ").append(TextUtils.isEmpty(payParams.getSymbol()) ? "" : payParams.getSymbol()).toString();
                amountTv.setText(amount);

            }

        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show(R.string.string_json_data_erro);

        }


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



    private void btnOnClick() {
        if (TextUtils.isEmpty(dialogPasswordEt.getText()) || TextUtils.isEmpty(dialogPasswordEt.getText().toString().trim())) {
            ToastUtils.show(R.string.password_et);
            TextAnimatorUtil.showTextAnimator(dialogPasswordEt);
            return;
        }
        if(mListener != null){

                mListener.paySuccess(pid, currencyId, dialogPasswordEt.getText().toString(), r2,type);

        }
        if(mPayObjListener != null){
            payParams.payPassword(dialogPasswordEt.getText().toString());
            mPayObjListener.paySuccessObj(payParams);
        }

        dismiss();

    }





}
