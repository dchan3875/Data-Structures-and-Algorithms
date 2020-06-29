/**
 * IsPlantException is a custom exception class that indicates that the OrganismNode is a plant and cannot be a predator.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #5 CSE214</dd>
 * </dl>
 */

public class IsPlantException extends Exception
{
    /**
     * Constructor that calls the parent Exception constructor with the error message.
     * @param msg
     *     The error message.
     */
    public IsPlantException(String msg)
    {
        super(msg);
    }
}
