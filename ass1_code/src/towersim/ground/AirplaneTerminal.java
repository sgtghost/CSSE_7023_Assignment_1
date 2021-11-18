package towersim.ground;

/**
 * Represents an airport terminal that is designed to accommodate airplanes.
 */
public class AirplaneTerminal extends Terminal {

    /**
     * Creates a new Terminal with the given unique terminal number.
     * It is not the responsibility of the Terminal class to ensure terminal numbers are unique.
     * Instead, the user should check that
     * no other terminal of the same type exists with the same terminal number
     * when instantiating a new terminal.
     * <p>
     * Newly created terminals should not be in a state of emergency by default.
     *
     * @param terminalNumber identifying number of this terminal
     */
    public AirplaneTerminal(int terminalNumber) {
        super(terminalNumber);
        this.terminalType = "AirplaneTerminal";
    }
}
