package com.bridgelabz.parkinglot;

import com.bridgelabz.parkinglot.exception.ParkingLotSystemException;
import com.bridgelabz.parkinglot.model.PersonType;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.observer.Observer;

import java.util.*;

import static com.bridgelabz.parkinglot.model.PersonType.HANDICAPPED;
import static com.bridgelabz.parkinglot.model.Vehicle.VehicleType.LARGE;

public class ParkingLotSystem {

    private static HashMap<Integer, Vehicle> parkingLot1;
    private static HashMap<Integer, Vehicle> parkingLot2;
    private boolean isParkingLotFull = false;
    private int parkingLotSize = 6;
    private int numOfVacantParking = 6;
    private List<Observer> observerList = new ArrayList<>();
    private Map activeParkingLot = parkingLot2;

    public ParkingLotSystem() {
        parkingLot1 = new HashMap<>();
        parkingLot1.put(1, null);
        parkingLot1.put(2, null);
        parkingLot1.put(3, null);

        parkingLot2 = new HashMap<>();
        parkingLot2.put(4, null);
        parkingLot2.put(5, null);
        parkingLot2.put(6, null);
    }

    public void addObserver(com.bridgelabz.parkinglot.observer.Observer observer) {
        this.observerList.add(observer);
    }

    public void setStatus(boolean isParkingLotFull) {
        this.isParkingLotFull = isParkingLotFull;
        for (Observer observer : this.observerList) {
            observer.update(isParkingLotFull);
        }
    }

    public int numOfVacantParkingInParkingLot(Map parkingLot) {
        int numOfParkingAvailable = 0;
        Iterator<Map.Entry<Integer, Vehicle>> iterator1 = parkingLot.entrySet().iterator();
        while (iterator1.hasNext()) {
            if (iterator1.next().getValue() == null) {
                numOfParkingAvailable++;
            }
        }
        return numOfParkingAvailable;
    }

    public Map parkingLotWithHighestNumOfFreeSpace() {
        int numOfParkingAvailableIn1 = 0;
        int numOfParkingAvailableIn2 = 0;
        Iterator<Map.Entry<Integer, Vehicle>> iterator1 = parkingLot1.entrySet().iterator();
        while (iterator1.hasNext()) {
            if (iterator1.next().getValue() == null) {
                numOfParkingAvailableIn1++;
            }
        }
        Iterator<Map.Entry<Integer, Vehicle>> iterator2 = parkingLot2.entrySet().iterator();
        while (iterator2.hasNext()) {
            if (iterator2.next().getValue() == null) {
                numOfParkingAvailableIn2++;
            }
        }
        if (numOfParkingAvailableIn1 >= numOfParkingAvailableIn2)
            return parkingLot1;
        else return parkingLot2;
    }

    private void activeParkingLot() {
        if (activeParkingLot == parkingLot1)
            activeParkingLot = parkingLot2;
        else activeParkingLot = parkingLot1;
    }

    public void park(Vehicle vehicle, PersonType personType) throws ParkingLotSystemException {
        activeParkingLot();
//        if (numOfVacantParking > 0)
            if (personType.equals(HANDICAPPED)) {
                handicappedPark(vehicle);
                return;
            } else if (vehicle.getVehicleType().equals(LARGE)) {
                largeVehiclePark(vehicle);
                return;
            } else {
                evenlyVehiclePark(vehicle);
                return;
            }
//         if (numOfVacantParking == 0) {
//            setStatus(true);
//            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_LOT_IS_FULL, "Parking Lot Is Full");
//        } else setStatus(false);
    }

    private void handicappedPark(Vehicle vehicle) throws ParkingLotSystemException {
        for (int index : parkingLot1.keySet()) {
            if (parkingLot1.get(index) == null) {
                parkingLot1.put(index, vehicle);
                numOfVacantParking--;
                break;
            }
        }
        if (!parkingLot1.containsValue(vehicle)) {
            for (int index : parkingLot2.keySet()) {
                if (parkingLot2.get(index) == null) {
                    parkingLot2.put(index, vehicle);
                    numOfVacantParking--;
                    break;
                }
            }
        }
        if (numOfVacantParking == 0)
            setStatus(true);
//        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.PARKING_LOT_IS_FULL, "Parking Lot Is Full");
    }

