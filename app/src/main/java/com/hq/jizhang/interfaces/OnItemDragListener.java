package com.hq.jizhang.interfaces;

import androidx.recyclerview.widget.RecyclerView;

/*
 * @创建者      肖天长
 * @创建时间    2023/3/31 10:22
 * @描述
 *
 */
public interface OnItemDragListener {
    void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos);

    void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to);

    void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos);
}
