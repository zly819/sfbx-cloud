package com.itheima.sfbx.trade.face;

import com.itheima.sfbx.framework.commons.dto.trade.TradeVO;
import com.itheima.sfbx.framework.commons.dto.trade.TradeVO;

/**
 * @ClassName AppPayFace.java
 * @Description 原生APP支付Face接口
 */
public interface AppPayFace {

    /***
     * @description 生成交易表单，渲染后自动跳转支付宝或者微信引导用户完成支付
     * @param tradeVO 订单单
     * @return  TradeVO对象中的placeOrderJson：阿里云form表单字符串、微信prepay_id标识
     */
    TradeVO appTrade(TradeVO tradeVO);
}
