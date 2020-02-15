package com.cd.o2o.service.impl;

import com.cd.o2o.dao.PersonProductMapDao;
import com.cd.o2o.dto.PersonProductMapExecution;
import com.cd.o2o.entity.PersonProductMap;
import com.cd.o2o.service.PersonProductMapService;
import com.cd.o2o.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("personProductMapService")
public class PersonProductMapServiceImpl implements PersonProductMapService {

    @Autowired
    private PersonProductMapDao personProductMapDao;

    /**
     * 根据查询条件分页返回用户购买商品的记录列表
     * @param personProductMapCondition 查询条件
     * @param pageIndex 页码（第几页）
     * @param pageSize 页宽（一页有多少行）
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PersonProductMapExecution listPersonProductMap(PersonProductMap personProductMapCondition, Integer pageIndex, Integer pageSize) {
        //判空操作
        if(personProductMapCondition!=null && pageIndex!=null && pageSize!=null){
            //将页码转化成行码
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex,pageSize);
            //分页获取商品记录列表
            List<PersonProductMap> personProductMapList = personProductMapDao.queryPersonProductMapList(personProductMapCondition,rowIndex,pageSize);
            //基于同样的查询条件，返回该查询条件下的商品总数
            int count = personProductMapDao.queryPersonProductMapCount(personProductMapCondition);
            //创建dto类对象
            PersonProductMapExecution personProductMapExecution = new PersonProductMapExecution();
            personProductMapExecution.setPersonProductMapList(personProductMapList);
            personProductMapExecution.setCount(count);
            return personProductMapExecution;
        }else {
            return null;
        }
    }


    /**
     * 添加顾客消费记录
     * @param personProductMap
     * @return
     */
    @Override
    public PersonProductMapExecution addPersonProductMap(PersonProductMap personProductMap) {
        return null;
    }

}
