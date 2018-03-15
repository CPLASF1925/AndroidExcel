package com.myexcel.bean;

import java.util.List;

/**
 * 名称：
 * 创建人：邹安富
 * 创建时间：2018/3/3
 * 详细说明：
 */
public class TableContentBean {
    /**
     * 是否有预订状态 1有预订 0未预订
     */
    private int ydStatc;


    private List<HourData> hourData;

    public int getYdStatc() {
        return ydStatc;
    }

    public void setYdStatc(int ydStatc) {
        this.ydStatc = ydStatc;
    }



    public List<HourData> getHourData() {
        return hourData;
    }

    public void setHourData(List<HourData> hourData) {
        this.hourData = hourData;
    }

    public static class HourData {
        /**
         * 预订开始时间
         */
        private String ydStartTime;

        /**
         * 预订结束时间
         */
        private String ydEndTime;

        /**
         * 时间段
         */
        private String ydHour;

        private String ydTimeSlot;


        public String getYdStartTime() {
            return ydStartTime;
        }

        public void setYdStartTime(String ydStartTime) {
            this.ydStartTime = ydStartTime;
        }

        public String getYdEndTime() {
            return ydEndTime;
        }

        public void setYdEndTime(String ydEndTime) {
            this.ydEndTime = ydEndTime;
        }

        public String getYdHour() {
            return ydHour;
        }

        public void setYdHour(String ydHour) {
            this.ydHour = ydHour;
        }

        public String getYdTimeSlot() {
            return ydTimeSlot;
        }

        public void setYdTimeSlot(String ydTimeSlot) {
            this.ydTimeSlot = ydTimeSlot;
        }
    }


}
