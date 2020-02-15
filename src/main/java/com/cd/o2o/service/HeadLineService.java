package com.cd.o2o.service;

import com.cd.o2o.entity.HeadLine;

import java.util.List;

public interface HeadLineService {

    /**
     * 获取头条列表
     *
     * @return
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition);




}
