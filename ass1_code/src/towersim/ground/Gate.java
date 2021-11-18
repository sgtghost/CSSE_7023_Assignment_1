package towersim.ground;

import towersim.aircraft.Aircraft;
import towersim.util.NoSpaceException;

/**
 * Represents an aircraft gate with facilities for a single aircraft to be parked.
 */
public class Gate {
    /**
     * gateNumber - identifying number of this gate
     */
    private int gateNumber;

    /**
     * occupied - whether an aircraft is currently parked at the gate
     */
    private boolean occuiped;

    /**
     * aircraft - aircraft parked at gate
     */
    private Aircraft aircraft;

    /**
     * Creates a new Gate with the given unique gate number.
     * Gate numbers should be unique across all terminals in the airport.
     *
     * Initially, there should be no aircraft occupying the gate.
     *
     * Parameters:
     * gateNumber - identifying number of this gate
     *
     * @param gateNumber identifying number of this gate
     */
    public Gate(int gateNumber) {
        this.gateNumber = gateNumber;
        this.occuiped = false;
        this.aircraft = null;
    }

    /**
     * Returns this gate's gate number.
     * @return gate number
     */
    public int getGateNumber() {
        return this.gateNumber;
    }

    /**
     * Parks the given aircraft at this gate, so that the gate becomes occupied.
     * If the gate is already occupied,
     * then a NoSpaceException should be thrown and the aircraft should not be parked.
     *
     * Parameters:
     * aircraft - aircraft to park at gate
     * Throws:
     * NoSpaceException - if the gate is already occupied by an aircraft
     *
     * @param aircraft aircraft to park at gate
     * @throws NoSpaceException if the gate is already occupied by an aircraft
     */
    public void parkAircraft(Aircraft aircraft) throws NoSpaceException {
        if (this.isOccupied()) {
            throw new NoSpaceException();
        } else {
            this.occuiped = true;
            this.aircraft = aircraft;
        }
    }

    /**
     * Removes the currently parked aircraft from the gate.
     * If no aircraft is parked at the gate, no action should be taken.
     */
    public void aircraftLeaves() {
        if (this.isOccupied()) {
            this.occuiped = false;
            this.aircraft = null;
        }
    }

    /**
     * Returns true if there is an aircraft currently parked at the gate, or false otherwise.
     * Returns:
     * whether an aircraft is currently parked
     *
     * @return true if the gate is occupied, false else.
     */
    public boolean isOccupied() {
        return this.occuiped;
    }

    /**
     * Returns the aircraft currently parked at the gate, or null if there is no aircraft parked.
     * Returns:
     * currently parked aircraft
     *
     * @return currently parked aircraft
     */
    public Aircraft getAircraftAtGate() {
        return this.aircraft;
    }

    /**
     * Returns the human-readable string representation of this gate.
     *
     * The format of the string to return is
     * Gate gateNumber [callsign]
     *
     * where gateNumber is the gate number of this gate
     * and callsign is the callsign of the aircraft parked at this gate,
     * or empty if the gate is unoccupied.
     *
     * For example: "Gate 15 [ABC123]" or "Gate 24 [empty]".
     * @return string representation of this gate
     */
    @Override
    public String toString() {
        String string = "Gate " + this.getGateNumber();
        if (this.isOccupied()) {
            string = string + " [" + this.getAircraftAtGate().getCallsign() + "]";
        } else {
            string = string + " [empty]";
        }
        return string;
    }
}
