/**
 * The NameComparator class implements the Comparator interface, used for sorting users by their name.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #7 CSE214</dd>
 * </dl>
 */

import java.util.Comparator;

public class NameComparator implements Comparator<User>
{
    /**
     * Compares two users by their name alphabetically.
     *
     * @param o1
     *      The first user to be compared.
     * @param o2
     *      The second user to be compared.
     * @return
     *      0 if the names are equal, 1 if the second user precedes alphabetically, -1 if the first user precedes alphabetically.
     */
    public int compare(User o1, User o2)
    {
        return(o1.getUserName().compareTo(o2.getUserName()));
    }
}
