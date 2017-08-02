package com.wanpu.tantandemo.layout_manager;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/6/5.
 */

public class CardLayoutManager extends LayoutManager {
    public static final int DEFAULT_SHOW_ITEM = 3;
    public static final float DEFAULT_SCALE = 0.1f;
    public static final int DEFAULT_TRANSLATE_Y = 15;
    public static final int DEFAULT_ROTATE_DEGREE = 12;

    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;

    public CardLayoutManager(RecyclerView mRecyclerView, ItemTouchHelper mItemTouchHelper) {
        this.mRecyclerView = mRecyclerView;
        this.mItemTouchHelper = mItemTouchHelper;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //先移除所有view
        removeAllViews();
        //在布局之前，将所有的子 View 先 Detach 掉，放入到 Scrap 缓存中
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        if (itemCount > DEFAULT_SHOW_ITEM) {
            // 把数据源倒着循环，这样，第0个数据就在屏幕最上面了
            for (int position = DEFAULT_SHOW_ITEM; position >= 0; position--) {
                View view = recycler.getViewForPosition(position);
                addView(view);
                // 测量 Item View
                measureChildWithMargins(view, 0, 0);
                // getDecoratedMeasuredWidth(view) 可以得到 Item View 的宽度
                // 所以 widthSpace 就是除了 Item View 剩余的值
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int hightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                // 将 Item View 放入 RecyclerView 中布局
                // 在这里默认布局是放在 RecyclerView 中心
//                layoutDecoratedWithMargins(view, widthSpace / 2, hightSpace / 2, widthSpace / 2 + getDecoratedMeasuredWidth(view), hightSpace / 2 + getDecoratedMeasuredHeight(view));
                layoutDecoratedWithMargins(view, 0, 0, getWidth(), getDecoratedMeasuredHeight(view) + 40);
                // 其实屏幕上有四张卡片，但是我们把第三张和第四张卡片重叠在一起，这样看上去就只有三张
                // 第四张卡片主要是为了保持动画的连贯性
                if (position == DEFAULT_SHOW_ITEM) {
                    // 按照一定的规则缩放，并且偏移Y轴。
                    view.setScaleX(1 - (position - 1) * DEFAULT_SCALE);
                    view.setScaleY(1 - (position - 1) * DEFAULT_SCALE);
                    view.setTranslationY((position - 1) * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                } else if (position > 0) {
                    view.setScaleX(1 - position * DEFAULT_SCALE);
                    view.setScaleY(1 - position * DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                } else {
                    // 设置 mTouchListener 的意义就在于我们想让处于顶层的卡片是可以随意滑动的
                    // 而第二层、第三层等等的卡片是禁止滑动的
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        } else {
            // 当数据源个数小于或等于最大显示数时，和上面的代码差不多
            for (int position = itemCount - 1; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);

//                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
//                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
//                        heightSpace / 2 + getDecoratedMeasuredHeight(view));
                layoutDecoratedWithMargins(view, 0, 0, getWidth(), getDecoratedMeasuredHeight(view) + 40);

                if (position > 0) {
                    view.setScaleX(1 - position * DEFAULT_SCALE);
                    view.setScaleY(1 - position * DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / DEFAULT_TRANSLATE_Y);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        }

    }


    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mItemTouchHelper.startSwipe(childViewHolder);
            }
            return false;
        }
    };
}
