package com.wanpu.tantandemo.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.wanpu.tantandemo.util.CheckUtil;

/**
 * Created by Administrator on 2017/6/19.
 */

public class RegistViewModel {
    AreaCode areaCode = new AreaCode("中国","+86");
    ObservableField<String> phone = new ObservableField<>();
    ObservableField<Boolean> oneNext = new ObservableField<>();

    public ObservableField<String> getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public ObservableField<Boolean> getOneNext() {
        return oneNext;
    }

    public void setOneNext(boolean oneNext) {
        this.oneNext.set(oneNext);
    }

    public AreaCode getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(AreaCode areaCode) {
        this.areaCode = areaCode;
    }

    public RegistViewModel() {
        oneNext.set(false);
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
            if (CheckUtil.isMobileNO(s.toString())) {
                oneNext.set(true);
            } else {
                oneNext.set(false);
            }
        }
    };

//    android:onClick="@{() -> regist.onNextClick(context,areacode.id,regist.phone)}"
    public void onNextClick(View view) {
        Intent intent = new Intent(view.getContext(), RegistTwoActivity.class);
        intent.putExtra("areaCode",areaCode.getId().get());
        intent.putExtra("phone",phone.get());
        view.getContext().startActivity(intent);
        ((Activity)(view.getContext())).finish();
    }



}
