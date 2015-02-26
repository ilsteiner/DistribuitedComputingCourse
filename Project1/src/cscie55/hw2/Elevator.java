package cscie55.hw2;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-10
 */
public class Elevator {
    public static final int CAPACITY = 10;
    private int direction;
    private int currFloor;
    /*@TODO Change all references to this thing to be references to the parent Building's Floor.*/
    private int[][] floors;
    private Building building;

    /**
     * Creates a new Elevator with default values
     */
    public Elevator(Building building) {
        direction = -1;
        currFloor = 0;
        /* Creates an empty array to hold the number of floors in the building
        *  and two pieces of information for each floor, the number of passengers
        *  destined for that floor and whether a stop was requested. The first
        *  element in the subarray is the number of passengers, an integer >= 0.
        *  The second element is the "stop here" flag, indicating whether the
        *  Elevator should stop (0 means no, 1 means yes).*/
        floors = new int[Building.FLOORS][2];
        this.building = building;
    }

    /**
     * @return direction the current direction of the elevator ()
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @return currFloor the floor the Elevator is currently on (0 indexed)
     */
    public int currentFloor() {
        return currFloor;
    }

    /**
     * @param oneIndex whether or not to convert the current floor to a 1 indexed value, like the variable FLOORS. True returns a 1 indexed floor number, while False returns a 0 indexed floor number.
     * @return currFloor the current floor number, either 1 indexed or 0 indexed based on the value of the parameter
     */
    public int getCurrFloor(boolean oneIndex) {
        if (oneIndex) {
            return currentFloor() + 1;
        }
        else {
            return currentFloor();
        }
    }

    /**
     * @return floors the array containing floor and passenger information for this Elevator
     * @see Elevator#Elevator(Building building)
     */
    public int[][] getFloors() {
        return floors;
    }

    /**
     * Returns the number of passengers destined for a given floor
     *
     * @param floor the floor for which you want the number of passengers
     * @return numPassengers the number of passengers destined for the specified floor
     */
    public int passengers(int floor) {
        return building.getFloor(floor).passengersWaiting();
    }

    /**
     * Returns the total number of passengers, regardless of the floors for which they are destined
     *
     * @return numPassengers the number of total passengers in the Elevator
     */
    public int passengers() {
        int numPassengers = 0;

        for(Floor floor : building.floors) {
            numPassengers += floor.passengersWaiting();
        }

        return numPassengers;
    }

    /**
     * Switches the direction of the Elevator. If the Elevator was going up, it will now be going down, and if it was going down, it will now be going up. If the Elevator did not have a direction, the direction will not be changed.
     *
     * @return direction the new direction of the Elevator
     */
    private int toggleDirection() {
        if (direction == 1) {
            direction = -1;
        }
        else if (direction == -1) {
            direction = 1;
        }
        else {
            direction = 0;
        }

        return direction;
    }

    /**
     * Moves the Elevator, making appropriate changes to state(
     * Change Elevator direction if necessary,
     * Change current floor,
     * Disembark all passengers on the new floor)
     */
    public void move() {
        /*If the Elevator is at the top or bottom floor, switch directions*/
        if (currFloor == 0 || currFloor == Building.FLOORS - 1) {
            toggleDirection();
        }

        /*Increase or decrease the floor as appropriate*/
        if (direction == 1) {
            currFloor++;
        }
        else if (direction == -1) {
            currFloor--;
        }
        else {
            throw new UnsupportedOperationException("Cannot move Elevator without a direction. Direction is currently " + direction + ".");
        }

        /*Clear the passengers destined for this floor.*/
        disembark(currFloor);

        /*Clear the "stop here" flag for this floor*/
        floors[currFloor][1] = 0;
    }

    private void disembark(int floor){
        floors[floor][0] = 0;
    }

    /**
     * Adds 1 new passenger to the Elevator, destined for the specified floor
     *
     * @param floor the floor for which the passenger is destined
     */
    public void boardPassenger(int floor) throws ElevatorFullException {
        if (passengers() + 1 > CAPACITY) {
            throw new ElevatorFullException(this);
        }
        /*Add 1 to the number of passengers destined for the indicated floor*/
        floors[floor - 1][0]++;
        /*Mark the floor as a stop, regardless of whether it already is one*/
        floors[floor - 1][1] = 1;
    }

    public String toString() {
        return "Floor " + getCurrFloor(true) + ": " + passengers() + " passengers";
    }
}
