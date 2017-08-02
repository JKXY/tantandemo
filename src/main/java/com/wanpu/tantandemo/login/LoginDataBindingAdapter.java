package com.wanpu.tantandemo.login;

import android.databinding.BindingAdapter;

import com.wanpu.tantandemo.EntityList;
import com.wanpu.tantandemo.widget.RecyclerBanner;

/**
 * Created by Administrator on 2017/6/19.
 */

public class LoginDataBindingAdapter {
    @BindingAdapter("autoPlay")
    public static void setAutoPlay(RecyclerBanner view ,boolean autoPlay){
        view.setAutoPlay(autoPlay);
    }

    @BindingAdapter("bannerData")
    public static void setBannerData(RecyclerBanner view , EntityList list){
        view.setDatas(list.getList());
    }
}
