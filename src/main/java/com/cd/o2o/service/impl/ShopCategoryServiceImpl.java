package com.cd.o2o.service.impl;

import com.cd.o2o.cache.JedisUtil;
import com.cd.o2o.dao.ShopCategoryDao;
import com.cd.o2o.entity.ShopCategory;
import com.cd.o2o.service.ShopCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("shopCategoryService")
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private JedisUtil jedisUtil;
    //指定key前缀
    private static final String SHOPCATEGORYLIST = "shopcategory";


    /**
     * 获取店铺类别列表，先从Redis缓存数据库中获取，若缓存中没有再从MySQL数据库中获取
     *
     * @param shopCategoryCondition
     * @return
     */
    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        //指定Redis的key前缀
        String key = SHOPCATEGORYLIST;
        //指定数据接收对象
        List<ShopCategory> shopCategoryList = null;
        //创建Jackson数据转换对象
        ObjectMapper objectMapper = new ObjectMapper();
        //拼接出所需的key
        if(shopCategoryCondition == null){
            //如果查询条件为空，则列出所有首页大类，即店铺为空的店铺类别
            key = key + "_firstlevel";
        }else if(shopCategoryCondition != null && shopCategoryCondition.getParent() != null){
            //若parentId不为空，则列出parentId下的所有子类别
            key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
        }else if(shopCategoryCondition != null){
            //列出所有的二级分类，不管它是属于哪个一级分类下的
            key = key + "_allsecondlevel";
        }

        //判断指定的key是否存在
        if(!jedisUtil.exists(key)){
            //若key不存在，则从MySQL数据库中取出相应数据
            shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            String jsonString = null;
            try {
                //将从数据库中取出的实体类集合类型数据转化成String类型数据
                jsonString = objectMapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                //抛出RuntimeException才能回滚事务
                throw new RuntimeException(e.getMessage());
            }
            //将转化后的String数据存入Redis指定的key中
            jedisUtil.set(key,jsonString);
        }else {
            //若key存在，则从redis中获取
            String jsonString = jedisUtil.get(key);
            //指定key的String类型的value值 要转化成的 实体类集合类型
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class,ShopCategory.class);
            try {
                //将String类型数据转化成List<ShopCategory>实体类集合
                shopCategoryList = objectMapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return shopCategoryList;
    }


}
