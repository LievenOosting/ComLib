package com.qcj.common.util.localImageHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.qcj.common.R;
import com.qcj.common.base.BaseActivity;
import com.qcj.common.util.PermissionUtil;
import com.qcj.common.util.localImageHelper.adapter.ImageListAdapter;

import java.util.ArrayList;


/**
 * author：qiuchunjia time：下午4:01:50 类描述：这个类是实现
 */

public class LocalImagListActivity extends BaseActivity {

    private ListView imageListView;
    private ArrayList<PhotoDirInfo> mDirInfos;
    private ImageListAdapter adapter;
    ArrayList<String> mPhotoPathlist;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_localimagelist_layout;
    }

    public void initView() {
        setToolbarTitle("相册");
        imageListView = (ListView) findViewById(R.id.imageListView);
        if (PermissionUtil.checkReadExternalStoragePermission(this)) {
            getPhotoData();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtil.isOpenPermission(requestCode, permissions, grantResults, PermissionUtil.READ_EXTERNAL_STORAGE)) {
            getPhotoData();
        }
    }

    private void getPhotoData() {
        mDirInfos = LocalImage.getImageDir(getApplicationContext());
        adapter = new ImageListAdapter(this, mDirInfos);
        imageListView.setAdapter(adapter);
        imageListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PhotoDirInfo photoDirInfo = mDirInfos.get(position);
                Intent intent = new Intent(getApplicationContext(),
                        LocalAlbumActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Bundle bundle = new Bundle();
                bundle.putString("bucketId", photoDirInfo.getDirId());
                intent.putExtras(bundle);
                startActivityForResult(intent, GET_DATA_FROM_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.GET_DATA_FROM_ACTIVITY) {
            if (data == null) {
                return;
            }
            mPhotoPathlist = getReturnResultSeri(resultCode, data, null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPhotoPathlist != null) {
            setReturnResultSeri(mPhotoPathlist, null);
            onBackPressed();
        }
    }

}
