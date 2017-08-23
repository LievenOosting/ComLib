package com.qcj.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qcj.common.R;
import com.qcj.common.interf.MultiItemTypeSupport;
import com.qcj.common.util.ImageUtil;
import com.qcj.common.util.JUtil;
import com.qcj.common.util.UIUtils;
import com.qcj.common.util.localImageHelper.ImageBrowseActivity;
import com.qcj.common.util.localImageHelper.LocalImagListActivity;
import com.qcj.common.util.localImageHelper.LocalImageManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by qiuchunjia on 2016/9/1.
 */
public class ShowPhotoToGVAdapter extends CommonAdapter<String> {
    private static int mImageViewHeight;   //计算图片的高度

    public ShowPhotoToGVAdapter(Context context, List<String> datas, int layoutId) {
        super(context, datas, layoutId);
        mImageViewHeight = UIUtils.getWindowWidth(context) / 3 - UIUtils.dip2px(context, 10);
    }

    @Override
    public void convertView(ViewHolder holder, final int pos) {
        SimpleDraweeView simpleDraweeView = holder.getView(R.id.sdv_show_photo);
        final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
        layoutParams.width = mImageViewHeight;
        layoutParams.height = mImageViewHeight;
        simpleDraweeView.setLayoutParams(layoutParams);
        ImageUtil.loadCommonImg(simpleDraweeView, mDataList.get(pos));
        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mBaseActivity, ImageBrowseActivity.class);
                intent.putStringArrayListExtra(ImageBrowseActivity.LIST, (ArrayList<String>) mDataList);
                intent.putExtra(ImageBrowseActivity.SELECT, pos);
                mBaseActivity.startActivity(intent);
            }
        });
    }
}
