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
import com.fewwind.mydesign.bean.PlayListInfo;
import com.fewwind.mydesign.utils.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by fewwind on 2015/11/13.
 */
public class PlayListRecycAdapter extends RecyclerView.Adapter<PlayListRecycAdapter.ViewHolder> {



    public interface onCilickMyListener{
        void onClickPosition(int pos);
    }

    private onCilickMyListener myListener;

    private Context mContext;
    private List<PlayListInfo> mDatas;
    private LayoutInflater mInflater;



    public PlayListRecycAdapter(List<PlayListInfo> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setMyListener( onCilickMyListener listener){
        this.myListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_play_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PlayListInfo playListInfo = mDatas.get(position);
        if (playListInfo==null)return;

        holder.tvName.setText(playListInfo.getName());
        holder.tvSinger.setText(playListInfo.getSinger());

        initImageLoader(holder.ivConUrl, Constants.URL_HEAD + playListInfo.getIcon().substring(4));

        if (myListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myListener.onClickPosition(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }



    public void updateDate(List<PlayListInfo> infos) {
        mDatas.clear();
        this.mDatas.addAll(infos);
        notifyDataSetChanged();
    }

    private void initImageLoader(ImageView ivChan, String imageUrl) {
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(imageUrl, ivChan, options);
    }






    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvSinger;
        ImageView ivConUrl;
        ImageView ivPlaying;
        ImageView ivMore;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.id_tv_name_play_list);
            tvSinger = (TextView) itemView.findViewById(R.id.id_tv_singer_play_list);
            ivConUrl = (ImageView) itemView.findViewById(R.id.id_iv_play_list);
            ivPlaying = (ImageView) itemView.findViewById(R.id.id_iv_isplaying_playlist);
            ivMore = (ImageView) itemView.findViewById(R.id.id_iv_playlist_more);
        }
    }

}
