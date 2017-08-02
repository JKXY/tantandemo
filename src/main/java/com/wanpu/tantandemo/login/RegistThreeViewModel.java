package com.wanpu.tantandemo.login;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingMethods;
import android.databinding.ObservableField;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.wanpu.tantandemo.BR;
import com.wanpu.tantandemo.databinding.ActivityRegistThreeBinding;
import com.wanpu.tantandemo.util.CheckUtil;

/**
 * Created by Administrator on 2017/6/21.
 */

public class RegistThreeViewModel {
    ActivityRegistThreeBinding binding;

    public RegistThreeViewModel(ActivityRegistThreeBinding binding) {
        this.binding = binding;
        oneNext.set(false);
    }

    ObservableField<Boolean> oneNext = new ObservableField<>();

    public ObservableField<Boolean> getOneNext() {
        return oneNext;
    }

    public void setOneNext(boolean oneNext) {
        this.oneNext.set(oneNext);
    }

    public TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                binding.inputLayout.setErrorEnabled(true);
                binding.inputLayout.setError("请输入昵称");
            } else if (s.length() < 2 || s.length() > 8) {
                binding.inputLayout.setErrorEnabled(true);
                binding.inputLayout.setError("昵称长度必须为2-8位");
            } else {
                binding.inputLayout.setErrorEnabled(false);
            }
            isNext();
        }
    };
    public TextWatcher watcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                binding.inputLayout2.setErrorEnabled(true);
                binding.inputLayout2.setError("请输入密码(6-20位)");
            } else if (s.length() < 6 || s.length() > 20) {
                binding.inputLayout2.setErrorEnabled(true);
                binding.inputLayout2.setError("密码长度必须为6-20位");
            } else {
                binding.inputLayout2.setErrorEnabled(false);
            }
            isNext();
        }
    };


    private void isNext() {
        String name = binding.inputLayout.getEditText().getText().toString();
        if (name.length() < 2 || name.length() > 8) {
            oneNext.set(false);
            return;
        }
        if (binding.tvTime.getText().toString().length() == 0) {
            oneNext.set(false);
            return;
        }
        if (!binding.rbMan.isChecked() && !binding.rbWaman.isChecked()) {
            oneNext.set(false);
            return;
        }
        String pass = binding.inputLayout2.getEditText().getText().toString();
        if (pass.length() < 6 || pass.length() > 20) {
            oneNext.set(false);
            return;
        }
        oneNext.set(true);
    }


    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId){
        isNext();
    }

    public void onNextClick(View view) {
        Intent intent = new Intent(view.getContext(), RegistFourActivity.class);
        view.getContext().startActivity(intent);
        ((Activity) (view.getContext())).finish();
    }
}
