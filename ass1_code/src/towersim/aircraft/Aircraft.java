package towersim.aircraft;

import towersim.tasks.Task;
import towersim.tasks.TaskList;
import towersim.tasks.TaskType;
import towersim.util.EmergencyState;
import towersim.util.OccupancyLevel;
import towersim.util.Tickable;

/**
 * Represents an aircraft whose movement is managed by the system.
 */
public abstract class Aircraft implements OccupancyLevel, Tickable, EmergencyState {
    //Fields

    /**
     * Weight of a litre of aviation fuel, in kilograms.
     */
    public static final double LITRE_OF_FUEL_WEIGHT = 0.8;

    /**
     * callsign - unique callsign.
     */
    private String callsign;

    /**
     * characteristics - characteristics that describe this aircraft.
     */
    private AircraftCharacteristics characteristics;

    /**
     * tasks - task list to be used by aircraft.
     */
    private TaskList tasks;

    /**
     * fuelAmount - current amount of fuel onboard, in litres.
     */
    private double fuelAmount;

    /**
     * emergency - have emergency state or not - true or false.
     */
    private boolean emergency = false;

    //Constructor

    /**
     * Creates a new aircraft with the given callsign, task list, fuel capacity and amount.
     * Newly created aircraft should not be in a state of emergency by default.
     *
     * If the given fuel amount is less than zero
     * or greater than the aircraft's maximum fuel capacity
     * as defined in the aircraft's characteristics,
     * then an IllegalArgumentException should be thrown.
     *
     * @param callsign unique callsign
     * @param characteristics  characteristics that describe this aircraft
     * @param tasks task list to be used by aircraft
     * @param fuelAmount current amount of fuel onboard, in litres
     */
    protected Aircraft(String callsign, AircraftCharacteristics characteristics,
                       TaskList tasks, double fuelAmount) {
        this.callsign = callsign;
        this.characteristics = characteristics;
        this.tasks = tasks;
        this.fuelAmount = fuelAmount;
        this.emergency = false;

        boolean fuelAmountCheck = this.fuelAmountCheck(fuelAmount, characteristics.fuelCapacity);
        if (fuelAmountCheck) {
            ;
        } else {
            throw new IllegalArgumentException();
        }
    }

    //Methods

    /**
     * fuel amount check
     *
     * @param fuelAmount current amount of fuel onboard, in litres
     * @param fuelCapacity maximum fuel capacity of the plane
     *
     * @return true if the fuelAmount falls in the acceptable field: (0, fuelCapacity)
     */
    protected boolean fuelAmountCheck(double fuelAmount, double fuelCapacity) {
        return (fuelAmount >= 0 && fuelAmount <= fuelCapacity);
    }

    /**
     * Returns the callsign of the aircraft.
     *
     * @return aircraft callsign
     */
    public String getCallsign() {
        return this.callsign;
    }

    /**
     * Returns the current amount of fuel onboard, in litres.
     *
     * @return current fuel amount
     */
    public double getFuelAmount() {
        return this.fuelAmount;
    }

    /**
     * Returns this aircraft's characteristics.
     *
     * @return aircraft characteristics
     */
    public AircraftCharacteristics getCharacteristics() {
        return this.characteristics;
    }

    /**
     * Returns the percentage of fuel remaining, rounded to the nearest whole percentage, 0 to 100.
     * This is calculated as 100 multiplied by the fuel amount divided by the fuel capacity,
     * rounded to the nearest integer.
     *
     * @return percentage of fuel remaining
     */
    public int getFuelPercentRemaining() {
        double ratio = 100.0 * this.getFuelAmount() / this.getCharacteristics().fuelCapacity;
        return (int) Math.round(ratio);
    }

