package com.xyp.meyki_bear.recyclerviewdemo.wheel.count;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.xyp.meyki_bear.recyclerviewdemo.R;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel.WheelAdapter;

import java.util.List;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/3/20 9:50
 * 修改人：meyki-bear
 * 修改时间：2017/3/20 9:50
 * 修改备注：
 */

public class CountWheelAdapter extends WheelAdapter<String> {
    public CountWheelAdapter(Context context, List<String> data) {
        super(context, R.layout.item_string, data);
    }


    @Override
    public int getType(int position) {
        return 0;
    }

    @Override
    public void convert(InnerBaseViewHolder holder, String s, int position, int itemType) {
            TextView tv=holder.getView(R.id.tv1);
            tv.setText(s);
    }
}
