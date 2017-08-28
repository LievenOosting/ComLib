package com.comlib.leoray.app.test;

import android.widget.Button;
import com.comlib.leoray.app.R;
import com.qcj.common.base.BaseActivity;
import com.qcj.common.util.ButtonCountdown;

/**
 * Created by leoray on 2017-08-28.
 */

public class UtilToolDimen extends BaseActivity{

    private Button test;

    @Override
    public void initView() {

        test = (Button) findViewById(R.id.test);
        ButtonCountdown btnCD = new ButtonCountdown(60000,1000,test);
        btnCD.start();
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_main;
    }
}
