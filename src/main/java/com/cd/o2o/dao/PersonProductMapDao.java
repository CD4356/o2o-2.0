package com.cd.o2o.dao;

import com.cd.o2o.entity.PersonProductMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonProductMapDao {

    /**
     * 根据查询条件分页返回用户购买商品的记录列表
     * @param personProductMapCondition 查询条件
     * @param rowIndex 行码（第几行）
     * @param pageSize 页宽（一页有多少行）
     * @return
     */
    List<PersonProductMap> queryPersonProductMapList(@Param("personProductMapCondition") PersonProductMap personProductMapCondition,
                                                     @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 根据查询条件返回用户购买商品的记录总数
     * @param personProductMapCondition 查询条件
     * @return 记录总数
     */
    int queryPersonProductMapCount(@Param("personProductMapCondition") PersonProductMap personProductMapCondition);


    /**
     * 添加用户购买商品的记录
     * @param personProductMap 查询条件
     * @return
     */
    int insertPersonProductMap(PersonProductMap personProductMap);

}
