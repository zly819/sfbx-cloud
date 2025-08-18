package com.itheima.bank;

public class FacadeTest {
    public static void main(String[] args) {
        //开户和开信用卡；使用门面模式
        BankFacade bankFacade = new BankFacade();
        bankFacade.openAccountAndCreditCard();
    }
}
