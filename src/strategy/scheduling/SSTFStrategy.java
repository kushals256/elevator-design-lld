package strategy.scheduling;

import model.Direction;
import model.ExternalRequest;
import model.InternalRequest;
import model.Request;

import java.util.List;

/**
 * Shortest-Seek-Time-First strategy.
 * Sorts the queue so that the nearest floor is serviced first.
 */
public class SSTFStrategy implements SchedulingStrategy {

    @Override
    public void reorder(List<Request> queue, int currentFloor, Direction dir) {
        queue.sort((r1, r2) -> {
            int dist1 = Math.abs(getFloor(r1) - currentFloor);
            int dist2 = Math.abs(getFloor(r2) - currentFloor);
            return Integer.compare(dist1, dist2);
        });
    }

    private int getFloor(Request req) {
        if (req instanceof InternalRequest) {
            return ((InternalRequest) req).getDestinationFloor();
        } else if (req instanceof ExternalRequest) {
            return ((ExternalRequest) req).getSourceFloor();
        }
        return 0;
    }
}
