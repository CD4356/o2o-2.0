package com.cd.o2o.dto;

import java.util.HashSet;

/**
 * 配合Echarts的xAxis组件使用
 */
public class EchartXAxis {

    private String type = "category";

    //HashSet有去重复的特性
    private HashSet<String> date;

    public String getType() {
        return type;
    }

    public HashSet<String> getDate() {
        return date;
    }

    public void setDate(HashSet<String> date) {
        this.date = date;
    }
}
