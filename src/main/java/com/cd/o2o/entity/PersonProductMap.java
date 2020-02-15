package com.cd.o2o.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 顾客消费的商品映射（即哪个顾客在哪个店铺消费了那件商品，并且得到了多少积分）
 */
public class PersonProductMap {
    /**
     * 为什么属性使用引用类型而不是基本类型？
     * 基本类型为空时,会赋一个默认值,而引用类型不会,为空就是null,不会赋默认值
     */

    //主键Id
    private Long personProductId;
    //消费商品所得积分
    private Integer point;
    //顾客信息实体类
    private Person person;
    //商品信息实体类
    private Product product;
    //店铺信息实体类
    private Shop shop;
    //创建时间
    /* 使用@JsonFormat注解格式化日期，才能按指定格式在前端页面，具体参考：https://www.jb51.net/article/144235.htm */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    public Long getPersonProductId() {
        return personProductId;
    }

    public void setPersonProductId(Long personProductId) {
        this.personProductId = personProductId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
