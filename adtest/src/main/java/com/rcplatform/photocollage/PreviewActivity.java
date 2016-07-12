package com.rcplatform.photocollage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.rcplatform.photocollage.adapter.FilterBean;
import com.rcplatform.photocollage.baseadapter.ViewHolder;
import com.rcplatform.photocollage.baseadapter.recyclerview.MultiItemCommonAdapter;
import com.rcplatform.photocollage.baseadapter.recyclerview.MultiItemTypeSupport;
import com.rcplatform.photocollage.test.R;

import java.util.ArrayList;
import java.util.List;

public class PreviewActivity extends BaseActivity {


    RecyclerView mList;
    List<FilterBean> mDatas = new ArrayList<>();

    MultiItemCommonAdapter mAdapter;


    private int mClickPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        Logger.e("PreViewActivity 创建了");

        mDatas.clear();
        for (int i = 0; i < 20; i++) {
            if (i == 0) {
                mDatas.add(new FilterBean(false, "我是header==" + i, 0));
            } else if (i % 5 == 0) {
                mDatas.add(new FilterBean(false, "我是标题==" + i, -1));
            } else {
                mDatas.add(new FilterBean(false, "我是具体内容==" + i, 1));
            }
        }

        mList = (RecyclerView) findViewById(R.id.list);
        mList.setLayoutManager(new LinearLayoutManager(this));

        mList.setAdapter(mAdapter = new MultiItemCommonAdapter<FilterBean>(this, mDatas, new MultiItemTypeSupport<FilterBean>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == 0) {
                    return R.layout.item_header;
                } else if (itemType == -1) {
                    return R.layout.item;
                } else if (itemType == 1) {
                    return R.layout.item_grid;
                }
                return 0;
            }

            @Override
            public int getItemViewType(int position, FilterBean bean) {
                return bean.getType();
            }
        }) {
            @Override
            public void convert(ViewHolder holder, FilterBean o) {
                int itemType = holder.getItemViewType();
                if (itemType == 0) {
                    holder.setText(R.id.id_tv_header,o.getName());
                } else if (itemType == -1) {
                    holder.setText(R.id.id_tv,o.getName());
                } else if (itemType == 1) {
                    holder.setText(R.id.id_tv_grid,o.getName());
                }
            }

        });

    }

}
