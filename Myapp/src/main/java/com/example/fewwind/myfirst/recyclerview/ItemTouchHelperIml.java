package com.example.fewwind.myfirst.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by fewwind on 2016/1/7.
 */
public class ItemTouchHelperIml extends ItemTouchHelper.Callback {



    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = 0;
        int swipeFlag = 0;

        RecyclerView.LayoutManager manger = recyclerView.getLayoutManager();
        if (manger instanceof GridLayoutManager || manger instanceof StaggeredGridLayoutManager){
            dragFlag = ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;

        } else{
            dragFlag  = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
            swipeFlag = ItemTouchHelper.START|ItemTouchHelper.END;

        }


        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType()!=target.getItemViewType())return false;

        if (recyclerView.getAdapter() instanceof OnItemMoveListener){
            OnItemMoveListener listener = (OnItemMoveListener) recyclerView.getAdapter();
            listener.onItemMove(viewHolder.getLayoutPosition(),target.getLayoutPosition());
        }

        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState!=ItemTouchHelper.ACTION_STATE_IDLE){
            if (viewHolder instanceof  OnDragVHListener){
                OnDragVHListener listener = (OnDragVHListener) viewHolder;
                listener.onItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof  OnDragVHListener){
            OnDragVHListener listener = (OnDragVHListener) viewHolder;
            listener.onItemFinish();
        }
        super.clearView(recyclerView, viewHolder);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }
}
