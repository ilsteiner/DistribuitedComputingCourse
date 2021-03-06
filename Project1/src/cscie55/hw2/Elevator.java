package cscie55.hw2;

/**
 * @author Isaac Lebwohl-Steiner
 * @since 2015-02-26
 */
public class Elevator {
    public static final int CAPACITY = 10;
    private int direction;
    private int currFloor;
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
     * @return direction the current direction of the elevator (-1 means down, 1 means up, and 0 means it currently has no direction)
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @return currFloor the floor the Elevator is currently on (0 indexed)
     */
    public int currentFloor() {
        return currFloor + 1;
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
        return floors[floor - 1][0];
    }

    /**
     * Returns the total number of passengers, regardless of the floors for which they are destined
     *
     * @return numPassengers the number of total passengers in the Elevator
     */
    public int passengers() {
        int numPassengers = 0;
        for (int i = 0; i < Building.FLOORS; i++) {
            numPassengers += floors[i][0];
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
     * Disembark all passengers on the new floor
     * Board all passengers that are waiting on the current floor, provided there is room)
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

        /*Board any waiting passengers*/
        Floor floorObject = building.floor(currFloor + 1);

        /*If the current Floor has the "stop here" flag set, then board one passenger for every passenger on that floor.*/
        if(floorObject.needsStop){
            while(floorObject.passengersWaiting() > 0 && passengers() != CAPACITY){
                try {
                    boardPassenger(1);
                }
                catch (ElevatorFullException e){
                    System.out.println("The Elevator is currently full but will keep trying to board the " + floorObject.passengersWaiting() + " passengers that are waiting on floor " + currFloor + " each time it arrives on this floor.");
                }
            }
        }
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

        /*Mark the destination floor as a stop, regardless of whether it already is one*/
        floors[floor - 1][1] = 1;

        /*Clear the "stop here" flag for the current floor*/
        floors[currFloor][1] = 0;

        /*Tell the Floor that we are boarding a passenger*/
        building.floor(currFloor + 1).boardPassenger();
    }

    public String toString() {
        return "Floor " + currentFloor() + ": " + passengers() + " passengers";
    }
}
