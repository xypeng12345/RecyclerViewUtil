package com.xyp.meyki_bear.recyclerviewdemo.wheel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.xyp.meyki_bear.recyclerviewdemo.wheel.count.CountWheelView;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.loop.LoopWheelView;
import com.xyp.meyki_bear.recyclerviewdemo.wheel.wheel.WheelView;


/**
 * 项目名称：RecyclerViewDemo
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/3/21 9:39
 * 修改人：meyki-bear
 * 修改时间：2017/3/21 9:39
 * 修改备注：
 */

public class WheelFactory {
    /**
     * 普通的滚轮选择控件
     */
    public static final int TYPE_COUNT=10;
    /**
     * 无限滚轮选择的控件
     */
    public static final int TYPE_LOOP=11;

    /**
     * 创建一个滚轮控件
     * @param rv 显示用的控件
     * @param type 滚轮显示的类型
     * @param context 上下文对象
     * @return
     */
    public static WheelView getWheelView(RecyclerView rv, int type, Context context){
        WheelView wheelView=null;
        switch(type){
            case TYPE_COUNT:
                wheelView= new CountWheelView(context,rv);
                break;
            case TYPE_LOOP:
                wheelView= new LoopWheelView(context,rv);
                break;
            default:
                break;
        }
        return wheelView;
    }
}
