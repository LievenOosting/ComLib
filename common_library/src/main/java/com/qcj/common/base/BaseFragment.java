package com.qcj.common.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qcj.common.AppConfig;
import com.qcj.common.interf.DialogControl;
import com.qcj.common.interf.UIInterface;

import java.io.Serializable;


public abstract class BaseFragment extends Fragment implements
        View.OnClickListener, UIInterface {
    public String TAG = this.getClass().toString();  //tag标志
    private View mView;  //父布局的view
    protected LayoutInflater mInflater;
    public BaseApplication mApp;
    public BaseActivity mBaseActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            if (getActivity() instanceof BaseActivity) {
                mBaseActivity = (BaseActivity) getActivity();
            }
            mView = inflater.inflate(getLayoutId(), null);
            mInflater = inflater;
            mApp = BaseApplication.getInstance();
            initView();
            initData();
            setListener();
        } else {
            // 当存在mview的时候清除父布局的里面的mView
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
        }
        return mView;
    }

    /**
     * 获取layoutid
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected View inflateView(int resId) {
        return this.mInflater.inflate(resId, null);
    }

    public boolean onBackPressed() {
        return false;
    }

    protected void hideWaitDialog() {
        FragmentActivity activity = getActivity();
        if (activity instanceof DialogControl) {
            ((DialogControl) activity).hideWaitDialog();
        }
    }

    protected ProgressDialog showWaitDialog() {
        return showWaitDialog("加载中");
    }

    protected ProgressDialog showWaitDialog(int resid) {
        FragmentActivity activity = getActivity();
        if (activity instanceof DialogControl) {
            return ((DialogControl) activity).showWaitDialog(resid);
        }
        return null;
    }

    protected ProgressDialog showWaitDialog(String str) {
        FragmentActivity activity = getActivity();
        if (activity instanceof DialogControl) {
            return ((DialogControl) activity).showWaitDialog(str);
        }
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View v) {

    }

    /*******************
     * 当时一个页面有多个fragment加载数据的时候（没有viewpager的情况）
     * 使用方法为：1：调用requestDataDelay()，然后重写performRequestdata（）来请求网络数据就可以了
     **********************************/
    private Handler mRequsetHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            performRequestdata();
        }
    };

    private static final int mDelayTime = (int) (0.5 * 500);

    /**
     * 延迟显示加载动画
     */
    public void requestDataDelay() {
        mRequsetHander.sendEmptyMessageDelayed(0, mDelayTime);
    }

    /**
     * 执行请求
     */
    public void performRequestdata() {

    }
    /*************************end*********************************/
    /*******************
     * 当时一个页面有多个fragment加载数据的时候（使用viewpager的情况）解决预加载的的问题
     * 使用方法为：重写requsetDataWhenUseViewPager（）来请求网络数据就可以了
     **********************************/
    private boolean isLoaded = false;  //是否加载了

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && !isLoaded) {
            //TO-DO 执行网络数据请求
            isLoaded = true;
            requsetDataWhenUseViewPager();
        }
    }

    /**
     * 当使用viewpager加fragmment的时候加载数据,子类需要的时候就实现它（这个不强求哈）
     */
    public void requsetDataWhenUseViewPager() {

    }
    /*************************end*********************************/


    /**
     * 通过id 获取view
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(int viewId) {
        if (mView != null) {
            return (T) mView.findViewById(viewId);
        }
        return null;
    }

    /**
     * 把数据封装到bundel中 用于传递
     */
    public Bundle sendDataToBundle(Serializable serializable, String flag) {
        String defaultFlag = AppConfig.ACTIVITY_TRANSFER_BUNDLE;
        if (flag != null) {
            defaultFlag = flag;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(defaultFlag, serializable);
        return bundle;
    }

    public Bundle sendDataToBundle(Serializable serializable) {
        return sendDataToBundle(serializable, null);
    }
}
