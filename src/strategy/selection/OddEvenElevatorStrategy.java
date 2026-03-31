package strategy.selection;

import model.Elevator;
import model.ElevatorState;
import model.ExternalRequest;

import java.util.List;

/**
 * Odd-Even strategy: assigns odd-floor requests to odd-indexed elevators
 * and even-floor requests to even-indexed elevators.
 * Falls back to the first available elevator if no match is found.
 */
public class OddEvenElevatorStrategy implements ElevatorSelectionStrategy {

    @Override
    public Elevator selectElevator(List<Elevator> elevators, ExternalRequest req) {
        boolean isOddFloor = req.getSourceFloor() % 2 != 0;

        // First pass: find a matching elevator (odd/even alignment)
        for (Elevator elevator : elevators) {
            if (elevator.getState() == ElevatorState.MAINTENANCE) {
                continue;
            }

            boolean isOddId = elevator.getId() % 2 != 0;

            if (isOddFloor == isOddId) {
                return elevator;
            }
        }

        // Fallback: return the first non-maintenance elevator
        for (Elevator elevator : elevators) {
            if (elevator.getState() != ElevatorState.MAINTENANCE) {
                return elevator;
            }
        }

        return null;
    }
}
