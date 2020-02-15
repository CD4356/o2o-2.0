package com.cd.o2o.dto;

import com.cd.o2o.entity.PersonProductMap;
import com.cd.o2o.enums.PersonProductMapStateEnums;

import java.util.List;

public class PersonProductMapExecution {

    //结果状态
    private int state;
    //状态标识
    private String stateInfo;
    //数量
    private int count;
    //消费记录
    private PersonProductMap personProductMap;
    //消费记录列表
    private List<PersonProductMap> personProductMapList;

    public PersonProductMapExecution() {
    }

    //获取消费记录/列表失败，返回的构造器
    public PersonProductMapExecution(PersonProductMapStateEnums personProductMapStateEnums) {
        this.state = personProductMapStateEnums.getState();
        this.stateInfo = personProductMapStateEnums.getStateInfo();
    }

    //获取消费记录成功，返回的构造器
    public PersonProductMapExecution(PersonProductMapStateEnums personProductMapStateEnums, PersonProductMap personProductMap) {
        this.state = personProductMapStateEnums.getState();
        this.stateInfo = personProductMapStateEnums.getStateInfo();
        this.personProductMap = personProductMap;
    }

    //获取消费记录列表成功，返回的构造器
    public PersonProductMapExecution(PersonProductMapStateEnums personProductMapStateEnums, List<PersonProductMap> personProductMapList) {
        this.state = personProductMapStateEnums.getState();
        this.stateInfo = personProductMapStateEnums.getStateInfo();
        this.personProductMapList = personProductMapList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PersonProductMap getPersonProductMap() {
        return personProductMap;
    }

    public void setPersonProductMap(PersonProductMap personProductMap) {
        this.personProductMap = personProductMap;
    }

    public List<PersonProductMap> getPersonProductMapList() {
        return personProductMapList;
    }

    public void setPersonProductMapList(List<PersonProductMap> personProductMapList) {
        this.personProductMapList = personProductMapList;
    }
}
