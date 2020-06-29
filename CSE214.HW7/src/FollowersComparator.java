/**
 * The FollowersComparator class implements the Comparator interface, used for sorting users by number of followers.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #7 CSE214</dd>
 * </dl>
 */

import java.util.Comparator;

public class FollowersComparator implements Comparator<User>
{
    private FollowerGraph fg; //FollowerGraph to be used to compare

    /**
     * Constructor with a FollowerGraph parameter
     *
     * @param graph
     *      The FollowGraph the users are located in to be compared.
     */
    public FollowersComparator(FollowerGraph graph)
    {
        super();
        fg = graph;
    }

    /**
     * Compares two user objects by their number of followers for sorting.
     *
     * @param o1
     *      The first user to be compared.
     * @param o2
     *      The second user to be compared.
     * @return
     *      0 if the users have the same number of followers, 1 if the second user has more, -1 if the first has more.
     */
    public int compare(User o1, User o2) {

        if(fg.getFollowerCount(o1) == fg.getFollowerCount(o2))
            return 0;
        else if (fg.getFollowerCount(o1) < fg.getFollowerCount(o2))
            return 1;
        else
            return -1;
    }
}
