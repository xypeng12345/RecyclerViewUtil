package com.xyp.meyki_bear.recyclerviewdemo.review1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xyp.meyki_bear.recyclerviewdemo.R;
import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyListenerAdapter;

import java.util.List;

/**
 * 项目名称：RecyclerViewUtil
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/5/31 18:21
 * 修改人：meyki-bear
 * 修改时间：2017/5/31 18:21
 * 修改备注：
 */

public class R2Adapter extends MyListenerAdapter<R2Adapter.R2ViewHolder> {
    private Context context;
    private List<String> datas;

    public R2Adapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }



    @Override
    protected int getDataCount() {
        return datas.size();
    }


    @Override
    protected void convertData(R2Adapter.R2ViewHolder holder, int position, List<Object> payloads) {
        TextView textView = (TextView) holder.itemView.findViewById(R.id.tv1);
        textView.setText(datas.get(position));
    }

    @Override
    protected R2Adapter.R2ViewHolder onCreateListenerDataViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.item_content, parent, false);
        R2ViewHolder r2ViewHolder = new R2ViewHolder(contentView);
        return r2ViewHolder;
    }

    public class R2ViewHolder extends MyListenerAdapter.ListenerViewHolder {

        public R2ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
