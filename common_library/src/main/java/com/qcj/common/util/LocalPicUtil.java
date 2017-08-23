package com.qcj.common.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.soundcloud.android.crop.Crop;

import java.io.File;

import static com.qcj.common.util.MyIntent.TEMP_PHOTO_NAME;
import static com.qcj.common.util.MyIntent.TEMP_PHOTO_PATH;

/**
 * Created by qiuchunjia on 2016/9/19.
 * 实现选择本地照片或者拍照后的时候解析照片uri
 */
public class LocalPicUtil {
    public static String getCalleyPhoto(int resultCode, Intent data, Activity activity) {
        if (resultCode != Activity.RESULT_OK) return null;
        Uri originalUri = data.getData();
        return Uri2Path.getPhotoPath(activity, originalUri);
    }

    public static void getCalleyPhotoByCrop(int resultCode, Intent data, Activity activity) {
        if (resultCode != Activity.RESULT_OK) return;
        Uri originalUri = data.getData();
        beginCrop(originalUri, activity);
    }

    /**
     * 获取拍照后的照片
     *
     * @param resultCode
     * @param activity
     * @return
     */
    public static String getCapturePhoto(int resultCode, Activity activity) {
        return TEMP_PHOTO_PATH;
    }

    /**
     * 获取拍照后裁剪的照片
     *
     * @param
     */
    public static void getCapturePhotoByCrop(int resultCode, Activity activity) {
        if (resultCode != Activity.RESULT_OK) return;
        beginCrop(Uri.fromFile(new File(TEMP_PHOTO_PATH, TEMP_PHOTO_NAME)), activity);
    }

    /**
     * 开始裁剪
     *
     * @param source
     */
    private static void beginCrop(Uri source, Activity activity) {
        Uri destination = Uri.fromFile(new File(activity.getCacheDir(), "crop.jpg"));
        Crop.of(source, destination).asSquare().start(activity);
    }


    /**
     * 处理裁剪后的数据
     *
     * @param resultCode
     * @param result
     */
    public static String handleCrop(int resultCode, Intent result) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = Crop.getOutput(result);
            String path = uri.getPath();
            return path;
        } else if (resultCode == Crop.RESULT_ERROR) {
            ToastUtils.showToast(Crop.getError(result).getMessage());
        }
        return null;
    }

}
