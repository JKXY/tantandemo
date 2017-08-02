package com.wanpu.tantandemo.layout_manager;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 */

public class CardItemTouchHelperCallback<T> extends ItemTouchHelper.Callback{
    private final RecyclerView.Adapter adapter;
    private List<T> dataList;
    private OnSwipeListener<T> mListener;

    public CardItemTouchHelperCallback(RecyclerView.Adapter adapter, List<T> dataList) {
        this.adapter = checkIsNull(adapter);
        this.dataList = checkIsNull(dataList);
    }

    public CardItemTouchHelperCallback(RecyclerView.Adapter adapter, List<T> dataList, OnSwipeListener<T> mListener) {
        this.adapter = checkIsNull(adapter);
        this.dataList = checkIsNull(dataList);
        this.mListener = mListener;
    }

    public void setmListener(OnSwipeListener<T> mListener) {
        this.mListener = mListener;
    }

    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof CardLayoutManager){
            swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT|ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        }
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        viewHolder.itemView.setOnTouchListener(null);
        int layoutPosition  = viewHolder.getLayoutPosition();
        T remove = dataList.remove(layoutPosition);
        adapter.notifyDataSetChanged();
        if (mListener!=null){
            mListener.onSwiped(viewHolder,remove,direction == ItemTouchHelper.LEFT?OnSwipeListener.SWIPING_LEFT:OnSwipeListener.SWIPING_RIGHT);
        }
        // 当没有数据时回调 OnSwipeListener 监听器
        if (adapter.getItemCount() == 0) {
            if (mListener != null) {
                mListener.onSwipedClear();
            }
        }
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // 得到滑动的阀值
            float ratio = dX / getThreshold(recyclerView, viewHolder);
            // ratio 最大为 1 或 -1
            if (ratio > 1) {
                ratio = 1;
            } else if (ratio < -1) {
                ratio = -1;
            }
            // 默认最大的旋转角度为 15 度
            itemView.setRotation(ratio * CardLayoutManager.DEFAULT_ROTATE_DEGREE);
            int childCount = recyclerView.getChildCount();
            // 当数据源个数大于最大显示数时
            if (childCount > CardLayoutManager.DEFAULT_SHOW_ITEM) {
                for (int position = 1; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    // 和之前 onLayoutChildren 是一个意思，不过是做相反的动画
                    view.setScaleX(1 - index * CardLayoutManager.DEFAULT_SCALE + Math.abs(ratio) * CardLayoutManager.DEFAULT_SCALE);
                    view.setScaleY(1 - index * CardLayoutManager.DEFAULT_SCALE + Math.abs(ratio) * CardLayoutManager.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / CardLayoutManager.DEFAULT_TRANSLATE_Y);
                }
            } else {
                // 当数据源个数小于或等于最大显示数时
                for (int position = 0; position < childCount - 1; position++) {
                    int index = childCount - position - 1;
                    View view = recyclerView.getChildAt(position);
                    view.setScaleX(1 - index * CardLayoutManager.DEFAULT_SCALE + Math.abs(ratio) * CardLayoutManager.DEFAULT_SCALE);
                    view.setScaleY(1 - index * CardLayoutManager.DEFAULT_SCALE + Math.abs(ratio) * CardLayoutManager.DEFAULT_SCALE);
                    view.setTranslationY((index - Math.abs(ratio)) * itemView.getMeasuredHeight() / CardLayoutManager.DEFAULT_TRANSLATE_Y);
                }
            }
            // 回调监听器
            if (mListener != null) {
                if (ratio != 0) {
                    mListener.onSwiping(viewHolder, ratio, ratio < 0 ? OnSwipeListener.SWIPING_LEFT : OnSwipeListener.SWIPING_RIGHT);
                } else {
                    mListener.onSwiping(viewHolder, ratio, OnSwipeListener.SWIPING_NONE);
                }
            }
        }
    }

    private float getThreshold(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return recyclerView.getWidth() * getSwipeThreshold(viewHolder);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setRotation(0f);
    }
}
