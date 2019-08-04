package newmatch.zbmf.com.testapplication.utils;

import android.util.Log;

/**
 * Create By Administrator
 * on 2019/7/7
 */
public class LogUtils {

    private static final String TAG = "==YeHi";

    public static void V(String tip) {
        Log.v(TAG, tip);
    }

    public static void D(String tip) {
        Log.d(TAG, tip);
    }

    public static void I(String tip) {
        Log.i(TAG, tip);
    }

    public static void W(String tip) {
        Log.w(TAG, tip);
    }

    public static void E(String tip) {
        Log.e(TAG, tip);
    }

    public static void A(String tip) {
        Log.w(TAG,tip);
    }
}
