package com.qcj.common.util.localImageHelper;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import com.qcj.common.R;
import com.qcj.common.base.BaseActivity;
import com.qcj.common.util.ToastUtils;
import com.qcj.common.util.localImageHelper.adapter.AlbumGridViewAdapter;

import java.util.ArrayList;


/**
 * author：qiuchunjia time：下午4:17:40 类描述：这个类是实现
 */

public class LocalAlbumActivity extends BaseActivity {
    public static final int PHOTO_COUNT = 6; // 最多允许选六张
    private GridView gridView;
    private ArrayList<String> dataList = new ArrayList<String>();
    private ArrayList<String> selectedDataList = new ArrayList<String>();
    private String bucketId = "";
    private ProgressBar progressBar;
    private AlbumGridViewAdapter gridImageAdapter;
    private Context mContext;

    private Button mNext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_local_album;
    }

    @Override
    public void onClick(View v) {
        setReturnResultSeri(selectedDataList, null);
        onBackPressed();
    }

    public void initView() {
        setToolbarTitle("选择照片");
        mContext = this;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        bucketId = bundle.getString("bucketId");
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        gridView = (GridView) findViewById(R.id.myGrid);
        mNext = (Button) findViewById(R.id.next);
        gridImageAdapter = new AlbumGridViewAdapter(this, dataList,
                selectedDataList);
        gridView.setAdapter(gridImageAdapter);
        refreshData();
        initListener();
    }

    /**
     * 刷新数据
     */
    private void refreshData() {

        new AsyncTask<Void, Void, ArrayList<String>>() {

            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected ArrayList<String> doInBackground(Void... params) {
                ArrayList<String> tmpList = new ArrayList<String>();
                tmpList = (ArrayList<String>) LocalImage.getPhotoFileNameById(
                        mContext, bucketId);
                for (String str : tmpList) {

                }
                return tmpList;
            }

            protected void onPostExecute(ArrayList<String> tmpList) {

                if (LocalAlbumActivity.this == null
                        || LocalAlbumActivity.this.isFinishing()) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                dataList.clear();
                dataList.addAll(tmpList);
                gridImageAdapter.notifyDataSetChanged();
                return;

            }

            ;

        }.execute();

    }

    private void initListener() {
        mNext.setOnClickListener(this);
        gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ToggleButton view, int position,
                                    String path, boolean isChecked) {
                if (isChecked) {
                    if (selectedDataList.size() >= PHOTO_COUNT) {
                        ToastUtils.showToast("最多只能选六张");
                        return;
                    }
                    view.setBackgroundResource(R.mipmap.choseed);
                    selectedDataList.add(path);
                } else {
                    view.setBackgroundResource(R.mipmap.notchose);
                    removeOneData(selectedDataList, path);
                }
            }

        });
    }

    /**
     * 移掉路径
     *
     * @param arrayList 需要移掉的list
     * @param s         被移掉的文件路径
     */
    private void removeOneData(ArrayList<String> arrayList, String s) {
        if (arrayList != null) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).equals(s)) {
                    arrayList.remove(i);
                    return;
                }
            }
        }
    }
}
