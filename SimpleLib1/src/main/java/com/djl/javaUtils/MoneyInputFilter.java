package com.djl.javaUtils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import com.djl.androidutils.DJLUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jackie on 2016/1/30.
 * 过滤用户输入只能为金额格式
 */
public class MoneyInputFilter implements InputFilter {
    private EditText et;
    Pattern mPattern;

    //输入的最大金额
    private BigDecimal maxValue = new BigDecimal(Integer.MAX_VALUE);
    //小数点后的位数
    private static final int POINTER_LENGTH = 2;
    private static final String POINTER = "." ;
    private static final String ZERO = "0" ;
    private OnFilterChangeListener onFilterChangeListener;
    private boolean isForce;
    private boolean isZERO = true;


    public MoneyInputFilter(EditText et, BigDecimal max, OnFilterChangeListener Listener, boolean force2Max) {
        this.et = et;
        maxValue = max;
        isForce = force2Max;
        DJLUtils.log("max=" + max);
        mPattern = Pattern.compile("([0-9]|\\.)*");
        onFilterChangeListener = Listener;
    }

    public MoneyInputFilter(EditText et, BigDecimal bigDecimal, OnFilterChangeListener onFilterChangeListener, boolean isForce, boolean isZERO) {
        this(et, bigDecimal, onFilterChangeListener, isForce);
        this.isZERO = isZERO;
    }

    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return 输入内容
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();
        DJLUtils.log("destText=" + destText);
        //验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            return "" ;
        }

        Matcher matcher = mPattern.matcher(source);
        //已经输入小数点的情况下，只能输入数字
        if (destText.contains(POINTER)) {
            if (!matcher.matches()) {
                return "" ;
            } else {
                if (POINTER.equals(source)) {  //只能输入一个小数点
                    return "" ;
                }
            }

            //验证小数点精度，保证小数点后只能输入两位
            int index = destText.indexOf(POINTER);
            int length = dend - index;

            if (length > POINTER_LENGTH) {
                if (onFilterChangeListener != null) {
                    onFilterChangeListener.noMoreThan2AfterPoint(et);
                }
                return dest.subSequence(dstart, dend);
            }
        } else {
            //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0
            if (!matcher.matches()) {
                return "" ;
            } else {
                if (TextUtils.isEmpty(destText)) {
                    if (POINTER.equals(source)) {
                        return "" ;
                    }
                    if (isZERO && ZERO.equals(source)) {
                        if (onFilterChangeListener != null) {
                            onFilterChangeListener.noStartZero(et);
                        }
                        return "" ;
                    }
                } else if ((!isZERO) && destText.equals("0") && ZERO.equals(source)) {
                    return "" ;
                }
            }
        }
        //验证输入金额的大小
        BigDecimal sumText = new BigDecimal(destText + sourceText);
        if (sumText.doubleValue() > maxValue.doubleValue()) {
            if (et != null) {
//                if (message != null && message.length > 0 && (!StringUtils.isEmpty(message[0])))
//                    DJLUtils.toastS(et.getContext(), message[0]);
                if (onFilterChangeListener != null) {
                    onFilterChangeListener.noMoreThanMax(et);
                }
                if (isForce) {
                    et.setText(maxValue.doubleValue() + "");
                    et.setSelection(et.getText().length());
                    if (onFilterChangeListener != null) {
                        onFilterChangeListener.toMax(maxValue.doubleValue());
                    }
                }
            }

            return dest.subSequence(dstart, dend);
        }

        return dest.subSequence(dstart, dend) + sourceText;
    }

    /**
     * 当输入金额大于最大金额是强制等于最大金额
     *
     * @param isForce
     * @param max     最大输入金额
     * @param et
     */
    public static void maxForce2MaxFilter(double max, EditText et, OnFilterChangeListener onFilterChangeListener, boolean isForce) {
        et.setFilters(new InputFilter[]{new MoneyInputFilter(et, new BigDecimal(max / 100), onFilterChangeListener, isForce)});
    }

    public static void maxForce2MaxFilter(double max, EditText et, OnFilterChangeListener onFilterChangeListener, boolean isForce, boolean isZERO) {
        et.setFilters(new InputFilter[]{new MoneyInputFilter(et, new BigDecimal(max / 100), onFilterChangeListener, isForce, isZERO)});
    }

    public static class OnFilterChangeListener {

        public void toMax(double max) {
        }

        public void noStartZero(EditText editText) {
            if (editText != null) {
                DJLUtils.toastS(editText.getContext(), "输入金额必须大于1元");
            }

        }

        public void noMoreThan2AfterPoint(EditText editText) {
            if (editText != null) DJLUtils.toastS(editText.getContext(), "分以下的金额无效");
        }

        public void noMoreThanMax(EditText editText) {
//            if (editText != null) DJLUtils.toastS(editText.getContext(), "分以下的金额无效");
        }


    }


}