package com.xyp.meyki_bear.recyclerviewdemo.groupDemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Pair;
import android.view.ViewGroup;

import com.xyp.meyki_bear.recyclerviewdemo.adapter.MyBaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：RecyclerViewUtil
 * 类描述：电话溥效果的adapter
 * 创建人：xyp
 * 创建时间：2017/5/27 17:26
 * 修改人：meyki-bear
 * 修改时间：2017/5/27 17:26
 * 修改备注：
 */

public abstract class GroupRecyclerViewAdapter<T> extends MyBaseRecyclerAdapter<T> {
    private List<Pair<Integer, Integer>> mPairIndexList;
    private OnGroupChildClickListener onGroupChildClickListener;
    private OnGroupClickListener onGroupClickListener;

    public GroupRecyclerViewAdapter(Context context, @LayoutRes int layoutId, List data) {
        super(context, layoutId, data);
        setOnItemClickListener(new onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Pair<Integer, Integer> integerPair = mPairIndexList.get(position);
                if (isTitle(position)) {
                    if (onGroupClickListener != null) {
                        onGroupClickListener.onGroupClickListener(integerPair.first);
                    }
                } else {
                    if (onGroupChildClickListener != null) {
                        onGroupChildClickListener.onGroupChildClickListener(integerPair.first, integerPair.second);
                    }
                }
            }
        });
    }

    public final void notifyPositionChanged() {
        if (null == mPairIndexList) {
            mPairIndexList = new ArrayList<>();
        } else {
            mPairIndexList.clear();
        }
        for (int i = 0; i < getGroupCount(); i++) {
            mPairIndexList.add(new Pair<>(i, -1));
            for (int j = 0; j < getChildrenCount(i); j++) {
                mPairIndexList.add(new Pair<>(i, j));
            }
        }
    }

    @Override
    public int getType(int position) {
        Pair<Integer, Integer> integerPair = mPairIndexList.get(position);
        if (isTitle(position)) {
            return -(getGroupItemViewType(integerPair.first) == 0 ? -1 : getGroupItemViewType(integerPair.first));
        } else {
            return Math.abs(getChildItemViewType(integerPair.first, integerPair.second));
        }
    }

    public int getGroupItemViewType(int groupPosition) {
        return 1;
    }

    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 1;
    }


    private boolean isTitle(int position) {
        Pair<Integer, Integer> integerPair = mPairIndexList.get(position);
        return integerPair.second == -1;
    }


    @Override
    protected void onInitViewHolder(InnerBaseViewHolder holder, ViewGroup parent, int viewType) {
        super.onInitViewHolder(holder, parent, viewType);
    }

    @Override
    public void convert(InnerBaseViewHolder holder, T t, int position, int itemType) {
        Pair<Integer, Integer> integerPair = mPairIndexList.get(position);
        if (integerPair.second == -1) {
            onBindGroupViewHolder(holder, integerPair.first);
        } else {
            onBindChildViewHolder(holder, integerPair.first, integerPair.second);
        }
    }

    public abstract int getGroupCount();

    public abstract int getChildrenCount(int groupPosition);

    public abstract void onBindGroupViewHolder(MyBaseRecyclerAdapter.InnerBaseViewHolder holder, int groupPosition);

    public abstract void onBindChildViewHolder(MyBaseRecyclerAdapter.InnerBaseViewHolder holder, int groupPosition, int childPosition);

    /**
     * 父布局被点击的监听
     */
    public interface OnGroupClickListener {
        void onGroupClickListener(int groupPosition);
    }

    public interface OnGroupChildClickListener {
        void onGroupChildClickListener(int groupPosition, int childPosition);
    }
}
