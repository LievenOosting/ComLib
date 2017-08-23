package com.qcj.common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.qcj.common.R;
import com.qcj.common.adapter.ViewpagerCommonAdapter;
import com.qcj.common.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuchunjia on 2016/10/28.
 */
public abstract class BaseGuidePageActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private int[] mImageRes;
    List<View> mViews = new ArrayList<View>();
    private ViewpagerCommonAdapter mAdapter;
    public static final String ISUSED = "isUsed";  //是否使用过

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentLayout();
        setContentView(getLayoutId());
        initView();
        initData();
    }

    protected void onBeforeSetContentLayout() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
    }

    protected int getLayoutId() {
        return R.layout.activity_guide_page;
    }

    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mImageRes = getImageResoure();
    }

    public void initData() {
        if (!(Boolean) SharedPreferencesUtil.getData(this, ISUSED, false)) {
            for (int i = 0; i < mImageRes.length; i++) {
                ImageView view = new ImageView(this);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
                view.setImageResource(mImageRes[i]);
                mViews.add(view);
                if (i == mImageRes.length - 1) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //// TODO: 2016/10/28
                            startToActivity();
                            SharedPreferencesUtil.saveData(getBaseContext(), ISUSED, true);
                            finish();
                        }
                    });
                }
            }
            mAdapter = new ViewpagerCommonAdapter(mViews);
            mViewPager.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ((Boolean) SharedPreferencesUtil.getData(this, ISUSED, false)) {
            startToActivity();
            finish();
        }
    }

    /**
     * 获取图片数组图片的id
     *
     * @return
     */
    public abstract int[] getImageResoure();

    /**
     * 跳转页面
     *
     * @return
     */
    public abstract void startToActivity();
}

