package com.fewwind.mydesign.test.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fewwind.mydesign.R;

/**
 * Created by fewwind on 2016/3/19.
 */
public class AgeSelectAdapter extends RecyclerView.Adapter<AgeSelectAdapter.AgeHolder> {

    public static final int ITEM_NUM = 7;
    private int mFrom;
    private int mEnd;
    private int mHighLight = -1;

    private Context mCxt;
    public AgeSelectAdapter(int mEnd, int mFrom,Context context) {
        this.mEnd = mEnd;
        this.mFrom = mFrom;
        this.mCxt = context;
    }

    @Override
    public AgeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mCxt).inflate(R.layout.textview, parent, false);
        ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
        layoutParams.width = getItemWidth();

        return new AgeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AgeHolder holder, int position) {
        holder.mTvl.setText(String.valueOf(mFrom+position));

        if (isSelect(position)){
            holder.mTvl.setTextSize(30);
            holder.mTvl.setTextColor(Color.RED);
        } else{
            holder.mTvl.setTextSize(20);
            holder.mTvl.setTextColor(Color.BLACK);
        }
    }

    public void setmHighLight(int pos){
        mHighLight  =pos;
        int offset = ITEM_NUM/2;
 /*       for (int i = pos-offset;i<=pos+offset;i++){
            notifyItemChanged(i);
        }*/
//        Logger.i(pos+"===start pos==="+(offset));

        notifyItemRangeChanged( pos-offset,ITEM_NUM);

    }

    public boolean isSelect(int pos){
        return pos==mHighLight;
    }

    @Override
    public int getItemCount() {
        return mEnd-mFrom+1;
    }

    public int getItemWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels / ITEM_NUM;
    }

    public class AgeHolder extends RecyclerView.ViewHolder {
        public TextView mTvl;

        public AgeHolder(View itemView) {
            super(itemView);

            mTvl = (TextView) itemView.findViewById(R.id.id_tv);
            mTvl.setTag(this);
        }

    }


}
