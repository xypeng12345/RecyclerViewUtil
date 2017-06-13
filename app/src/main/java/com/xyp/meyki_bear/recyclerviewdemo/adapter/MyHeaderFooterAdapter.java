package com.xyp.meyki_bear.recyclerviewdemo.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：RecyclerViewUtil
 * 类描述：
 * 创建人：xyp
 * 创建时间：2017/5/31 15:28
 * 修改人：meyki-bear
 * 修改时间：2017/5/31 15:28
 * 修改备注：
 */

public abstract class MyHeaderFooterAdapter<VH extends MyHeaderFooterAdapter.HeaderAndFooterViewHolder>
        extends RecyclerView.Adapter<MyHeaderFooterAdapter.HeaderAndFooterViewHolder> {

    private HeaderAndFooterList mHeaderViews;
    private HeaderAndFooterList mFooterViews;
    private int headerType = 20000;
    private int footerType = 30000;

    public void addHeaderView(View headerView) {
        addHeaderView(headerView, -1);
    }

    /**
     * 在置顶位置插入一个headerView
     *
     * @param headerView
     * @param index      这个index不能超过插入前headerViews的长度
     */
    public void addHeaderView(View headerView, int index) {
        if (mHeaderViews == null) {
            mHeaderViews = new HeaderAndFooterList();
        }
        //同一个headerView不能重复添加
        for (int i = 0; i < mHeaderViews.size(); i++) {
            if (mHeaderViews.get(i).view == headerView) {
                return;
            }
        }
        HeaderAndFooterBean map = new HeaderAndFooterBean();
        map.itemType = headerType;
        map.view = headerView;
        if (index == -1) {
            mHeaderViews.add(map);
            notifyItemInserted(mHeaderViews.size() - 1);
        } else {
            mHeaderViews.add(index, map);
            notifyItemInserted(index);
        }
        headerType++;
    }

    public void removeHeaderView(View headerView) {
        if (mHeaderViews == null) {
            return;
        }
        for (int i = mHeaderViews.size() - 1; i >= 0; i--) {
            if (mHeaderViews.get(i).view == headerView) {
                mHeaderViews.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public void clearHeaderView() {
        if (mHeaderViews == null) {
            return;
        }
        notifyItemRangeRemoved(0, mHeaderViews.size() - 1);
        mHeaderViews.clear();
    }

    public int findHeaderPosition(View view) {
        for (int i = 0; i < mHeaderViews.size(); i++) {
            if (mHeaderViews.get(i).view == view) {
                return i;
            }
        }
        return -1;
    }

    public void addFooterView(View footerView) {
        if (mFooterViews == null) {
            mFooterViews = new HeaderAndFooterList();
        }
        //如果已经添加过了，则不在添加
        for (int i = 0; i < mFooterViews.size(); i++) {
            View view = mFooterViews.get(i).view;
            if (view == footerView) {
                return;
            }
        }
        HeaderAndFooterBean map = new HeaderAndFooterBean();
        map.itemType = footerType;
        map.view = footerView;
        mFooterViews.add(map);
        int headerSize = 0;
        int dataSize = 0;
        int footerSize = 0;
        if (mHeaderViews != null) {
            headerSize = mHeaderViews.size();
        }
        if (mFooterViews != null) {
            footerSize = mFooterViews.size();
        }
        dataSize = getDataCount();
        footerType++;
        //size从1开始，position从0开始，所以需要减1
        notifyItemInserted((headerSize + footerSize + dataSize + mFooterViews.size()) - 1);
        return;
    }

    public void removeFooterView(View footerView) {
        if (mFooterViews == null) {
            return;
        }
        for (int i = mFooterViews.size() - 1; i >= 0; i--) {
            if (mFooterViews.get(i).view == footerView) {
                mFooterViews.remove(i);
            }
        }
        int headerSize = 0;
        int dataSize = 0;
        int footerSize = 0;
        if (mHeaderViews != null) {
            headerSize = mHeaderViews.size();
        }
        if (mFooterViews != null) {
            footerSize = mFooterViews.size();
        }
        dataSize = getDataCount();
        footerType++;
        //size从1开始，position从0开始，所以需要减1
        notifyItemRemoved((headerSize + footerSize + dataSize + mFooterViews.size()) - 1);
    }

    public void clearFooterView() {
        if (mFooterViews == null) {
            return;
        }
        mFooterViews.clear();
    }

    @Override
    public int getItemCount() {
        return getHeaderCount() + getDataCount() + getFooterCount();
    }

    private int getHeaderCount() {
        return mHeaderViews != null ? mHeaderViews.size() : 0;
    }

    private int getFooterCount() {
        return mFooterViews != null ? mFooterViews.size() : 0;
    }

    protected abstract int getDataCount();

    protected abstract int getType(int position);

    @Override
    final public int getItemViewType(int position) {
        //有头布局且当前显示的Position在头布局的范围内
        if (isHeaderView(position)) {
            return mHeaderViews.get(position).itemType;
        }
        //有尾布局且当前显示的position在尾布局的范围内
        if (isFooterView(position)) {
            position = position - getHeaderCount() - getDataCount();
            return mFooterViews.get(position).itemType;
        }
        int pos = getRealPosition(position);
        return getType(pos);
    }

    /**
     * 头尾布局中realPosition只需要处理头布局的影响，尾布局不需要
     *
     * @param position
     * @return
     */
    final protected int getRealPosition(int position) {
        if (mHeaderViews != null && position < getDataCount() + getHeaderCount()) {
            position = position - (mHeaderViews.size());
        }
        return position;
    }

    private boolean isHeaderView(int position) {
        return mHeaderViews != null && mHeaderViews.getShow() && position < mHeaderViews.size();
    }

    private boolean isFooterView(int position) {
        return mFooterViews != null && mFooterViews.getShow() && position > getDataCount() + getHeaderCount() - 1;
    }

    @Override
    final public void onBindViewHolder(MyHeaderFooterAdapter.HeaderAndFooterViewHolder holder, int position, List<Object> payloads) {
        if (isHeaderView(position) || isFooterView(position)) {
            return;
        }
        final int pos = getRealPosition(position);
        convert((VH) holder, pos, payloads);
    }

    @Override
    final public void onBindViewHolder(MyHeaderFooterAdapter.HeaderAndFooterViewHolder holder, int position) {

    }


    /**
     * 专门处理瀑布流的RecyclerView的头布局
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(MyHeaderFooterAdapter.HeaderAndFooterViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (gridLayoutManagerSpanLookUp == null) {
            gridLayoutManagerSpanLookUp = new GridLayoutManagerSpanLookUp();
        }
        //GridLayoutManager的头尾布局判断
        RecyclerView recyclerView = (RecyclerView) holder.itemView.getParent();
        //GridLayoutManager头尾布局判断方法
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        if (lm instanceof GridLayoutManager) {
            final GridLayoutManager glm = (GridLayoutManager) lm;
            if (gridLayoutManagerSpanLookUp.getLayoutManager() != glm) {
                gridLayoutManagerSpanLookUp.setLayoutManager(glm);
                glm.setSpanSizeLookup(gridLayoutManagerSpanLookUp);
            }
        } else if (lm instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager sgm = (StaggeredGridLayoutManager) lm;
            //瀑布流StaggeredGridLayoutManager头尾布局的判断方法
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            int layoutPosition = holder.getLayoutPosition();
            StaggeredGridLayoutManager.LayoutParams slp = (StaggeredGridLayoutManager.LayoutParams) lp;
            int spanSize = getSpanSize(layoutPosition, sgm.getSpanCount());
            //瀑布流没有占几行只说，只有是否占满的区别
            slp.setFullSpan(spanSize == sgm.getSpanCount());
        }
    }

    private GridLayoutManagerSpanLookUp gridLayoutManagerSpanLookUp;

    /**
     * 这个回调只在GirdLayoutManager时有精准效果，如果是瀑布流则只能选择是否占满一行
     *
     * @param position
     * @param spanCount
     * @return
     */
    final protected int getSpanSize(int position, int spanCount) {
        return isHeaderView(position) || isFooterView(position) ?
                spanCount :
                getViewHolderSpanSize(getRealPosition(position), spanCount);
    }

    /**
     * 这个ViewHolder占几行显示，这个方法返回值在grid布局中精准有效
     * 而在瀑布流布局中，如果返回值小于行数，则无效，大于等于行数则占满一行显示
     *
     * @return
     */
    protected int getViewHolderSpanSize(int position, int spanCount) {
        return 1;
    }

    private class GridLayoutManagerSpanLookUp extends GridLayoutManager.SpanSizeLookup {
        private GridLayoutManager layoutManager;

        public GridLayoutManager getLayoutManager() {
            return layoutManager;
        }

        public void setLayoutManager(GridLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public int getSpanSize(int position) {
            return MyHeaderFooterAdapter.this.getSpanSize(position, layoutManager.getSpanCount());
        }
    }

    protected abstract void convert(VH holder, int position, List<Object> payloads);

    @Override
    final public HeaderAndFooterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews != null && mHeaderViews.getShow()) {
            for (int i = 0; i < mHeaderViews.size(); i++) {
                if (mHeaderViews.get(i).itemType == viewType) {
                    HeaderAndFooterViewHolder viewHolder = new HeaderAndFooterViewHolder(mHeaderViews.get(i).view);
                    return viewHolder;
                }
            }
        }
        if (mFooterViews != null && mFooterViews.getShow()) {
            for (int i = 0; i < mFooterViews.size(); i++) {
                if (mFooterViews.get(i).itemType == viewType) {
                    HeaderAndFooterViewHolder viewHolder = new HeaderAndFooterViewHolder(mFooterViews.get(i).view);
                    return viewHolder;
                }
            }
        }
        return onCreateDataViewHolder(parent, viewType);
    }

    protected abstract VH onCreateDataViewHolder(ViewGroup parent, int viewType);


    private class HeaderAndFooterBean {
        int itemType;
        View view;
    }

    private class HeaderAndFooterList extends ArrayList<HeaderAndFooterBean> {
        private boolean isShow = true;

        public void setShow(boolean isShow) {
            this.isShow = isShow;
        }

        public boolean getShow() {
            return isShow;
        }

        @Override
        public int size() {
            if (!isShow) {
                return 0;
            }
            return super.size();
        }
    }

    public class HeaderAndFooterViewHolder extends RecyclerView.ViewHolder {

        public HeaderAndFooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
