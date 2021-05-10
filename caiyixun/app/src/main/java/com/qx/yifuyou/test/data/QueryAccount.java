package com.qx.yifuyou.test.data;

import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class QueryAccount implements Runnable
{
    private Quest quest;
    private Handler handler;
    private User user;

    public QueryAccount(Quest quest, Handler handler)
    {
        this.handler = handler;
        this.quest = quest;
    }

    public User getUser()
    {
        return user;
    }

    @Override
    public void run()
    {
        try
        {
            String host = "103.46.128.21";
            int port = 14646;
            // 与服务端建立连接
            Socket socket = null;
            socket = new Socket(host, port);

            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            os.writeObject(quest);
            os.flush();
            Object obj = is.readObject();

            if (obj != null)
            {
                user = (User) obj;
                socket.close();
            }
            handler.sendEmptyMessage(0);
        } catch (Exception e)
        {

        }

    }
}
