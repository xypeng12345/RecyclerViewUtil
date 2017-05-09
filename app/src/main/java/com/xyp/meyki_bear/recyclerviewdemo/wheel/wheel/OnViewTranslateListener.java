package com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel;

import android.view.View;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/3/16 15:48
 * 修改人：meyki-bear
 * 修改时间：2017/3/16 15:48
 * 修改备注：
 */

public interface OnViewTranslateListener {
    /**
     *
     * @param view 控件
     * @param positionOffset 滑动的百分比
     * @param positionOffsetPixels 滑动的尺寸
     */
    void onViewTranslate(View view, float positionOffset, int positionOffsetPixels);
}
