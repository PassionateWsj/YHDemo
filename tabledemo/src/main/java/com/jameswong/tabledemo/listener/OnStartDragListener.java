package com.jameswong.tabledemo.listener;

import android.support.v7.widget.RecyclerView;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/21 下午5:04
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */
public interface OnStartDragListener {
    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
