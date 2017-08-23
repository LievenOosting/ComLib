package com.qcj.common.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * author：qiuchunjia time：下午5:31:55 类描述：这个类是实现
 */

public class ViewpagerCommonAdapter extends PagerAdapter {
    private List<View> views;

    public ViewpagerCommonAdapter(List<View> views) {
        this.views = views;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(views.get(arg1 % views.size()));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        View view = views.get(arg1 % views.size());
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        ((ViewPager) arg0).addView(view, 0);
        return views.get(arg1 % views.size());
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {

    }

}
