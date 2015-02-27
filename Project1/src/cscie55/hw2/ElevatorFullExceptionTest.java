package cscie55.hw2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ElevatorFullExceptionTest {
    @Test
    public void testElevatorFullException(){
        Building building = new Building();
        Elevator elevator = new Elevator(building);

        for(int i=0;i<=11;i++) {
            building.getFloor(0).waitForElevator();
        }

        for(int i=0;i<=11;i++){
            try{
                elevator.boardPassenger(3);
            }
            catch (ElevatorFullException e){
                assertTrue("If this exception was thrown, there should be 10 passengers on the elevator already",elevator.passengers() == 10);
            }

            assertTrue("If we got here, there should be 10 or less passengers on the elevator",elevator.passengers()<=10);
        }
    }
}