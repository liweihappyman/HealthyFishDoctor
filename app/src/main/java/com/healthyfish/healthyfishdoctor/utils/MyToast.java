package com.healthyfish.healthyfishdoctor.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * 描述：优化Tost，避免用户一直重复点击
 * Created by LYQ on 2016/12/18.
 */

public class MyToast {
    private static Toast mToast;
    private static Handler mhandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        };
    };

    public static void showToast(Context context, String text) {
        mhandler.removeCallbacks(r);
        if (null != mToast) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        mhandler.postDelayed(r, 5000);
        mToast.show();
    }

    public static void showToast(Context context, String text, int duration) {
        mhandler.removeCallbacks(r);
        if (null != mToast) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(context, text, duration);
        }
        mhandler.postDelayed(r, 5000);
        mToast.show();
    }

    public static void showToast(Context context, int strId, int duration) {
        showToast(context, context.getString(strId), duration);
    }
}
