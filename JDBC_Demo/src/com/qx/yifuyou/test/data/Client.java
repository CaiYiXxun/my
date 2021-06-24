package com.qx.yifuyou.test.data;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client
{
    public static void main(String args[]) throws Exception
    {
        // 要连接的服务端IP地址和端口
        /*String host = "127.0.0.1";
        int port = 8888;*/
        String host = "103.46.128.53";
        int port = 50488;
        // 与服务端建立连接
        Socket socket = new Socket(host, port);
        System.out.println("连接到服务器");
        int type = 1;
        String msg = "001";
        String password = "123456";
        //String msg="196272706235";
        ObjectInputStream is = null;
        ObjectOutputStream os = null;
        //Quest quest=new Quest(type,msg);
        Quest quest = new Quest(type, msg, password);
        os = new ObjectOutputStream(socket.getOutputStream());
        is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        os.writeObject(quest);
        os.flush();

        Object obj = is.readObject();
        User user;
        Package cn_package;
        Package[] phone_packages;
        int i, j, k;
        System.out.println("读取对象成功");
        if (obj != null)
        {
            System.out.println("传输成功");
            //1
            user = (User) obj;

            ArrayList<Package> send_packages = user.getSend_packages();
            ArrayList<Package> receive_package = user.getReceive_packages();
            for (i = 0; user.isCorrect() && i < send_packages.size(); i++)
            {
                System.out.println(send_packages.get(i).getAccountNumber());
                System.out.println(send_packages.get(i).getOrderNumber());
                System.out.println(send_packages.get(i).getRecipientAddress());
                for (j = 0; j < send_packages.get(i).getTransportRecordList().size(); j++)
                {
                    System.out.println(send_packages.get(i).getTransportRecordList().get(j).getStartTime());
                    System.out.println(send_packages.get(i).getTransportRecordList().get(j).getEndingTime());
                }
            }
//            for (i = 0; i < receive_package.length; i++) {
//                System.out.println(receive_package[i].getAccountNumber());
//                System.out.println(receive_package[i].getOrderNumber());
//                System.out.println(receive_package[i].getRecipientAddress());
//                for (j = 0; j < receive_package[i].getTransportRecordList().size(); j++) {
//                    System.out.println(receive_package[i].getTransportRecordList().get(j).getStartTime());
//                    System.out.println(receive_package[i].getTransportRecordList().get(j).getEndingTime());
//                }
//            }
            //2
//            cn_package = (Package) obj;
//            System.out.println(cn_package.getFreight());
//            System.out.println(cn_package.getAccountNumber());
//            System.out.println(cn_package.getOrderNumber());
//            System.out.println(cn_package.getRecipientAddress());
//            for (j = 0; j < cn_package.getTransportRecordList().size(); j++) {
//                System.out.println(cn_package.getTransportRecordList().get(j).getStartTime());
//                System.out.println(cn_package.getTransportRecordList().get(j).getEndingTime());
//            }
            //3
//            phone_packages=(Package[])obj;
//            for (i = 0; i < phone_packages.length; i++) {
//                System.out.println(phone_packages[i].getAccountNumber());
//                System.out.println(phone_packages[i].getOrderNumber());
//                System.out.println(phone_packages[i].getRecipientAddress());
//                for (j = 0; j < phone_packages[i].getTransportRecordList().size(); j++) {
//                    System.out.println(phone_packages[i].getTransportRecordList().get(j).getStartTime());
//                    System.out.println(phone_packages[i].getTransportRecordList().get(j).getEndingTime());
//                }
//            }

        }

//        InputStream inputStream = socket.getInputStream();
//        byte[] bytes = new byte[1024];
//        int len;
//        StringBuilder sb = new StringBuilder();
//        while ((len = inputStream.read(bytes)) != -1) {
//            //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
//            sb.append(new String(bytes, 0, len,"UTF-8"));
//        }
//        System.out.println("get message from server: " + sb);
//
//        inputStream.close();
//        outputStream.close();
        socket.close();
    }
}
