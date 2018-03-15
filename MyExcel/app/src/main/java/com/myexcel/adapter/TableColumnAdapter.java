package com.myexcel.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.myexcel.R;
import com.myexcel.base.LvBaseViewHolder;
import com.myexcel.base.LvSuperBaseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 名称：左侧列适配器
 * 创建人：邹安富
 * 创建时间：2018/3/1
 * 详细说明：
 */
public class TableColumnAdapter extends LvSuperBaseAdapter<String> {

    public TableColumnAdapter(Context context, List<String> mDatas) {
        super(context, mDatas);
    }

    @Override
    public int getLayoutItemId() {
        return R.layout.item_table_column_view;
    }

    @Override
    public LvBaseViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void setDatas(LvBaseViewHolder holder, String itemBean, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.idTv11.setText(itemBean);
    }

    static class ViewHolder extends LvBaseViewHolder {
        @BindView(R.id.id_tv_11)
        TextView idTv11;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
