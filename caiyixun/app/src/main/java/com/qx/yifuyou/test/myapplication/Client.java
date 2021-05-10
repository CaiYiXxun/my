package com.qx.yifuyou.test.myapplication;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Client
{
    public static void main(String args[]) throws Exception
    {
        // 要连接的服务端IP地址和端口
        String host = "103.46.128.21";
        int port = 14646;
        Socket socket = new Socket(host, port);
        ObjectInputStream is = null;
        is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        Object obj = is.readObject();
        Account ac;
        if (obj != null)
        {
            ac = (Account) obj;
            System.out.println("Account_number: " + ac.getAccount_number() + "\n" + "Account_blance: " + ac.getAccount_blance());
        }

        socket.close();
    }
}
