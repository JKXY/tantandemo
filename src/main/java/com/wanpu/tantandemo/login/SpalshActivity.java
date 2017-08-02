package com.wanpu.tantandemo.login;

import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wanpu.tantandemo.EntityList;
import com.wanpu.tantandemo.R;
import com.wanpu.tantandemo.databinding.ActivitySpalshBinding;
import com.wanpu.tantandemo.widget.RecyclerBanner;

import java.util.ArrayList;
import java.util.List;

public class SpalshActivity extends AppCompatActivity {
    ActivitySpalshBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_spalsh);
        initData();

    }

    private void initData() {
        List<RecyclerBanner.BannerEntity> banners = new ArrayList<>();
        EntityList.Entity e0 = new EntityList.Entity(0, BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
        banners.add(e0);
        banners.add(e0);
        banners.add(e0);
        binding.setEntityList(new EntityList(banners));
    }
}
