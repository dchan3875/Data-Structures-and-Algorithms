/**
 * The BloodType represents the blood type of a Patient and contains a static method for comparisons and is
 * serializable.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #7 CSE214</dd>
 * </dl>
 */

import java.io.Serializable;

public class BloodType implements Serializable
{
    private String bloodType; //String that holds the blood type.

    /**
     * Default constructor that instantiates bloodType.
     */
    public BloodType()
    {
        bloodType = "";
    }

    /**
     * Constructor with a parameter for the blood type.
     *
     * @param type
     *      The blood type to be set to.
     */
    public BloodType(String type)
    {
        bloodType = type;
    }

    /**
     * Determinies compatiability of two blood types.
     *
     * @param recipient
     *      The recipient's blood type.
     * @param donor
     *      The donor's blood type.
     * @return
     *      True if they are compatible , false if they are not.
     */
    public static boolean isCompatible(BloodType recipient, BloodType donor)
    {
        if(recipient != null && donor !=null)
        {
            if(recipient.bloodType.equals("O") && donor.bloodType.equals("O"))
                return true;

            if(recipient.bloodType.equals("A") && (donor.bloodType.equals("O") || donor.bloodType.equals("A")))
                return true;

            if(recipient.bloodType.equals("B") && (donor.bloodType.equals("O") || donor.bloodType.equals("B")))
                return true;

            if(recipient.bloodType.equals("AB") && !donor.bloodType.isBlank())
                return true;
        }
        return false;
    }

    /**
     * Getter for the blood type.
     *
     * @return
     *      The blood type.
     */
    public String getBloodType()
    {
        return bloodType;
    }
}
