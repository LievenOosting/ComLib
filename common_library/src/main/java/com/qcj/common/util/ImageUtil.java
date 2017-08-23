package com.qcj.common.util;


import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.qcj.common.R;
import com.qcj.common.base.BaseApplication;

import java.io.File;


/**
 * Created by qcj on 2015/11/30.
 * <p/>
 * 使用Fresco加载图片<p/>
 * 远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案<br/>
 * 本地文件	file://	FileInputStream<br/>
 * Content provider	content://	ContentResolver<br/>
 * asset目录下的资源	asset://	AssetManager<br/>
 * res目录下的资源	res://	Resources.openRawResource<br/>
 * Uri uri = Uri.parse("res://包名(实际可以是任何字符串甚至留空)/" + R.drawable.ic_launcher);
 */
public class ImageUtil {

    private static GenericDraweeHierarchy circleHierarchy = null;
    private static GenericDraweeHierarchy normalHierarchy = null;

    /**
     * 通用图片加载<br/>
     * 远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案<br/>
     * 本地文件	file://	FileInputStream<br/>
     * Content provider	content://	ContentResolver<br/>
     * asset目录下的资源	asset://	AssetManager<br/>
     * res目录下的资源	res://	Resources.openRawResource<br/>
     * Uri uri = Uri.parse("res://包名(实际可以是任何字符串甚至留空)/" + R.drawable.ic_launcher);
     *
     * @param view
     * @param imgurl
     */
    public static void loadCommonImg(SimpleDraweeView view, String imgurl) {
        if (TextUtils.isEmpty(imgurl)) return;
        GenericDraweeHierarchyBuilder builderNormal = new GenericDraweeHierarchyBuilder(BaseApplication.getInstance().getResources());
        normalHierarchy = builderNormal.setFadeDuration(300).build();
        normalHierarchy.setPlaceholderImage(R.mipmap.icon_load_default);
        view.setHierarchy(normalHierarchy);
        view.setController(getDraweeController(view, imgurl));
    }

    /**
     * 圆形图片加载方法<br/>
     * 远程图片	http://, https://	HttpURLConnection 或者参考 使用其他网络加载方案<br/>
     * 本地文件	file://	FileInputStream<br/>
     * Content provider	content://	ContentResolver<br/>
     * asset目录下的资源	asset://	AssetManager<br/>
     * res目录下的资源	res://	Resources.openRawResource<br/>
     * Uri uri = Uri.parse("res://包名(实际可以是任何字符串甚至留空)/" + R.drawable.ic_launcher);
     *
     * @param view
     * @param imgurl
     */
    public static void loadCircleImg(SimpleDraweeView view, String imgurl) {
        RoundingParams roundingParams = RoundingParams.asCircle();
        roundingParams.setRoundAsCircle(true);
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(BaseApplication.getInstance().getResources());
        builder.setRoundingParams(roundingParams);
        circleHierarchy = builder.setFadeDuration(500).build();
        circleHierarchy.setPlaceholderImage(R.mipmap.icon_load_default);
        view.setHierarchy(circleHierarchy);
        view.setController(getDraweeController(view, imgurl));
    }

    public static void loadRoundParamsImg(SimpleDraweeView view, String imgurl, RoundingParams params) {
        if (params == null) {
            params = new RoundingParams();
            params.setCornersRadius(5);
        }
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(BaseApplication.getInstance().getResources());
        builder.setRoundingParams(params);
        circleHierarchy = builder.setFadeDuration(500).build();
        circleHierarchy.setPlaceholderImage(R.mipmap.icon_load_default);
        view.setHierarchy(circleHierarchy);
        view.setController(getDraweeController(view, imgurl));
    }

    /**
     * @param targetView
     * @param imgurl
     * @return
     */
    private static DraweeController getDraweeController(DraweeView targetView, String imgurl) {
        Uri uri = Uri.parse(imgurl);
        int width = targetView.getLayoutParams().width;
        int height = targetView.getLayoutParams().height;
        if (width <= 0 || height <= 0) {
            width = UIUtils.dip2px(BaseApplication.getInstance(), 80);
            height = UIUtils.dip2px(BaseApplication.getInstance(), 80);
        }
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(targetView.getController())
                .setImageRequest(request)
                .setCallerContext(uri)
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)//点击重新加载图
                .build();
        return controller;
    }
}
