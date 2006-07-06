package de.laures.cewolf;

/**
 * Base class of all Cewolf related exceptions
 * @author  Guido Laures
 */
public class CewolfRuntimeException extends RuntimeException {

    /**
     * Constructs an instance of <code>CewolfRuntimeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public CewolfRuntimeException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs exception with causing exception
     * @param msg message
     * @param cause cause exception
     */
    public CewolfRuntimeException(String msg, Exception cause) {
        super(msg, cause);
    }
    
}