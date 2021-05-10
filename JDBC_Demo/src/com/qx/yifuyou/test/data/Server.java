package com.qx.yifuyou.test.data;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.*;

public class Server {
    public static void main(String args[]) throws Exception {
        //JDBC
        //加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //用户信息和url
        String url = "jdbc:mysql://localhost:3306/delivery?useUnicode=true&characterEncoding=utf8&useSSL=false";
        String username = "root";
        String password = "123456";
        //获得连接 Connection代表数据库
        Connection connection = DriverManager.getConnection(url, username, password);
        //执行sql的对象 statement 这也是我们要进行操作的对象
        Statement statement = connection.createStatement();

        //SOCKET
        // 监听指定的端口
        int port = 8888;
        ServerSocket server = new ServerSocket(port);
        // server将一直等待连接的到来
        System.out.println("server将一直等待连接的到来");

        //如果使用多线程，那就需要线程池，防止并发过高时创建过多线程耗尽资源
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        while (true) {
            //等待接收信息
            Socket socket = server.accept();

            Runnable runnable = () -> {

                System.out.println("接收到client请求");
                //传对象用的
                ObjectInputStream is = null;
                ObjectOutputStream os = null;
                Quest quest = null;
                Package cn_package = null;
                try {

                    //out = new PrintWriter(socket.getOutputStream());
                    //传对象用的
                    is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                    os = new ObjectOutputStream(socket.getOutputStream());
                    //执行sql 一般只需executeQuery查询和executeUpdate更新

                    Object obj = is.readObject();
                    if (obj != null) {
                        quest = (Quest) obj;
                    }
                    String sql;
                    ResultSet resultSet = null;
                    int packages_count;
                    int t_record_count = 0;
                    switch (quest.getType()) {
                        case 1:
                            String account = quest.getMsg();
                            String pword = quest.getPassword();
                            String pwd;
                            sql = "select * from 账户 where 账号=" + account + ";";
                            resultSet = statement.executeQuery(sql);//返回结果集，里面有查询结果
                            resultSet.next();

                            pwd = resultSet.getString("密码");
                            if (!pwd.equals(pword)) {
                                User user = new User(null, null, false);
                                os.writeObject(user);
                                os.flush();
                                break;
                            }


                            sql = "select * from 订单 natural join 包裹 natural join 账户 where 账号=" + account + ";";
                            resultSet = statement.executeQuery(sql);//返回结果集，里面有查询结果
                            resultSet.last();
                            packages_count = resultSet.getRow();
                            resultSet.beforeFirst();//计算resultSet记录数
                            ArrayList<Package> send_packages = new ArrayList<>();
                            //Package[] send_packages = new Package[packages_count];
                            int i = 0, j = 0;
                            while (resultSet.next()) {
                                send_packages.add(new Package());

                                send_packages.get(i).setAccountNumber(account);
                                send_packages.get(i).setOrderNumber(resultSet.getString("订单号"));
                                send_packages.get(i).setFreight(resultSet.getInt("运费"));
                                send_packages.get(i).setCourierNumber(resultSet.getString("快递单号"));
                                send_packages.get(i).setSendId(resultSet.getString("寄件信息"));
                                send_packages.get(i).setRecipientId(resultSet.getString("收件信息"));
                                i++;
                            }
                            //寄件人
                            for (i = 0; i < packages_count; i++) {
                                sql = "select * from 地址信息 where ID=" + send_packages.get(i).getSendId() + ";";
                                resultSet = statement.executeQuery(sql);
                                resultSet.next();
                                send_packages.get(i).setSenderName(resultSet.getString("姓名"));
                                send_packages.get(i).setSenderPhone(resultSet.getString("电话"));
                                send_packages.get(i).setSendingProvince(resultSet.getString("省份"));
                                send_packages.get(i).setSendingCity(resultSet.getString("城市"));
                                send_packages.get(i).setSendingAddress(resultSet.getString("详细地址"));
                            }
                            //收件人
                            for (i = 0; i < packages_count; i++) {
                                sql = "select * from 地址信息 where ID=" + send_packages.get(i).getRecipientId() + ";";
                                resultSet = statement.executeQuery(sql);
                                resultSet.next();
                                send_packages.get(i).setRecipientName(resultSet.getString("姓名"));
                                send_packages.get(i).setRecipientPhone(resultSet.getString("电话"));
                                send_packages.get(i).setRecipientProvince(resultSet.getString("省份"));
                                send_packages.get(i).setRecipientCity(resultSet.getString("城市"));
                                send_packages.get(i).setRecipientAddress(resultSet.getString("详细地址"));
                            }
                            //运输记录
                            for (i = 0; i < packages_count; i++) {
                                sql = "select * from 运输记录 where 快递单号=" + send_packages.get(i).getCourierNumber() + ";";
                                resultSet = statement.executeQuery(sql);
                                resultSet.last();
                                t_record_count = resultSet.getRow();
                                resultSet.beforeFirst();
                                ArrayList<TransportRecord> transportRecords = new ArrayList<>();
//                                TransportRecord[] transportRecords = new TransportRecord[t_record_count];
                                j = 0;
                                while (resultSet.next()) {
                                    transportRecords.add(new TransportRecord());
                                    transportRecords.get(j).setSerialNumber(resultSet.getString("流水号"));
                                    transportRecords.get(j).setStartWarehouse(resultSet.getString("出发仓库"));
                                    transportRecords.get(j).setDestinationWarehouse(resultSet.getString("目的仓库"));
                                    transportRecords.get(j).setTransportVehicleId(resultSet.getString("交通工具"));
                                    boolean complete = complete(resultSet.getInt("是否完成"));
                                    transportRecords.get(j).setCompleted(complete);

                                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("开始时间"));
                                    transportRecords.get(j).setStartTime(time);
                                    if (resultSet.getTimestamp("完成时间") != null) {
                                        time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("完成时间"));
                                    } else {
                                        time = null;
                                    }
                                    transportRecords.get(j).setEndingTime(time);
                                    j++;
                                }
                                for (j = 0; j < t_record_count; j++) {
                                    sql = "select * from 交通工具 where ID=" + transportRecords.get(j).getTransportVehicleId() + ";";
                                    resultSet = statement.executeQuery(sql);
                                    resultSet.next();
                                    transportRecords.get(j).setTransportVehicle(resultSet.getString("类型"));
                                    send_packages.get(i).getTransportRecordList().add(transportRecords.get(j));
                                }
                            }


                            sql = "select * from 订单 natural join 包裹 natural join 账户 where 收件信息=" + account + ";";
                            resultSet = statement.executeQuery(sql);//返回结果集，里面有查询结果
                            resultSet.last();
                            packages_count = resultSet.getRow();
                            resultSet.beforeFirst();//计算resultSet记录数
                            ArrayList<Package> receive_packages = new ArrayList<>();
                            //Package[] receive_packages = new Package[packages_count];
                            i = j = 0;
                            while (resultSet.next()) {
                                receive_packages.add(new Package());
                                receive_packages.get(i).setAccountNumber(account);
                                receive_packages.get(i).setOrderNumber(resultSet.getString("订单号"));
                                receive_packages.get(i).setFreight(resultSet.getInt("运费"));
                                receive_packages.get(i).setCourierNumber(resultSet.getString("快递单号"));
                                receive_packages.get(i).setSendId(resultSet.getString("寄件信息"));
                                receive_packages.get(i).setRecipientId(resultSet.getString("收件信息"));
                                i++;
                            }
                            //寄件人
                            for (i = 0; i < packages_count; i++) {
                                sql = "select * from 地址信息 where ID=" + receive_packages.get(i).getSendId() + ";";
                                resultSet = statement.executeQuery(sql);
                                resultSet.next();
                                receive_packages.get(i).setSenderName(resultSet.getString("姓名"));
                                receive_packages.get(i).setSenderPhone(resultSet.getString("电话"));
                                receive_packages.get(i).setSendingProvince(resultSet.getString("省份"));
                                receive_packages.get(i).setSendingCity(resultSet.getString("城市"));
                                receive_packages.get(i).setSendingAddress(resultSet.getString("详细地址"));
                            }
                            //收件人
                            for (i = 0; i < packages_count; i++) {
                                sql = "select * from 地址信息 where ID=" + receive_packages.get(i).getRecipientId() + ";";
                                resultSet = statement.executeQuery(sql);
                                resultSet.next();
                                receive_packages.get(i).setRecipientName(resultSet.getString("姓名"));
                                receive_packages.get(i).setRecipientPhone(resultSet.getString("电话"));
                                receive_packages.get(i).setRecipientProvince(resultSet.getString("省份"));
                                receive_packages.get(i).setRecipientCity(resultSet.getString("城市"));
                                receive_packages.get(i).setRecipientAddress(resultSet.getString("详细地址"));
                            }
                            //运输记录
                            for (i = 0; i < packages_count; i++) {
                                sql = "select * from 运输记录 where 快递单号=" + receive_packages.get(i).getCourierNumber() + ";";
                                resultSet = statement.executeQuery(sql);
                                resultSet.last();
                                t_record_count = resultSet.getRow();
                                resultSet.beforeFirst();
                                ArrayList<TransportRecord> transportRecords = new ArrayList<>();
                                //TransportRecord[] transportRecords = new TransportRecord[t_record_count];
                                j = 0;
                                while (resultSet.next()) {
                                    transportRecords.add(new TransportRecord());
                                    transportRecords.get(j).setSerialNumber(resultSet.getString("流水号"));
                                    transportRecords.get(j).setStartWarehouse(resultSet.getString("出发仓库"));
                                    transportRecords.get(j).setDestinationWarehouse(resultSet.getString("目的仓库"));
                                    transportRecords.get(j).setTransportVehicleId(resultSet.getString("交通工具"));
                                    boolean complete = complete(resultSet.getInt("是否完成"));
                                    transportRecords.get(j).setCompleted(complete);

                                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("开始时间"));
                                    transportRecords.get(j).setStartTime(time);
                                    if (resultSet.getTimestamp("完成时间") != null) {
                                        time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("完成时间"));
                                    } else {
                                        time = null;
                                    }
                                    transportRecords.get(j).setEndingTime(time);
                                    j++;
                                }
                                for (j = 0; j < t_record_count; j++) {
                                    sql = "select * from 交通工具 where ID=" + transportRecords.get(j).getTransportVehicleId() + ";";
                                    resultSet = statement.executeQuery(sql);
                                    resultSet.next();
                                    transportRecords.get(j).setTransportVehicle(resultSet.getString("类型"));
                                    receive_packages.get(i).getTransportRecordList().add(transportRecords.get(j));
                                }
                            }
                            User user = new User(send_packages, receive_packages, true);
                            os.writeObject(user);
                            os.flush();
                            break;
                        case 2:
//                            String cn_number="754413348752313";
                            String cn_number = quest.getMsg();
                            sql = "select * from 订单 natural join 包裹 natural join 账户 where 快递单号=" + cn_number + ";";
                            resultSet = statement.executeQuery(sql);//返回结果集，里面有查询结果

                            cn_package = new Package();
                            while (resultSet.next()) {
                                cn_package.setAccountNumber(resultSet.getString("账号"));
                                cn_package.setOrderNumber(resultSet.getString("订单号"));
                                cn_package.setFreight(resultSet.getInt("运费"));
                                cn_package.setCourierNumber(resultSet.getString("快递单号"));
                                cn_package.setSendId(resultSet.getString("寄件信息"));
                                cn_package.setRecipientId(resultSet.getString("收件信息"));

                            }
                            //寄件人
                            sql = "select * from 地址信息 where ID=" + cn_package.getSendId() + ";";
                            resultSet = statement.executeQuery(sql);
                            resultSet.next();
                            cn_package.setSenderName(resultSet.getString("姓名"));
                            cn_package.setSenderPhone(resultSet.getString("电话"));
                            cn_package.setSendingProvince(resultSet.getString("省份"));
                            cn_package.setSendingCity(resultSet.getString("城市"));
                            cn_package.setSendingAddress(resultSet.getString("详细地址"));

                            //收件人

                            sql = "select * from 地址信息 where ID=" + cn_package.getRecipientId() + ";";
                            resultSet = statement.executeQuery(sql);
                            resultSet.next();
                            cn_package.setRecipientName(resultSet.getString("姓名"));
                            cn_package.setRecipientPhone(resultSet.getString("电话"));
                            cn_package.setRecipientProvince(resultSet.getString("省份"));
                            cn_package.setRecipientCity(resultSet.getString("城市"));
                            cn_package.setRecipientAddress(resultSet.getString("详细地址"));

                            //运输记录

                            sql = "select * from 运输记录 where 快递单号=" + cn_package.getCourierNumber() + ";";
                            resultSet = statement.executeQuery(sql);
                            resultSet.last();
                            t_record_count = resultSet.getRow();
                            resultSet.beforeFirst();
                            ArrayList<TransportRecord> transportRecords = new ArrayList<>();
                            //TransportRecord[] transportRecords = new TransportRecord[t_record_count];
                            j = 0;
                            while (resultSet.next()) {
                                transportRecords.add(new TransportRecord());
                                transportRecords.get(j).setSerialNumber(resultSet.getString("流水号"));
                                transportRecords.get(j).setStartWarehouse(resultSet.getString("出发仓库"));
                                transportRecords.get(j).setDestinationWarehouse(resultSet.getString("目的仓库"));
                                transportRecords.get(j).setTransportVehicleId(resultSet.getString("交通工具"));
                                boolean complete = complete(resultSet.getInt("是否完成"));
                                transportRecords.get(j).setCompleted(complete);

                                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("开始时间"));
                                transportRecords.get(j).setStartTime(time);
                                if (resultSet.getTimestamp("完成时间") != null) {
                                    time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("完成时间"));
                                } else {
                                    time = null;
                                }
                                transportRecords.get(j).setEndingTime(time);
                                j++;
                            }
                            for (j = 0; j < t_record_count; j++) {
                                sql = "select * from 交通工具 where ID=" + transportRecords.get(j).getTransportVehicleId() + ";";
                                resultSet = statement.executeQuery(sql);
                                resultSet.next();
                                transportRecords.get(j).setTransportVehicle(resultSet.getString("类型"));
                                cn_package.getTransportRecordList().add(transportRecords.get(j));
                            }
                            os.writeObject(cn_package);
                            os.flush();
                            break;
