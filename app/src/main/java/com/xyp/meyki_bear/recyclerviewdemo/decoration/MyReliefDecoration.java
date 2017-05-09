package com.xyp.meyki_bear.recyclerviewdemo.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：
 * 创建人：meyki-bear
 * 创建时间：2017/2/27 14:04
 * 修改人：meyki-bear
 * 修改时间：2017/2/27 14:04
 * 修改备注：
 */

public class MyReliefDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private List<CityBean> data;


    private int mTitleHeight;//title的高
    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF000000");
    private static int mTitleFontSize;//title字体大小
    private Rect mBounds;//用于存放测量文字Rect

    public MyReliefDecoration(Context context, List<CityBean> data) {
        this.data = data;
        mPaint = new Paint();
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        mPaint.setTextSize(mTitleFontSize);
        mPaint.setAntiAlias(true);
    }

    private int myLineHeight = 100; //头布局的样式

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        //只绘制一次，之后会随着滑动不断的更新绘制，与之前想象的不一样，不是一个item一个item进行绘制的，而是一次将屏幕上的item绘制完
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
            //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
            if (position > -1) {
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, child, params, position);
                } else {//其他的通过判断
                    if (null != data.get(position).getPinyin() && !data.get(position).getPinyin().equals(data.get(position - 1).getPinyin())) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c, left, right, child, params, position);
                    } else {
                        //none
                    }
                }
            }
        }
    }

    /**
     * 绘制Title区域背景和文字的方法
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {//最先调用，绘制在最下层
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);

        mPaint.getTextBounds(data.get(position).getPinyin(), 0, data.get(position).getPinyin().length(), mBounds);
        c.drawText(data.get(position).getPinyin(), child.getPaddingLeft(), child.getTop() - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2), mPaint);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        String tag = data.get(pos).getPinyin();
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;//出现一个奇怪的bug，有时候child为空，所以将 child = parent.getChildAt(i)。-》 parent.findViewHolderForLayoutPosition(pos).itemView
        boolean isMove=false;//用于绘制过量移动的动画
        if ((pos + 1) < data.size()){ //放置数组下标越界
            if(!data.get(pos).getPinyin().equals(data.get(pos+1).getPinyin())){//当前第一个item的拼音不等于下一个item的拼音了，说明要移动了
                if (child.getHeight() + child.getTop()<mTitleHeight){ //item的可见尺寸不够显示完整的炫富框了
                    isMove=true;
                    c.save();
                    c.translate(0,child.getHeight()+child.getTop()-mTitleHeight);
                }
            }
        }

        mPaint.setColor(Color.YELLOW);
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        mPaint.getTextBounds(tag, 0, tag.length(), mBounds);

        c.drawText(tag,child.getPaddingLeft(),
                parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2),
                mPaint);
        if(isMove){
            c.restore();
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
        int position = params.getViewLayoutPosition();
        if (position <= -1) {//记得Rv的item position在重置时可能为-1(NO_POSITION，adapter在notify后的16ms内).保险点判断一下吧,
            return;
        }
        if (position == 0) { //第一个，一定绘制头布局
            outRect.set(0, myLineHeight, 0, 0);
        } else {
            if (null != data.get(position) && data.get(position).getPinyin().equals(data.get(position - 1).getPinyin())) {//如果当前的数据与前一个数据相同则不绘制title
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, myLineHeight, 0, 0);
            }
        }
    }
}
