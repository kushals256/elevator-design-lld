package strategy.selection;

import model.Elevator;
import model.ElevatorState;
import model.ExternalRequest;

import java.util.List;

/**
 * Selects the nearest non-maintenance elevator to the requesting floor.
 * Prefers elevators that are IDLE or already moving in the compatible direction.
 */
public class NearestElevatorStrategy implements ElevatorSelectionStrategy {

    @Override
    public Elevator selectElevator(List<Elevator> elevators, ExternalRequest req) {
        Elevator best = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            if (elevator.getState() == ElevatorState.MAINTENANCE) {
                continue;
            }

            int distance = Math.abs(elevator.getCurrentFloor() - req.getSourceFloor());

            // Prefer idle elevators or those moving towards the request floor
            if (elevator.getState() == ElevatorState.IDLE) {
                // Idle elevators get a slight bonus (tie-breaker)
                distance = distance > 0 ? distance - 1 : 0;
            }

            if (distance < minDistance) {
                minDistance = distance;
                best = elevator;
            }
        }

        return best;
    }
}
