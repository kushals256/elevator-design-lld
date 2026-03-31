package system;

import model.Elevator;
import model.ExternalRequest;
import strategy.selection.ElevatorSelectionStrategy;

import java.util.List;

public class ElevatorSystem {
    private List<Elevator> elevators;
    private ElevatorSelectionStrategy selectionStrategy;

    public ElevatorSystem(List<Elevator> elevators, ElevatorSelectionStrategy selectionStrategy) {
        this.elevators = elevators;
        this.selectionStrategy = selectionStrategy;
    }

    /**
     * Handles an external request by selecting the best elevator and dispatching the request.
     */
    public void handleExternalReq(ExternalRequest req) {
        Elevator selected = selectionStrategy.selectElevator(elevators, req);
        if (selected == null) {
            System.out.println("No available elevator for request: " + req);
            return;
        }
        System.out.println("Dispatching " + req + " to Elevator " + selected.getId());
        selected.addRequest(req);
    }

    /**
     * Swap the selection strategy at runtime (Strategy pattern).
     */
    public void setSelectionStrategy(ElevatorSelectionStrategy strategy) {
        this.selectionStrategy = strategy;
        System.out.println("Selection strategy changed to: " + strategy.getClass().getSimpleName());
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    /**
     * Convenience: move all elevators one step (for simulation).
     */
    public void stepAll() {
        for (Elevator elevator : elevators) {
            elevator.move();
        }
    }
}
