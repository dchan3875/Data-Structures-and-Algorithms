/**
 * This is a custom exception class for when the Applicant is not found during a search.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #1 CSE214</dd>
 * </dl>
 *
 */
public class ApplicantNotFoundException extends Exception
{
    public ApplicantNotFoundException(String exceptionMsg)
    {
        super(exceptionMsg);
    }
}
