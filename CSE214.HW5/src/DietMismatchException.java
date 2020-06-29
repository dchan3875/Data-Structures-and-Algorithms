/**
 * DietMismatchException is a custom exception class that indicates that the prey OrganismNode conflicts with the predator node's diet.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #5 CSE214</dd>
 * </dl>
 */

public class DietMismatchException extends Exception
{
    /**
     * Constructor that calls the parent Exception constructor with the error message.
     * @param msg
     *     The error message.
     */
    public DietMismatchException(String msg)
    {
        super(msg);
    }
}