//                        case 3:
////                            String phone="19627270623";
//                            String phone = quest.getMsg();
//                            sql = "select * from 地址信息 where 电话=" + phone + ";";
//                            resultSet = statement.executeQuery(sql);//返回结果集，里面有查询结果
//                            resultSet.next();
//                            String ID = resultSet.getString("ID");
//                            System.out.println(ID);
//
//                            sql = "select * from 订单 natural join 包裹 natural join 账户 where 收件信息=" + ID + ";";
//                            resultSet = statement.executeQuery(sql);//返回结果集，里面有查询结果
//                            resultSet.last();
//                            packages_count = resultSet.getRow();
//                            resultSet.beforeFirst();//计算resultSet记录数
//                            Package[] phone_packages = new Package[packages_count];
//                            i = j = 0;
//                            while (resultSet.next()) {
//                                phone_packages[i] = new Package();
//                                phone_packages[i].setAccountNumber(resultSet.getString("账号"));
//                                phone_packages[i].setOrderNumber(resultSet.getString("订单号"));
//                                phone_packages[i].setFreight(resultSet.getInt("运费"));
//                                phone_packages[i].setCourierNumber(resultSet.getString("快递单号"));
//                                phone_packages[i].setSendId(resultSet.getString("寄件信息"));
//                                phone_packages[i].setRecipientId(resultSet.getString("收件信息"));
//                                i++;
//                            }
//                            //寄件人
//                            for (i = 0; i < packages_count; i++) {
//                                sql = "select * from 地址信息 where ID=" + phone_packages[i].getSendId() + ";";
//                                resultSet = statement.executeQuery(sql);
//                                resultSet.next();
//                                phone_packages[i].setSenderName(resultSet.getString("姓名"));
//                                phone_packages[i].setSenderPhone(resultSet.getString("电话"));
//                                phone_packages[i].setSendingProvince(resultSet.getString("省份"));
//                                phone_packages[i].setSendingCity(resultSet.getString("城市"));
//                                phone_packages[i].setSendingAddress(resultSet.getString("详细地址"));
//                            }
//                            //收件人
//                            for (i = 0; i < packages_count; i++) {
//                                sql = "select * from 地址信息 where ID=" + phone_packages[i].getRecipientId() + ";";
//                                resultSet = statement.executeQuery(sql);
//                                resultSet.next();
//                                phone_packages[i].setRecipientName(resultSet.getString("姓名"));
//                                phone_packages[i].setRecipientPhone(resultSet.getString("电话"));
//                                phone_packages[i].setRecipientProvince(resultSet.getString("省份"));
//                                phone_packages[i].setRecipientCity(resultSet.getString("城市"));
//                                phone_packages[i].setRecipientAddress(resultSet.getString("详细地址"));
//                            }
//                            //运输记录
//                            for (i = 0; i < packages_count; i++) {
//                                sql = "select * from 运输记录 where 快递单号=" + phone_packages[i].getCourierNumber() + ";";
//                                resultSet = statement.executeQuery(sql);
//                                resultSet.last();
//                                t_record_count = resultSet.getRow();
//                                resultSet.beforeFirst();
//                                TransportRecord[] transportRecords2 = new TransportRecord[t_record_count];
//                                j = 0;
//                                while (resultSet.next()) {
//                                    transportRecords2[j] = new TransportRecord();
//                                    transportRecords2[j].setSerialNumber(resultSet.getString("流水号"));
//                                    transportRecords2[j].setStartWarehouse(resultSet.getString("出发仓库"));
//                                    transportRecords2[j].setDestinationWarehouse(resultSet.getString("目的仓库"));
//                                    transportRecords2[j].setTransportVehicleId(resultSet.getString("交通工具"));
//                                    boolean complete = complete(resultSet.getInt("是否完成"));
//                                    transportRecords2[j].setCompleted(complete);
//
//                                    String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("开始时间"));
//                                    transportRecords2[j].setStartTime(time);
//                                    if (resultSet.getTimestamp("完成时间") != null) {
//                                        time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("完成时间"));
//                                    } else {
//                                        time = null;
//                                    }
//                                    transportRecords2[j].setEndingTime(time);
//                                    j++;
//                                }
//                                for (j = 0; j < t_record_count; j++) {
//                                    sql = "select * from 交通工具 where ID=" + transportRecords2[j].getTransportVehicleId() + ";";
//                                    resultSet = statement.executeQuery(sql);
//                                    resultSet.next();
//                                    transportRecords2[j].setTransportVehicle(resultSet.getString("类型"));
//                                    phone_packages[i].getTransportRecordList().add(transportRecords2[j]);
//                                }
//                            }
//                            os.writeObject(phone_packages);
//                            os.flush();
//                            break;

                        default:
                            System.out.println("请求错误");
                            break;
                    }


                    //释放连接
                    // socket.close();
                    //resultSet.close();
                } catch (SQLException e1) {
                    if (quest.getType() == 1) {
                        User user = new User(null, null, false);
                        try {
                            os.writeObject(user);
                            os.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (quest.getType() == 2) {
                        try {
                            cn_package.setFreight(-1);
                            os.writeObject(cn_package);
                            os.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            };

            threadPool.submit(runnable);
        }


    }

//    public static String gainTime(Date date) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateStr = sdf.format(date);
//        return dateStr;
//    }

    public static boolean complete(int x) {
        if (x == 1) {
            return true;
        } else {
            return false;
        }
    }
}