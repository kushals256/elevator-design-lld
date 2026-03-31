package model;

public class InternalRequest extends Request {
    private int destinationFloor;

    public InternalRequest(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    @Override
    public String toString() {
        return "InternalRequest{destinationFloor=" + destinationFloor + "}";
    }
}
