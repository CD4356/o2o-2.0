package com.cd.o2o.entity;

import java.util.Date;

/**
 * 顾客消费的商品映射（即哪个店铺的哪个商品在哪天的销量是多少）
 */
public class ProductSellDaily {
    /**
     * 为什么属性使用引用类型而不是基本类型？
     * 基本类型为空时,会赋一个默认值,而引用类型不会,为空就是null,不会赋默认值
     */
    //商品日销量id
    private Long productSellDailyId;
    //销量
    private Integer total;
    //时间：精确到天
    private Date createTime;
    //店铺实体类信息
    private Shop shop;
    //商品实体类信息
    private Product product;

    public Long getProductSellDailyId() {
        return productSellDailyId;
    }

    public void setProductSellDailyId(Long productSellDailyId) {
        this.productSellDailyId = productSellDailyId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
