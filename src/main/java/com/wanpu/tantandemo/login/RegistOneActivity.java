package com.wanpu.tantandemo.login;

import android.app.Activity;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wanpu.tantandemo.R;
import com.wanpu.tantandemo.databinding.ActivityRegistOneBinding;

public class RegistOneActivity extends AppCompatActivity {
    ActivityRegistOneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_regist_one);
        initData();
    }

    private void initData() {
        binding.setRegist(new RegistViewModel());
    }
}
