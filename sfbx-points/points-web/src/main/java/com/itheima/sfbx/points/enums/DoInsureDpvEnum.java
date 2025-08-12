package com.itheima.sfbx.points.enums;

import com.itheima.sfbx.framework.commons.enums.basic.IBaseEnum;

/**
* @ClassName DoInsureDpvEnum.java
* @Description 日投保访问页面量枚举
*/

public enum DoInsureDpvEnum implements IBaseEnum {

    PAGE_FAIL(53001, "查询日投保访问页面量分页失败"),
    LIST_FAIL(53002, "查询日投保访问页面量列表失败"),
    FIND_ONE_FAIL(53003, "查询日投保访问页面量对象失败"),
    SAVE_FAIL(53004, "保存日投保访问页面量失败"),
    UPDATE_FAIL(53005, "修改日投保访问页面量失败"),
    DEL_FAIL(53006, "删除日投保访问页面量失败")
    ;

    private Integer code;

    private String msg;

    DoInsureDpvEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
