/**
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #1 CSE214</dd>
 * </dl>
 */
public class TransactionAlreadyExistsException extends Exception
{
    public TransactionAlreadyExistsException(String msg)
    {
        super(msg);
    }
}
