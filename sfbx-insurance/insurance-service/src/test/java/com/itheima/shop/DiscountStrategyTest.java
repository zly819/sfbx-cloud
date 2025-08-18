package com.itheima.shop;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DiscountStrategyTest {
    static Map<String, DiscountStrategy> strategyMap = new HashMap<String, DiscountStrategy>();
    //初始化策略map
    static {
        strategyMap.put("normal", new NormalMemberDiscount());
        strategyMap.put("silver", new SilverMemberDiscount());
        strategyMap.put("gold", new GoldMemberDiscount());
    }
    public static void main(String[] args) {
        while (true){
            //输入会员类型
            System.out.println("请输入会员类型：normal/silver/gold");
            //获取从键盘输入的内容类型
            Scanner scanner = new Scanner(System.in);
            String memberType = scanner.next();
            System.out.println("请输入商品价格：");
            double price = scanner.nextDouble();
            DiscountStrategy discountStrategy = strategyMap.get(memberType);
            System.out.println("折扣价为：" + discountStrategy.calculate(price));
        }
    }
}
