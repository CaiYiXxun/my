package com.qx.yifuyou.test.data;

import java.io.Serializable;

//傳輸記錄
public class TransportRecord implements Serializable
{
    private String serialNumber; // 流水号
    private String startWarehouse; // 出发仓库
    private String destinationWarehouse; // 目的仓库
    private String transportVehicleId;//交通工具号
    private String transportVehicle; // 交通工具类型
    private String startTime; // 开始时间
    private String endingTime; // 完成时间
    private boolean Completed; // 是否已完成运输

    public TransportRecord()
    {

    }
    public TransportRecord(String serialNumber, String startWarehouse, String destinationWarehouse, String transportVehicleId, String transportVehicle, String startTime, String endingTime, boolean completed)
    {
        this.serialNumber = serialNumber;
        this.startWarehouse = startWarehouse;
        this.destinationWarehouse = destinationWarehouse;
        this.transportVehicleId = transportVehicleId;
        this.transportVehicle = transportVehicle;
        this.startTime = startTime;
        this.endingTime = endingTime;
        this.Completed = completed;
    }

    public String getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public String getStartWarehouse()
    {
        return startWarehouse;
    }

    public void setStartWarehouse(String startWarehouse)
    {
        this.startWarehouse = startWarehouse;
    }

    public String getDestinationWarehouse()
    {
        return destinationWarehouse;
    }

    public void setDestinationWarehouse(String destinationWarehouse)
    {
        this.destinationWarehouse = destinationWarehouse;
    }

    public String getTransportVehicle()
    {
        return transportVehicle;
    }

    public void setTransportVehicle(String transportVehicle)
    {
        this.transportVehicle = transportVehicle;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndingTime()
    {
        return endingTime;
    }

    public void setEndingTime(String endingTime)
    {
        this.endingTime = endingTime;
    }

    public boolean isCompleted()
    {
        return Completed;
    }

    public void setCompleted(boolean completed)
    {
        Completed = completed;
    }

    public String getTransportVehicleId() {
        return transportVehicleId;
    }

    public void setTransportVehicleId(String transportVehicleId) {
        this.transportVehicleId = transportVehicleId;
    }
}
