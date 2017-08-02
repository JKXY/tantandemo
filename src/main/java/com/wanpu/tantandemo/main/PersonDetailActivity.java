package com.wanpu.tantandemo.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;

import com.wanpu.tantandemo.PersonBean;
import com.wanpu.tantandemo.R;
import com.wanpu.tantandemo.databinding.ActivityPersonDetailBinding;
import com.wanpu.tantandemo.login.RegistTwoActivity;
import com.wanpu.tantandemo.util.EasyTransition;

public class PersonDetailActivity extends AppCompatActivity {
    ActivityPersonDetailBinding binding;
    private PersonBean person;
    private boolean finishEnter;
    long transitionDuration = 500;

    public class HandlerClick {
        public void onImgClick(View view) {
            startBackAnim();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_person_detail);
        initDatas();
        initTransition(savedInstanceState);
        initViewAnim();
    }

    private void initViewAnim() {
        binding.linMessage.setTranslationY(600f);
        binding.linMessage.animate()
                .setDuration(transitionDuration)
                .translationY(0);

        binding.igUnlike.setTranslationX(-200f);
        binding.igUnlike.animate()
                .setDuration(transitionDuration)
                .translationX(0)
                .rotation(360);

        binding.igLike.setTranslationX(200f);
        binding.igLike.animate()
                .setDuration(transitionDuration)
                .translationX(0)
                .rotation(-360);
    }

    private void initTransition(Bundle savedInstanceState) {
        if (null != savedInstanceState)
            transitionDuration = 0;
        EasyTransition.enter(
                this,
                transitionDuration,
                new DecelerateInterpolator(),
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // init other views after transition anim
                        finishEnter = true;

                    }
                });
    }

    private void initDatas() {
        person = (PersonBean) getIntent().getSerializableExtra("person");
        binding.setPerson(person);
        binding.setClick(new HandlerClick());
    }

    @Override
    public void onBackPressed() {
        if (finishEnter) {
            finishEnter = false;
            startBackAnim();
        }
    }

    private void startBackAnim() {
        binding.linMessage.animate()
                .setDuration(transitionDuration - 100)
                .translationY(600f);

        binding.igUnlike.animate()
                .setDuration(transitionDuration - 100)
                .translationX(-200f)
                .rotation(0);

        binding.igLike.animate()
                .setDuration(transitionDuration - 100)
                .translationX(200f)
                .rotation(0);
        EasyTransition.exit(PersonDetailActivity.this, transitionDuration, new DecelerateInterpolator());
    }
}
