package com.xyp.meyki_bear.recyclerviewdemo.layout_manager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 项目名称：HuanHangTest
 * 类描述：流式布局的layoutManager
 * 创建人：meyki-bear
 * 创建时间：2017/pic1/4 21:45
 * 修改人：meyki-bear
 * 修改时间：2017/pic1/4 21:45
 * 修改备注：
 */

public class HuanHangLayoutManager extends RecyclerView.LayoutManager {
    private SparseArray<Rect> mItemRects=new SparseArray<>() ;//用于记录mItem的rect
    private final static String TAG="yupeng/HuanHang";
    private int mVerticalOffset;//竖直偏移量 每次换行时，要根据这个offset判断
    private int mFirstVisiPos;//屏幕可见的第一个View的Position
    private int mLastVisiPos;//屏幕可见的最后一个View的Position
    //注释一个区别，
    //getItemCount： 就是adapter的getItemCount
    //getChildCount： 获取显示在recyclerView 上的item数量，不包括被销毁的与临时放置的(就是addView一个算一个)
    //第一次绘制的时候调用
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //getItemCount是获取全部的数据内容，如果没数据自然要清除布局上的所有内容
        int itemCount = getItemCount();
        if(itemCount ==0){
            detachAndScrapAttachedViews(recycler);
        }
        //getChildCount是获取要在recyclerView中显示的view如果没有自然不需要绘制什么了
        int childCount = getChildCount();
        if(childCount ==0&&state.isPreLayout()){ //state.isPreLayout是支持动画的
            return;
        }
        //onLayoutChildren方法在RecyclerView 初始化时 会执行两遍，所以每次执行前需要清楚上次绘制的内容
        detachAndScrapAttachedViews(recycler);
        //初始化
        mVerticalOffset = 0;
        mFirstVisiPos = 0;
        mLastVisiPos = getItemCount();

