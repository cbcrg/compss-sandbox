package compss.piper_Original

/**
 * Created with IntelliJ IDEA.
 * User: bsanjuan
 * Author: Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 * Date: 5/30/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
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