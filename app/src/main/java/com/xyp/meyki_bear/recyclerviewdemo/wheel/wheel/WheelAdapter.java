package com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyBaseRecyclerAdapter;

import java.util.List;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/3/15 21:25
 * 修改人：meyki-bear
 * 修改时间：2017/3/15 21:25
 * 修改备注：
 */

public abstract class WheelAdapter<T> extends MyBaseRecyclerAdapter<T>{
    private int rawCount=3; //默认显示三行

    private double rawHeight;

    public WheelAdapter(Context context, @LayoutRes int layoutId, List<T> data) {
        super(context, layoutId, data);
    }


    @Override
    protected void onInitViewHolder(MyBaseRecyclerAdapter.InnerBaseViewHolder holder,ViewGroup parent,  int viewType) {
        //对父布局的尺寸进行测量
        int measuredHeight = parent.getLayoutParams().height;
        ViewGroup.LayoutParams la = holder.itemView.getLayoutParams();
        if (la == null) {
            la = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        rawHeight = measuredHeight * 1.0 / rawCount;
        la.width = ViewGroup.LayoutParams.MATCH_PARENT;
        la.height = (int) rawHeight;
        //有时候会出现控件的高度*rawCount不够父布局高度的问题，这是除法导致的精度丢失
        holder.itemView.setLayoutParams(la);
    }


    public int getRawHeight(){
        return (int) rawHeight;
    }

    public void setRawCount(int rawCount) {
        this.rawCount = rawCount;
    }
}
