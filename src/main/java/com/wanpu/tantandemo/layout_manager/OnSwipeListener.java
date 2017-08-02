package com.wanpu.tantandemo.layout_manager;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/6/5.
 */

public interface OnSwipeListener<T> {
    public static final int SWIPING_LEFT = -1;
    public static final int SWIPING_RIGHT = 1;
    public static final int SWIPING_NONE = 0;
    /**
     * 卡片还在滑动时回调
     *
     * @param viewHolder 该滑动卡片的viewHolder
     * @param ratio      滑动进度的比例
     * @param direction  卡片滑动的方向，SWIPING_LEFT 为向左滑，SWIPING_RIGHT 为向右滑，
     *                   SWIPING_NONE 为不偏左也不偏右
     */
    void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction);

    /**
     * 卡片完全滑出时回调
     *
     * @param viewHolder 该滑出卡片的viewHolder
     * @param t          该滑出卡片的数据
     * @param direction  卡片滑出的方向，CardConfig.SWIPED_LEFT 为左边滑出；CardConfig.SWIPED_RIGHT 为右边滑出
     */
    void onSwiped(RecyclerView.ViewHolder viewHolder, T t, int direction);

    /**
     * 所有的卡片全部滑出时回调
     */
    void onSwipedClear();

}
