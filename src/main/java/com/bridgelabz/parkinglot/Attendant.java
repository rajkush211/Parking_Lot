package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.exception.ParkingLotSystemException;
import com.bridgelabz.parkinglot.model.PersonType;
import com.bridgelabz.parkinglot.model.Vehicle;

public class Attendant {

    ParkingLotSystem parkingLotSystem = new ParkingLotSystem();

    public void attendantPark(Vehicle vehicle, PersonType personType) throws ParkingLotSystemException {
        parkingLotSystem.park(vehicle, personType);
    }

    public void attendantUnPark(Vehicle vehicle) throws ParkingLotSystemException {
        parkingLotSystem.unPark(vehicle);
    }
}
