package com.tymall.ui.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cazaea.sweetalert.OptAnimationLoader;
import com.github.ybq.android.spinkit.SpinKitView;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import com.lzy.okgo.OkGo;
import com.tymall.R;
import com.tymall.app.Constant;
import com.tymall.ui.event.EosEvent;
import com.tymall.utils.BigDecimalUtils;
import com.tymall.utils.CustomDialog;
import com.tymall.utils.LogUtil;
import com.tymall.utils.MoneyValueFilter;
import com.tymall.utils.TextAnimatorUtil;
import com.tymall.utils.XToastUtils;
import com.tymall.view.ClearEditText;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yzq.zxinglibrary.android.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.web3j.crypto.WalletUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * Created by wds on 2018/5/8.
 */

public class TransferOutDialogFragment extends DialogFragment {

    LinearLayout mDialogView;
    ClearEditText editAmount;
    ClearEditText tvAddressValue;
    Button tranferButton;
    LinearLayout qrcodeLayout;
    LinearLayout quiteLayout;
    ClearEditText editPwd;
    TextView tvFeeValue, tvAmountValue,tv_DBgas;
    CheckBox cbConfirmPassword;
    @BindView(R.id.amount_loading)
    SpinKitView amountLoading;
    @BindView(R.id.fee_loading)
    SpinKitView feeLoading;
    @BindView(R.id.gas_loading)
    SpinKitView gasloading;

    String fee_dbgas_percent = "";
    String fee_dbgas_min = "";
    private Map<String, String> jsonmap = null;

    private CompositeDisposable compositeDisposable; //OKgo Rxjava  关闭页面停止加载

    private AnimationSet mModalInAnim;
    String txAmount = "";

