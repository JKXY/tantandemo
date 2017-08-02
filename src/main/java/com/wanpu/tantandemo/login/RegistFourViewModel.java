package com.wanpu.tantandemo.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.ObservableField;
import android.graphics.Paint;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.wanpu.tantandemo.main.MainActivity;
import com.wanpu.tantandemo.databinding.ActivityRegistFourBinding;

/**
 * Created by Administrator on 2017/6/21.
 */

public class RegistFourViewModel {
    ActivityRegistFourBinding binding;

    public RegistFourViewModel(ActivityRegistFourBinding binding) {
        this.binding = binding;
        binding.tvUser.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        binding.tvUser.getPaint().setAntiAlias(true);//抗锯齿
        oneNext.set(true);
    }


    ObservableField<Boolean> oneNext = new ObservableField<>();

    public ObservableField<Boolean> getOneNext() {
        return oneNext;
    }

    public void setOneNext(boolean oneNext) {
        this.oneNext.set(oneNext);
    }

    public void onNextClick(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        view.getContext().startActivity(intent);
        ((Activity) (view.getContext())).finish();
    }

    public void onIconImageClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permission = ContextCompat.checkSelfPermission(view.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission == PackageManager.PERMISSION_DENIED) { //表示未授权
                ((Activity) (view.getContext())).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RegistFourActivity.Reuqest_Code_Permisser);
                return;
            }
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        ((Activity) (view.getContext())).startActivityForResult(intent, RegistFourActivity.TAKE_GALLERY);
    }
}
