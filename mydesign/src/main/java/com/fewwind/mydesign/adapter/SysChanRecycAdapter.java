package com.fewwind.mydesign.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fewwind.mydesign.R;
import com.fewwind.mydesign.bean.ChannelInfo;
import com.fewwind.mydesign.utils.Constants;
import com.fewwind.mydesign.utils.PreferenceUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fewwind on 2015/10/15.
 */
public class SysChanRecycAdapter extends RecyclerView.Adapter<SysChanRecycAdapter.ViewHolder> {

    private List<ChannelInfo> sysChanLists ;

    private  ClickItemListener mClickItem;
    private Context mContext;

    public interface ClickItemListener {
        void clickIcon(View v,ChannelInfo info);
        void clickTitle(ChannelInfo info);
    }

    public void setOnClickItemListener(ClickItemListener listener) {
        this.mClickItem = listener;
    }
    public SysChanRecycAdapter(Context context,List<ChannelInfo> sysChanLists) {
        this.sysChanLists = sysChanLists.size()==0?new ArrayList<ChannelInfo>():sysChanLists;
        this.mContext = context;
    }

    public void updateSysChans(List<ChannelInfo> datas){
        this.sysChanLists = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_sys_chan,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ChannelInfo channelInfo = sysChanLists.get(position);
        if (channelInfo==null)return;

        holder.tvChan.setText(channelInfo.getName());
        initImageLoader(holder.ivChan, Constants.URL_HEAD + channelInfo.getIcon().substring(4));
        holder.tvChan.setTag(channelInfo);
        holder.ivChan.setTag(channelInfo);

        holder.tvChan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickItem.clickTitle(channelInfo);
            }
        });

        holder.ivChan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickItem.clickIcon(v,channelInfo);
            }
        });

        //判断是不是新增加的频道，如果是的话就把新的 标签显示出来
        if (PreferenceUtil.getInstance(mContext).isMyChan(String.valueOf(channelInfo.getId()))){
            holder.tvLable.setVisibility(View.VISIBLE);
        } else{
            holder.tvLable.setVisibility(View.GONE);
        }
    }

    private void initImageLoader(ImageView ivChan,String imageUrl ) {
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, ivChan, options);
    }

    @Override
    public int getItemCount() {
        return sysChanLists==null?0:sysChanLists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivChan;
        private TextView  tvChan;
        private TextView  tvLable;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivChan = (ImageView) view.findViewById(R.id.id_iv_syschan);
            tvChan = (TextView) view.findViewById(R.id.id_tv_syschan);
            tvLable = (TextView) view.findViewById(R.id.id_tv_lable_sys);
        }
    }
}
