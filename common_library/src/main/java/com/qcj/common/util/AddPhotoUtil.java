package com.qcj.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qcj.common.AppConfig;
import com.qcj.common.R;
import com.qcj.common.adapter.AddPhotoToGVAdapter;
import com.qcj.common.base.BaseActivity;
import com.qcj.common.base.BaseApplication;
import com.qcj.common.util.localImageHelper.ImageBrowseActivity;
import com.qcj.common.util.localImageHelper.LocalImagListActivity;
import com.qcj.common.util.localImageHelper.LocalImageManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cashc on 16/10/19.
 * 该类封装图片添加器.
 * 实现横着添加(HorizontalScrollView)和从网络中获取图片显示到这个控件上面
 * 和竖着添加(GridView)和从网络中获取图片显示到这个控件上面
 * 使用秘籍之-----------------葵花宝典
 * 1：使用HorizontalScrollView步骤（第一步）--先添加布局文件（第二步）initPhotoToHSV方法（）（第三步）在onactivityresult方法里面调用addPhotoToHSV（）；
 * <p/>
 * 2：使用GridView步骤（第一步）--先添加布局文件（第二步）initPhotoToGV（）（第三步）在onactivityresult方法里面调用addPhotoToGV（）；
 */

public class AddPhotoUtil {
    //最大保存的图片的数量
    public static final int MAX_SAVE_PHOTO_NUMBER = 6;
    //默认的添加的数据 用来区分添加图片的按钮
    public static final String DEFAULTPATH = "defaultPath";
    //图片的路径集合
    private static List<String> mFiles = new ArrayList<>();
    private static AddPhotoToGVAdapter mAddPhotoToGvAdapter;  //用于添加图片到gridview

