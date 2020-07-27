package com.bridgelabz.parkinglot.exception;

public class ParkingLotSystemException extends Exception {

    public enum ExceptionType {
        PARKING_LOT_IS_FULL, NO_SUCH_A_VEHICLE
    }

    public ExceptionType exceptionType;

    public ParkingLotSystemException(ExceptionType exceptionType, String message) {
        super();
        this.exceptionType = exceptionType;
    }

    public ParkingLotSystemException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }
}