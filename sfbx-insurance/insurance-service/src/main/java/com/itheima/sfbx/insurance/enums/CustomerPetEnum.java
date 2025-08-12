package com.itheima.sfbx.insurance.enums;

import com.itheima.sfbx.framework.commons.enums.basic.IBaseEnum;

/**
* @ClassName CustomerPetEnum.java
* @Description 客户宠物枚举
*/

public enum CustomerPetEnum implements IBaseEnum {

    PAGE_FAIL(53001, "查询客户宠物分页失败"),
    LIST_FAIL(53002, "查询客户宠物列表失败"),
    FIND_ONE_FAIL(53003, "查询客户宠物对象失败"),
    SAVE_FAIL(53004, "保存客户宠物失败"),
    UPDATE_FAIL(53005, "修改客户宠物失败"),
    DEL_FAIL(53006, "删除客户宠物失败")
    ;

    private Integer code;

    private String msg;

    CustomerPetEnum(Integer code, String msg) {
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
