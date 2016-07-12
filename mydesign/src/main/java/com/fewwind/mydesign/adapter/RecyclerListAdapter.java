/*
 * Copyright (C) 2015 Paul Burke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fewwind.mydesign.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fewwind.mydesign.MyApplication;
import com.fewwind.mydesign.R;
import com.fewwind.mydesign.bean.ChannelInfo;
import com.fewwind.mydesign.helper.ItemTouchHelperAdapter;
import com.fewwind.mydesign.helper.ItemTouchHelperViewHolder;
import com.fewwind.mydesign.helper.OnStartDragListener;
import com.fewwind.mydesign.utils.Constants;
import com.fewwind.mydesign.utils.PreferenceUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;


/**
 * Simple RecyclerView.Adapter that implements {@link ItemTouchHelperAdapter} to respond to move and
 * dismiss events from a {@link android.support.v7.widget.helper.ItemTouchHelper}.
 *
 * @author Paul Burke (ipaulpro)
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private List<ChannelInfo> mItems;
    private final OnStartDragListener mDragStartListener;

    private int playingPos = -1;
    private int mLastPosition = -1;
    private Context mContext;
    private OnMyClickListener myClickListener;

    public interface OnMyClickListener {
        void onMyClick(View v, int pos);
    }

    public void setOnMyclickListener(OnMyClickListener clickListener) {
        this.myClickListener = clickListener;
    }

    public RecyclerListAdapter(Context context, List<ChannelInfo> datas, OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
        mItems = datas;
        this.mContext = context;

    }


    public void updateMyChans(List<ChannelInfo> datas) {
        mItems.clear();
        this.mItems.addAll(datas);
        notifyDataSetChanged();
        notifyItemRangeInserted(getItemCount(),getItemCount());
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_main, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {

        final ChannelInfo channelInfo = mItems.get(position);
        if (channelInfo == null) return;
        holder.tvTitle.setText(channelInfo.getName());
        holder.tvDesc.setText(channelInfo.getDesc());
        holder.ivPlaying.setTag(channelInfo);
        holder.itemView.setTag(channelInfo);
        initImageLoader(holder.ivCover, Constants.URL_HEAD + channelInfo.getIcon().substring(4));

        if (position == playingPos) {
            holder.ivPlaying.setVisibility(View.VISIBLE);

        } else {
            holder.ivPlaying.setVisibility(View.GONE);
        }
        // Start a drag whenever the handle view it touched
//        holder.ivPlaying.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                    mDragStartListener.onStartDrag(holder);
//                }
//                return false;
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myClickListener != null) {
                    int pos = holder.getLayoutPosition();
//                    myClickListener.onMyClick(holder.itemView,position);
                }
            }
        });
        //这里给itemview设个tag，封装了channelinfo的信息，但是怎么把当前view控件用回调的方式传过去呢？
        holder.clickPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myClickListener.onMyClick(holder.ivPlaying, position);
            }
        });


        //判断是不是新增加的频道，如果是的话就把新的 标签显示出来
        if (PreferenceUtil.getInstance(mContext).isNewChan(String.valueOf(channelInfo.getId()))) {
            holder.tvLable.setVisibility(View.VISIBLE);
        } else {
            holder.tvLable.setVisibility(View.GONE);
        }

        //为item增加滑动动画
        int animResId;
        if (position > mLastPosition) {
            animResId = R.anim.from_bottom_to_top;
        } else {
            animResId = R.anim.from_top_to_bottom;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, animResId);
        //滑动的时候增加动画
        holder.itemView.startAnimation(animation);
        mLastPosition = position;
    }

    public void setPostion(int pos) {

        this.playingPos = pos;
    }

    private void initImageLoader(ImageView ivChan, String imageUrl) {
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.drawable.default_icon)
                .showImageOnLoading(R.drawable.default_icon)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, ivChan, options);
    }

    @Override
    public void onItemDismiss(int position) {


        PreferenceUtil.getInstance(mContext).deleteMyChan(String.valueOf(mItems.get(position).getId()), false);
        mItems.remove(position);
        notifyItemRemoved(position);

        MyApplication.helper.put(Constants.CACHEMYCHANNELS, (Serializable) mItems);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        MyApplication.helper.put(Constants.CACHEMYCHANNELS, (Serializable) mItems);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * Simple example of a view holder that implements {@link ItemTouchHelperViewHolder} and has a
     * "handle" view that initiates a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public final RelativeLayout clickPlay;
        public final TextView tvTitle;
        public final TextView tvLable;
        public final TextView tvDesc;
        public final ImageView ivCover;
        public final ImageView ivPlaying;
        public final CardView cardView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.id_tv_title_channel);
            tvDesc = (TextView) itemView.findViewById(R.id.id_tv_desc_channel);
            tvLable = (TextView) itemView.findViewById(R.id.id_tv_mylable);
            clickPlay = (RelativeLayout) itemView.findViewById(R.id.id_rl_clickplay);
            ivCover = (ImageView) itemView.findViewById(R.id.id_iv_cover);
            ivPlaying = (ImageView) itemView.findViewById(R.id.id_iv_playing);
            cardView = (CardView) itemView.findViewById(R.id.id_cardview);
        }

        @Override
        public void onItemSelected() {
            cardView.setCardBackgroundColor(Color.parseColor("#e6004a"));
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            cardView.setCardBackgroundColor(Color.WHITE);
            itemView.setBackgroundColor(0);

        }
    }
}
