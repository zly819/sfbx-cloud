package com.itheima.sfbx.framework.mybatisplus.handler;

import com.itheima.sfbx.framework.mybatisplus.constant.MybatisPlusConstant;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;

/**
 * @ClassName DataSecurityLineHandler.java
 * @Description 数据权限处理器（ DeptNo 行级 ）
 */
public interface DeptNoLineHandler {

    /**
     * 获取数据权限值表达式
     * @return 数据权限值表达式
     */
    ExpressionList getDeptNoList();

    /**
     * 获取数据权限字段名
     * 默认字段名叫: data_security_id
     * @return 数据权限字段名
     */
    default String getDeptNoColumn() {
        return MybatisPlusConstant.DATA_DEPT_NO;
    }

    /**
     * 根据表名判断是否忽略拼接数据权限条件
     * 默认都要进行解析并拼接数据权限条件
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接数据权限条件
     */
    default boolean ignoreTable(String tableName) {
        return false;
    }
}
