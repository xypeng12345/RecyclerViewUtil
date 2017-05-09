package com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/3/16 19:09
 * 修改人：meyki-bear
 * 修改时间：2017/3/16 19:09
 * 修改备注：
 */

public abstract class ChooseDecoration extends RecyclerView.ItemDecoration {
    private int lineHeight=10;
    private int rawCount=3;// 要显示几行
    private Paint mPaint;

    public void setRawCount(int rawCount) {
        this.rawCount = rawCount;
    }

    public ChooseDecoration() {
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
        int rawHeight=layoutParams.height/rawCount;
        int top=(rawCount-1)/2*rawHeight-lineHeight/2; //上
        int right=parent.getMeasuredWidth(); //右
        int bottom=(rawCount+1)/2*rawHeight+lineHeight/2; //下
        //左肯定是0
        Rect rect=new Rect(0,top,right,bottom);
        onDrawOver(c,rect,parent,state);
    }

    /**
     * 蒙层绘制，rect是目标区域的大小,可在此范围内适量
     * @param c
     * @param rect
     * @param parent
     * @param state
     */
    protected abstract void onDrawOver(Canvas c, Rect rect, RecyclerView parent, RecyclerView.State state);
}
