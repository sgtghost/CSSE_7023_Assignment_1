package towersim.util;

/**
 * Exception thrown when there is no suitable gate available for an aircraft.
 */

public class NoSuitableGateException extends Exception {
    /**
     * Constructs a NoSuitableGateException with no detail message.
     */
    public NoSuitableGateException() {
    }

    /**
     * Constructs a NoSuitableGateException
     * that contains a helpful detail message
     * @param message explaining why the exception occurred.
     */
    public NoSuitableGateException(String message) {
        super(message);
    }
}