        //初始化时调用 填充childView
        fill(recycler, state);
    }

    /**
     * 自定义layoutManager一生的敌人
     * 在考虑滑动位移的情况下：
     * pic1 回收所有屏幕不可见的子View
     * 2 layout所有可见的子View
     * @param recycler
     * @param state
     */
    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int topOffset=getPaddingTop(); //开始绘制的上偏移量
        int minPos=mFirstVisiPos;//一开始不知道要layout多少个item，所以这里一次性layout全部
        int lastPos=getItemCount();
        sequenceLayoutChild(recycler, topOffset, minPos, lastPos,0);
    }

    /**
     * 顺序layout子View，这个方法会从position的View开始绘制直到屏幕放不下为止，
     * @param recycler
     * @param topOffset    绘制的起始上间距 请用getChildCount-1作为
     * @param minPos   开始绘制的position
     * @param lastPos  结束绘制的position
     */
    private void sequenceLayoutChild(RecyclerView.Recycler recycler, int topOffset, int minPos, int lastPos, int dy) {
        int leftOffset=getPaddingLeft(); //开始绘制的左偏移量
        int lineHeight=0; //每一行的最大高度
        if (getChildCount() > 0) {
                View lastView = getChildAt(getChildCount() - 1);
                minPos = getPosition(lastView) + 1;//从最后一个View+1开始吧
                topOffset = getDecoratedTop(lastView);
                leftOffset = getDecoratedRight(lastView);
                lineHeight = Math.max(lineHeight, getDecoratedMeasurementVertical(lastView));
            }
        for (int i=minPos;i<lastPos;i++){
            //内部会从一系列缓存中取出ViewHolder，最后返回的是viewHolder.itemView
            View child=recycler.getViewForPosition(i);
            //把这个viewAdd到recyclerView里面
            addView(child);
            //对view进行测量，后两个参数是要额外添加的宽和高，具体可以看源码,这里不再额外进行增加
            measureChildWithMargins(child, 0, 0);
            if(leftOffset+getDecoratedMeasurementHorizontal(child)<=getHorizontalSpace()){
                //绘制出带有偏移的item，这个方法绘制出来的item会将item的margin一并计算进去
                layoutDecoratedWithMargins(child,leftOffset,topOffset,leftOffset+getDecoratedMeasurementHorizontal(child),topOffset+getDecoratedMeasurementVertical(child));
                //保存位置信息,由于layou布置的是相对于显示区域的坐标而不是内容区域的坐标，而记录位置信息时需要的是内容位置的坐标，所以要加上滑动尺寸。
                Rect rect=new Rect(leftOffset,topOffset+ mVerticalOffset,leftOffset+getDecoratedMeasurementHorizontal(child),topOffset+getDecoratedMeasurementVertical(child)+ mVerticalOffset);
                mItemRects.put(i,rect);//保存位置信息以为逆序排列使用
                //每放置一个就更新一次行高
                lineHeight=Math.max(lineHeight,getDecoratedMeasurementVertical(child));
                //同时更新leftOffset的位置
                leftOffset+=getDecoratedMeasurementHorizontal(child);
            }else{//放不下，要换行显示
                //上偏移更新
                topOffset+=lineHeight;
                //左偏移重置
                //新起一行之前判断一下是否到了边界了，如果是快速滑动就得与判断一下topOffset+dy是否到边界了，不然就要多绘制几个
                leftOffset=getPaddingLeft();
                if(topOffset-dy>getHeight()-getPaddingBottom()){
                    //到了边界，则回收当前的view因为已经不需要绘制了
                    removeAndRecycleView(child,recycler);
                    //同时跳出绘制循环
                    break;
                }else{
                    layoutDecoratedWithMargins(child,leftOffset,topOffset,leftOffset+getDecoratedMeasurementHorizontal(child),topOffset+getDecoratedMeasurementVertical(child));
                    //保存位置信息
                    Rect rect=new Rect(leftOffset,topOffset+ mVerticalOffset,leftOffset+getDecoratedMeasurementHorizontal(child),topOffset+getDecoratedMeasurementVertical(child)+ mVerticalOffset);
                    mItemRects.put(i,rect);//保存位置信息以为逆序排列使用
                    //每放置一个就更新一次行高
                    lineHeight=Math.max(lineHeight,getDecoratedMeasurementVertical(child));
                    //同时更新leftOffset的位置
                    leftOffset+=getDecoratedMeasurementHorizontal(child);
                }
            }
        }
    }


    private int fill(RecyclerView.Recycler recycler, RecyclerView.State state, int dy){
        int topOffset=getPaddingTop();
        if(getChildCount()>0){//当前有显示在recyclerView的控件
            for (int i = getChildCount() - 1; i >= 0; i--) {
                View child = getChildAt(i);
                //因为是先回收再滑动的，所以得加上dy，来预判断滑动后的位置
                if(dy>0){ //界面内容向上滑动
                    if (getDecoratedBottom(child) - dy < topOffset) {//对上越界的View进行回收，向上
                        removeAndRecycleView(child, recycler);
                        mFirstVisiPos++;
                        continue;
                    }
                }else if(dy<0){ //界面内容向下滑动的，
                    if(getDecoratedTop(child)-dy>getHeight()-getPaddingBottom()){//对下越界的View进行回收
                        removeAndRecycleView(child, recycler);
                        mFirstVisiPos--;
                        continue;
                    }
                }
            }
            //接下来是布局阶段
            if(dy>0){ //界面内容向上滑动，即逐步显示底部内容，采用正常地绘制流程即可

                View lastView=getChildAt(getChildCount()-1); //屏幕上的最后一个View

                int minPos=getPosition(lastView);//这个View在Item里的position;

                minPos=minPos+1;//从最后一个View+1开始layout

                ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) lastView.getLayoutParams();

                topOffset =getDecoratedBottom(lastView);//这个方法只是获得了lastView的bottom+偏移的bottom，并没有加上marginBottom
                sequenceLayoutChild(recycler,topOffset+params.bottomMargin,minPos,getItemCount(),dy);
                //添加完成后进行判断如果绘制完了dy还有剩余，则对dy进行修正
                View child = getChildAt(getChildCount() - 1);
                if(getPosition(child)==getItemCount()-1){//最后一个了
                    int gap = getHeight() - getPaddingBottom() - getDecoratedBottom(child);
                    if(gap>0){
                        dy-=gap;
                    }
                }
            }else if(dy<0){ //界面内容向下滑动，即逐步显示顶部内容
                reversedLayoutChild(recycler,dy);
            }
        }
        return dy;
    }

    /**
     * 布局向顶部显示的话layout是逆序的，这里拿出顺序绘制时记录的位置信息进行逆序绘制
     * @param recycler
     * @param dy
     */
    private void reversedLayoutChild(RecyclerView.Recycler recycler, int dy) {
        View child=getChildAt(0);
        int position = getPosition(child);
        for (int i=position-1;i>=0;i--){
            Rect rect = mItemRects.get(i);
            child = recycler.getViewForPosition(i);
            if(rect.bottom-mVerticalOffset-dy<getPaddingTop()){//一旦绘制越界，就不再进行绘制
                break;
            }
            //一般的addView都会把控件按照顺序add进去，这里是逆序，肯定是插到第一个的,所以add,0
            addView(child,0);
            measureChildWithMargins(child, 0, 0);
            layoutDecoratedWithMargins(child, rect.left, rect.top-mVerticalOffset, rect.right, rect.bottom-mVerticalOffset);
        }
    }

    /**
     * 是否允许竖直滑动,注意这个方法需要跟scrollVerticallyBy连用，不然直接滑动到顶
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if(dy==0||getChildCount()==0){ //没有滑动或者内部没有内容
            return 0; //不让滑动
        }
        int realOffset = dy;//实际滑动的距离， 可能会在边界处被修复
        if(mVerticalOffset+realOffset<0){ //滑动到上边界了
            realOffset=-mVerticalOffset; //滑到上边界即可
        }else if(realOffset>0){ //向下滑动
            //利用最后一个view进行滑动比较
            View child=getChildAt(getChildCount()-1);
            int position = getPosition(child);
            int i = getItemCount() - 1;
            if(position == i){//显示在屏幕上的view是数据要显示的最后一个view
                int gap=getHeight()-getPaddingBottom()-getDecoratedBottom(child);
                if(gap<0){ //最后一个控件的bottom+间隔线的bottom离父布局的底边(外加paddingBottom)还有点距离，那就可以滑动，但最多滑动到gap
                    realOffset=Math.min(realOffset,-gap);
                }else if(gap==0){//刚好到父布局的边了
                    realOffset=0;
                }else { //当前最后一个布局已经在外面了，就不用滑动了,这里是为了后面的累加滑动距离，得将多出来的部分剪掉
                    realOffset = -gap;
                }
            }
        }
        //模仿linearLayoutManager写法先进行控件的回收与添加
        realOffset=fill(recycler, state, realOffset);//先填充，再位移。
        mVerticalOffset+=realOffset;//累加实际滑动距离
        //移动内容控件
        offsetChildrenVertical(-realOffset);
        return realOffset;
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
    }


    /**
     * 如果子控件本身没有layoutParams的时候会为控件生成一个layoutParams
     *
     * @return
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 获取某个childView在水平方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementHorizontal(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredWidth(view) + params.leftMargin
                + params.rightMargin;
    }

    /**
     * 获取某个childView在竖直方向所占的空间
     *
     * @param view
     * @return
     */
    public int getDecoratedMeasurementVertical(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin
                + params.bottomMargin;
    }

    /**
     * 获取水平方向可以绘制的尺寸
     * @return
     */
    public int getHorizontalSpace(){
        return getWidth()-getPaddingLeft()-getPaddingRight();
    }

    /**
     * 获取竖直方向可以绘制的尺寸
     * @return
     */
    public int getVerticalSpace(){
        return getWidth()-getPaddingLeft()-getPaddingRight();
    }
}
