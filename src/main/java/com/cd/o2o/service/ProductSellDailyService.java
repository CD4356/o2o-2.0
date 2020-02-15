package com.cd.o2o.service;

import com.cd.o2o.entity.ProductSellDaily;

import java.util.Date;
import java.util.List;

public interface ProductSellDailyService {

    /**
     * 每日对所有店铺的商品销量进行统计
     */
    void dailyCalculate();


    /**
     * 根据查询条件返回商品日销统计列表
     * @return
     */
    List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime, Date endTime);

}
