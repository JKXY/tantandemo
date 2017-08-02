package com.wanpu.tantandemo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wanpu.tantandemo.util.SPUtil;

import static okhttp3.internal.Internal.instance;

/**
 * Created by Administrator on 2017/6/19.
 */

public class TanTanApplication extends Application{
    private SPUtil preUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }

    /**
     * @return 返回 preUtil
     */
    public SPUtil getPreUtils() {
        if (preUtil == null) {
            preUtil = new SPUtil(this, Constant.PrefConst.PREFERENCE_FILENAME, MODE_PRIVATE);
        }
        return preUtil;
    }


}
