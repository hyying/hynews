package com.hyying.hynews.module.main.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hyying.hynews.R;
import com.hyying.hynews.bean.Item;
import com.hyying.hynews.module.main.ui.viewholder.TopicViewHolder;

import java.util.List;

public class MainPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<Item> mItemList;
    private LayoutInflater mLayoutInflater;
    private static final int TOPIC_TYPE = 0;
    private static final int COMMON_TYPE = 1;

    public MainPageAdapter(Context context, List<Item> itemList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TOPIC_TYPE) {
            return new TopicViewHolder(mLayoutInflater.inflate(R.layout.topic_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = mItemList.get(position);
        if (getItemViewType(position) == TOPIC_TYPE) {
            TopicViewHolder topicViewHolder = (TopicViewHolder) holder;
            Glide.with(mContext).load(item.getImage_url()).into(topicViewHolder.ivTopicPic);
            topicViewHolder.tvTitle.setText(item.getTitle());
            topicViewHolder.tvCommentNum.setText(String.format(mContext.getString(R.string.comment_format),
                    item.getComment_num()));
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Item item = mItemList.get(position);
        if ("topic".equals(item.getType()))
            return TOPIC_TYPE;
        else
            return COMMON_TYPE;
    }

}
