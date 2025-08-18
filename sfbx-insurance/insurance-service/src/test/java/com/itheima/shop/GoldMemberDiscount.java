package com.itheima.shop;

public class GoldMemberDiscount implements DiscountStrategy{
    @Override
    public double calculate(double price) {
        //金牌会员折扣
        return price * 0.85;
    }
}
