package strategy.scheduling;

import model.Direction;
import model.Request;

import java.util.List;

/**
 * First-Come-First-Served strategy.
 * Requests are processed in the order they arrive — no reordering.
 */
public class FCFSStrategy implements SchedulingStrategy {

    @Override
    public void reorder(List<Request> queue, int currentFloor, Direction dir) {
        // No reordering — requests stay in arrival order
    }
}
