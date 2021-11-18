package towersim.aircraft;

/**
 * Stores information about particular models of aircraft.
 * Characteristics of an individual aircraft include
 * the type of aircraft, its empty weight, fuel capacity, etc.
 */
public enum AircraftCharacteristics {

    //Enums

    /**
     * Narrow-body twin-jet airliner.
     */
    AIRBUS_A320(42600, 0, 27200, 150, AircraftType.AIRPLANE),

    /**
     * Wide-body quad-jet freighter.
     */
    BOEING_747_8F(197131, 137756, 226117, 0, AircraftType.AIRPLANE),

    /**
     * Long range, wide-body twin-jet airliner.
     */
    BOEING_787(119950, 0, 126206, 242, AircraftType.AIRPLANE),

    /**
     * Twin-jet regional airliner.
     */
    FOKKER_100(24375, 0, 13365, 97, AircraftType.AIRPLANE),

    /**
     * Four-seater light helicopter.
     */
    ROBINSON_R44(658, 0, 190, 4, AircraftType.HELICOPTER),

    /**
     * Twin-engine heavy-lift helicopter.
     */
    SIKORSKY_SKYCRANE(8724, 9100, 3328, 0, AircraftType.HELICOPTER);

    //Fields

    /**
     * Weight of aircraft with no load or fuel, in kilograms.
     */
    public final int emptyWeight;

    /**
     * Maximum amount of freight able to be carried, in kilograms.
     */
    public final int freightCapacity;

    /**
     * Maximum amount of fuel able to be carried, in litres.
     */
    public final double fuelCapacity;

    /**
     * Maximum number of passengers able to be carried.
     */
    public final int passengerCapacity;

    /**
     * Type of aircraft.
     */
    public final AircraftType type;

    AircraftCharacteristics(int emptyWeight, int freightCapacity,
                            double fuelCapacity, int passengerCapacity,
                            AircraftType type) {
        this.emptyWeight = emptyWeight;
        this.freightCapacity = freightCapacity;
        this.fuelCapacity = fuelCapacity;
        this.passengerCapacity = passengerCapacity;
        this.type = type;
    }

}

