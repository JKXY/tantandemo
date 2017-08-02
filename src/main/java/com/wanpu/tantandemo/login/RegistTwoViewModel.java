package com.wanpu.tantandemo.login;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.wanpu.tantandemo.databinding.ActivityRegistTwoBinding;
import com.wanpu.tantandemo.util.CheckUtil;

/**
 * Created by Administrator on 2017/6/19.
 */

public class RegistTwoViewModel {
    ActivityRegistTwoBinding binding;
    AreaCode areaCode = new AreaCode("中国", "+86");
    ObservableField<String> phone = new ObservableField<>();
    ObservableField<String> showPhone = new ObservableField<>();
    ObservableField<Boolean> oneNext = new ObservableField<>();
    TimeCount timeCount;

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

    public String getShowPhone() {
        StringBuffer sb = new StringBuffer();
        sb.append(areaCode.getId().get()).append(" ");
        sb.append(phone.get().substring(0, 3)).append(" ");
        sb.append(phone.get().substring(3, 7)).append(" ");
        sb.append(phone.get().substring(7, 11)).append(" ");
        return sb.toString();
    }

    public RegistTwoViewModel(ActivityRegistTwoBinding binding) {
        this.binding = binding;
        codeTimeText.set("重新发送(60s)");
        codeSendShow.set(true);
        codeTimeShow.set(false);
        timeCount = new TimeCount(60000, 1000);
    }


    ObservableField<String> Code1 = new ObservableField<String>();
    ObservableField<String> Code2 = new ObservableField<String>();
    ObservableField<String> Code3 = new ObservableField<String>();
    ObservableField<String> Code4 = new ObservableField<String>();

    public ObservableField<String> getCode1() {
        return Code1;
    }

    public ObservableField<String> getCode2() {
        return Code2;
    }

    public ObservableField<String> getCode3() {
        return Code3;
    }

    public ObservableField<String> getCode4() {
        return Code4;
    }


    public void setCode1(String code1) {
        Code1.set(code1);
    }

    public void setCode2(String code2) {
        Code2.set(code2);
    }

    public void setCode3(String code3) {
        Code3.set(code3);
    }

    public void setCode4(String code4) {
        Code4.set(code4);
    }

    public TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() >= 1) {
                Code1.set(s.toString());
                binding.etCode2.requestFocus();
            } else {
                binding.etCode1.requestFocus();
            }
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
            if (s.length() >= 1) {
                Code2.set(s.toString());
                binding.etCode3.requestFocus();
            } else {
                binding.etCode2.requestFocus();
            }
        }
    };
    public TextWatcher watcher3 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() >= 1) {
                Code3.set(s.toString());
                binding.etCode4.requestFocus();
            } else {
                binding.etCode3.requestFocus();
            }
        }
    };
    public TextWatcher watcher4 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() >= 1) {
                Code4.set(s.toString());
                oneNext.set(true);
            } else {
                binding.etCode4.requestFocus();
                oneNext.set(false);
            }
        }
    };


    public void onNextClick(View view) {
        Intent intent = new Intent(view.getContext(), RegistThreeActivity.class);
        intent.putExtra("areaCode", areaCode.getId().get());
        intent.putExtra("phone", phone.get());
        String code = Code1.get() + Code2.get() + Code3.get() + Code4.get();
        intent.putExtra("code", code);
        view.getContext().startActivity(intent);
        ((Activity) (view.getContext())).finish();
        timeCount.cancel();
    }


    ObservableField<Boolean> codeSendShow = new ObservableField<>();
    ObservableField<Boolean> codeTimeShow = new ObservableField<>();
    ObservableField<String> codeTimeText = new ObservableField<>();


    public ObservableField<Boolean> getCodeSendShow() {
        return codeSendShow;
    }

    public ObservableField<Boolean> getCodeTimeShow() {
        return codeTimeShow;
    }

    public ObservableField<String> getCodeTimeText() {
        return codeTimeText;
    }

    public void setCodeSendShow(Boolean codeSendShow) {
        this.codeSendShow.set(codeSendShow);
    }

    public void setCodeTimeShow(Boolean codeTimeShow) {
        this.codeTimeShow.set(codeTimeShow);
    }

    public void setCodeTimeText(String codeTimeText) {
        this.codeTimeText.set(codeTimeText);
    }

    // 计时器
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {
            codeTimeShow.set(false);
            codeSendShow.set(true);
        }


        @Override
        public void onTick(long millisUntilFinished) {
            codeTimeText.set("重新发送(" + millisUntilFinished / 1000 + "s)");
        }
    }


    public void onSendCodeClick(View view) {
        timeCount.start();
        codeTimeShow.set(true);
        codeSendShow.set(false);
    }

}
