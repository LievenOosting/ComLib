package com.qcj.common.util;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by leoray on 2016-09-26.
 * 1000 = 1s
 * 根据需要配置此工具，实例化后，调用start()
 */
public class ButtonCountdown extends CountDownTimer {
    private Button btn;
    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public ButtonCountdown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }
    public ButtonCountdown(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    @Override
    public void onTick(long millisUntilFinished) {

        btn.setClickable(false);
        btn.setText(millisUntilFinished / 1000 + "秒后重新获取");

    }

    @Override
    public void onFinish() {

        btn.setClickable(true);
        btn.setText("获取验证码");

    }
}
