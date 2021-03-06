package cscie55.hw3;

/**
 * Created by Isaac on 3/14/2015.
 */
public class Passenger {
    protected static final int UNDEFINED_FLOOR = -1;
    private int currentFloor;
    private int destinationFloor;

    /**
     * Creates a new Passenger that is currently on the specified floor
     * @param currentFloor an integer representing the Passenger's current floor number (0 indexed)
     */
    public Passenger(int currentFloor){
        this.currentFloor = currentFloor + 1;
        destinationFloor = UNDEFINED_FLOOR;
    }

    /**
     * Returns the Passenger's current floor number as an integer, or -1 if they are not on a floor.
     * @return currentFloor an integer representing the Passenger's current floor number
     */
    public int currentFloor(){
        return  currentFloor;
    }

    /**
     * Sets the Passenger's current floor to the specified integer value.
     * @param floorNumber the integer value of the new floor number
     */
    public void setCurrentFloor(int floorNumber){
        currentFloor = floorNumber;
    }

    /**
     * Returns the Passenger's current floor number, or -1 if they have no destination.
     * @return destinationFloor the Passenger's destination floor
     */
    public int destinationFloor(){
        return destinationFloor;
    }

    /**
     * Sets a new destination for the Passenger, overwriting any previous destination they may have had.
     * @param newDestinationFloor an integer representing the Passenger's new destination floor
     */
    public void waitForElevator(int newDestinationFloor){
        destinationFloor = newDestinationFloor;
    }

    /**
     * Clears the passenger's current floor
     */
    public void boardElevator(){
        currentFloor = UNDEFINED_FLOOR;
    }

    /**
     * Sets the current floor to the Passenger's destination floor and clears the destination floor.
     */
    public void arrive(){
        currentFloor = destinationFloor;
        destinationFloor = UNDEFINED_FLOOR;
    }

    public String toString() {
        String output = "Passenger is ";

        if(currentFloor == UNDEFINED_FLOOR){
            output += "on the Elevator and ";
        }
        else{
            output += "on floor " + currentFloor + " and ";
        }

        if(destinationFloor == UNDEFINED_FLOOR){
            output += "has no current destination.";
        }
        else{
            output += "is destined for floor " + destinationFloor + ".";
        }

        return output;
    }
}
