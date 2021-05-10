package com.qx.yifuyou.test.data;


import java.io.Serializable;

public class Quest implements Serializable
{
    private int type;
    private static final long serialVersionUID = 12345L;

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    private String msg;
    private String password;


    public Quest(int type, String msg)
    {
        this.type = type;
        this.msg = msg;
    }

    public Quest(int type, String msg, String password)
    {
        this.type = type;
        this.msg = msg;
        this.password = password;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
