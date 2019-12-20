package com.tymall.utils;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.util.Log;


/**
 *
 *描述   ：金额输入过滤器，限制小数点后输入位数
 *
 * 默认限制小数点4位
 * 默认第一位输入小数点时，转换为0.
 * 如果起始位置为0,且第二位跟的不是".",则无法后续输入
 *
 *作者   ：Created by DuanRui on 2017/9/28.
 */

public class MoneyValueFilter extends DigitsKeyListener {

    private static final String TAG = "MoneyValueFilter";
    public MoneyValueFilter() {
        super(false, true);
    }

    private int digits = 4;

    public MoneyValueFilter setDigits(int d) {
        digits = d;
        return this;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        CharSequence out = super.filter(source, start, end, dest, dstart, dend);


        Log.d(TAG, "-------source:"+source);
        Log.d(TAG, "-------start:"+start);
        Log.d(TAG, "-------end:"+end);
        Log.d(TAG, "-------dest:"+dest);
        Log.d(TAG, "-------dstart:"+dstart);
        Log.d(TAG, "-------dend:"+dend);


        // if changed, replace the source
        if (out != null) {
            source = out;
            start = 0;
            end = out.length();
        }

        int len = end - start;

        // if deleting, source is empty
        // and deleting can't break anything
        if (len == 0) {
            return source;
        }

        //以点开始的时候，自动在前面添加0
        if(source.toString().equals(".") && dstart == 0){
            return "0.";
        }
        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if(!source.toString().equals(".") && dest.toString().equals("0")){
            return "";
        }

        int dlen = dest.length();

        // Find the position of the decimal .
        for (int i = 0; i < dstart; i++) {
            if (dest.charAt(i) == '.') {
                // being here means, that a number has
                // been inserted after the dot
                // check if the amount of digits is right
                return (dlen-(i+1) + len > digits) ? 
                    "" :
                    new SpannableStringBuilder(source, start, end);
            }
        }

        for (int i = start; i < end; ++i) {
            if (source.charAt(i) == '.') {
                // being here means, dot has been inserted
                // check if the amount of digits is right
                if ((dlen-dend) + (end-(i + 1)) > digits)
                    return "";
                else
                    break;  // return new SpannableStringBuilder(source, start, end);
            }
        }


        Log.d(TAG, "-------return:"+new SpannableStringBuilder(source, start, end).toString());
        Log.d(TAG, "-----------------------");

        // if the dot is after the inserted part,
        // nothing can break
        return new SpannableStringBuilder(source, start, end);
    }
}