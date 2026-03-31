package strategy.selection;

import model.Elevator;
import model.ExternalRequest;

import java.util.List;

public interface ElevatorSelectionStrategy {
    Elevator selectElevator(List<Elevator> elevators, ExternalRequest req);
}
