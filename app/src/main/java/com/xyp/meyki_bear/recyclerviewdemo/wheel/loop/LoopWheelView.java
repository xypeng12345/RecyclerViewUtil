package com.xyp.meyki_bear.recyclerviewdemo.wheel.loop;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel.WheelView;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：可以无限选择的滚轮选择器
 * 创建人：RBQ
 * 创建时间：2017/3/20 9:14
 * 修改人：meyki-bear
 * 修改时间：2017/3/20 9:14
 * 修改备注：
 */

public class LoopWheelView extends WheelView {

    public LoopWheelView(Context context, @NonNull final RecyclerView rv) {
        super(context, rv);
        rv.scrollToPosition(Integer.MAX_VALUE/2);
        rv.post(new Runnable() {
            @Override
            public void run() {
                scrollToPosition(0);
            }
        });

    }

    @Override
    protected void notify(RecyclerView recyclerView, int top, float abs) {
        super.notify(recyclerView, top, abs);
    }

    @Override
    protected void chooseItem(int position) {
        position=position%getAdapter().getData().size();
        super.chooseItem(position);
    }

    /**
     * 想要滑动到的数据的position
     * @param position
     */
    @Override
    public void scrollToPosition(int position) {
        View childAt = getRecyclerView().getChildAt(0);
        int childAdapterPosition = getRecyclerView().getChildAdapterPosition(childAt); //获取第一个item的adapterPosition
        int truePosition = childAdapterPosition + (getRawCount() - 1) / 2; //算出目标区域的itemPosition
        truePosition=truePosition%getAdapter().getData().size(); //计算出目标item的真实Position
        int i1 = position - truePosition; //算出两者之差
        //滑动
        getLayoutManager().scrollToPositionWithOffset(childAdapterPosition+i1,0);
    }
}
