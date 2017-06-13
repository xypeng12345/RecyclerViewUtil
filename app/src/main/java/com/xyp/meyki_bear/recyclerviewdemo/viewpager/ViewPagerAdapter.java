package com.xyp.meyki_bear.recyclerviewdemo.viewpager;

import android.content.Context;
import android.graphics.Color;

import com.xyp.meyki_bear.recyclerviewdemo.R;
import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyBaseRecyclerAdapter;

import java.util.List;

/**
 * 项目名称：RecyclerViewUtil
 * 类描述：仿ViewPager实现的adapter
 * 创建人：xyp
 * 创建时间：2017/5/18 16:25
 * 修改人：meyki-bear
 * 修改时间：2017/5/18 16:25
 * 修改备注：
 */

public class ViewPagerAdapter extends MyBaseRecyclerAdapter<String>{
    public ViewPagerAdapter(Context context, List<String> data) {
        super(context, R.layout.item_view, data);
    }

    @Override
    public int getType(int position) {
        return 0;
    }

    @Override
    public void convert(InnerBaseViewHolder holder, String s, int position, int itemType) {
        int color= Color.BLUE;
        switch (position){
            case 0:
                color=Color.RED;
                break;
            case 1:
                color=Color.GRAY;
                break;
            case 2:
                color=Color.LTGRAY;
                break;
        }
        holder.itemView.setBackgroundColor(color);
    }
}
