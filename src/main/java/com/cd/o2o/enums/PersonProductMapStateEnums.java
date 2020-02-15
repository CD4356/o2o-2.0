package com.cd.o2o.enums;

/**
 * 枚举类
 */
public enum PersonProductMapStateEnums {

    SUCCESS(1, "操作成功！"),
    INNER_ERROR(-1001,"内部系统错误"),
    NULL_PERSONPRODUCT_ID(-1002,"personProductId为空"),
    NULL_INFO(-1003,"传入的信息为空");

    //结果状态
    private int state;
    //状态标识
    private String stateInfo;

    PersonProductMapStateEnums(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
