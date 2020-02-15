package com.cd.o2o.dao;

import com.cd.o2o.entity.ProductSellDaily;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductSellDailyDao {

    /**
     * 根据查询条件返回商品日销量统计列表
     * @param productSellDailyCondition
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ProductSellDaily> queryProductSellDailyList(@Param("productSellDailyCondition") ProductSellDaily productSellDailyCondition,
                                                     @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 统计平台所有商品的日销量
     * @return
     */
    int insertProductSellDaily();


    /**
     * 统计平台当天没有销量的商品，补全统计信息
     * @return
     */
    int insertDefaultProductSellDaily();
}
