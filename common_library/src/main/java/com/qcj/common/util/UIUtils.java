package com.qcj.common.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * @author qcj
 */
public class UIUtils {

    private static final String TAG = "UIUtils";

    /**
     * 隐藏输入法
     *
     * @param paramContext
     * @param paramEditText
     */
    public static void hideSoftKeyboard(Context paramContext,
                                        EditText paramEditText) {
        ((InputMethodManager) paramContext
                .getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
    }

    /**
     * 显示输入法
     *
     * @param paramContext
     * @param paramEditText
     */
    public static void showSoftKeyborad(Context paramContext,
                                        EditText paramEditText) {
        ((InputMethodManager) paramContext
                .getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(
                paramEditText, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 返回的是毫米
     *
     * @param context
     * @return
     */
    public static int getWindowWidthMM(Context context) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / displayMetrics.densityDpi;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */

    public static int getWindowHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getOrignHeight(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    //获取屏幕原始尺寸宽度，包括虚拟功能键宽度
    public static int getOrignWidth(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 返回的是英寸
     *
     * @return
     */
    public static double getScreenSizeOfDevice2(Context context) {
        Point point = new Point();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            manager.getDefaultDisplay().getRealSize(point);
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double x = Math.pow(point.x / dm.xdpi, 2);
        double y = Math.pow(point.y / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y) + 0.03;  //加0.03 减少误差
        Log.d("measure", "Screen inches : " + screenInches);
        return screenInches;
    }

    public static int getMeasureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int specResult = View.MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == View.MeasureSpec.EXACTLY) {
            return specResult;
        } else {
            result = 500;
            return Math.min(result, specResult);
        }

    }

    public static int getMeasureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int specResult = View.MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == View.MeasureSpec.EXACTLY) {
            return specResult;
        } else {
            result = 500;
            return Math.min(result, specResult);
        }
    }

    /**
     * 获取每一cm需要的px
     *
     * @param context
     * @return
     */
    public static int get1CmPx(Context context) {
        /************/
        double diagonalMm = UIUtils.getScreenSizeOfDevice2(context);
        int width = UIUtils.getOrignWidth(context);
        int height = UIUtils.getOrignHeight(context);
        //获取屏幕对角线的长度，单位:px
        diagonalMm = diagonalMm * 2.54 * 10;//转换单位为：毫米
        /****声明手机的物理宽高*/
        /**
         * 根据数学公式可以得出：widthMm=heightMm*(width/height)
         * 所以：heightMm*(width/height)*heightMm*(width/height)+heightMm*heightMm=diagonalMm*diagonalMm;
         * ****/
        float widthMm, heightMm;
        heightMm = (float) Math.sqrt(diagonalMm * diagonalMm / (Math.pow((width * 1.0 / height), 2) + 1));
        widthMm = (float) (heightMm * (width * 1.0 / height));
        int squareWidth = (int) (width / widthMm * 10);
        return squareWidth;
    }

}
