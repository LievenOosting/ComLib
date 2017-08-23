package com.qcj.common.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.qcj.common.R;

/**
 * Created by qiuchunjia on 2016/10/28.
 */
public abstract class BaseSplashActivity extends AppCompatActivity {
    private ImageView ivPage;
    private int mSplashImageResoureId;  //图片的id；
    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startToActivity();
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentLayout();
        setContentView(getLayoutId());
        initView();
    }

    protected void onBeforeSetContentLayout() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
    }

    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    public void initView() {
        ivPage = (ImageView) findViewById(R.id.iv_page);
        mSplashImageResoureId = getSplashImageResoureId();
        ivPage.setImageResource(mSplashImageResoureId);
        mHandle.sendEmptyMessageDelayed(1, 2 * 1000);
    }


    /**
     * 获取闪屏的图片id
     *
     * @return
     */
    public abstract int getSplashImageResoureId();

    /**
     * 跳转页面
     *
     * @return
     */
    public abstract void startToActivity();
}
