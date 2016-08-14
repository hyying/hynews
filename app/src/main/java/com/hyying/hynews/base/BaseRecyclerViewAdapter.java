package com.hyying.hynews.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    protected LayoutInflater mLayoutInflater;
    private List<T> mDatas;
    private boolean showFooter;
    private boolean showHeader;
    protected static final int TYPE_HEADER = 100;
    protected static final int TYPE_ITEM = 101;
    protected static final int TYPE_FOOTER = 102;


    public BaseRecyclerViewAdapter(Context context, List<T> datas) {
        mContext = context;
        mLayoutInflater=LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER)
            return createHeaderViewHolder(parent);
        else if (viewType == TYPE_ITEM)
            return createItemViewHolder(parent);
        else if (viewType == TYPE_FOOTER)
            return createFooterViewHolder(parent);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER)
            bindHeader(holder);
        else if (getItemViewType(position) == TYPE_FOOTER)
            bindFooter(holder);
        else
            bindItem(holder, position);

    }

    @Override
    public int getItemCount() {
        return mDatas.size() + (showFooter ? 1 : 0) + (showHeader ? 1 : 0);
    }

    protected abstract RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent);

    protected abstract RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent);

    protected abstract RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent);

    protected abstract void bindHeader(RecyclerView.ViewHolder holder);

    protected abstract void bindFooter(RecyclerView.ViewHolder holder);

    protected abstract void bindItem(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemViewType(int position) {
        if (showHeader && position == 0)
            return TYPE_HEADER;
        else if (showFooter && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setShowFooter(boolean showFooter) {
        this.showFooter = showFooter;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }
}
