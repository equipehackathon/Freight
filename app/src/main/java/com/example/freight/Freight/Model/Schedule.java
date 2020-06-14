package com.example.freight.Freight.Model;

import java.io.Serializable;

public class Schedule implements Serializable {
    private int id;
    private int driverId;
    private int clientId;
    private String scheduledDate;
    private int transportCategory;
    private int cargoType;
    private int axisNumber;
    private float distance;
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public int getTransportCategory() {
        return transportCategory;
    }

    public void setTransportCategory(int transportCategory) {
        this.transportCategory = transportCategory;
    }

    public int getCargoType() {
        return cargoType;
    }

    public void setCargoType(int cargoType) {
        this.cargoType = cargoType;
    }

    public int getAxisNumber() {
        return axisNumber;
    }

    public void setAxisNumber(int axisNumber) {
        this.axisNumber = axisNumber;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
