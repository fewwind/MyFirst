package com.example.fewwind.myfirst.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fewwind.myfirst.R;

import java.util.List;

/**
 * Created by fewwind on 2016/1/6.
 */
public class RecyclerGridAdapter <T> extends RecyclerView.Adapter<GridViewHolder> {

    private static final int TYPE_HEADER = 0, TYPE_ITEM = 1, TYPE_FOOTER = 2;
    public List<T> mDatas;

    private View headView;
    private View footerView;
    private int headSize = 0;
    private int footerSize = 0;
    private boolean isAddHead;
    private boolean isAddFooter;
    ChangeGridLayoutSpance changeGridLayoutSpance;

    boolean mEditMode;

    public interface ChangeGridLayoutSpance {
        void changeSpnce(int size, boolean isAddHead, boolean isAddFooter);
    }

    public void setChangeGridLayoutSpance(ChangeGridLayoutSpance listener) {
        this.changeGridLayoutSpance = listener;
        changeGridLayoutSpance.changeSpnce(getItemCount() - 1, isAddHead, isAddFooter);
    }

    public RecyclerGridAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
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
    public GridViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEADER:
                view = headView;
                break;

            case TYPE_FOOTER:
                view = footerView;
                break;

            case TYPE_ITEM:
                view = View.inflate(parent.getContext(), R.layout.item, null);
                GridViewHolder myHolder =   new GridViewHolder(view);
                myHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mEditMode = true;
                        RecyclerView recycler = (RecyclerView) parent;

                        for (int i = 0; i < recycler.getChildCount(); i++) {

                            ImageView iv = (ImageView) recycler.getChildAt(i).findViewById(R.id.img_edit);
                            if (iv!=null)
                            iv.setVisibility(View.VISIBLE);
                        }

                        Log.e("tag", "onLongClick: "+ recycler.getChildCount());

                        return true;
                    }
                });

                break;
        }

        return new GridViewHolder(view);
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
    public void onBindViewHolder(final GridViewHolder holder, int position) {
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//
//                holder.iv.setVisibility(View.VISIBLE);
//
//                Log.e("tag", "onLongClick: ");
//
//                notifyDataSetChanged();
//                return false;
//            }
//        });
        if (mEditMode){
            if (holder.iv!=null)
            holder.iv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size() + headSize + footerSize;
    }
}

    class GridViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        public GridViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.img_edit);
        }
    }
