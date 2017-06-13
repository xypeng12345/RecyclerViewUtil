package com.xyp.meyki_bear.recyclerviewdemo.adapter;

import android.view.View;

/**
 * 项目名称：RecyclerViewUtil
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/6/1 10:12
 * 修改人：meyki-bear
 * 修改时间：2017/6/1 10:12
 * 修改备注：
 */

public abstract class MyGroupAdapter extends MyListenerAdapter {


    private class GroupViewHolder extends MyListenerAdapter.ListenerViewHolder {

        public GroupViewHolder(View itemView) {
            super(itemView);
        }
    }
}
