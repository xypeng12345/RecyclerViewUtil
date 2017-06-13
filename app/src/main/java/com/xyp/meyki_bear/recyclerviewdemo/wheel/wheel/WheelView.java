package com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xyp.meyki_bear.recyclerviewdemo.R;


/**
 * 项目名称：RecyclerViewDemo
 * 类描述：滚轮选择控件
 * 创建人：xyp
 * 创建时间：2017/3/16 15:45
 * 修改人：meyki-bear
 * 修改时间：2017/3/16 15:45
 * 修改备注：
 */

public abstract class WheelView {
    private static final String TAG = "WheelView";
    /**
     * 用于绘制遮挡区的工具
     */
    private ChooseDecoration decoration;
    private Context context;
    /**
     * 要显示的行数
     */
    private int rawCount = 5;//要显示的行数
    /**
     * 核心控件
     */
    private RecyclerView rv; //目标控件
    /**
     * adapter，主要作用是根据行数控制item的尺寸
     */
    private WheelAdapter adapter; //显示用的adapter
    /**
     * 滚轮选择器自然是linearLayout
     */
    private LinearLayoutManager layoutManager; //布局管理器

    private OnItemChooseListener onItemChooseListener; //当item被滑到目标区域的监听

    public OnItemChooseListener getOnItemChooseListener() {
        return onItemChooseListener;
    }

    public void setOnItemChooseListener(OnItemChooseListener onItemChooseListener) {
        this.onItemChooseListener = onItemChooseListener;
    }

    protected RecyclerView getRecyclerView() {
        return rv;
    }

    private LinearSnapHelper snapHelper;
    /**
     * 控件滑动时的监听
     */
    protected OnViewTranslateListener onViewTranslateListener = new OnViewTranslateListener() {
        @Override
        public void onViewTranslate(View view, float positionOffset, int positionOffsetPixels) {
            TextView tv = (TextView) view.findViewById(R.id.tv1);
            tv.setScaleX(2 - positionOffset);
            tv.setScaleY(2 - positionOffset);
        }
    }; //里面View滑动的监听

    /**
     * 设置行数，设置完成后adapter内部会notify刷新显示
     *
     * @param rawCount
     */
    public void setRawCount(int rawCount) {
        if (rawCount % 2 == 0) {
            throw new IllegalArgumentException("行数不能为偶数");
        }
        this.rawCount = rawCount;
        decoration.setRawCount(rawCount);
        adapter.setRawCount(rawCount);
        adapter.notifyDataSetChanged();
    }

    public WheelView(Context context, @NonNull RecyclerView rv) {
        this.context = context;
        this.rv = rv;
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = (WheelAdapter) rv.getAdapter();
        adapter.setRawCount(rawCount);
        if (decoration != null) {
            rv.addItemDecoration(decoration);
        }
        rv.setLayoutManager(layoutManager);
        rv.addOnScrollListener(listener);
        rv.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                return false;
            }
        });
    }

    public void setDecoration(ChooseDecoration decoration) {
        this.decoration = decoration;
        rv.addItemDecoration(decoration);
    }

    private boolean mScrolled;
    //recyclerView滑动时的监听
    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    View childAt = recyclerView.getChildAt(0);
                    if (childAt == null) {
                        return;
                    }
                    int top = childAt.getTop();
                    top = top - 0 * adapter.getRawHeight();
                    int measuredHeight = childAt.getMeasuredHeight() / 2;
                    if (top == 0) { //停止且第一个item的高度为0，则说明粘性滑动结束，这时候触发被选中监听
                        View childAt1 = recyclerView.getChildAt((rawCount - 1) / 2); //获取目标ITEM
                        //获取目标item在adapter里的position
                        int childAdapterPosition = recyclerView.getChildAdapterPosition(childAt1);
                        //计算方法
                        chooseItem(childAdapterPosition);
                        return;
                    }
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && mScrolled) {
                        //粘性滑动的效果
                        int i = top + measuredHeight;
                        if (i < 0) { //item的一半高度都超出超出了上限,那就滑到下一个item
                            smoothScrollByPosition(1);
                        } else { //如果滑出高度少于一半则粘性滑回自身
                            smoothScrollByPosition(0);
                        }
                        mScrolled = false;
                    }
                    break;
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE && mScrolled) {
                View childAt = recyclerView.getChildAt(0);
                if (childAt == null) {
                    return;
                }
                int top = childAt.getTop();
                top = top - 0 * adapter.getRawHeight();
                int measuredHeight = childAt.getMeasuredHeight() / 2;
                //粘性滑动的效果
                int i = top + measuredHeight;
                if (i < 0) { //item的一半高度都超出超出了上限,那就滑到下一个item
                    smoothScrollByPosition(1);
                } else { //如果滑出高度少于一半则粘性滑回自身
                    smoothScrollByPosition(0);
                }
                mScrolled = false;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            View childAt = recyclerView.getChildAt(0);
            int top = childAt.getTop();
            int measuredHeight = childAt.getMeasuredHeight();
            float v = (top * 1.0f) / measuredHeight;
            //获取目标控件(显示3行，则第二行是目标行，显示5行则第三行是目标行)
            float abs = Math.abs(v);
            WheelView.this.notify(recyclerView, (int) v, abs);
            if (dx != 0 || dy != 0) {
                mScrolled = true;
            }
        }
    };

    protected void chooseItem(int position) {
        if (onItemChooseListener != null) {
            onItemChooseListener.onItemChooseListener(position);
        }
    }

    /**
     * 控件变化的监听
     *
     * @param recyclerView
     * @param top          控件离开目标位置的距离
     * @param abs          控件离开目标位置的距离所占其高度的百分比
     */
    protected void notify(RecyclerView recyclerView, int top, float abs) {
        if (onViewTranslateListener != null) {
            View childAt1 = recyclerView.getChildAt(0 + ((rawCount - 1) / 2));
            View childAt2 = recyclerView.getChildAt(0 + ((rawCount + 1) / 2));
            //滑动的时候目标控件以及目标控件下面的一个控件的滑动比例
            //离开的控件
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                if (i == (rawCount - 1) / 2) {
                    onViewTranslateListener.onViewTranslate(childAt1, abs, top);
                } else if (i == (rawCount + 1) / 2) {
                    onViewTranslateListener.onViewTranslate(childAt2, 1 - abs, -top);
                } else {
                    onViewTranslateListener.onViewTranslate(recyclerView.getChildAt(i), 1, 0);
                }
            }
        }
    }

    public void setOnViewTranslateListener(OnViewTranslateListener onViewTranslateListener) {
        this.onViewTranslateListener = onViewTranslateListener;
    }

    /**
     * 将一个控件滑动到第一个item的位置
     *
     * @param position
     */
    private void smoothScrollByPosition(final int position) {
        View childAt = layoutManager.getChildAt(position);
        int top = childAt.getTop();
        if (top != 0) {
            rv.smoothScrollBy(0, top);
        }
    }

    /**
     * 初始化空控件
     *
     * @return
     */
    public int getEmptyViewHeight() {
        int height = rv.getLayoutParams().height;
        return (int) (height * 1.0 / rawCount);
    }

    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    public WheelAdapter getAdapter() {
        return adapter;
    }

    public Context getContext() {
        return context;
    }

    public int getRawCount() {
        return rawCount;
    }

    public interface OnItemChooseListener {
        void onItemChooseListener(int position);
    }

    /**
     * 滑动到目标item
     *
     * @param position
     */
    public void scrollToPosition(int position) {
        Log.d(TAG, "你必须自己实现这个方法");
    }
}
