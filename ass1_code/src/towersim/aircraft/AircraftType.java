package towersim.aircraft;

/**
 * Represents the possible types that an aircraft can be.
 */
public enum AircraftType {
    // Enum definitions

    /**
     * Airplanes are powered aircraft with a fixed wing.
     */
    AIRPLANE(),

    /**
     * Helicopters generate lift with spinning rotors.
     */
    HELICOPTER();

    AircraftType(){
    }
}
