/**
 * PositionNotAvailableException is a custom exception class that indicates that the current OrganismNode does not have any left, middle, and right nodes available.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #5 CSE214</dd>
 * </dl>
 */

public class PositionNotAvailableException extends Exception
{
    /**
     * Constructor that calls the parent Exception constructor with the error message.
     * @param msg
     *     The error message.
     */
    public PositionNotAvailableException(String msg)
    {
        super(msg);
    }
}
