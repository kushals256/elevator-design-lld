import model.*;
import panel.*;
import strategy.scheduling.*;
import strategy.selection.*;
import system.ElevatorSystem;

import java.util.Arrays;
import java.util.List;

/**
 * Simulation demonstrating the Elevator System LLD.
 *
 * Scenario:
 *  - 3 elevators (LOOK, SSTF, FCFS scheduling)
 *  - NearestElevator selection strategy
 *  - External requests from hall panels on floors 0, 3, 7
 *  - Internal requests from inside elevator panels
 *  - Demonstrates maintenance mode and disabled floors
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   ELEVATOR SYSTEM — LLD SIMULATION");
        System.out.println("========================================\n");

        // --- Create 3 elevators with different scheduling strategies ---
        Elevator e1 = new Elevator(1, 800.0, new LookStrategy());   // LOOK
        Elevator e2 = new Elevator(2, 800.0, new SSTFStrategy());   // SSTF
        Elevator e3 = new Elevator(3, 800.0, new FCFSStrategy());   // FCFS

        List<Elevator> elevators = Arrays.asList(e1, e2, e3);

        // --- Create the system with NearestElevator selection ---
        ElevatorSelectionStrategy selectionStrategy = new NearestElevatorStrategy();
        ElevatorSystem system = new ElevatorSystem(elevators, selectionStrategy);

        // --- Create hall panels on floors 0, 3, 7 ---
        OutsideHallPanel hallFloor0 = new OutsideHallPanel(system, 0);
        OutsideHallPanel hallFloor3 = new OutsideHallPanel(system, 3);
        OutsideHallPanel hallFloor7 = new OutsideHallPanel(system, 7);

        // --- Create inside panels for each elevator ---
        InsideElevatorPanel insidePanel1 = new InsideElevatorPanel(e1);
        InsideElevatorPanel insidePanel2 = new InsideElevatorPanel(e2);
        InsideElevatorPanel insidePanel3 = new InsideElevatorPanel(e3);

        // =============================================
        // SCENARIO 1: Hall button presses (External)
        // =============================================
        System.out.println("--- SCENARIO 1: External Requests ---\n");

        hallFloor0.pressUpButton();    // Someone on floor 0 wants to go UP
        hallFloor7.pressDownButton();  // Someone on floor 7 wants to go DOWN
        hallFloor3.pressUpButton();    // Someone on floor 3 wants to go UP

        System.out.println();

        // =============================================
        // SCENARIO 2: Inside panel button presses (Internal)
        // =============================================
        System.out.println("--- SCENARIO 2: Internal Requests ---\n");

        // Passenger in Elevator 1 presses floor 5 and 8
        insidePanel1.pressFloorButton(5);
        insidePanel1.pressFloorButton(8);

        // Passenger in Elevator 2 presses floor 2
        insidePanel2.pressFloorButton(2);

        System.out.println();

        // =============================================
        // SCENARIO 3: Simulate elevator movement
        // =============================================
        System.out.println("--- SCENARIO 3: Simulating Movement (10 steps) ---\n");

        for (int step = 1; step <= 10; step++) {
            System.out.println(">>>>> Step " + step + " <<<<<");
            system.stepAll();
            System.out.println();
        }

        // =============================================
        // SCENARIO 4: Maintenance mode
        // =============================================
        System.out.println("--- SCENARIO 4: Maintenance Mode ---\n");

        e3.setMaintenance(true);  // Put elevator 3 under maintenance

        // New request — elevator 3 should NOT be selected
        hallFloor0.pressUpButton();

        System.out.println();

        // Bring it back
        e3.setMaintenance(false);
        System.out.println();

        // =============================================
        // SCENARIO 5: Disabled floors
        // =============================================
        System.out.println("--- SCENARIO 5: Disabled Floor ---\n");

        insidePanel1.disableFloor(6);
        insidePanel1.pressFloorButton(6);  // Should be rejected
        insidePanel1.pressFloorButton(9);  // Should work

        System.out.println();

        // Re-enable and try again
        insidePanel1.enableFloor(6);
        insidePanel1.pressFloorButton(6);  // Should work now

        System.out.println();

        // =============================================
        // SCENARIO 6: Door and bell controls
        // =============================================
        System.out.println("--- SCENARIO 6: Door & Bell ---\n");

        insidePanel1.openDoor();
        insidePanel1.closeDoor();
        insidePanel1.ringBell();

        System.out.println();

        // =============================================
        // SCENARIO 7: Switch selection strategy at runtime
        // =============================================
        System.out.println("--- SCENARIO 7: Strategy Swap ---\n");

        system.setSelectionStrategy(new OddEvenElevatorStrategy());
        hallFloor3.pressUpButton();  // Should use OddEven now

        System.out.println();

        // =============================================
        // SCENARIO 8: Load check
        // =============================================
        System.out.println("--- SCENARIO 8: Load Check ---\n");

        e1.setCurrentLoad(750);
        System.out.println("Elevator 1 can take 100kg more? " + e1.canTakeLoad(100)); // false
        System.out.println("Elevator 1 can take 40kg more?  " + e1.canTakeLoad(40));  // true

        System.out.println("\n========================================");
        System.out.println("   SIMULATION COMPLETE");
        System.out.println("========================================");
    }
}
