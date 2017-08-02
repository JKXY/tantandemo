package com.wanpu.tantandemo.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.wanpu.tantandemo.R;
import com.wanpu.tantandemo.databinding.ActivityRegistThreeBinding;

public class RegistThreeActivity extends AppCompatActivity {
    ActivityRegistThreeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_regist_three);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String areaCode = intent.getStringExtra("areaCode");
        String phone = intent.getStringExtra("phone");
        String code = intent.getStringExtra("code");
        binding.setRegist(new RegistThreeViewModel(binding));
    }
}
