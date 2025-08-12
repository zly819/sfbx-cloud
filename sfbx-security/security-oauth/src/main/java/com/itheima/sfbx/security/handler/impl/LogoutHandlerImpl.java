package com.itheima.sfbx.security.handler.impl;

import com.itheima.sfbx.security.handler.CacheTokenHandler;
import com.itheima.sfbx.security.handler.LogoutHandler;
import com.itheima.sfbx.framework.commons.dto.security.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName LogoutHandlerImpl.java
 * @Description 退出处理实现
 */
@Component
public class LogoutHandlerImpl implements LogoutHandler {

    @Autowired
    CacheTokenHandler cacheTokenHandler;

    @Override
    public Boolean logout(UserVO userVO) {

        return cacheTokenHandler.deleteToken(userVO);
    }
}
