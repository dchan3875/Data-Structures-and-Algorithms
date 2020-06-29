/**
 * The FullSceneException class is a custom exception class that indicates that the SceneNode does not
 * have an empty child reference.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #5 CSE214</dd>
 * </dl>
 */
public class FullSceneException extends Exception
{
    /**
     * Constructor that supers the message.
     *
     * @param msg
     *      The error message.
     */
    public FullSceneException(String msg)
    {
        super(msg);
    }
}
