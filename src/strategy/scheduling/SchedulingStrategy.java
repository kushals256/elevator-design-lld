package strategy.scheduling;

import model.Direction;
import model.Request;

import java.util.List;

public interface SchedulingStrategy {
    void reorder(List<Request> queue, int currentFloor, Direction dir);
}
