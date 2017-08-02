package com.wanpu.tantandemo.main;

import android.animation.Animator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.wanpu.tantandemo.BindingViewHolder;
import com.wanpu.tantandemo.PersonBean;
import com.wanpu.tantandemo.R;
import com.wanpu.tantandemo.databinding.ActivityMainBinding;
import com.wanpu.tantandemo.layout_manager.CardItemTouchHelperCallback;
import com.wanpu.tantandemo.layout_manager.CardLayoutManager;
import com.wanpu.tantandemo.layout_manager.OnSwipeListener;
import com.wanpu.tantandemo.util.EasyTransition;
import com.wanpu.tantandemo.util.EasyTransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainAdapter adapter;
    private CardItemTouchHelperCallback callback;
    List<PersonBean> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initToolbar();
        EasyTransition.enter(this);
        initData();
        initView();
        initLoading();
    }

    private void initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.main_menu);//设置导航栏图标
        binding.toolbar.setTitle("");//设置主标题
        binding.toolbar.inflateMenu(R.menu.main_menu);//设置右上角的填充菜单
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_messgae) {
                    binding.drawerLayout.openDrawer(Gravity.RIGHT);
                }
                return true;
            }
        });
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        binding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (drawerView.getId() == R.id.naviLeft){
                    binding.mianContent.setTranslationX(slideOffset*drawerView.getWidth());
                }else{
                    binding.mianContent.setTranslationX(-slideOffset*drawerView.getWidth());
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    private void initLoading() {
        binding.setIsLoading(true);
        binding.scanPersonView.startScan();
        binding.scanPersonView.postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.scanPersonView.animate()
                        .scaleX(0)
                        .scaleY(0)
                        .setDuration(500)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                binding.setIsLoading(false);
                                binding.scanPersonView.stopScan();
                                binding.scanPersonView.destroyDrawingCache();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
            }
        }, 3000);
    }

    private void initData() {
        personList = new ArrayList<>();
        PersonBean bean;
        for (int i = 0; i < 10; i++) {
            bean = new PersonBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498110395257&di=81961c3d33014e938541d00f868ae9f2&imgtype=0&src=http%3A%2F%2Fimg3.duitang" +
                    ".com%2Fuploads%2Fitem%2F201608%2F03%2F20160803213658_5cRw4.thumb.700_0.jpeg"
                    , "迪丽热巴", 25 + i, i % 2, "双子座", "明星");
            personList.add(bean);
        }
    }

    private void initView() {
        adapter = new MainAdapter(this);
        adapter.setPersonList(personList);
        adapter.setOnItemClickListener(new onClickListener());
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        callback = new CardItemTouchHelperCallback(adapter, personList);
        callback.setmListener(new OnSwipeListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
                View like = viewHolder.itemView.findViewById(R.id.igLike);
                View unlike = viewHolder.itemView.findViewById(R.id.igUnlike);
                float scale = Math.abs(ratio) / 2 + 1;
                if (direction == OnSwipeListener.SWIPING_LEFT) {
                    unlike.setAlpha(Math.abs(ratio));
                    binding.mainUnlike.setScaleX(scale);
                    binding.mainUnlike.setScaleY(scale);
                } else if (direction == OnSwipeListener.SWIPING_RIGHT) {
                    like.setAlpha(Math.abs(ratio));
                    binding.mainlike.setScaleX(scale);
                    binding.mainlike.setScaleY(scale);
                } else {
                    unlike.setAlpha(0f);
                    like.setAlpha(0f);
                    binding.mainUnlike.setScaleX(1);
                    binding.mainUnlike.setScaleY(1);
                    binding.mainlike.setScaleX(1);
                    binding.mainlike.setScaleY(1);
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction) {
                binding.mainUnlike.setScaleX(1);
                binding.mainUnlike.setScaleY(1);
                binding.mainlike.setScaleX(1);
                binding.mainlike.setScaleY(1);
            }

            @Override
            public void onSwipedClear() {

            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        binding.recyclerView.setLayoutManager(new CardLayoutManager(binding.recyclerView, itemTouchHelper));
        binding.recyclerView.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(binding.recyclerView);
        adapter.notifyDataSetChanged();
    }


    private class onClickListener implements MainAdapter.onItemClickListener {

        @Override
        public void onItemClickListener(BindingViewHolder holder, PersonBean personBean) {
            Intent intent = new Intent(MainActivity.this, PersonDetailActivity.class);
            intent.putExtra("person", personBean);
            // ready for transition options
            EasyTransitionOptions options =
                    EasyTransitionOptions.makeTransitionOptions(
                            MainActivity.this,
                            holder.itemView.findViewById(R.id.personImg));

            // start transition
            EasyTransition.startActivity(intent, options);
        }
    }


    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            binding.drawerLayout.closeDrawer(Gravity.LEFT, true);
            return;
        }

        if (binding.drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            binding.drawerLayout.closeDrawer(Gravity.RIGHT, true);
            return;
        }
        super.onBackPressed();
    }

}
