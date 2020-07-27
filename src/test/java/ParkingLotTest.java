import com.bridgelabz.parkinglot.*;
import com.bridgelabz.parkinglot.exception.ParkingLotSystemException;
import com.bridgelabz.parkinglot.model.Vehicle;
import com.bridgelabz.parkinglot.observer.AirportSecurity;
import com.bridgelabz.parkinglot.observer.Owner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.bridgelabz.parkinglot.model.PersonType.HANDICAPPED;
import static com.bridgelabz.parkinglot.model.PersonType.NORMAL;

public class ParkingLotTest {
    ParkingLotSystem parkingLotSystem = null;
    Vehicle vehicle = null;
    Owner owner = null;
    AirportSecurity airportSecurity = null;
    Attendant attendant = null;

    @Before
    public void setUp() {
        parkingLotSystem = new ParkingLotSystem();
        owner = new Owner();
        airportSecurity = new AirportSecurity();
        attendant = new Attendant();
    }

    @Test
    public void givenAVehicle_WhenParked_thenCheckIfParked_ShouldReturnTrue() throws ParkingLotSystemException {
        vehicle = new Vehicle("MH07BE0001", "BMW", "6:30");
        parkingLotSystem.park(vehicle, NORMAL);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenAVehicle_WhenUnParked_thenCheckIfUnparked_ShouldReturnTrue() throws ParkingLotSystemException {
        vehicle = new Vehicle("MH12AN0101", "AUDI", "7:30");
        parkingLotSystem.park(vehicle, NORMAL);
        parkingLotSystem.unPark(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(true, isUnParked);
    }

    @Test
    public void givenANullVehicle_WhenUnParked_ShouldThrowException() {
        try {
            parkingLotSystem.unPark(null);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals(ParkingLotSystemException.ExceptionType.NO_SUCH_A_VEHICLE, e.exceptionType);
        }
    }

    @Test
    public void givenAVehicle_WhenAlreadyParkedAndCheckIfUnPark_ShouldReturnFalse() throws ParkingLotSystemException {
        vehicle = new Vehicle("MH12AN0101", "Lamborghini", "17:56");
        parkingLotSystem.park(vehicle, NORMAL);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(false, isUnParked);
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldThrowException() {
        try {
            vehicle = new Vehicle("MH12AN0808", "Bugatti", "23:45");
            parkingLotSystem.park(vehicle, NORMAL);
            Vehicle vehicle2 = new Vehicle("MH010A0201", "BMW", "19:34");
            parkingLotSystem.park(vehicle2, NORMAL);
            Vehicle vehicle3 = new Vehicle("MH10BQ8108", "Porsche", "12:23");
            parkingLotSystem.park(vehicle3, NORMAL);
            Vehicle vehicle4 = new Vehicle("MH12AN0808", "Bugatti", "23:45");
            parkingLotSystem.park(vehicle4, NORMAL);
            Vehicle vehicle5 = new Vehicle("MH010A0101", "Ford", "20:34");
            parkingLotSystem.park(vehicle5, NORMAL);
            Vehicle vehicle6 = new Vehicle("MH10CQ1208", "TOYOTA", "14:26");
            parkingLotSystem.park(vehicle6, NORMAL);
            Vehicle vehicle7 = new Vehicle("MH10BQ2308", "AUDI", "19:23");
            parkingLotSystem.park(vehicle7, NORMAL);
        } catch (ParkingLotSystemException e) {
            Assert.assertEquals(ParkingLotSystemException.ExceptionType.PARKING_LOT_IS_FULL, e.exceptionType);
        }
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldInformToOwner() throws ParkingLotSystemException {
        parkingLotSystem.addObserver(owner);
        vehicle = new Vehicle("MH03AN0808", "Bugatti", "22:50");
        parkingLotSystem.park(vehicle, NORMAL);
        Vehicle vehicle2 = new Vehicle("MH10BQ8108", "Aston", "23:45");
        parkingLotSystem.park(vehicle2, NORMAL);
        Vehicle vehicle3 = new Vehicle("MH12AN0808", "Bugatti", "23:45");
        parkingLotSystem.park(vehicle3, NORMAL);
        Vehicle vehicle4 = new Vehicle("MH010A0101", "Ford", "20:34");
        parkingLotSystem.park(vehicle4, NORMAL);
        Vehicle vehicle5 = new Vehicle("MH10CQ1208", "TOYOTA", "14:26");
        parkingLotSystem.park(vehicle5, NORMAL);
        Vehicle vehicle6 = new Vehicle("MH10BQ2308", "AUDI", "19:23");
        parkingLotSystem.park(vehicle6, NORMAL);
        Assert.assertEquals(true, owner.getParkinglotStatusIfFull());
    }

    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldInformToAllObservers() throws ParkingLotSystemException {
        parkingLotSystem.addObserver(owner);
        parkingLotSystem.addObserver(airportSecurity);
        vehicle = new Vehicle("MH04AN0808", "BMW", "15:23");
        parkingLotSystem.park(vehicle, NORMAL);
        Vehicle vehicle2 = new Vehicle("MH10BQ8109", "Audi", "19:28");
        parkingLotSystem.park(vehicle2, NORMAL);
        Vehicle vehicle3 = new Vehicle("MH12AN0808", "Bugatti", "23:45");
        parkingLotSystem.park(vehicle3, NORMAL);
        Vehicle vehicle4 = new Vehicle("MH010A0101", "Ford", "20:34");
        parkingLotSystem.park(vehicle4, NORMAL);
        Vehicle vehicle5 = new Vehicle("MH10CQ1208", "TOYOTA", "14:26");
        parkingLotSystem.park(vehicle5, NORMAL);
        Vehicle vehicle6 = new Vehicle("MH10BQ2308", "AUDI", "19:23");
        parkingLotSystem.park(vehicle6, NORMAL);
        Assert.assertEquals(true, airportSecurity.getParkingLotStatusIfFull());
        Assert.assertEquals(true, owner.getParkinglotStatusIfFull());
    }

    @Test
    public void givenAVehicles_WhenParkingLotHasSpaceAgain_ShouldInformToParkingLotOwner() throws ParkingLotSystemException {
        parkingLotSystem.addObserver(owner);
        vehicle = new Vehicle("MH30AN0808", "Audi", "22:57");
        parkingLotSystem.park(vehicle, NORMAL);
        Vehicle vehicle2 = new Vehicle("MH03BQ8109", "Marshal", "18:34");
        parkingLotSystem.park(vehicle2, NORMAL);
        Vehicle vehicle3 = new Vehicle("MH12AN0808", "Bugatti", "23:45");
        parkingLotSystem.park(vehicle3, NORMAL);
        Vehicle vehicle4 = new Vehicle("MH010A0101", "Ford", "20:34");
        parkingLotSystem.park(vehicle4, NORMAL);
        Vehicle vehicle5 = new Vehicle("MH10CQ1208", "TOYOTA", "14:26");
        parkingLotSystem.park(vehicle5, NORMAL);
        Vehicle vehicle6 = new Vehicle("MH10BQ2308", "AUDI", "19:23");
        parkingLotSystem.park(vehicle6, NORMAL);
        Assert.assertEquals(true, owner.getParkinglotStatusIfFull());
        parkingLotSystem.unPark(vehicle);
        Assert.assertEquals(false, owner.getParkinglotStatusIfFull());
    }

    @Test
    public void givenAVehicleToAttendant_WhenParked_thenCheckIfParked_ShouldReturnTrue() throws ParkingLotSystemException {
        vehicle = new Vehicle("MH07BE0001", "BMW", "6:30");
        attendant.attendantPark(vehicle, NORMAL);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenAVehicleToAttendant_WhenUnParked_thenCheckIfParked_ShouldReturnTrue() throws ParkingLotSystemException {
        vehicle = new Vehicle("MH07BE0001", "Porsche", "17:30");
        attendant.attendantPark(vehicle, NORMAL);
        attendant.attendantUnPark(vehicle);
        boolean isParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(true, isParked);
    }

    @Test
    public void givenAVehicle_WhenParked_thenCheckPosition_ShouldReturnPosition() throws ParkingLotSystemException {
        vehicle = new Vehicle("MH07BE0401", "BMW", "16:45");
        parkingLotSystem.park(vehicle, NORMAL);
        int vehiclePosition = parkingLotSystem.getVehiclePosition(vehicle);
        Assert.assertEquals(4, vehiclePosition);
    }

    @Test
    public void givenAVehicle_WhenParked_thenCheckTimeWhenParked_ShouldReturnParkedTime() throws ParkingLotSystemException {
        vehicle = new Vehicle("MH07BE2301", "BMW", "6:30");
        parkingLotSystem.park(vehicle, NORMAL);
        String parkingTime = parkingLotSystem.getParkingTime(vehicle);
        Assert.assertEquals("6:30", parkingTime);
    }

    @Test
    public void givenAVehicleByHandicapped_WhenParked_thenParkInNearestFreeSpace_ShouldReturnPosition() throws ParkingLotSystemException {
        vehicle = new Vehicle("MH07BE0001", "BMW", "18:00");
        parkingLotSystem.park(vehicle, HANDICAPPED);
        int vehiclePosition = parkingLotSystem.getVehiclePosition(vehicle);
        Assert.assertEquals(1, vehiclePosition);
    }
}