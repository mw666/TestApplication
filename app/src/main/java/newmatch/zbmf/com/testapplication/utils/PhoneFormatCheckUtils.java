package newmatch.zbmf.com.testapplication.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by **
 * on 2017/12/20.
 */

public class PhoneFormatCheckUtils {

    /**
     * 验证用户名
     *
     * @param account
     * @return
     */
    public static boolean validateAccount(Context context, String account, String tip) {
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showSquareTvToast(context, tip);
            return false;
        }
        if (account.length() < 8 || account.length() > 11) {
            ToastUtils.showSquareTvToast(context, "请检查号码位数");
            return false;
        }
        return PhoneFormatCheckUtils.isPhoneLegal(account);
    }

    /**
     * 检查验证码不能为空
     */
    public static boolean validateCode(Context context, String code, String tip) {
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showSquareTvToast(context, tip);
            return false;
        }else {
            return true;
        }
    }

    /**
     * 验证密码
     *
     * @return
     */
    public static boolean validatePassword(Context context, EditText et) {
        if (TextUtils.isEmpty(et.getText().toString().trim())) {
            ToastUtils.showSquareTvToast(context, "密码不能为空");
            return false;
        } else {
            if (et.getText().toString().trim().length() < 6 || et.getText().toString().trim().length() > 18) {
                showError(et, "密码长度为6-18位");
                return false;
            }else {
                return true;
            }
        }
//        clearError(itUserPass);
    }

    /**
     * 显示错误提示，并获取焦点
     *
     * @param et
     * @param error
     */
    public static void showError(EditText et, String error) {
        et.setFocusable(true);
        et.setError(error);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
    }

    public static void clearError(EditText et) {
        if (!TextUtils.isEmpty(et.getText().toString())) {
            et.getText().clear();
        }
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    private static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(18[0-9])|(17[0-9])|(19[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    private static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
}
