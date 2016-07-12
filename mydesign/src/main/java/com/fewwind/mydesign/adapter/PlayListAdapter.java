package com.fewwind.mydesign.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fewwind.mydesign.R;
import com.fewwind.mydesign.bean.PlayListInfo;
import com.fewwind.mydesign.utils.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by fewwind on 2015/11/4.
 */
public class PlayListAdapter extends BaseAdapter {

    private Context mContext;
    private List<PlayListInfo> mDatas;
    private LayoutInflater mInflater;


    public PlayListAdapter(List<PlayListInfo> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PlayListInfo playListInfo = mDatas.get(position);
        if (playListInfo == null) return null;


        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_play_list, parent,false);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.id_tv_name_play_list);
            viewHolder.tvSinger = (TextView) convertView.findViewById(R.id.id_tv_singer_play_list);
            viewHolder.ivConUrl = (ImageView) convertView.findViewById(R.id.id_iv_play_list);
            viewHolder.ivPlaying = (ImageView) convertView.findViewById(R.id.id_iv_isplaying_playlist);
            viewHolder.ivMore = (ImageView) convertView.findViewById(R.id.id_iv_playlist_more);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(playListInfo.getName());
        viewHolder.tvSinger.setText(playListInfo.getSinger());

        initImageLoader(viewHolder.ivConUrl, Constants.URL_HEAD + playListInfo.getIcon().substring(4));


        return convertView;
    }

    static class ViewHolder {
        TextView tvName;
        TextView tvSinger;
        ImageView ivConUrl;
        ImageView ivPlaying;
        ImageView ivMore;

    }

    public void updateDate(List<PlayListInfo> infos) {
        mDatas.addAll(infos);
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
}
