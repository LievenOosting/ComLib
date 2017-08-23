package com.qcj.common.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.qcj.common.R;
import com.qcj.common.interf.MultiItemTypeSupport;
import com.qcj.common.util.UIUtils;
import com.qcj.common.util.localImageHelper.ImageBrowseActivity;
import com.qcj.common.util.localImageHelper.LocalImagListActivity;
import com.qcj.common.util.localImageHelper.LocalImageManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by qiuchunjia on 2016/9/1.
 */
public class AddPhotoToGVAdapter extends MultiItemCommonAdapter<String> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ADD = 1;
    private static int mImageViewHeight;   //计算图片的高度

    public AddPhotoToGVAdapter(Context context, List<String> datas, MultiItemTypeSupport multiItemTypeSupport) {
        super(context, datas, multiItemTypeSupport);
        mImageViewHeight = UIUtils.getWindowWidth(context) / 3 - UIUtils.dip2px(context, 10);
    }

    @Override
    public void convertView(ViewHolder holder, final int pos) {
        ImageView imageView = holder.getView(R.id.iv_add_photo);
        final ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = mImageViewHeight;
        layoutParams.height = mImageViewHeight;
        imageView.setLayoutParams(layoutParams);
        ImageView deleteView = holder.getView(R.id.iv_delete_photo);
        if (judgeTheViewType(pos) == TYPE_ADD) {
            deleteView.setVisibility(View.GONE);
            holder.setOnClickListener(R.id.iv_add_photo, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //// TODO: 2016/9/1
                    mApp.startActivityForResult(mBaseActivity, LocalImagListActivity.class, null);
                }
            });

        } else {
            LocalImageManager.from(mBaseActivity).displayImage(imageView, mDataList.get(pos), 0, UIUtils.dip2px(mBaseActivity, 80), UIUtils.dip2px(mBaseActivity, 80));
            deleteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDataList.remove(pos);
                    notifyDataSetChanged();
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mBaseActivity, ImageBrowseActivity.class);
                    ArrayList pathlist = new ArrayList();
                    for (int j = 0; j < mDataList.size() - 1; j++) {
                        pathlist.add(mDataList.get(j));
                    }
                    intent.putStringArrayListExtra(ImageBrowseActivity.LIST, pathlist);
                    intent.putExtra(ImageBrowseActivity.SELECT, pos);
                    mBaseActivity.startActivity(intent);
                }
            });
        }

    }

    /**
     * 根据pos 判断需要加载view的类型
     *
     * @param pos
     * @return
     */
    private int judgeTheViewType(int pos) {
        if (pos == mDataList.size() - 1) {
            return TYPE_ADD;
        }
        return TYPE_ITEM;
    }

    public class AddPhotoTypeSupport implements MultiItemTypeSupport<String> {


        @Override
        public int getLayoutId(int position, String str) {
            if (TYPE_ITEM == judgeTheViewType(position)) {
                return R.layout.item_add_delete_photo;
            } else {
                return R.layout.item_add_delete_photo;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int postion, String str) {
            return judgeTheViewType(postion);
        }
    }
}
