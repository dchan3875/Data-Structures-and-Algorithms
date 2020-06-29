/**
 * The OrganComparator class implements a Comparator for Patient to be used for sorting.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #7 CSE214</dd>
 * </dl>
 */

import java.util.Comparator;

public class OrganComparator implements Comparator<Patient>
{
    /**
     * Compares two patients and alphabetizes the organ names.
     *
     * @param o1
     *      First patient
     * @param o2
     *      Second patient
     * @return
     *      0 if equal, 1 if o1 precedes o2, else -1
     */
    public int compare(Patient o1, Patient o2) {
        return(o1.getOrgan().compareTo(o2.getOrgan()));
    }
}
