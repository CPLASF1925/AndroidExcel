package com.myexcel;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myexcel.adapter.TableColumnAdapter;
import com.myexcel.adapter.TableContentAdapter;
import com.myexcel.bean.TableContentBean;
import com.myexcel.utils.AppDensityUtil;
import com.myexcel.utils.ToastUtil;
import com.myexcel.view.MyHorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 名称：图表主类
 * 创建人：邹安富
 * 创建时间：2018/3/15
 * 详细说明：
 */
public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_table_title)
    TextView tvTableTitle;
    @BindView(R.id.rl_table_title)
    RelativeLayout rlTableTitle;
    @BindView(R.id.ll_row_content)
    LinearLayout llRowContent;
    @BindView(R.id.mhsc_row)
    MyHorizontalScrollView mhscRow;
    @BindView(R.id.lv_column)
    ListView lvColumn;
    @BindView(R.id.lv_content)
    ListView lvContent;
    @BindView(R.id.mhsc_content)
    MyHorizontalScrollView mhscContent;
    @BindView(R.id.srl_table_content)
    SwipeRefreshLayout srlTableContent;

    public Handler mHandler = new Handler();

    private List<TableContentBean> contentList = new ArrayList<>();

    private TableContentAdapter contentAdapter;
    private TableColumnAdapter columnAdapter;

    /**
     * 两个监听器，分别控制水平和垂直方向上的同步滑动
     */
    private HorizontalScrollListener horizontalScrollListener = new HorizontalScrollListener();
    private VerticalScrollListener verticalScrollListener = new VerticalScrollListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initContentDataList();
        columnAdapter = new TableColumnAdapter(this, getColumnDataList());
        contentAdapter = new TableContentAdapter(this, contentList);
        lvColumn.setAdapter(columnAdapter);
        lvContent.setAdapter(contentAdapter);
        initRowLayou();
        mhscRow.setOnHorizontalScrollListener(horizontalScrollListener);
        mhscContent.setOnHorizontalScrollListener(horizontalScrollListener);
        lvContent.setOnScrollListener(verticalScrollListener);
        lvColumn.setOnScrollListener(verticalScrollListener);
        mhscContent.setHandler(mHandler);
        mhscContent.setOnScrollStateChangedListener(new MyHorizontalScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyHorizontalScrollView.ScrollType scrollType) {
//                LogUtils.e("scrollType==" + scrollType);
                if (scrollType == MyHorizontalScrollView.ScrollType.IDLE) {
                    int firstVisiblePosition = lvContent.getFirstVisiblePosition();
//                    LogUtils.e("firstVisiblePosition==" + firstVisiblePosition);
                    if (firstVisiblePosition == 0) {
                        srlTableContent.setEnabled(true);
                    }
                } else {
                    srlTableContent.setEnabled(false);
                }
            }
        });
        srlTableContent.setColorSchemeColors(getResources().getColor(R.color.main_color));
        srlTableContent.setOnRefreshListener(this);
        //设置横向初始位置，延迟500，不然无效
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mhscRow.scrollTo(topItmeWidth * 8, 0);
            }
        }, 500);

    }


    private int topItmeWidth;
    private int topItmeHeight;

    private void initRowLayou() {
        topItmeWidth = AppDensityUtil.dip2px(this, 100);
        topItmeHeight = AppDensityUtil.dip2px(this, 35);
        List<String> rowDataList = getRowDataList();
        for (int i = 0; i < rowDataList.size(); i++) {
            RelativeLayout inflate = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.item_table_row_view, null);
            final TextView tvRowTxt = (TextView) inflate.findViewById(R.id.tv_row_txt);
            tvRowTxt.setText(rowDataList.get(i));
            tvRowTxt.setTag(i);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(topItmeWidth, topItmeHeight);
//            tvRowTxt.setWidth(topItmeWidth);//设置宽度
//            tvRowTxt.setHeight(topItmeHeight);//设置宽度
            tvRowTxt.getPaint().setFakeBoldText(true);
            tvRowTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showMessage(tvRowTxt.getTag() + "");
                }
            });
            llRowContent.addView(inflate, layoutParams);
        }
    }

    /**
     * 初始化content数据
     */
    private void initContentDataList() {
        for (int i = 0; i < 10; i++) {
            List<TableContentBean.HourData> hourDataList = new ArrayList<>();
            TableContentBean tableContentBean = new TableContentBean();
            tableContentBean.setYdStatc(0);

            if (i == 0) {
                tableContentBean.setYdStatc(1);
            }
            if (i == 2) {
                tableContentBean.setYdStatc(1);
            }
            if (i == 4) {
                tableContentBean.setYdStatc(1);
            }
            if (i == 7) {
                tableContentBean.setYdStatc(1);
            }
            if (i == 9) {
                tableContentBean.setYdStatc(1);
            }

            for (int j = 0; j < 4; j++) {
                TableContentBean.HourData hourData = new TableContentBean.HourData();
                if (i == 0) {
                    if (j == 0) {
                        hourData.setYdTimeSlot("00:10-00:40");
                    }
                    if (j == 1) {
                        hourData.setYdTimeSlot("01:10-10:20");
                    }
                }

                if (i == 2) {
                    if (j == 0) {
                        hourData.setYdTimeSlot("01:10-02:30");
                    }
                    if (j == 1) {
                        hourData.setYdTimeSlot("04:00-05:50");
                    }

                    if (j == 2) {
                        hourData.setYdTimeSlot("07:10-08:00");
                    }
                    if (j == 3) {
                        hourData.setYdTimeSlot("15:10-17:05");
                    }
                }
                if (i == 4) {
                    if (j == 0) {
                        hourData.setYdTimeSlot("01:10-02:30");
                    }
                    if (j == 1) {
                        hourData.setYdTimeSlot("10:10-15:40");
                    }
                    if (j == 2) {
                        hourData.setYdTimeSlot("07:10-08:00");
                    }
                    if (j == 3) {
                        hourData.setYdTimeSlot("20:00-22:20");
                    }
                }

                if (i == 7) {
                    if (j == 0) {
                        hourData.setYdTimeSlot("07:10-10:40");
                    }
                    if (j == 1) {
                        hourData.setYdTimeSlot("16:10-18:20");
                    }
                }
                if (i == 9) {
                    if (j == 0) {
                        hourData.setYdTimeSlot("00:10-20:40");
                    }
                    if (j == 1) {
//                        hourData.setYdTimeSlot("01:10-10:20");
                    }
                }
                hourDataList.add(hourData);
            }
            tableContentBean.setHourData(hourDataList);
            contentList.add(tableContentBean);
        }
    }


    /**
     * 生成一份横向表头的内容
     *
     * @return List<String>
     */
    private List<String> getRowDataList() {
        List<String> rowDataList = new ArrayList<>();
        rowDataList.add("00:00");
        rowDataList.add("01:00");
        rowDataList.add("02:00");
        rowDataList.add("03:00");
        rowDataList.add("04:00");
        rowDataList.add("05:00");
        rowDataList.add("06:00");
        rowDataList.add("07:00");
        rowDataList.add("08:00");
        rowDataList.add("09:00");
        rowDataList.add("10:00");
        rowDataList.add("11:00");
        rowDataList.add("12:00");
        rowDataList.add("13:00");
        rowDataList.add("14:00");
        rowDataList.add("15:00");
        rowDataList.add("16:00");
        rowDataList.add("17:00");
        rowDataList.add("18:00");
        rowDataList.add("19:00");
        rowDataList.add("20:00");
        rowDataList.add("21:00");
        rowDataList.add("22:00");
        rowDataList.add("23:00");
        return rowDataList;
    }


    public List<String> getColumnDataList() {
        List<String> columnDataList = new ArrayList<>();
        columnDataList.add("会议室1");
        columnDataList.add("会议室2");
        columnDataList.add("会议室3");
        columnDataList.add("会议室4");
        columnDataList.add("会议室5");
        columnDataList.add("会议室6");
        columnDataList.add("会议室7");
        columnDataList.add("会议室8");
        columnDataList.add("会议室9");
        columnDataList.add("会议室10");
        return columnDataList;
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                srlTableContent.setRefreshing(false);
            }
        }, 1000);
    }

    /**
     * HorizontalScrollView的滑动监听（水平方向同步控制）
     */
    private class HorizontalScrollListener implements MyHorizontalScrollView.OnHorizontalScrollListener {
        @Override
        public void onHorizontalScrolled(MyHorizontalScrollView view, int l, int t, int oldl, int oldt) {
            if (view == mhscContent) {
                mhscRow.scrollTo(l, t);
            } else {
                mhscContent.scrollTo(l, t);
            }
        }


    }


    /**
     * 两个ListView的滑动监听（垂直方向同步控制）
     */
    private class VerticalScrollListener implements AbsListView.OnScrollListener {

        int scrollState;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            this.scrollState = scrollState;
            if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                View subView = view.getChildAt(0);
                if (subView != null && view == lvContent) {
                    int top = subView.getTop();
                    int position = view.getFirstVisiblePosition();
                    lvColumn.setSelectionFromTop(position, top);
                } else if (subView != null && view == lvColumn) {
                    int top = subView.getTop();
                    int position = view.getFirstVisiblePosition();
                    lvContent.setSelectionFromTop(position, top);
                }
            }

            // 滑动事件冲突的解决：如果ListView的首条item的position != 0，即此时不再顶上，则将下拉刷新禁用
//            if (swipeRefreshEnable) {
            if (view.getFirstVisiblePosition() != 0 && srlTableContent.isEnabled()) {
                srlTableContent.setEnabled(false);
            }

            if (view.getFirstVisiblePosition() == 0) {
                srlTableContent.setEnabled(true);
            }
//            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            //判断滑动是否终止，以停止自动对齐，否则该方法会一直被调用，影响性能
            if (scrollState == SCROLL_STATE_IDLE) {
                return;
            }
            View subView = view.getChildAt(0);
            if (subView != null && view == lvContent) {
                int top = subView.getTop();
                lvColumn.setSelectionFromTop(firstVisibleItem, top);
            } else if (subView != null && view == lvColumn) {
                int top = subView.getTop();
                lvContent.setSelectionFromTop(firstVisibleItem, top);
            }
        }
    }

}
