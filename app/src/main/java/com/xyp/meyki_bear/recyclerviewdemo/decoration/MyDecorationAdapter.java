package com.xyp.meyki_bear.recyclerviewdemo.decoration;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.widget.TextView;

import com.xyp.meyki_bear.recyclerviewdemo.R;
import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyBaseRecyclerAdapter;

import java.util.List;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：
 * 创建人：meyki-bear
 * 创建时间：2017/2/27 13:56
 * 修改人：meyki-bear
 * 修改时间：2017/2/27 13:56
 * 修改备注：
 */

public class MyDecorationAdapter extends MyBaseRecyclerAdapter<CityBean> {

    public MyDecorationAdapter(Context context, List<CityBean> data) {
        this(context, R.layout.item_string, data);
    }

    public MyDecorationAdapter(Context context, @LayoutRes int layoutId, List<CityBean> data) {
        super(context, layoutId, data);
    }

    @Override
    public int getType(int position) {
        return 0;
    }

    @Override
    public void convert(InnerBaseViewHolder holder, CityBean s, int position, int itemType) {
        TextView tv1 = holder.getView(R.id.tv1);
        tv1.setText(s.getCityName());
    }
}
