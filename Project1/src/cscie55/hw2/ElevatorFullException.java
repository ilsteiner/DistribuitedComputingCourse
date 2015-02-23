package cscie55.hw2;

/**
 * Created by Isaac on 2/22/2015.
 */
public class ElevatorFullException extends java.lang.Exception{
    /**
     * Creates a new ElevatorFullException with a default message.
     */
    public ElevatorFullException(Elevator elevator){
        super("Cannot board passenger(s). Adding the requested number of passengers would make the Elevator exceed its maximum capacity. There are currently " + elevator.passengers() + " out of " + elevator.CAPACITY + " passengers on the Elevator.");
    }

    /**
     * Creates a new ElevatorFullException with the specified error message.
     * @param message the error message that should be returned when this ElevatorFullException is thrown.
     */
    public ElevatorFullException(String message){
        super(message);
    }
}
