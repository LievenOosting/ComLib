package com.qcj.common.base;

import android.widget.BaseAdapter;

import com.qcj.common.interf.RefreshListener;
import com.qcj.common.model.Model;
import com.qcj.common.util.ToastUtils;
import com.qcj.common.widget.listview.BaseListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 来都来了 on 2016/11/16.
 */

public class BaseListActivity extends BaseActivity implements RefreshListener {
    private BaseListView mBaseListView;  //可以上拉加载更多，下拉刷新的listview
    public List<Model> mDataList = new ArrayList<>(); //装数据的list；
    private BaseAdapter mBaseAdapter;
    public static int STATE_DOREFRESHHEAD = 0;  //刷新头部
    public static int STATE_DOREFRESHFOOTER = 1; //刷新尾部
    public int mCurrentState = STATE_DOREFRESHHEAD;
    /******************************
     * listview当存在的时候做下来上拉获取数据
     *******************************************/
    /*********
     * 本项目分页需要用来的参数
     **********/
    private boolean isGetData = true;  //是否获取了数据默认为true，如果没有返回的数据不够的话就为false
    public int mStart = 0;  //偏移量
    public int mLimit = 20;  //每一页的数量
    /**
     * 计算offset偏移量
     */
    private void countOffset() {
        if (isGetData) {
            mStart = mStart + mLimit;
            mLimit = 20;
}
    }

    /**
     * 设置baselistview，这样才能用下面这三个方法来实现，或者将无效
     *
     * @param mBaseListView
     */
    public void setBaseListView(BaseListView mBaseListView) {
        this.mBaseListView = mBaseListView;
        mBaseListView.setRefreshListener(this);
    }
    /**
     * 设置adapter
     *
     * @param baseAdapter
     */
    public void setBaseAdapter(BaseAdapter baseAdapter) {
        this.mBaseAdapter = baseAdapter;
    }
    /**
     * 添加数据到mdatalist
     *
     * @param list
     */
    public <T extends Model> void addDataToList(List<T> list) {
        if (list != null && list.size() > 0) {
            if (mCurrentState == STATE_DOREFRESHHEAD) {
                /****************这里暂时这样处理，到时候看要求再改，可能不clear直接加到头部***********************/
                mDataList.clear();
                mDataList.addAll(list);
            } else {
                //// TODO: 2016/4/28 以后需要专门写一个方法判断是否重复
                mDataList.addAll(list);
            }
            isGetData = true;
        } else {
            isGetData = false;
            ToastUtils.showToast("没有更多了");
        }
        mBaseAdapter.notifyDataSetChanged();  //通知数据发生了变化
        mBaseListView.onStopLoad();
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void doRefreshHead() {
        mCurrentState = STATE_DOREFRESHHEAD;
        mStart = 0;
    }

    @Override
    public void doRefreshFooter() {
        if (isGetData) {
            countOffset();
        }
        mCurrentState = STATE_DOREFRESHFOOTER;
    }

    @Override
    public void initView() {

    }
}
