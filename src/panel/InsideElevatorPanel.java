package panel;

import model.Elevator;
import model.InternalRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Panel inside an elevator car.
 * Passengers use this to select a destination floor, open/close doors,
 * ring the emergency bell, and manage disabled floors.
 */
public class InsideElevatorPanel extends Panel {
    private Elevator elevator;
    private List<Integer> disabledFloors;

    public InsideElevatorPanel(Elevator elevator) {
        this.elevator = elevator;
        this.disabledFloors = new ArrayList<>();
    }

    /**
     * Press a floor button inside the elevator.
     */
    public void pressFloorButton(int floor) {
        if (disabledFloors.contains(floor)) {
            System.out.println("[InsidePanel] Floor " + floor + " is disabled for Elevator " + elevator.getId());
            return;
        }
        System.out.println("[InsidePanel] Floor " + floor + " pressed in Elevator " + elevator.getId());
        elevator.addRequest(new InternalRequest(floor));
    }

    /**
     * Open the elevator door (simulated).
     */
    public void openDoor() {
        System.out.println("[InsidePanel] Elevator " + elevator.getId() + " door OPENING at floor " + elevator.getCurrentFloor());
    }

    /**
     * Close the elevator door (simulated).
     */
    public void closeDoor() {
        System.out.println("[InsidePanel] Elevator " + elevator.getId() + " door CLOSING at floor " + elevator.getCurrentFloor());
    }

    /**
     * Ring the emergency bell.
     */
    public void ringBell() {
        System.out.println("[InsidePanel] *** EMERGENCY BELL *** Elevator " + elevator.getId());
    }

    /**
     * Disable a floor — requests to this floor will be rejected.
     */
    public void disableFloor(int floor) {
        if (!disabledFloors.contains(floor)) {
            disabledFloors.add(floor);
            System.out.println("[InsidePanel] Floor " + floor + " DISABLED for Elevator " + elevator.getId());
        }
    }

    /**
     * Re-enable a previously disabled floor.
     */
    public void enableFloor(int floor) {
        disabledFloors.remove(Integer.valueOf(floor));
        System.out.println("[InsidePanel] Floor " + floor + " ENABLED for Elevator " + elevator.getId());
    }

    public Elevator getElevator() {
        return elevator;
    }
}
