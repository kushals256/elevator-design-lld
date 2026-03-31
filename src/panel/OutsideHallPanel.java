package panel;

import model.Direction;
import model.ExternalRequest;
import system.ElevatorSystem;

/**
 * Panel on a hallway outside the elevator (one per floor).
 * Has UP and DOWN buttons to summon an elevator.
 */
public class OutsideHallPanel extends Panel {
    private ElevatorSystem system;
    private int floor;

    public OutsideHallPanel(ElevatorSystem system, int floor) {
        this.system = system;
        this.floor = floor;
    }

    /**
     * Press the UP button on this floor's hall panel.
     */
    public void pressUpButton() {
        System.out.println("[HallPanel] Floor " + floor + " UP button pressed.");
        system.handleExternalReq(new ExternalRequest(floor, Direction.UP));
    }

    /**
     * Press the DOWN button on this floor's hall panel.
     */
    public void pressDownButton() {
        System.out.println("[HallPanel] Floor " + floor + " DOWN button pressed.");
        system.handleExternalReq(new ExternalRequest(floor, Direction.DOWN));
    }

    public int getFloor() {
        return floor;
    }
}
