package com.xyp.meyki_bear.recyclerviewdemo.util;

import android.graphics.Color;
import android.support.annotation.ColorInt;

/**
 * 项目名称：RecyclerViewDemo
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/3/16 15:05
 * 修改人：meyki-bear
 * 修改时间：2017/3/16 15:05
 * 修改备注：
 */

public class ColorUtil {
    /**
     * 计算从startColor过度到endColor过程中百分比为franch时的颜色值
     * @param startColor 起始颜色 int类型
     * @param endColor 结束颜色 int类型
     * @param franch franch 百分比0.5
     * @return 返回int格式的color
     */
    @ColorInt
    public static int caculateColor(int startColor, int endColor, float franch){
        String strStartColor = "#" + Integer.toHexString(startColor);
        String strEndColor = "#" + Integer.toHexString(endColor);
        return Color.parseColor(caculateColor(strStartColor, strEndColor, franch));
    }

    /**
     * 计算从startColor过度到endColor过程中百分比为franch时的颜色值
     * @param startColor 起始颜色 （格式#FFFFFFFF）
     * @param endColor 结束颜色 （格式#FFFFFFFF）
     * @param franch 百分比0.5
     * @return 返回String格式的color（格式#FFFFFFFF）
     */
    public static String caculateColor(String startColor, String endColor, float franch){

        int startAlpha = Integer.parseInt(startColor.substring(1, 3), 16);
        int startRed = Integer.parseInt(startColor.substring(3, 5), 16);
        int startGreen = Integer.parseInt(startColor.substring(5, 7), 16);
        int startBlue = Integer.parseInt(startColor.substring(7), 16);

        int endAlpha = Integer.parseInt(endColor.substring(1, 3), 16);
        int endRed = Integer.parseInt(endColor.substring(3, 5), 16);
        int endGreen = Integer.parseInt(endColor.substring(5, 7), 16);
        int endBlue = Integer.parseInt(endColor.substring(7), 16);

        int currentAlpha = (int) ((endAlpha - startAlpha) * franch + startAlpha);
        int currentRed = (int) ((endRed - startRed) * franch + startRed);
        int currentGreen = (int) ((endGreen - startGreen) * franch + startGreen);
        int currentBlue = (int) ((endBlue - startBlue) * franch + startBlue);

        return "#" + getHexString(currentAlpha) + getHexString(currentRed)
                + getHexString(currentGreen) + getHexString(currentBlue);

    }
    /**
     * 将10进制颜色值转换成16进制。
     */
    public static String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }
    
    public static int caucleColor(@ColorInt int startColor,@ColorInt int endColor,float precent){
        int startAlpha=Color.alpha(startColor);
        int startRed=Color.red(startColor);
        int startGreen=Color.green(startColor);
        int startBlue=Color.blue(startColor);
        
        int endAlpha=Color.alpha(endColor);
        int endRed=Color.red(endColor);
        int endGreen=Color.green(endColor);
        int endBlue=Color.blue(endColor);

        int alpha= (int) ((endAlpha-startAlpha)*precent+startAlpha);
        int red= (int) ((endRed-startRed)*precent+startRed);
        int green= (int) ((endGreen-startGreen)*precent+startGreen);
        int blue= (int) ((endBlue-startBlue)*precent+startBlue);
        return Color.argb(alpha,red,green,blue);
    }
}
