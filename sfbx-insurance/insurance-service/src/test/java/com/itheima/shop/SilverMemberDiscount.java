package com.itheima.shop;

public class SilverMemberDiscount implements DiscountStrategy{
    @Override
    public double calculate(double price) {
        //银牌会员折扣
        return price * 0.9;
    }
}
