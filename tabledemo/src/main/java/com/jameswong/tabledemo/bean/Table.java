package com.jameswong.tabledemo.bean;

import java.util.List;

/**
 * ****************************************************
 * author: jameswong
 * created on: 17/09/30 上午11:43
 * e-mail: PassionateWsj@outlook.com
 * name:
 * desc:
 * ****************************************************
 */

public class Table {

    private List<Head> head;
    private List<MainData> main_data;

    public List<Head> getHead() {
        return head;
    }

    public void setHead(List<Head> head) {
        this.head = head;
    }

    public List<MainData> getMain_data() {
        return main_data;
    }

    public void setMain_data(List<MainData> main_data) {
        this.main_data = main_data;
    }

    public static class Head {
        /**
         * value : 大区-门店-商行
         * defaultIndex : 0
         * show : true
         */

        private String value;
        private int defaultIndex;
        private boolean show;
        private boolean isKeyColumn = false;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getDefaultIndex() {
            return defaultIndex;
        }

        public void setDefaultIndex(int defaultIndex) {
            this.defaultIndex = defaultIndex;
        }

        public boolean isShow() {
            return show;
        }

        public void setShow(boolean show) {
            this.show = show;
        }

        public boolean isKeyColumn() {
            return isKeyColumn;
        }

        public void setKeyColumn(boolean keyColumn) {
            isKeyColumn = keyColumn;
        }
    }

    public static class MainData {
        /**
         * area : 黑龙江区
         * store : 黑龙江万达店
         * category : 加工
         * data : [{"value":"黑龙江区黑龙江万达店加工","headIndex":0,"color":0},{"value":"2.9","headIndex":1,"color":0},{"value":"2.7","headIndex":2,"color":0},{"value":"67.0","headIndex":3,"color":0},{"value":"-20.8%","headIndex":4,"color":0},{"value":"-12.1%","headIndex":5,"color":0},{"value":"3.8%","headIndex":6,"color":0},{"value":"5.4%","headIndex":7,"color":0},{"value":"0.4","headIndex":8,"color":0},{"value":"0.4","headIndex":9,"color":0},{"value":"10.2","headIndex":10,"color":0},{"value":"-15.4%","headIndex":11,"color":0},{"value":"-10.0%","headIndex":12,"color":0},{"value":"2.9%","headIndex":13,"color":0},{"value":"3.9%","headIndex":14,"color":0},{"value":"1757","headIndex":15,"color":0},{"value":"1608.9","headIndex":16,"color":0},{"value":"40222","headIndex":17,"color":0},{"value":"-28.0%","headIndex":18,"color":0},{"value":"-19.0%","headIndex":19,"color":0}]
         */

        private String area;
        private String store;
        private String category;
        private List<DataBean> data;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getStore() {
            return store;
        }

        public void setStore(String store) {
            this.store = store;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * value : 黑龙江区黑龙江万达店加工
             * headIndex : 0
             * color : 0
             */

            private String value;
            private int headIndex;
            private int color;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public int getHeadIndex() {
                return headIndex;
            }

            public void setHeadIndex(int headIndex) {
                this.headIndex = headIndex;
            }

            public int getColor() {
                return color;
            }

            public void setColor(int color) {
                this.color = color;
            }
        }
    }
}
