package com.itheima.sfbx.trade.face.impl;

import com.itheima.sfbx.trade.adapter.PagePayAdapter;
import com.itheima.sfbx.framework.commons.constant.trade.TradeCacheConstant;
import com.itheima.sfbx.framework.commons.enums.trade.TradeEnum;
import com.itheima.sfbx.framework.commons.exception.ProjectException;
import com.itheima.sfbx.trade.face.PagePayFace;
import com.itheima.sfbx.framework.commons.utils.ExceptionsUtil;
import com.itheima.sfbx.framework.commons.dto.trade.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName PagePayFaceImpl.java
 * @Description PC网页支付Face接口实现
 */
@Slf4j
@Component
public class PagePayFaceImpl implements PagePayFace {

    @Autowired
    PagePayAdapter pagePayAdapter;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public TradeVO pageTrade(TradeVO tradeVO) {
        //1、对交易订单加锁
        Long productOrderNo = tradeVO.getProductOrderNo();
        String key = TradeCacheConstant.CREATE_PAY + productOrderNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            if (lock.tryLock(TradeCacheConstant.REDIS_WAIT_TIME, TimeUnit.SECONDS)){
                return pagePayAdapter.pageTrade(tradeVO);
            }else {
                throw new ProjectException(TradeEnum.TRAD_PAY_FAIL);
            }
        } catch (Exception e) {
            log.error("统一收单线下交易预创建异常:{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new ProjectException(TradeEnum.TRAD_PAY_FAIL);
        }finally {
            //释放锁
            lock.unlock();
        }

    }
}
