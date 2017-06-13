package com.xyp.meyki_bear.recyclerviewdemo.review1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xyp.meyki_bear.recyclerviewdemo.R;
import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyHeaderFooterAdapter;

import java.util.List;

/**
 * 项目名称：RecyclerViewUtil
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/5/31 18:01
 * 修改人：meyki-bear
 * 修改时间：2017/5/31 18:01
 * 修改备注：
 */

public class R1Adapter extends MyHeaderFooterAdapter<R1Adapter.R1ViewHolder> {
    private Context context;
    private List<String> datas;

    public R1Adapter(Context context,List<String> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    protected int getDataCount() {
        return datas.size();
    }

    @Override
    protected int getType(int position) {
        return 0;
    }

    @Override
    protected void convert(R1ViewHolder holder, int position, List<Object> payloads) {
        TextView textView = (TextView) holder.itemView.findViewById(R.id.tv1);
        textView.setText(datas.get(position));
    }

    @Override
    protected R1ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.item_content, parent, false);
        R1ViewHolder r1ViewHolder = new R1ViewHolder(contentView);
        return r1ViewHolder;
    }

    public class R1ViewHolder extends MyHeaderFooterAdapter.HeaderAndFooterViewHolder {

        public R1ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
