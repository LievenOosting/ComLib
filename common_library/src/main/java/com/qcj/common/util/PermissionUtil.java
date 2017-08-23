package com.qcj.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by qiuchunjia on 2016/11/4.
 * 该类主要是适配6.0以及以上的权限的
 */
public class PermissionUtil {
    public static final int CAPTURE_PHOTO = 550;    //拍照
    public static final int READ_EXTERNAL_STORAGE = 551;    //获取内存卡里面内容的权限，主要是用到选择图片相册的时候
    public static final int CALL_PHONE = 552;    //打电话的权限


    /**
     * 检查是否存在该权限，如果存在就返回true，如果不存在就返回false，并请求获取该权限
     *
     * @param context
     * @param requestCode
     * @param permissions 请求的权限
     * @return
     */
    public static boolean checkPermission(Activity context, int requestCode, String[] permissions) {
        if (ContextCompat.checkSelfPermission(context,
                permissions[0])
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    permissions, requestCode);
            return false;
        }
        return true;
    }

    /**
     * 检验打开摄像头的权限
     *
     * @param context
     * @return
     */
    public static boolean checkCapturePermission(Activity context) {
        return checkPermission(context, CAPTURE_PHOTO, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    /**
     * 获取内存卡里面的内容
     * 主要是用到选择图片相册的时候
     *
     * @param context
     * @return
     */
    public static boolean checkReadExternalStoragePermission(Activity context) {
        return checkPermission(context, READ_EXTERNAL_STORAGE, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    /**检查打电话的权限
     * @param context
     * @return
     */
    public static boolean checkCallPhonePermission(Activity context) {
        return checkPermission(context, CALL_PHONE, new String[]{Manifest.permission.CALL_PHONE});
    }
    /******************************
     * 判断权限是否获取了
     **********************************************/
    /**
     * 前三个参数是activity的方法 onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) 的三个参数
     * 最后一个是用户请求的参数
     */
    public static boolean isOpenPermission(int requestCode, String[] permissions, int[] grantResults, int userRequestCode) {
        if (requestCode == userRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ToastUtils.showToast("Permission Denied");
            }
        }
        return false;
    }


}