package com.qcj.common.base;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import com.qcj.common.cache.DataCleanManager;
import com.qcj.common.util.Anim;
import com.qcj.common.util.MethodsCompat;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        //bug收集，托管到腾讯bugly里面用于后期的分析
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.initDelay = 5 * 1000;//设置启动延时为5s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
        Bugly.init(getApplicationContext(), "900031727", false);
        instance = this;
    }


    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        DataCleanManager.cleanDatabases(this);
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(this));
        }
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    /************************
     * activity之间的跳转方法
     ********************************************/
    public void startActivity(Activity now, Class<? extends Activity> target,
                              Bundle data) {
        Intent intent = new Intent();
        intent.setClass(now, target);
        if (data != null) {
            if (intent.getExtras() != null) {
                intent.replaceExtras(data);
            } else {
                intent.putExtras(data);
            }
        }
        now.startActivity(intent);
        Anim.in(now);
    }

    public void startActivity(Activity now, Class<? extends Activity> target,
                              Bundle data, int flag) {
        Intent intent = new Intent();
        intent.setClass(now, target);
        intent.setFlags(flag); // 注意本行的FLAG设置
        if (data != null) {
            if (intent.getExtras() != null) {
                intent.replaceExtras(data);
            } else {
                intent.putExtras(data);
            }
        }
        now.startActivity(intent);
        Anim.in(now);
    }

    public void startActivityForResult(Activity now,
                                       Class<? extends Activity> target, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(now, target);
        if (data != null) {
            if (intent.getExtras() != null) {
                intent.replaceExtras(data);
            } else {
                intent.putExtras(data);
            }
        }
        now.startActivityForResult(intent, BaseActivity.GET_DATA_FROM_ACTIVITY);
        Anim.in(now);
    }
}
