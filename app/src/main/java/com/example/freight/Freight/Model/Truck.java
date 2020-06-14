package com.example.freight.Freight.Model;

public class Truck {
    private int id;
    private String model;
    private int transportType;
    private int cargoType;
    private int axisNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTransportType() {
        return transportType;
    }

    public void setTransportType(int transportType) {
        this.transportType = transportType;
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
}
