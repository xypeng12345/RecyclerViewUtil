package com.xyp.meyki_bear.recyclerviewdemo.wheel.count;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel.WheelView;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：不能无限滑动选的滚轮选择器
 * 创建人：xyp
 * 创建时间：2017/3/20 9:15
 * 修改人：meyki-bear
 * 修改时间：2017/3/20 9:15
 * 修改备注：
 */

public class CountWheelView extends WheelView {
    public CountWheelView(Context context, @NonNull RecyclerView rv) {
        super(context, rv);
    }

    @Override
    public void setRawCount(int rawCount) {
        super.setRawCount(rawCount);
        getAdapter().clearHeaderView();
        getAdapter().clearFooterView();
        //添加HeaderView，FooterView补齐空白位置
        for (int i = 0; i < (rawCount - 1) / 2; i++) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.height = getEmptyViewHeight();
            EmptyView view = new EmptyView(getContext());
            view.setLayoutParams(layoutParams);
            EmptyView view1 = new EmptyView(getContext());
            view1.setLayoutParams(layoutParams);
            getAdapter().addHeaderView(view);
            getAdapter().addFooterView(view1);
        }
    }

    @Override
    protected void notify(RecyclerView recyclerView, int top, float abs) {
        if (onViewTranslateListener != null) {
            View childAt1 = recyclerView.getChildAt(0 + ((getRawCount() - 1) / 2));
            View childAt2 = recyclerView.getChildAt(0 + ((getRawCount() + 1) / 2));
            //滑动的时候目标控件以及目标控件下面的一个控件的滑动比例
            //离开的控件
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                View childAt3 = recyclerView.getChildAt(i);
                if (childAt3 instanceof EmptyView) {
                    continue;
                }
                if (i == (getRawCount() - 1) / 2) {
                    Log.d("yupeng","i"+i+"===abs"+abs);
                    onViewTranslateListener.onViewTranslate(childAt1, abs, top);
                } else if (i == (getRawCount() + 1) / 2) {
                    Log.d("yupeng","i"+i+"===abs"+abs);
                    onViewTranslateListener.onViewTranslate(childAt2, 1 - abs, -top);
                } else {
                    onViewTranslateListener.onViewTranslate(recyclerView.getChildAt(i), 1, 0);
                }
            }
        }
    }

    public class EmptyView extends View {

        public EmptyView(Context context) {
            super(context);
        }
    }

    @Override
    public void scrollToPosition(int position) {
        //这是layout的滑动，会设计头尾布局，所以position要去除头尾布局的影响
        position=position+getAdapter().getmHeadSize();
        getRecyclerView().smoothScrollToPosition(position);
    }
}
