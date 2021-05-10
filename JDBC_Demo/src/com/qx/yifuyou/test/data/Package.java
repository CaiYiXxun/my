package com.qx.yifuyou.test.data;

import java.io.Serializable;
import java.util.ArrayList;

//??????
public class Package implements Serializable {
    private String accountNumber;//账号
    private String orderNumber;// 订单号
    private int freight;// 运费


    private String sendId;//寄件人id
    private String senderName;// 寄件人姓名
    private String senderPhone;//寄件人电话
    private String sendingProvince;//寄件省份
    private String sendingCity;// 寄件城市
    private String sendingAddress;// 寄件详细地址

    private String recipientId;//收件人id
    private String recipientName;// 收件人姓名
    private String recipientPhone;// 收件人电话
    private String recipientProvince;//收件省份
    private String recipientCity;// 收件城市
    private String recipientAddress;// 收件详细地址

    private String courierNumber;// 快递单号
    private ArrayList<TransportRecord> transportRecordList = new ArrayList<>();// 运输记录[]

    public Package() {

    }

    public Package(String accountNumber, String orderNumber, int freight, String courierNumber, String senderName, String senderPhone, String sendingProvince, String sendingCity, String sendingAddress, String recipientName, String recipientPhone, String recipientProvince, String recipientCity, String recipientAddress, ArrayList<TransportRecord> transportRecordList) {
        this.accountNumber = accountNumber;
        this.orderNumber = orderNumber;
        this.freight = freight;
        this.courierNumber = courierNumber;
        this.senderName = senderName;
        this.senderPhone = senderPhone;
        this.sendingProvince = sendingProvince;
        this.sendingCity = sendingCity;
        this.sendingAddress = sendingAddress;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.recipientProvince = recipientProvince;
        this.recipientCity = recipientCity;
        this.recipientAddress = recipientAddress;
        this.transportRecordList = transportRecordList;
    }

    public ArrayList<TransportRecord> getTransportRecordList() {
        return transportRecordList;
    }

    public void setTransportRecordList(ArrayList<TransportRecord> transportRecordList) {
        this.transportRecordList = transportRecordList;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSendingProvince() {
        return sendingProvince;
    }

    public void setSendingProvince(String sendingProvince) {
        this.sendingProvince = sendingProvince;
    }

    public String getSendingCity() {
        return sendingCity;
    }

    public void setSendingCity(String sendingCity) {
        this.sendingCity = sendingCity;
    }

    public String getSendingAddress() {
        return sendingAddress;
    }

    public void setSendingAddress(String sendingAddress) {
        this.sendingAddress = sendingAddress;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public String getRecipientProvince() {
        return recipientProvince;
    }

    public void setRecipientProvince(String recipientProvince) {
        this.recipientProvince = recipientProvince;
    }

    public String getRecipientCity() {
        return recipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }
}
