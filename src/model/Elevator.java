package model;

import strategy.scheduling.SchedulingStrategy;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private int id;
    private int currentFloor;
    private Direction direction;
    private double currentLoad;
    private double maxLoad;
    private ElevatorState state;
    private List<Request> queue;
    private SchedulingStrategy schedulingStrategy;

    public Elevator(int id, double maxLoad, SchedulingStrategy schedulingStrategy) {
        this.id = id;
        this.currentFloor = 0; // ground floor
        this.direction = Direction.NONE;
        this.currentLoad = 0;
        this.maxLoad = maxLoad;
        this.state = ElevatorState.IDLE;
        this.queue = new ArrayList<>();
        this.schedulingStrategy = schedulingStrategy;
    }

    /**
     * Adds a request to this elevator's queue and re-orders using the scheduling strategy.
     */
    public synchronized void addRequest(Request req) {
        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("Elevator " + id + " is under maintenance. Cannot accept requests.");
            return;
        }

        queue.add(req);

        // Determine direction if currently IDLE
        if (direction == Direction.NONE || state == ElevatorState.IDLE) {
            int targetFloor = getTargetFloor(req);
            if (targetFloor > currentFloor) {
                direction = Direction.UP;
            } else if (targetFloor < currentFloor) {
                direction = Direction.DOWN;
            }
        }

        schedulingStrategy.reorder(queue, currentFloor, direction);
        state = ElevatorState.MOVING;

        System.out.println("Elevator " + id + " accepted " + req + " | Queue size: " + queue.size());
    }

    /**
     * Moves the elevator one step: processes the next request in the queue.
     * Simulates floor-by-floor movement towards the target.
     */
    public synchronized void move() {
        if (state == ElevatorState.MAINTENANCE) {
            System.out.println("Elevator " + id + " is under maintenance.");
            return;
        }

        if (queue.isEmpty()) {
            state = ElevatorState.IDLE;
            direction = Direction.NONE;
            System.out.println("Elevator " + id + " is IDLE at floor " + currentFloor);
            return;
        }

        // Re-order queue based on current state
        schedulingStrategy.reorder(queue, currentFloor, direction);

        Request nextReq = queue.get(0);
        int targetFloor = getTargetFloor(nextReq);

        // Move one floor towards target
        if (currentFloor < targetFloor) {
            currentFloor++;
            direction = Direction.UP;
        } else if (currentFloor > targetFloor) {
            currentFloor--;
            direction = Direction.DOWN;
        }

        System.out.println("Elevator " + id + " moving " + direction + " | Now at floor " + currentFloor);

        // Arrived at target floor
        if (currentFloor == targetFloor) {
            System.out.println("Elevator " + id + " *** STOPPED at floor " + currentFloor + " *** Servicing: " + nextReq);
            queue.remove(0);

            // If queue is empty, go idle
            if (queue.isEmpty()) {
                state = ElevatorState.IDLE;
                direction = Direction.NONE;
            }
        }
    }

    /**
     * Toggles maintenance mode on/off.
     */
    public void setMaintenance(boolean isMaintenance) {
        if (isMaintenance) {
            this.state = ElevatorState.MAINTENANCE;
            this.queue.clear();
            System.out.println("Elevator " + id + " is now under MAINTENANCE.");
        } else {
            this.state = ElevatorState.IDLE;
            this.direction = Direction.NONE;
            System.out.println("Elevator " + id + " is back in service.");
        }
    }

    /**
     * Checks if the elevator can take additional load.
     */
    public boolean canTakeLoad(double load) {
        return (currentLoad + load) <= maxLoad;
    }

    // --- Helper ---
    private int getTargetFloor(Request req) {
        if (req instanceof InternalRequest) {
            return ((InternalRequest) req).getDestinationFloor();
        } else if (req instanceof ExternalRequest) {
            return ((ExternalRequest) req).getSourceFloor();
        }
        return currentFloor;
    }

    // --- Getters ---
    public int getId() { return id; }
    public int getCurrentFloor() { return currentFloor; }
    public Direction getDirection() { return direction; }
    public double getCurrentLoad() { return currentLoad; }
    public double getMaxLoad() { return maxLoad; }
    public ElevatorState getState() { return state; }
    public List<Request> getQueue() { return queue; }
    public SchedulingStrategy getSchedulingStrategy() { return schedulingStrategy; }

    public void setSchedulingStrategy(SchedulingStrategy schedulingStrategy) {
        this.schedulingStrategy = schedulingStrategy;
    }

    public void setCurrentLoad(double currentLoad) {
        this.currentLoad = currentLoad;
    }

    @Override
    public String toString() {
        return "Elevator{id=" + id + ", floor=" + currentFloor
                + ", dir=" + direction + ", state=" + state
                + ", queueSize=" + queue.size() + "}";
    }
}
