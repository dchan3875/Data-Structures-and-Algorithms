/**
 * This is a custom exception class for when the HiringTable is full.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #1 CSE214</dd>
 * </dl>
 *
 */
public class FullTableException extends Exception
{
    public FullTableException(String exceptionMsg)
    {
        super(exceptionMsg);
    }
}
