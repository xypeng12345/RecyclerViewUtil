package com.xyp.meyki_bear.recyclerviewdemo.review2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xyp.meyki_bear.recyclerviewdemo.R;
import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyGroupAdapter;
import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyListenerAdapter;

import java.util.List;

/**
 * 项目名称：RecyclerViewUtil
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/6/1 16:19
 * 修改人：meyki-bear
 * 修改时间：2017/6/1 16:19
 * 修改备注：
 */

public class Review2Adapter extends MyGroupAdapter<Review2Adapter.Review2ViewHolder> {
    private Context context;
    private List<List<String>> lists;



    public Review2Adapter(Context context, List<List<String>> lists) {
        this.lists = lists;
        this.context = context;
    }

    @Override
    protected int getChildViewHolderSpanSize(int groupPosition, int childPosition, int spanCount) {
        if(groupPosition==3){
            return spanCount;
        }
        if(groupPosition==2){
            return spanCount/2;
        }
        return super.getChildViewHolderSpanSize(groupPosition, childPosition, spanCount);
    }


    @Override
    protected int getGroupItemType(int groupPosition) {
        return 1;
    }

    @Override
    protected int getGroupChildItemType(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    protected Review2ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1: //标题
                View titleView = LayoutInflater.from(context).inflate(R.layout.item_string_title, parent, false);
                Review2ViewHolder r2ViewHolder = new Review2ViewHolder(titleView);
                return r2ViewHolder;
            case 0: //内容
                View contentView = LayoutInflater.from(context).inflate(R.layout.item_content, parent, false);
                r2ViewHolder = new Review2ViewHolder(contentView);
                return r2ViewHolder;
        }
        return null;
    }

    @Override
    protected void converGroupData(Review2ViewHolder holder, int groupPosition, List<Object> payloads) {
        TextView tvTitle = (TextView) holder.itemView.findViewById(R.id.tv1);
        tvTitle.setText("这是标题:" + groupPosition);
    }

    @Override
    protected void converChildData(Review2ViewHolder holder, int groupPosition, int childPosition, List<Object> payloads) {
        TextView tvTitle = (TextView) holder.itemView.findViewById(R.id.tv1);
        String s = lists.get(groupPosition).get(childPosition);
        tvTitle.setText("这是内容:" + s);
    }


    @Override
    protected int getGroupCount() {
        return lists.size();
    }

    @Override
    protected int getChildrenCount(int groupPosition) {
        return lists.get(groupPosition).size();
    }

    public class Review2ViewHolder extends MyListenerAdapter.ListenerViewHolder {

        public Review2ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
