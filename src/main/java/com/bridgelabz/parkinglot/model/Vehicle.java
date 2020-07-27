package com.bridgelabz.parkinglot.model;

public class Vehicle {
    private String vehicleNumber;
    private String name;
    private String parkingTime;

    public Vehicle(String vehicleNumber, String name, String parkingTime) {
        this.vehicleNumber = vehicleNumber;
        this.name = name;
        this.parkingTime = parkingTime;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getName() {
        return name;
    }

    public String getParkingTime() {
        return parkingTime;
    }
}