package com.xyp.meyki_bear.recyclerviewdemo.wheel.loop;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xyp.meyki_bear.recyclerviewdemo.R;
import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyBaseRecyclerAdapter;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel.WheelAdapter;

import java.util.List;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/3/20 10:09
 * 修改人：meyki-bear
 * 修改时间：2017/3/20 10:09
 * 修改备注：
 */

public class LoopAdapter extends WheelAdapter<String> {
    public LoopAdapter(Context context, List<String> data) {
        super(context, R.layout.item_string, data);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    @Override
    protected int getRealPosition(int position) {
        position=position%(data.size()+getmHeadSize()+getmFootSize());
        return super.getRealPosition(position);
    }

    @Override
    protected void onInitViewHolder(MyBaseRecyclerAdapter.InnerBaseViewHolder holder, ViewGroup parent, int viewType) {
        super.onInitViewHolder(holder,parent,viewType);
    }

    @Override
    public int getType(int position) {
        return 0;
    }

    @Override
    public void convert(InnerBaseViewHolder holder, String s, int position, int itemType) {
        TextView tv1=holder.getView(R.id.tv1);
        tv1.setText(s);
    }
}
