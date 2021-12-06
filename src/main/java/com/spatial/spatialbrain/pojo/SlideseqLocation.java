/**
 * @author oyc
 * @Description:用户实体类
 * @date 2018/7/8 22:51
 */

package com.spatial.spatialbrain.pojo;

public class SlideseqLocation {

        private String barcodes;

        private double xcoord;

        private double ycoord;

        public double getXcoord() {
                return xcoord;
        }

        public double getYcoord() {
                return ycoord;
        }

        public String getBarcodes() {
                return barcodes;
        }

        public void setBarcodes(String barcodes) {
                this.barcodes = barcodes;
        }

        public void setXcoord(double xcoord) {
                this.xcoord = xcoord;
        }

        public void setYcoord(double ycoord) {
                this.ycoord = ycoord;
        }

    }
