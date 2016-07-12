package com.fewwind.mydesign.test;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fewwind.mydesign.R;
import com.fewwind.mydesign.bean.TestTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fewwind on 2016/3/3.
 */
public class RvTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int TYPE_LIST = Integer.MAX_VALUE;
    public static int TYPE_GRID = Integer.MAX_VALUE-1;
    public static int TYPE_HEADER = -1;

    private View mHeaderView;
    private List<View> mHeaderViews = new ArrayList<>();
    List<TestTypeBean> mDatas = new ArrayList<>();

    private SparseArray<TestTypeBean> mMap;

    public RvTypeAdapter() {
        for (int i = 0; i < 50; i++) {
            if (i % 4 == 0) {
                mDatas.add(new TestTypeBean(TYPE_LIST, "ListView" + i, "我是横向展示数据"));
            } else {
                mDatas.add(new TestTypeBean(TYPE_GRID, "Grid" + i, "我是列表展示数据"));
            }
        }
        mMap = new SparseArray<>();
    }

    public void addHeaderView(View headerView) {
        mHeaderViews.add(headerView);
        this.mHeaderView =headerView;
        notifyItemInserted(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType <mHeaderViews.size()) {
            ViewHolderHeader vh = new ViewHolderHeader(mHeaderViews.get(viewType));
            return vh;
        } else if (viewType == TYPE_LIST) {
            ViewHolderList vh = new ViewHolderList(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
            return vh;
        } else if (viewType == TYPE_GRID) {
            ViewHolderGrid vh = new ViewHolderGrid(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active, parent, false));
            return vh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int pos) {

        final int position = pos - mHeaderViews.size();
        if (holder instanceof ViewHolderList) {
            ((ViewHolderList) holder).title.setText(mDatas.get(position).title);
            ((ViewHolderList) holder).content.setText(mDatas.get(position).content);

            final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams sglp = (StaggeredGridLayoutManager.LayoutParams) lp;
                sglp.setFullSpan(true);
                holder.itemView.setLayoutParams(sglp);
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insert(position);
                }
            });
        } else if (holder instanceof ViewHolderGrid) {
            ((ViewHolderGrid) holder).title.setText(mDatas.get(position).title);
            ((ViewHolderGrid) holder).content.setText(mDatas.get(position).content);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insert(position);
                }
            });
        } else if (holder instanceof ViewHolderHeader) {

        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position < mHeaderViews.size()) {
            return position;
        } else {
            return mDatas.get(position - mHeaderViews.size()).type;
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size() + mHeaderViews.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position<mHeaderViews.size() || getItemViewType(position) == TYPE_LIST) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }

                }
            });
        }

    }

    class ViewHolderHeader extends RecyclerView.ViewHolder {

        public ViewHolderHeader(View itemView) {
            super(itemView);
        }
    }


    class ViewHolderList extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        View select;

        public ViewHolderList(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.id_test_title);
            content = (TextView) itemView.findViewById(R.id.id_test_content);
        }
    }

    class ViewHolderGrid extends RecyclerView.ViewHolder {

        TextView title;
        TextView content;
        View select;

        public ViewHolderGrid(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.id_test_title);
            content = (TextView) itemView.findViewById(R.id.id_test_content);
        }
    }

    public void insert(int old) {
        TestTypeBean bean = mDatas.get(old);
        mMap.append(old, bean);
        mDatas.remove(old);
        mDatas.add(0, bean);
        notifyDataSetChanged();
    }


    public void remove(int pos) {
        TestTypeBean bean = mDatas.get(pos);
        int indexOfValue = mMap.indexOfValue(bean);
        mMap.remove(pos);
        mDatas.remove(pos);
        mDatas.add(indexOfValue, bean);
        notifyDataSetChanged();

    }

}
