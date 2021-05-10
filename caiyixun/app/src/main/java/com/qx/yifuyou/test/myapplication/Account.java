package com.qx.yifuyou.test.myapplication;

public class Account implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;
    private double account_number;
    private double account_blance;

    public Account(double account_number, double account_blance)
    {
        this.account_number = account_number;
        this.account_blance = account_blance;
    }

    public double getAccount_blance()
    {
        return account_blance;
    }

    public double getAccount_number()
    {
        return account_number;
    }
}
