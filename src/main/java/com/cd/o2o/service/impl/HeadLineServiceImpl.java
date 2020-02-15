package com.cd.o2o.service.impl;

import com.cd.o2o.cache.JedisUtil;
import com.cd.o2o.dao.HeadLineDao;
import com.cd.o2o.entity.HeadLine;
import com.cd.o2o.service.HeadLineService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service("headLineService")
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil jedisUtil;
    //指定key前缀
    private static final String HEADLINE = "headline";


    /**
     * 获取头条列表，优先从Redis缓存数据库中获取,若缓存中没有再从MySQL数据库中获取
     *
     * @param headLineCondition
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED) // 开启管理
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) {
        //定义key前缀
        String key = HEADLINE;
        //定义数据接收对象
        List<HeadLine> headLineList = null;
        //定义Jackson数据转换操作类
        ObjectMapper objectMapper = new ObjectMapper();
        //拼接所需的key
        if(headLineCondition!=null && headLineCondition.getEnableStatus()!=null){
            key = key +"_"+ headLineCondition.getEnableStatus();
        }
        //判断key是否存在
        if(!jedisUtil.exists(key)){
            //从mysql数据库中查询所需数据
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            String jsonString = null;
            try {
                //将从数据库中取出的List<HeadLine>实体类集合类型数据转化成String类型数据
                jsonString = objectMapper.writeValueAsString(headLineList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                //抛出RuntimeException才能回滚事务
                throw new RuntimeException(e.getMessage());
            }
            //将转化后的String类型数据保存进redis中指定的key里
            jedisUtil.set(key,jsonString);
        }else {
            //若key存在，则从redis中取出key对应的数据
            String jsonString = jedisUtil.get(key);
            //指定string数据要转化成的集合类型
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class,HeadLine.class);
            try {
                //将key中的String类型的value值转化成实体类集合
                headLineList = objectMapper.readValue(jsonString,javaType);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        //将查询到的头条列表返回
        return headLineList;
    }

}
