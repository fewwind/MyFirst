package com.example.fewwind.myfirst.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fewwind.myfirst.R;

import java.util.List;

/**
 * Created by fewwind on 2016/1/6.
 */
public class RecycleLinerAdapter <T>extends RecyclerView.Adapter<LinerViewHolder> implements OnItemMoveListener {

    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOTER = 2;
    public List<T> mDatas;

    private View headView;
    private View footerView;
    private int headSize = 0;
    private int footerSize = 0;
    private boolean isAddHead;
    private boolean isAddFooter;

    private Context mCtxt;
    public RecycleLinerAdapter(List<T> mDatas,Context context) {
        this.mDatas = mDatas;
        this.mCtxt = context;
    }

    public void addHeaderView(View view) {
        this.headView = view;
        headSize = 1;
        isAddHead = true;
    }

    public void addFooterView(View view) {
        this.footerView = view;
        footerSize = 1;
        isAddFooter = true;
    }

    @Override
    public int getItemViewType(int position) {

        int type = TYPE_ITEM;
        if (headSize == 1 && position == 0) {
            type = TYPE_HEADER;
        } else if (footerSize == 1 && position == getItemCount() - 1) {
            type = TYPE_FOOTER;
        }

        return type;
    }

    @Override
    public LinerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEADER:
                view = headView;
                break;

            case TYPE_FOOTER:
                view = footerView;
                break;

            case TYPE_ITEM:
                view = LayoutInflater.from(mCtxt).inflate(R.layout.item,parent,false);

                break;
        }

        return new LinerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LinerViewHolder holder, int position) {
//        holder.tv.setText(mDatas.get(position) + "");
    }

    @Override
    public int getItemCount() {
        return mDatas.size() + headSize + footerSize;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        T t = mDatas.get(fromPosition);
        mDatas.remove(t);

        mDatas.add(toPosition, t);
        notifyItemMoved(fromPosition,toPosition);
    }
}
    class LinerViewHolder extends RecyclerView.ViewHolder implements OnDragVHListener{

        TextView tv;
        public LinerViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(android.R.id.text1);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.GREEN);
        }

        @Override
        public void onItemFinish() {
            itemView.setBackgroundColor(0);
        }
    }
