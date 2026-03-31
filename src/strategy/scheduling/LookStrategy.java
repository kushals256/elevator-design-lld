package strategy.scheduling;

import model.Direction;
import model.ExternalRequest;
import model.InternalRequest;
import model.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * LOOK strategy (variant of SCAN without going to extremes).
 * Serves all requests in the current direction first, then reverses.
 * Requests in the current direction are sorted ascending (UP) or descending (DOWN).
 * Remaining requests (behind the elevator) are sorted for the reverse pass.
 */
public class LookStrategy implements SchedulingStrategy {

    @Override
    public void reorder(List<Request> queue, int currentFloor, Direction dir) {
        if (queue.isEmpty()) return;

        List<Request> inDirection = new ArrayList<>();
        List<Request> opposite = new ArrayList<>();

        for (Request req : queue) {
            int floor = getFloor(req);
            if (dir == Direction.UP) {
                if (floor >= currentFloor) {
                    inDirection.add(req);
                } else {
                    opposite.add(req);
                }
            } else if (dir == Direction.DOWN) {
                if (floor <= currentFloor) {
                    inDirection.add(req);
                } else {
                    opposite.add(req);
                }
            } else {
                // Direction.NONE — treat everything as "in direction", sort by nearest
                inDirection.add(req);
            }
        }

        // Sort in-direction requests along the current travel direction
        if (dir == Direction.UP || dir == Direction.NONE) {
            inDirection.sort((r1, r2) -> Integer.compare(getFloor(r1), getFloor(r2)));
        } else {
            inDirection.sort((r1, r2) -> Integer.compare(getFloor(r2), getFloor(r1)));
        }

        // Opposite pass: reverse direction
        if (dir == Direction.UP) {
            opposite.sort((r1, r2) -> Integer.compare(getFloor(r2), getFloor(r1)));
        } else if (dir == Direction.DOWN) {
            opposite.sort((r1, r2) -> Integer.compare(getFloor(r1), getFloor(r2)));
        }

        queue.clear();
        queue.addAll(inDirection);
        queue.addAll(opposite);
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
