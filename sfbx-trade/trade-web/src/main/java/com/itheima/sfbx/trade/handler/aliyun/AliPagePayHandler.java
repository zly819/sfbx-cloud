package com.itheima.sfbx.trade.handler.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.itheima.sfbx.trade.handler.PagePayHandler;
import com.itheima.sfbx.trade.client.alipay.Factory;
import com.itheima.sfbx.framework.commons.constant.basic.SuperConstant;
import com.itheima.sfbx.framework.commons.constant.trade.TradeConstant;
import com.itheima.sfbx.framework.commons.enums.trade.TradeEnum;
import com.itheima.sfbx.framework.commons.exception.ProjectException;
import com.itheima.sfbx.trade.pojo.Trade;
import com.itheima.sfbx.framework.commons.utils.BeanConv;
import com.itheima.sfbx.framework.commons.utils.EmptyUtil;
import com.itheima.sfbx.framework.commons.utils.ExceptionsUtil;
import com.itheima.sfbx.framework.commons.dto.trade.TradeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName AliPagePayHandler.java
 * @Description PC网页支付处理实现
 */
@Slf4j
@Component
public class AliPagePayHandler  extends AliCommonPayHandler implements PagePayHandler {


    @Override
    public TradeVO pageTrade(TradeVO tradeVO) {
        //1、交易前置处理：检测交易单参数
        Boolean flag = beforePayHandler.checkeCreateTrade(tradeVO);
        if (!flag){
            throw new ProjectException(TradeEnum.TRAD_PAY_FAIL);
        }
        //2、交易前置处理：幂等性处理
        beforePayHandler.idempotentCreateTrade(tradeVO);
        //3、获得支付宝配置文件
        Config config = aliPayConfig.config(tradeVO.getCompanyNo());
        //4、配置如果为空，抛出异常
        if (EmptyUtil.isNullOrEmpty(config)){
            throw new ProjectException(TradeEnum.CONFIG_ERROR);
        }
        //5、使用配置
        Factory factory = new Factory();
        factory.setOptions(config);
        try {
            //6、调用支付宝API
            AlipayTradePagePayResponse pagePayResponse = factory.Page().pay(tradeVO.getMemo(),
                String.valueOf(tradeVO.getTradeOrderNo()),
                String.valueOf(tradeVO.getTradeAmount()),
                tradeVO.getReturnUrl());
            //7、受理结果【只表示请求是否成功，而不是支付是否成功】
            boolean isSuccess = ResponseChecker.success(pagePayResponse);
            if (isSuccess&&!EmptyUtil.isNullOrEmpty(pagePayResponse.getBody())){
                tradeVO.setPlaceOrderCode(TradeConstant.ALI_SUCCESS_CODE);
                tradeVO.setPlaceOrderMsg(TradeConstant.ALI_SUCCESS_MSG);
                tradeVO.setPlaceOrderJson(pagePayResponse.getBody());
                tradeVO.setTradeState(TradeConstant.TRADE_WAIT);
                Trade trade = BeanConv.toBean(tradeVO, Trade.class);
                tradeService.save(trade);
                return BeanConv.toBean(trade, TradeVO.class);
            }else {
                log.error("网关：支付宝网页支付统一下单创建：{},结果：{}", tradeVO.getTradeOrderNo(),
                    JSONObject.toJSONString(pagePayResponse));
                throw new RuntimeException("网关：支付宝网页支付统一下单创建失败！");
            }
        } catch (Exception e) {
            log.error("支付宝网页支付统一下单创建失败：{}", ExceptionsUtil.getStackTraceAsString(e));
            throw new RuntimeException("支付宝网页支付统一下单创建失败!");
        }
    }
}
