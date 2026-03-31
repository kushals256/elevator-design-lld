package model;

public class ExternalRequest extends Request {
    private int sourceFloor;
    private Direction direction;

    public ExternalRequest(int sourceFloor, Direction direction) {
        this.sourceFloor = sourceFloor;
        this.direction = direction;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "ExternalRequest{sourceFloor=" + sourceFloor + ", direction=" + direction + "}";
    }
}
