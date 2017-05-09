package com.xyp.meyki_bear.recyclerviewdemo.wheel.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;

import com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel.ChooseDecoration;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/3/20 9:59
 * 修改人：meyki-bear
 * 修改时间：2017/3/20 9:59
 * 修改备注：
 */

public class RectChooseDecoration extends ChooseDecoration {
    private Paint mPaint;
    public RectChooseDecoration() {
        super();
        mPaint=new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDrawOver(Canvas c, Rect rect, RecyclerView parent, RecyclerView.State state) {
        c.drawRect(rect,mPaint);
    }
}