    /**
     * Returns the total weight of the aircraft in its current state.
     * Note that for the Aircraft class,
     * any passengers/freight carried is not included in this calculation.
     *
     * The total weight for an aircraft is calculated as the sum of:
     * the aircraft's empty weight,
     * and the amount of fuel onboard the aircraft multiplied by the weight of a litre of fuel
     *
     * @return total weight of aircraft in kilograms
     */
    public double getTotalWeight() {
        return this.getCharacteristics().emptyWeight + this.getFuelAmount() * LITRE_OF_FUEL_WEIGHT;
    }

    /**
     * Returns the task list of this aircraft.
     *
     * @return task list
     */
    public TaskList getTaskList() {
        return this.tasks;
    }

    /**
     * Returns the number of ticks required to load the aircraft at the gate.
     * Different types and models of aircraft have different loading times.
     *
     * @return time to load aircraft, in ticks
     */
    public abstract int getLoadingTime();

    /**
     * Updates the aircraft's state on each tick of the simulation.
     * Aircraft burn fuel while flying.
     * If the aircraft's current task is AWAY,
     * the amount of fuel on the aircraft should decrease by 10% of the total capacity.
     * If the fuel burned during an AWAY tick ,
     * it would result in the aircraft having a negative amount of fuel,
     * and the fuel amount should instead be set to zero.
     *
     * Aircraft are refuelled while loading at the gate.
     * If the aircraft's current task is LOAD,
     * the amount of fuel should increase by capacity/loadingTime litres of fuel.
     * For example, if the fuel capacity is 120 litres and loadingTime(returned by getLoadingTime())
     * is 3, the amount of fuel should increase by 40 litres each tick.
     * Note that refuelling should not result in the aircraft's fuel onboard
     * exceeding its maximum fuel capacity.
     */
    @Override
    public void tick() {
        Task currentTask = this.getTaskList().getCurrentTask();
        double totalCapacity = this.getCharacteristics().fuelCapacity;
        if (currentTask.getType().equals(TaskType.AWAY)) {
            double newFuelAmount = (this.getFuelPercentRemaining() - 10) * 0.01 * totalCapacity;
            this.fuelAmount = Math.max(newFuelAmount, 0);
        } else if (currentTask.getType().equals(TaskType.LOAD)) {
            int loadingTime = this.getLoadingTime();
            double loadingPerTick = totalCapacity / loadingTime;
            double newFuelAmount = this.getFuelAmount() + loadingPerTick;
            this.fuelAmount = Math.min(newFuelAmount, totalCapacity);
        }
    }

    /**
     * Returns the human-readable string representation of this aircraft.
     *
     * The format of the string to return is
     * aircraftType callsign model currentTask
     *
     * where aircraftType is the AircraftType of the aircraft's AircraftCharacteristics,
     * callsign is the aircraft's callsign,
     * model is the string representation of the aircraft's AircraftCharacteristics,
     * and currentTask is the task type of the aircraft's current task.
     *
     * If the aircraft is currently in a state of emergency, the format of the string to return is
     * aicraftType callsign model currentTask (EMERGENCY)
     * For example, "AIRPLANE ABC123 AIRBUS_A320 LOAD (EMERGENCY)".
     * @return string representation of this aircraft
     */
    @Override
    public String toString() {
        String aircraftType = this.getCharacteristics().type.toString();
        String callsign = this.getCallsign();
        String model = this.getCharacteristics().toString();
        String currentTask = this.getTaskList().getCurrentTask().getType().toString();
        String result = aircraftType + " " + callsign + " " + model + " " + currentTask;
        if (this.hasEmergency()) {
            result = result + " (EMERGENCY)";
        }
        return result;
    }

    /**
     * Declares a state of emergency.
     */
    @Override
    public void declareEmergency() {
        this.emergency = true;
    }

    /**
     * Clears any active state of emergency.
     */
    @Override
    public void clearEmergency() {
        this.emergency = false;
    }

    /**
     * Returns whether or not a state of emergency is currently active.
     * @return true if in emergency; false otherwise
     */
    @Override
    public boolean hasEmergency() {
        return this.emergency;
    }

}
