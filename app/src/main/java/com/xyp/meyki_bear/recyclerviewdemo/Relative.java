package com.xyp.meyki_bear.recyclerviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/3/22 9:52
 * 修改人：meyki-bear
 * 修改时间：2017/3/22 9:52
 * 修改备注：
 */

public class Relative extends RelativeLayout {
    public Relative(Context context) {
        super(context);
    }

    public Relative(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Relative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("yupeng","---------w//"+w+"----------h//"+h+"----------oldw//"+oldw+"---------oldh//"+oldh);
    }
}
