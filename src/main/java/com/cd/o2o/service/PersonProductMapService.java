package com.cd.o2o.service;

import com.cd.o2o.dto.PersonProductMapExecution;
import com.cd.o2o.entity.PersonProductMap;


public interface PersonProductMapService {

    /**
     * 根据查询条件分页返回用户购买商品的记录列表
     * @param personProductMapCondition 查询条件
     * @param pageIndex 页码（第几页）
     * @param pageSize 页宽（一页有多少行）
     * @return
     */
    PersonProductMapExecution listPersonProductMap(PersonProductMap personProductMapCondition,
                                                   Integer pageIndex, Integer pageSize);

    /**
     * 添加顾客消费记录
     * @param personProductMap
     * @return
     */
    PersonProductMapExecution addPersonProductMap(PersonProductMap personProductMap);
}