    public static void initPhotoToHSV(LinearLayout view, final Activity activity) {
        mFiles.clear();
        mFiles.add(DEFAULTPATH);
        if (view != null && activity != null) {
            view.removeAllViews();
            View rootView = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout.item_add_delete_photo, null);
            view.addView(rootView);
            rootView.findViewById(com.qcj.common.R.id.iv_delete_photo).setVisibility(View.GONE);
            rootView.findViewById(com.qcj.common.R.id.iv_add_photo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseApplication.getInstance().startActivityForResult(activity, LocalImagListActivity.class, null);
                }
            });
        }
    }

    public static void initPhotoToGV(GridView view, Context context) {
        if (view != null) {
            mFiles.clear();
            mFiles.add(DEFAULTPATH);
            mAddPhotoToGvAdapter = new AddPhotoToGVAdapter(context, mFiles, null);
            AddPhotoToGVAdapter.AddPhotoTypeSupport typeSupport = mAddPhotoToGvAdapter.new AddPhotoTypeSupport();
            mAddPhotoToGvAdapter.setMultiItemTypeSupport(typeSupport);
            view.setAdapter(mAddPhotoToGvAdapter);
        }
    }

    /**
     * 添加图片的HorizontalScrollView
     *
     * @param view
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void addPhotoToHSV(final Activity activity, final LinearLayout view, int requestCode,
                                     int resultCode, Intent data) {
        if (requestCode == BaseActivity.GET_DATA_FROM_ACTIVITY) {
            if (data == null) {
                return;
            }
            if (getReturnResultSeri(resultCode, data, null) instanceof List && view != null) {
                List<String> tempFiles = getReturnResultSeri(resultCode, data, null);
                String tempfile = mFiles.remove(mFiles.size() - 1);
                mFiles.addAll(tempFiles);
                mFiles.add(tempfile);
                //根据文件路径的多少来生成多少view；
                view.removeAllViews();
                for (int i = 0; i < mFiles.size(); i++) {
                    View rootView = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout.item_add_delete_photo, null);
                    view.addView(rootView);
                    ImageView ivAddPhoto = (ImageView) rootView.findViewById(R.id.iv_add_photo);
                    ImageView ivDeletePhoto = (ImageView) rootView.findViewById(R.id.iv_delete_photo);
                    if (i == mFiles.size() - 1) {
                        rootView.findViewById(com.qcj.common.R.id.iv_delete_photo).setVisibility(View.GONE);
                        rootView.findViewById(com.qcj.common.R.id.iv_add_photo).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BaseApplication.getInstance().startActivityForResult(activity, LocalImagListActivity.class, null);
                            }
                        });
                    } else {
                        LocalImageManager.from(BaseApplication.getInstance()).displayImage(ivAddPhoto, mFiles.get(i), 0);
                        final int finalI = i;
                        ivAddPhoto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //// TODO: 2016/10/20  可以实现图片的预览
                                Intent intent = new Intent(activity, ImageBrowseActivity.class);
                                ArrayList pathlist = new ArrayList();
                                for (int j = 0; j < mFiles.size() - 1; j++) {
                                    pathlist.add(mFiles.get(j));
                                }
                                intent.putStringArrayListExtra(ImageBrowseActivity.LIST, pathlist);
                                intent.putExtra(ImageBrowseActivity.SELECT, finalI);
                                activity.startActivity(intent);
                            }
                        });
                        ivDeletePhoto.setTag(mFiles.get(i));
                        ivDeletePhoto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String tag = (String) v.getTag();
                                for (int j = 0; j < mFiles.size() - 1; j++) {
                                    if (mFiles.get(j).equals(tag)) {
                                        mFiles.remove(j);
                                        view.removeViewAt(j);
                                    }
                                }

                            }
                        });
                    }
                }
            }
        }

    }

    ;

    /**
     * 添加图片到GridView
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public static void addPhotoToGV(int requestCode,
                                    int resultCode, Intent data) {
        if (requestCode == BaseActivity.GET_DATA_FROM_ACTIVITY) {
            if (data == null) {
                return;
            }
            if (getReturnResultSeri(resultCode, data, null) instanceof List) {
                List<String> tempFiles = getReturnResultSeri(resultCode, data, null);
                String tempfile = mFiles.remove(mFiles.size() - 1);
                mFiles.addAll(tempFiles);
                mFiles.add(tempfile);
                //根据文件路径的多少来生成多少view；
                mAddPhotoToGvAdapter.notifyDataSetChanged();
            }
        }

    }

    /*******************************
     * 显示图片到相应的控件上面
     *****************************************/
    /**
     * 显示图片到HorizontalScrollView
     *
     * @param view
     * @param files
     * @param activity
     */
    public static void showPhotoToHSV(LinearLayout view, final List<String> files, final Activity activity) {
        if (view == null && files == null) return;
        view.removeAllViews();
        for (int i = 0; i < files.size(); i++) {
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(activity, 80), UIUtils.dip2px(activity, 80));
            params.leftMargin = UIUtils.dip2px(activity, 5);
            params.rightMargin = UIUtils.dip2px(activity, 5);
            simpleDraweeView.setLayoutParams(params);
            if (!files.get(i).contains("http")) {
                ImageUtil.loadCommonImg(simpleDraweeView, "file://" + files.get(i));
            } else {
                ImageUtil.loadCommonImg(simpleDraweeView, files.get(i));
            }

            simpleDraweeView.setTag(i);
            view.addView(simpleDraweeView);
            simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    Intent intent = new Intent(activity, ImageBrowseActivity.class);
                    intent.putStringArrayListExtra(ImageBrowseActivity.LIST, (ArrayList<String>) files);
                    intent.putExtra(ImageBrowseActivity.SELECT, tag);
                    activity.startActivity(intent);
                }
            });
        }
    }

    /**
     * 显示图片到GridView
     *
     * @param view
     * @param files
     * @param context
     */
    public static void showPhotoToGV(GridView view, List<String> files, Context context) {

    }


    /**
     * 获取真实的图片地址file
     *
     * @return
     */
    public static List<String> getRealFileList() {
        if (mFiles != null && mFiles.size() > 1) {
            ArrayList pathlist = new ArrayList();
            for (int j = 0; j < mFiles.size() - 1; j++) {
                pathlist.add(mFiles.get(j));
            }
            return pathlist;
        }
        return null;
    }


    /**
     * 获取图片的路径集合
     *
     * @return
     */
    public static List<String> getFiles() {
        return mFiles;
    }

    /**
     * 清除图片路径集合
     */
    public void cleanFiles() {
        mFiles.clear();
    }

    /**
     * 获取结果
     *
     * @param flag 区分传的值
     * @return
     */
    public static <T extends Serializable> T getReturnResultSeri(int resultCode, Intent intent,
                                                                 String flag) {
        String defaultFlag = AppConfig.ACTIVITY_TRANSFER_BUNDLE;
        if (resultCode == BaseActivity.ACTIVTIY_TRANFER && intent != null) {
            if (flag != null) {
                defaultFlag = flag;
            }
            Bundle bundle = intent.getExtras();
            return (T) bundle.getSerializable(defaultFlag);
        }
        return null;
    }

}
