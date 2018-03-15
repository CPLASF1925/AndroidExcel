package com.myexcel.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zouanfu on 2017/8/1.
 */

public abstract class LvSuperBaseAdapter<T> extends BaseAdapter {
    public List<T> mDatas = new ArrayList<>();
    public Context context;
    public Activity activity;
    private LayoutInflater inflater;

    private int count;

    public abstract int getLayoutItemId();

    public abstract LvBaseViewHolder getViewHolder(View itemView);


    public abstract void setDatas(LvBaseViewHolder holder, T itemBean, int position);


    public LvSuperBaseAdapter(Context context) {
        this.context = context;
        activity = (Activity) context;
        count = this.mDatas.size();
        inflater = LayoutInflater.from(context);
    }

    public LvSuperBaseAdapter(Context context, List<T> mDatas) {
        this.mDatas = mDatas;
        this.context = context;
        activity = (Activity) context;

        count = this.mDatas.size();
        inflater = LayoutInflater.from(context);
    }

    public Map baseMap;


    public Map getMap() {
        baseMap = new HashMap<String, Object>();
        return baseMap;
    }

    public void baseStartActivity(Class cs) {
        Intent intent = new Intent(context, cs);
        context.startActivity(intent);
    }


    public void baseStartActivity(Class cs, Map<String, Object> map) {
        Intent intent = new Intent(context, cs);
        Set<String> set = map.keySet();
        for (String key : set) {
            Object values = map.get(key);
            if (values instanceof Double) {
                intent.putExtra(key, (Double) values);
            }
            if (values instanceof String) {
                intent.putExtra(key, (String) values);
            }
            if (values instanceof Integer) {
                intent.putExtra(key, (int) values);
            }
        }
        context.startActivity(intent);
    }


    /**
     * 新增一个条目数据
     */
    public void addItemData(T itemData) {
        this.mDatas.add(itemData);
        count = this.mDatas.size();
        notifyDataSetChanged();
    }

    public void setCount(int count) {
        this.count = count;
        notifyDataSetChanged();
    }

    public void setmDatas(List<T> mDatas) {
        if (mDatas == null) {
            return;
        }
        this.mDatas.addAll(mDatas);
        count = this.mDatas.size();
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void clearData() {
        if (mDatas == null) {
            return;
        }
        mDatas.clear();
    }

    public void notifyData() {
        if (mDatas != null) {
            count = mDatas.size();
        } else {
            count = 0;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return count;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LvBaseViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(getLayoutItemId(), parent, false);
            holder = getViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LvBaseViewHolder) convertView.getTag();
        }

        T t = null;
        if (mDatas != null && mDatas.size() > 0) {
            t = mDatas.get(position);
        }

        setDatas(holder, t, position);//设置数据

        return convertView;
    }


    //条目点击回调
    private OnItemClickListener onItemClickListener;

    public void setOnIemClickListener(OnItemClickListener onItemClickListener) {
        if (onItemClickListener != null) {
            this.onItemClickListener = onItemClickListener;
        }
    }

    public interface OnItemClickListener<T> {
        void itemClick(T resultBean);
    }
}
