package com.hyying.hynews.module.main.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyying.hynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.topic_item_title)
    public TextView tvTitle;
    @BindView(R.id.topic_item_pic)
    public ImageView ivTopicPic;
    @BindView(R.id.topic_item_comment_num)
    public TextView tvCommentNum;
    @BindView(R.id.topic_item_post_time)
    public TextView tvPostTime;

    public TopicViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
