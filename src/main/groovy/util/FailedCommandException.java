package util;

/**
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
public class FailedCommandException extends Exception {

    /**
     * The exit code returned by the failed command
     */
    int exitCode;

    public FailedCommandException(int exitCode, String message) {
        super(message);
        this.exitCode = exitCode;
    }

}
