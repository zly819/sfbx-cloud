package com.itheima.sfbx.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.sfbx.security.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description：客户表Mapper接口
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

}
