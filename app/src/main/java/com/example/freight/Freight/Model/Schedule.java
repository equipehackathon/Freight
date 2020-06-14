package com.example.freight.Freight.Model;

public class Schedule {
    private int transportCategory;
    private int cargoType;
    private int axisNumber;
    private float distance;

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
