package com.itheima.shop;

public class NormalMemberDiscount implements DiscountStrategy{
    @Override
    public double calculate(double price) {
        //普通会员折扣
        return price * 0.95;
    }
}
