package com.itheima.sfbx.security.hystrix;

import com.itheima.sfbx.framework.commons.basic.ResponseResult;
import com.itheima.sfbx.framework.commons.dto.security.CustomerVO;
import com.itheima.sfbx.framework.commons.dto.security.UserVO;
import com.itheima.sfbx.security.feign.CustomerFeign;
import org.springframework.stereotype.Component;

/**
 * @ClassName CustomerFeign.java
 * @Description CustomerFeign的Htstrix
 */
@Component
public class CustomerHtstrix implements CustomerFeign {


    @Override
    public UserVO usernameLogin(String username, String company) {
        return null;
    }

    @Override
    public UserVO mobileLogin(String mobile, String company) {
        return null;
    }

    @Override
    public UserVO wechatLogin(String openId, String company) {
        return null;
    }

    @Override
    public UserVO registerUser(CustomerVO customerVO) {
        return null;
    }

    @Override
    public Boolean resetPasswords(String customerId) {
        return null;
    }

    @Override
    public Boolean updateRealName(String userId, String name) {
        return null;
    }
}
