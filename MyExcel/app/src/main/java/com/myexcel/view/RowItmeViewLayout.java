package com.myexcel.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.myexcel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：
 * 创建人：邹安富
 * 创建时间：2018/3/5
 * 详细说明：
 */
public class RowItmeViewLayout extends LinearLayout {
    private static final String TAG = "RowItmeViewLayout";
    private List<MyProgress> myProgressList = new ArrayList<>();

    public RowItmeViewLayout(Context context) {
        super(context);
    }

    public RowItmeViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public RowItmeViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        this.setOrientation(HORIZONTAL);
        //整天没有预订
        for (int i = 0; i < 24; i++) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.include_item_yd_row, null);
            MyProgress myProgress = (MyProgress) inflate.findViewById(R.id.mp_progress);
            myProgress.setMaxProgress(60);
            myProgress.setStardEndProgress(0, 0);
            this.addView(inflate);
//            myProgress.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtil.showMessage("没有预订");
//                }
//            });
            myProgressList.add(myProgress);
        }
    }

    /**
     * 清楚所有的进度展示
     */
    public void clearAllProgress() {
        for (int i = 0; i < myProgressList.size(); i++) {
            myProgressList.get(i).clearProgress();
        }
    }


    /**
     * 设置进度条
     */
    public void setProgress(String progressStr) {
//        String str = "09:10-10:20";
        try {
            String[] split = progressStr.split("-");
            String startHour = split[0].split(":")[0];
            String startMinute = split[0].split(":")[1];
            String endHour = split[1].split(":")[0];
            String endMinute = split[1].split(":")[1];


            if (TextUtils.equals(startHour, endHour)) {
                //相等
                String index;
                if (startHour.startsWith("0") && !startHour.endsWith("0")) {
                    index = startHour.replace("0", "");
                } else {
                    index = startHour;
                }

                MyProgress myProgressStart = myProgressList.get(Integer.parseInt(index));
                myProgressStart.setStardEndProgress(Integer.parseInt(startMinute), Integer.parseInt(endMinute));

            } else {
                //不相等
                int indexStart;
                int indexEnd;
                if (startHour.startsWith("0") && !startHour.endsWith("0")) {
                    indexStart = Integer.parseInt(startHour.replace("0", ""));
                } else {
                    indexStart = Integer.parseInt(startHour);
                }

                if (endHour.startsWith("0")) {
                    indexEnd = Integer.parseInt(endHour.replace("0", ""));
                } else {
                    indexEnd = Integer.parseInt(endHour);
                }

                for (int i = indexStart; i <= indexEnd; i++) {
                    MyProgress myProgressStart = myProgressList.get(i);
                    myProgressStart.setStardEndProgress(0, 60);
                    if (i == indexStart) {
                        myProgressStart.setStardEndProgress(Integer.parseInt(startMinute), 60);
                    }
                    if (i == indexEnd) {
                        myProgressStart.setStardEndProgress(0, Integer.parseInt(endMinute));
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "setProgress: e==" + e.toString());
        }
    }


}
