package com.itheima.bank;

public class BankFacade {
    //组合操作
    public void openAccountAndCreditCard() {
        openAccount();
        oauth();
        openCreditCard();
    }

    //开户
    public void openAccount() {
        //开户
        System.out.println("开户... 10000行代码。。。");
    }
    //认证
    public void oauth() {
        //认证
        System.out.println("开户需要认证... 流程多复杂。。。");
    }
    //开信用卡
    public void openCreditCard() {
        //开信用卡各种手续，流程判断... 20000行代码
        System.out.println("开信用卡各种手续，流程判断... 20000行代码");
    }
}