    public static TransferOutDialogFragment getInstance(Bundle bundle) {
        TransferOutDialogFragment phoneVerificationDialogFragment = new TransferOutDialogFragment();
        if (phoneVerificationDialogFragment != null) {
            phoneVerificationDialogFragment.setArguments(bundle);
        }
        return phoneVerificationDialogFragment;
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {

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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_transfer_out, null);
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
        mDialogView = view.findViewById(R.id.root_rl);
        tvAddressValue = view.findViewById(R.id.edit_address_value);
        qrcodeLayout = view.findViewById(R.id.qrcode_layout);
        editPwd = view.findViewById(R.id.edit_pwd);
        quiteLayout = view.findViewById(R.id.quite_layout);
        editAmount = view.findViewById(R.id.edit_amount);
        tvFeeValue = view.findViewById(R.id.tv_fee_value);
        tranferButton = view.findViewById(R.id.tranfer_button);
        cbConfirmPassword = view.findViewById(R.id.cb_confirm_password);
        tvAmountValue = view.findViewById(R.id.tv_amount_value);
        amountLoading = view.findViewById(R.id.amount_loading);
        feeLoading = view.findViewById(R.id.fee_loading);
        gasloading = view.findViewById(R.id.gas_loading);
        tv_DBgas = view.findViewById(R.id.tv_gas_value);
        editPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        cbConfirmPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    editPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                editPwd.setSelection(editPwd.getText().toString().length());
            }
        });


        EventBus.getDefault().register(this);

        addDisposable(RxView.clicks(quiteLayout)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    editPwd.clearFocus();
                    tvAddressValue.clearFocus();
                    editAmount.clearFocus();
                    OkGo.getInstance().cancelTag("applyWithdraw");
                    OkGo.getInstance().cancelTag("applyWithdrawPre");
                    dismiss();

                }));
        addDisposable(RxView.clicks(qrcodeLayout)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    doScan();
                }));
        addDisposable(RxView.clicks(tranferButton)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    doTransfer();
                }));
        editAmount.setFilters(new InputFilter[]{new MoneyValueFilter().setDigits(4)});
        RxTextView.textChangeEvents(editAmount)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TextViewTextChangeEvent>() {

                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }


                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                        if (!editAmount.isFocused()) {
                            return;
                        }

                        String result = editAmount.getText().toString().trim();
                        if (!TextUtils.isEmpty(result)) {
                            if (!TextUtils.isEmpty(txAmount) && BigDecimalUtils.compare(result, txAmount)) {
                                editAmount.setText(txAmount);
                                XToastUtils.showRoundRectToast(getString(R.string.string_tx_convertible_quantity));
                            }

                            if (!TextUtils.isEmpty(fee_dbgas_percent)) {
                                String dbgas = BigDecimalUtils.mul6(result, fee_dbgas_percent, 4);
                                if (!TextUtils.isEmpty(fee_dbgas_min) && BigDecimalUtils.compare(fee_dbgas_min, dbgas)) {
                                    dbgas = fee_dbgas_min;
                                }
                                tv_DBgas.setVisibility(View.VISIBLE);
                                tv_DBgas.setText( dbgas+ "DBGAS");
                            }else {
                                tv_DBgas.setVisibility(View.GONE);
                            }


                            applyWithdrawPre(result);


                        } else {
                            tvFeeValue.setText("");
                        }


                    }
                });


        builder.setView(view);
        return builder.create();

    }

    private void showAmountLoading(boolean show) {
        if (amountLoading != null)
            if (show) {
                amountLoading.setVisibility(View.VISIBLE);
            } else {
                amountLoading.setVisibility(View.GONE);

            }
    }

    private void showFeeLoading(boolean show) {
        if (feeLoading != null)
            if (show) {
                feeLoading.setVisibility(View.VISIBLE);
            } else {
                feeLoading.setVisibility(View.GONE);

            }
    }

    private void showGasLoading(boolean show) {
        if (gasloading != null)
            if (show) {
                gasloading.setVisibility(View.VISIBLE);
            } else {
                gasloading.setVisibility(View.GONE);

            }
    }






    /**
     * 执行扫码操作
     */
    private void doScan() {
        AndPermission.with(this)
                .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Intent intent = new Intent(getActivity(), CaptureActivity.class);
                        startActivityForResult(intent, 1);
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Uri packageURI = Uri.parse("package:" + getContext().getPackageName());
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        XToastUtils.showRoundRectToast(getContext().getResources().getString(R.string.no_permission_to_scan));
                    }
                }).start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            String content = data.getStringExtra(com.yzq.zxinglibrary.common.Constant.CODED_CONTENT);

            if (TextUtils.isEmpty(content)) {
                return;
            }
            LogUtil.d("wds", content);
            if (content.startsWith("eth") || WalletUtils.isValidAddress(content)) {
                parseQRCode(content);
            } else {
                XToastUtils.showRoundRectToast(getResources().getString(R.string.the_address_format_is_incorrect));

            }

        }


    }


    private void parseQRCode(String QRcode) {
        if (TextUtils.isEmpty(QRcode)) {
            return;
        }

        if (QRcode.startsWith("eth")) {
            String address = QRcode.substring(QRcode.indexOf("ethereum:") + 9, QRcode.lastIndexOf("?"));
            String mContractAddress = "";
            if (QRcode.contains("contractAddress")) {
                mContractAddress = QRcode.substring(QRcode.indexOf("contractAddress=") + 16, QRcode.indexOf("&"));
            }
            String mDecimal = QRcode.substring(QRcode.indexOf("decimal=") + 8, QRcode.lastIndexOf("&"));
            String value = QRcode.substring(QRcode.lastIndexOf("value=") + 6);

            Log.d("wds", "address = " + address
                    + " contractAddress = " + mContractAddress
                    + " decimal = " + mDecimal
                    + " value = " + value);

            if (!TextUtils.isEmpty(address)) {
                tvAddressValue.setText(address);
            }

        } else if (WalletUtils.isValidAddress(QRcode)) {
            if (!TextUtils.isEmpty(QRcode)) {
                tvAddressValue.setText(QRcode);
            }
        }
    }


    /**
     * 提现手续费详情
     *
     * @param
     */
    private void applyWithdrawPre(String num) {
        if (jsonmap == null || TextUtils.isEmpty(num) || BigDecimalUtils.isZeroNew(num)) {
            return;
        }

    }



    private void doTransfer() {
        editPwd.clearFocus();


        if (TextUtils.isEmpty(tvAddressValue.getText())) {
            XToastUtils.showRoundRectToast(getString(R.string.eos_transfer_intpu_address));
            return;
        }

        if (!WalletUtils.isValidAddress(tvAddressValue.getText().toString().trim())) {
            XToastUtils.showRoundRectToast(getResources().getString(R.string.the_address_format_is_incorrect));
            return;
        }


        if (TextUtils.isEmpty(editAmount.getText())) {
            XToastUtils.showRoundRectToast(getString(R.string.eos_transfer_intpu_amount));
            return;
        }


        if (TextUtils.isEmpty(editPwd.getText())) {
            TextAnimatorUtil.showTextAnimator(editPwd);
            XToastUtils.showRoundRectToast(getString(R.string.string_input_pwd_et));
            return;
        }

        tvAddressValue.clearFocus();
        editAmount.clearFocus();


    }

    /**
     * 余额
     *
     * @param
     */
    private void getBlance() {
        if (jsonmap == null) {
            return;
        }
//        ServerApi.getInstance().moneyInfo(getActivity(), jsonmap)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(@io.reactivex.annotations.NonNull Disposable disposable) throws Exception {
//                        showAmountLoading(true);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())//
//                .subscribe(new BaseSubscriber<String>() {
//                    @Override
//                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
//                        addDisposable(d);
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull String responseBody) {
//                        try {
//
//                            if (CommonParametersUtils.validationResultsForPhp(getContext(), responseBody)) {
//
//
//                                JSONObject obj = new JSONObject(responseBody);
//                                JSONObject data = obj.optJSONObject("data");
//                               txAmount = data.optString("num");
//                                if (!TextUtils.isEmpty(txAmount)) {
//                                    LogUtil.d("wds", txAmount);
//                                    tvAmountValue.setText(txAmount + "DBM");
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//
//
//                    @Override
//                    public void onError(ApiException e) {
//                        e.printStackTrace();
//                        XToastUtils.showRoundRectToast(e.getMessage());
//                        showAmountLoading(false);
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        showAmountLoading(false);
//
//                    }
//                });

    }




    @Override
    public void onStart() {
        super.onStart();
        try {
            if (mDialogView != null) {
                mDialogView.startAnimation(mModalInAnim);
            }
            jsonmap = (Map<String, String>) getArguments().getSerializable(Constant.DBM_JSON_MAP);
            getBlance();
        } catch (Exception e) {

        }


    }


    public void show(FragmentManager manager, String tag) {
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(this, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
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


    public void showLoading() {
        CustomDialog.getInstance(getActivity()).show();
    }


    public void hideLoading() {
        CustomDialog.getInstance(getActivity()).dismiss();
    }


    @Subscribe
    public void onEvent(EosEvent event) {
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


