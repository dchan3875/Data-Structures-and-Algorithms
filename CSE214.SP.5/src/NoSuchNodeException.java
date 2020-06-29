/**
 * The NoSuchNodeException class is a custom exception class that indicates that the SceneNode does not
 * exist.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #5 CSE214</dd>
 * </dl>
 */
public class NoSuchNodeException extends Exception
{
    /**
     * Constructor that supers the message.
     *
     * @param msg
     *      The error message.
     */
    public NoSuchNodeException(String msg)
    {
        super(msg);
    }
}
