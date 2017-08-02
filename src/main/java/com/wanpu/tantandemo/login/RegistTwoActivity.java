package com.wanpu.tantandemo.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wanpu.tantandemo.R;
import com.wanpu.tantandemo.databinding.ActivityRegistTwoBinding;

public class RegistTwoActivity extends AppCompatActivity {
    ActivityRegistTwoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_regist_two);
        initData();


    }

    private void initData() {
        Intent intent = getIntent();
        String areaCode = intent.getStringExtra("areaCode");
        String phone = intent.getStringExtra("phone");
        AreaCode areaCode1 = new AreaCode(areaCode);
        RegistTwoViewModel model = new RegistTwoViewModel(binding);
        model.setAreaCode(areaCode1);
        model.setPhone(phone);
        binding.setRegist(model);
    }
}
