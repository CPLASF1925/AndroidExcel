package com.myexcel.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.myexcel.R;
import com.myexcel.base.LvBaseViewHolder;
import com.myexcel.base.LvSuperBaseAdapter;
import com.myexcel.bean.CheckableLinearLayout;
import com.myexcel.bean.TableContentBean;
import com.myexcel.view.RowItmeViewLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 名称：主内容适配器
 * 创建人：邹安富
 * 创建时间：2018/3/1
 * 详细说明：
 */
public class TableContentAdapter extends LvSuperBaseAdapter<TableContentBean> {
    private static final String TAG = "TableContentAdapter";


    public TableContentAdapter(Context context, List<TableContentBean> mDatas) {
        super(context, mDatas);
    }

    @Override
    public int getLayoutItemId() {
        return R.layout.item_table_content_view;
    }

    @Override
    public LvBaseViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void setDatas(LvBaseViewHolder holder, TableContentBean itemBean, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        int childCount = viewHolder.llTableContent.getChildCount();
        Log.e(TAG, "setDatas: childCount===" + childCount);
        List<TableContentBean.HourData> hourData = itemBean.getHourData();

        viewHolder.rowlContent.clearAllProgress();

        if (itemBean.getYdStatc() == 1) {
            for (int i = 0; i < hourData.size(); i++) {
                String ydTimeSlot = hourData.get(i).getYdTimeSlot();
                if (!TextUtils.isEmpty(ydTimeSlot)) {
                    viewHolder.rowlContent.setProgress(ydTimeSlot);
                }
            }
        } else {
            viewHolder.rowlContent.clearAllProgress();
        }


//        if (position == 0) {
//            viewHolder.rowlContent.setProgress("01:10-10:20");
//            viewHolder.rowlContent.setProgress("00:10-00:40");
//        }
//        if (position == 1) {
//            viewHolder.rowlContent.setProgress("01:10-02:30");
//            viewHolder.rowlContent.setProgress("04:00-05:50");
//            viewHolder.rowlContent.setProgress("07:10-08:00");
//        }
//        if (position == 2) {
//            viewHolder.rowlContent.setProgress("10:10-15:40");
//        }
//        if (position == 3) {
//            viewHolder.rowlContent.setProgress("20:00-22:20");
//        }
//        if (position == 4) {
//            viewHolder.rowlContent.setProgress("00:00-22:20");
//        }
//


//        ThreadManager.getInstance().addNewRunnable(new Runnable() {
//            @Override
//            public void run() {
//                //整天没有预订
//                for (int i = 0; i < 24; i++) {
//                    View inflate = LayoutInflater.from(context).inflate(R.layout.include_item_yd_row, null);
//                    MyProgress myProgress = (MyProgress) inflate.findViewById(R.id.mp_progress);
//                    myProgress.setMaxProgress(60);
//                    myProgress.setStardEndProgress(0, 0);
//                    viewHolder.llTableContent.addView(inflate);
//                    myProgress.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            ToastUtil.showMessage("没有预订");
//                        }
//                    });
//                }
//            }
//        });


//        int ydStatc = itemBean.getYdStatc();
//        if (ydStatc == 1) {
//            //有预订
//            List<TableContentBean.HourData> hourData = itemBean.getHourData();
//            for (int i = 0; i < hourData.size(); i++) {
//                LogUtils.e("hourData.size()==" + hourData.size());
//                try {
//                    TableContentBean.HourData hourDataItem = hourData.get(i);
//                    final String ydEndTime = hourDataItem.getYdEndTime();
//                    final String ydStartTime = hourDataItem.getYdStartTime();
//                    final String ydHour = hourDataItem.getYdHour();
//                    View inflate = LayoutInflater.from(context).inflate(R.layout.include_item_yd_row, null);
//                    MyProgress myProgress = (MyProgress) inflate.findViewById(R.id.mp_progress);
//                    myProgress.setMaxProgress(60);
//
//                    myProgress.setStardEndProgress(Integer.parseInt(ydStartTime), Integer.parseInt(ydEndTime));
//                    viewHolder.llTableContent.addView(inflate);
//
//                    myProgress.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            ToastUtil.showMessage("预订时间段" + ydHour + ":" + ydStartTime + "-" + ydHour + ":" + ydEndTime);
//                        }
//                    });
//
//                } catch (Exception e) {
//                    LogUtils.e("e==" + e);
//                }
//            }
//        } else {
//            //整天没有预订
//            for (int i = 0; i < 24; i++) {
//                View inflate = LayoutInflater.from(context).inflate(R.layout.include_item_yd_row, null);
//                MyProgress myProgress = (MyProgress) inflate.findViewById(R.id.mp_progress);
//                myProgress.setMaxProgress(60);
//                myProgress.setStardEndProgress(0, 0);
//                viewHolder.llTableContent.addView(inflate);
//
//                myProgress.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ToastUtil.showMessage("没有预订");
//                    }
//                });
//            }
//        }

    }


    static class ViewHolder extends LvBaseViewHolder {
        @BindView(R.id.rowl_content)
        RowItmeViewLayout rowlContent;
        @BindView(R.id.ll_table_content)
        CheckableLinearLayout llTableContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