    private void largeVehiclePark(Vehicle vehicle) throws ParkingLotSystemException {
        Map parkingLotWithHighestNumOfFreeSpace = parkingLotWithHighestNumOfFreeSpace();
        for (Object index : parkingLotWithHighestNumOfFreeSpace.keySet()) {
            if (parkingLotWithHighestNumOfFreeSpace.get(index) == null) {
                parkingLotWithHighestNumOfFreeSpace.put(index, vehicle);
                numOfVacantParking--;
                break;
            }
        }
        if (numOfVacantParking == 0)
            setStatus(true);
    }

    private void evenlyVehiclePark(Vehicle vehicle) throws ParkingLotSystemException {
        if (numOfVacantParkingInParkingLot(activeParkingLot) > 0 && activeParkingLot == parkingLot1) {
            for (int index : parkingLot1.keySet()) {
                if (parkingLot1.get(index) == null) {
                    parkingLot1.put(index, vehicle);
                    numOfVacantParking--;
                    break;
                }
            }
        } else if (numOfVacantParkingInParkingLot(activeParkingLot) > 0 && activeParkingLot == parkingLot2) {
            for (int index : parkingLot2.keySet()) {
                if (parkingLot2.get(index) == null) {
                    parkingLot2.put(index, vehicle);
                    numOfVacantParking--;
                    break;
                }
            }
        }
        if (numOfVacantParking == 0)
            setStatus(true);
    }

    public void unPark(Vehicle vehicle) throws ParkingLotSystemException {
        if (vehicle == null || (!parkingLot1.containsValue(vehicle) && !parkingLot2.containsValue(vehicle)))
            throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.NO_SUCH_A_VEHICLE, "No Such Vehicle Found");
        else {
            if (parkingLot1.containsValue(vehicle)) {
                for (int index : parkingLot1.keySet()) {
                    if (parkingLot1.get(index) == vehicle) {
                        parkingLot1.put(index, null);
                        numOfVacantParking++;
                        break;
                    }
                }
            } else {
                for (int index : parkingLot2.keySet()) {
                    if (parkingLot2.get(index) == vehicle) {
                        parkingLot2.put(index, null);
                        numOfVacantParking++;
                        break;
                    }
                }
            }
        }
        if (numOfVacantParking > 0)
            setStatus(false);
    }

    public int getVehiclePosition(Vehicle vehicle) throws ParkingLotSystemException {
        for (int position : parkingLot1.keySet()) {
            if (parkingLot1.get(position) == vehicle) {
                return position;
            }
        }
        for (int position : parkingLot2.keySet()) {
            if (parkingLot2.get(position) == vehicle) {
                return position;
            }
        }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.NO_SUCH_A_VEHICLE, "No Such Vehicle Found");
    }

    public String getParkingTime(Vehicle vehicle) throws ParkingLotSystemException {
        for (int position : parkingLot1.keySet()) {
            if (parkingLot1.get(position) == vehicle) {
                return parkingLot1.get(position).getParkingTime();
            }
        }
        for (int position : parkingLot2.keySet()) {
            if (parkingLot2.get(position) == vehicle) {
                return parkingLot2.get(position).getParkingTime();
            }
        }
        throw new ParkingLotSystemException(ParkingLotSystemException.ExceptionType.NO_SUCH_A_VEHICLE, "No Such Vehicle Found");
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        boolean isVehicleParkedInParkingLot1 = parkingLot1.containsValue(vehicle);
        boolean isVehicleParkedInParkingLot2 = parkingLot2.containsValue(vehicle);
        return isVehicleParkedInParkingLot1 || isVehicleParkedInParkingLot2;
    }

    public boolean isVehicleUnParked(Vehicle vehicle) {
        boolean isVehicleParkedInParkingLot1 = parkingLot1.containsValue(vehicle);
        boolean isVehicleParkedInParkingLot2 = parkingLot2.containsValue(vehicle);
        return !isVehicleParkedInParkingLot1 && !isVehicleParkedInParkingLot2;
    }
}