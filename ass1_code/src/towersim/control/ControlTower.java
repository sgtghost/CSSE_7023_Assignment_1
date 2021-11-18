package towersim.control;

import towersim.aircraft.Aircraft;
import towersim.aircraft.AircraftType;
import towersim.ground.Gate;
import towersim.ground.Terminal;
import towersim.tasks.TaskType;
import towersim.util.NoSpaceException;
import towersim.util.NoSuitableGateException;
import towersim.util.Tickable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a the control tower of an airport.
 * The control tower is responsible for managing the operations of the airport,
 * including arrivals and departures in/out of the airport,
 * as well as aircraft that need to be loaded with cargo at gates in terminals.
 */
public class ControlTower implements Tickable {
    /**
     * a list of all terminals currently managed by this control tower
     */
    private List<Terminal> terminals;

    /**
     * Creates a new ControlTower.
     */
    public ControlTower() {
        this.terminals = new ArrayList<>();
    }

    /**
     * Adds the given terminal to the jurisdiction of this control tower.
     *
     * @param terminal terminal to add
     */
    public void addTerminal(Terminal terminal) {
        this.terminals.add(terminal);
    }

    /**
     * Returns a list of all terminals currently managed by this control tower.
     * The order in which terminals appear in this list should be the same as
     * the order in which they were added by calling addTerminal(Terminal).
     * Adding or removing elements from the returned list should not affect the original list.
     *
     * @return all terminals
     */
    public List<Terminal> getTerminals() {
        return this.terminals;
    }

    /**
     * Adds the given aircraft to the jurisdiction of this control tower.
     * If the aircraft's current task type is WAIT or LOAD,
     * it should be parked at a suitable gate as found by the findUnoccupiedGate(Aircraft) method.
     * If there is no suitable gate for the aircraft,
     * the NoSuitableGateException thrown by findUnoccupiedGate()
     * should be propagated out of this method.
     *
     * @param aircraft aircraft to add
     * @throws NoSuitableGateException if there is no suitable gate for an aircraft
     * with a current task type of WAIT or LOAD
     */
    public void addAircraft(Aircraft aircraft) throws NoSuitableGateException {
        TaskType tasktype = aircraft.getTaskList().getCurrentTask().getType();
        if (tasktype.equals(TaskType.WAIT) | tasktype.equals(TaskType.LOAD)) {
            try {
                Gate gate = this.findUnoccupiedGate(aircraft);
                gate.parkAircraft(aircraft);
            } catch (NoSuitableGateException | NoSpaceException ex) {
                throw new NoSuitableGateException();
            }
        }
    }

    /**
     * Returns a list of all aircraft currently managed by this control tower.
     * The order in which aircraft appear in this list should be the same as
     * the order in which they were added by calling addAircraft(Aircraft).
     *
     * Adding or removing elements from the returned list should not affect the original list.
     *
     * @return all aircraft
     */
    public List<Aircraft> getAircraft() {
        List<Aircraft> aircrafts = new ArrayList<>();
        for (Terminal terminal : this.getTerminals()) {
            for (Gate gate : terminal.getGates()) {
                Aircraft aircraft = gate.getAircraftAtGate();
                if (aircraft != null) {
                    aircrafts.add(aircraft);
                }
            }
        }
        return aircrafts;
    }

    /**
     * Attempts to find an unoccupied gate in a compatible terminal for the given aircraft.
     * Only terminals of the same type as the aircraft's AircraftType
     * (see AircraftCharacteristics.type) should be considered.
     * For example, for an aircraft with an AircraftType of AIRPLANE,
     * only AirplaneTerminals may be considered.
     *
     * For each compatible terminal,
     * the Terminal.findUnoccupiedGate() method should be called
     * to attempt to find an unoccupied gate in that terminal.
     * If findUnoccupiedGate() does not find a suitable gate,
     * the next compatible terminal in the order they were added should be checked instead,
     * and so on.
     *
     * If no unoccupied gates could be found across all compatible terminals,
     * a NoSuitableGateException should be thrown.
     *
     * @param aircraft aircraft for which to find gate
     * @return gate for given aircraft if one exists
     * @throws NoSuitableGateException  if no suitable gate could be found
     */
    public Gate findUnoccupiedGate(Aircraft aircraft) throws NoSuitableGateException {
        String aircraftType = aircraft.getCharacteristics().type.toString();
        Gate gate = null;
        for (Terminal terminal : this.getTerminals()) {
            String[] strings = terminal.toString().split("T");
            String terminalType = strings[0].toUpperCase();
            if (terminalType.equals(aircraftType)) {
                try {
                    gate = terminal.findUnoccupiedGate();
                } catch (NoSuitableGateException ex) {
                    ;
                }
            }
        }
        if (gate == null) {
            throw new NoSuitableGateException();
        } else {
            return gate;
        }
    }

    /**
     * Finds the gate where the given aircraft is parked,
     * and returns null if the aircraft is not parked at any gate in any terminal.
     *
     * @param aircraft aircraft whose gate to find
     * @return gate occupied by the given aircraft; or null if none exists
     */
    public Gate findGateOfAircraft(Aircraft aircraft) {
        Gate aircraftGate = null;
        for (Terminal terminal : this.getTerminals()) {
            for (Gate gate : terminal.getGates()) {
                Aircraft parkedAircraft = gate.getAircraftAtGate();
                if (parkedAircraft.equals(aircraft)) {
                    aircraftGate = gate;
                }
            }
        }
        return aircraftGate;
    }

    /**
     * Advances the simulation by one tick.
     * On each tick,
     * the control tower should call Aircraft.tick() on all aircraft managed by the control tower.
     *
     * Note that the actions performed by tick() are very simple
     * at the moment and will be expanded on in assignment 2.
     */
    @Override
    public void tick() {
        for (Terminal terminal : this.getTerminals()) {
            for (Gate gate : terminal.getGates()) {
                Aircraft aircraft = gate.getAircraftAtGate();
                aircraft.tick();
            }
        }
    }
}
