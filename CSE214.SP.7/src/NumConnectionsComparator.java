/**
 * The NumConnectionsComparator class implements a Comparator for Patient to be used for sorting.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #7 CSE214</dd>
 * </dl>
 */

import java.util.Comparator;

public class NumConnectionsComparator implements Comparator<Patient>
{
    /**
     * Compares the number of connections between two patients.
     *
     * @param p1
     *      First patient
     * @param p2
     *      Second patient
     * @return
     *      0 if the number of connections are equal, 1 if p2 has more, -1 if p1 has more.
     */
    public int compare(Patient p1, Patient p2) {

        if (p1.getNumConnections() == p2.getNumConnections())
            return 0;
        else if (p1.getNumConnections() < p2.getNumConnections())
            return 1;
        else
            return -1;
    }
}
