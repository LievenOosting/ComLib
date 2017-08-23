package com.qcj.common.util.localImageHelper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.qcj.common.R;
import com.qcj.common.base.BaseActivity;
import com.qcj.common.widget.ViewPagerFixed;

import java.util.List;

import uk.co.senab.photoview.PhotoView;


/**
 * 图片浏览
 * Created by qiuchunjia
 */
public class ImageBrowseActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    public static final String LIST = "list";
    public static final String SELECT = "select";
    private int position;//点击的图片序号
    /**
     * ViewPager
     */
    private ViewPagerFixed viewPager;
    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;
    /**
     * 图片资源
     */
    private List<String> mFiles;

    ImageLoader mImageLoader;
    DisplayImageOptions options;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_browse_actvity;
    }

    @Override
    public void initView() {
        setToolbarTitle("图片浏览");
    }

    public void initData() {
        mImageLoader=ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                // 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)
                .build();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(this));
        Intent intent = getIntent();
        mFiles = intent.getStringArrayListExtra(LIST);
        position = intent.getIntExtra(SELECT, 0);
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        viewPager = (ViewPagerFixed) findViewById(R.id.viewPager);
        //将点点加入到ViewGroup中
        tips = new ImageView[mFiles.size()];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(5, 0, 5, 40);
            imageView.setLayoutParams(params);
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.choseed);
            } else {
                tips[i].setBackgroundResource(R.mipmap.notchose);
            }
            group.addView(imageView);
        }
        //设置Adapter
        viewPager.setAdapter(new MyAdapter());
        //设置监听，主要是设置点点的背景
        viewPager.setOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        /*viewPager.setCurrentItem((mImageViews.length) * 100);*/
        viewPager.setCurrentItem(position);
        group.getChildAt(position).setSelected(true);
    }

    /**
     * 适配器
     */
    public class MyAdapter extends PagerAdapter {
        // 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
        @Override
        public int getCount() {
            return mFiles.size();
        }

        // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setBackgroundColor(Color.parseColor("#000000"));
            if(mFiles.get(position).contains("http")){
                mImageLoader.displayImage(mFiles.get(position), photoView,options);
            }else{
                mImageLoader.displayImage("file://"+mFiles.get(position), photoView,options);
            }
            // 然后将加载了图片的photoView添加到viewpager中，并且设置宽高
            container.addView(photoView, LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            return photoView;
        }
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.mipmap.choseed);
            } else {
                tips[i].setBackgroundResource(R.mipmap.notchose);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}