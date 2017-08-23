package com.qcj.common.base;


import android.support.v4.view.ViewPager;

import com.qcj.common.R;
import com.qcj.common.adapter.ViewPageFragmentAdapter1;
import com.qcj.common.interf.Indicator;
import com.qcj.common.ui.EmptyLayout;

/**
 * 带有导航条的基类
 *
 * @author qiuchunjia
 * @created 2016年2月6日
 */
public abstract class BaseViewPager1Fragment extends BaseFragment {
    protected Indicator mIndicator;
    protected ViewPager mViewPager;
    protected ViewPageFragmentAdapter1 mTabsAdapter;
    protected EmptyLayout mErrorLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.base_viewpage_fragment_and_activity1;
    }

    @Override
    public void initView() {
        mIndicator = findViewById(R.id.viewpager_indicator);
        mViewPager = findViewById(R.id.pager);
        mErrorLayout = findViewById(R.id.error_layout);
        mTabsAdapter = new ViewPageFragmentAdapter1(getChildFragmentManager(),
                mIndicator, mViewPager);
        setScreenPageLimit();
        onSetupTabAdapter(mTabsAdapter);
    }

    protected void setScreenPageLimit() {

    }


    protected abstract void onSetupTabAdapter(ViewPageFragmentAdapter1 adapter);
}