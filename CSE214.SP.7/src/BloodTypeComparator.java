/**
 * The BloodTypeComparator class implements a Comparator for Patient to be used for sorting.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #7 CSE214</dd>
 * </dl>
 */

import java.util.Comparator;

public class BloodTypeComparator implements Comparator<Patient>
{
    /**
     * Compares the blood type of two patients.
     *
     * @param o1
     *      First patient
     * @param o2
     *      Second patient
     * @return
     *      0 if they are equal, or the difference of the string of o1 and o2 blood types.
     */
    public int compare(Patient o1, Patient o2)
    {
        return(o1.getBloodType().getBloodType().compareTo(o2.getBloodType().getBloodType()));
    }
}
