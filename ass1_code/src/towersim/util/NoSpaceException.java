package towersim.util;

/**
 * Exception thrown when there is insufficient space to undertake an action.
 */



public class NoSpaceException extends Exception {
    /**
     * Constructs a NoSpaceException with no detail message.
     */
    public NoSpaceException() {
    }

    /**
     * Constructs a NoSpaceException that contains a helpful detail message.
     * @param message explaining why the exception occurred.
     */
    public NoSpaceException(String message) {
        super(message);
    }
}
