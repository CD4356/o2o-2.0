package com.cd.o2o.service.impl;

import com.cd.o2o.dao.ProductSellDailyDao;
import com.cd.o2o.entity.ProductSellDaily;
import com.cd.o2o.service.ProductSellDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 定时任务类
 */
@Service("productSellDailyService")
public class ProductSellDailyServiceImpl implements ProductSellDailyService {

    @Autowired
    private ProductSellDailyDao productSellDailyDao;

    /**
     * 每日对所有店铺的商品销量进行统计（任务被触发时执行的方法）
     */
    @Override
    public void dailyCalculate() {
        //每天凌晨统计person_product_map中每个店铺卖出商品的日销量，存入product_sell_daily中
        productSellDailyDao.insertProductSellDaily();
        //每天凌晨将person_product_map中每个店铺没有卖出商品的销量值置为0，存入product_sell_daily中
        productSellDailyDao.insertDefaultProductSellDaily();
    }

    /**
     * 根据查询条件返回商品日销统计列表
     * @param productSellDailyCondition
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition, Date beginTime, Date endTime) {
        return productSellDailyDao.queryProductSellDailyList(productSellDailyCondition, beginTime, endTime);
    }

}
