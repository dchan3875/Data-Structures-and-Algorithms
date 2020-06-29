/**
 * The Applicant class sets up the main element to be used in HiringTable.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #1 CSE214</dd>
 * </dl>
 *
 */
public class Applicant
{

    private String[] companyName; //String array to store the companies
    private String applicantName; //Name of applicant
    private double applicantGPA; //GPA of applicant
    private String applicantCollege; //College of applicant
    private String[] applicantSkills; //String array to store applicant's skills

    /**
     * Default constructor of an Applicant.
     */
    public Applicant()
    {
        companyName = null;
        applicantName = null;
        applicantGPA = 0;
        applicantCollege = null;
        applicantSkills = null;

    }

    /**
     * Constructor with specific parameters
     * @param companyName
     *     String array of names of the companies
     * @param applicantName
     *     Applicant's name
     * @param applicantGPA
     *     Applicant's GPA
     * @param applicantCollege
     *     Applicant's college
     * @param applicantSkills
     *     String array of Applicant's skills
     */
    public Applicant(String[] companyName, String applicantName, double applicantGPA, String applicantCollege, String[] applicantSkills)
    {
        this.companyName = companyName;
        this.applicantName = applicantName;
        this.applicantGPA = applicantGPA;
        this.applicantCollege = applicantCollege;
        this.applicantSkills = applicantSkills;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     String array of the applicant's company names.
     */
    public String[] getCompanyName()
    {
        return companyName;
    }

    /**
     *
     * @param companyName
     *     The array of company names to be set to the applicant.
     * @throws IllegalArgumentException
     *     Indicates that an invalid company name was inputted.
     */
    public void setCompanyName(String[] companyName) throws IllegalArgumentException
    {
        this.companyName = companyName;

    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The applicant's name.
     */
    public String getApplicantName() {
        return applicantName;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @param applicantName
     *     Name to set the applicant's name to.
     * @throws IllegalArgumentException
     *     Indicates that a non alphabetical name was inputted.
     */
    public void setApplicantName(String applicantName) throws IllegalArgumentException
    {
        if(!(applicantName.matches("^[ A-Za-z]+$")))
            throw new IllegalArgumentException("Name input is invalid.");
        else
            this.applicantName = applicantName;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The applicant's GPA.
     */
    public double getApplicantGPA()
    {
        return applicantGPA;
    }

    /**
     *
     * @param applicantGPA
     *     The new GPA to set the applicant's to.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @throws IllegalArgumentException
     *     Indicates that a number was not inputted.
     */
    public void setApplicantGPA(double applicantGPA) throws IllegalArgumentException
    {
        if(applicantGPA < 0)
            throw new IllegalArgumentException("GPA must be a non negative number.");

        this.applicantGPA = applicantGPA;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     Applicant's college.
     */
    public String getApplicantCollege()
    {
        return applicantCollege;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @param applicantCollege
     *     The new college to be set to the Applicant.
     *
     * @throws IllegalArgumentException
     *     Indicates that the college parameter was blank.
     */
    public void setApplicantCollege(String applicantCollege) throws IllegalArgumentException
    {
        if(applicantCollege.isEmpty())
            throw new IllegalArgumentException("Applicant college cannot be blank.");
        this.applicantCollege = applicantCollege;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     Applicant's array of skills.
     */
    public String[] getApplicantSkills()
    {
        return applicantSkills;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @param applicantSkills
     *     String array of skills to be set for the Applicant.
     */
    public void setApplicantSkills(String[] applicantSkills)
    {
        this.applicantSkills = applicantSkills;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     A copy of this applicant.
     */
    public Object clone()
    {
        String[] clonedCompany = new String[this.companyName.length];
        String[] clonedSkills = new String[this.applicantSkills.length];
        for(int i = 0; i < this.companyName.length; i++)
            clonedCompany[i] = this.companyName[i]; //copies over the string in the original array

        for(int i = 0; i < this.applicantSkills.length; i++)
            clonedSkills[i] = this.applicantSkills[i];

        return new Applicant(clonedCompany, applicantName, applicantGPA, applicantCollege, clonedSkills);
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @param obj
     *     The object to be tested against this applicant.
     * @return
     *     True if the object is equivalent to this applicant, False if it is not.
     */
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Applicant))
        {
            return false;
        }
        Applicant appObj = (Applicant)obj;

        for(int i = 0; i < appObj.getCompanyName().length; i++) //Checks the equivalencies of the companies in both arrays
        {
            if(!(appObj.getCompanyName()[i].equals(this.companyName[i])))
                return false;
        }

        for(int i = 0; i < appObj.getApplicantSkills().length; i++) //Checks the equivalencies of the skills in both arrays
        {
            if(!(appObj.getApplicantSkills()[i].equals(this.applicantSkills[i])))
                return false;
        }
        return this.applicantName.equals(appObj.getApplicantName()) && this.applicantGPA == appObj.getApplicantGPA() && this.applicantCollege.equals(appObj.getApplicantCollege());
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Applicant has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     A formatted string that lists the Applicant's information.
     */
    public String toString()
    {
        String roundApplicantGPA = String.format("%.2f", applicantGPA);
        String s = "Applicant Name: " + applicantName + "\n" + "Applicant Applying From: " + strArrayFormat(applicantSkills) + "\n" +
                "Applicant GPA: " + roundApplicantGPA + "\n" + "Applicant College: " + applicantCollege + "\n"
                + "Applicant Skills: " + strArrayFormat(applicantSkills);

        return s;
    }

    /**
     *Helps format array for printing/.
     *
     * @param arr
     *     Array to be formatted for printing.
     *
     * @return
     *     A neatly formatted string
     */
    public String strArrayFormat(String[] arr)
    {
        String str = arr[0];

        for(int i = 1; i < arr.length; i++ )
        {
            if(arr[i] != null)
                str += ", " + arr[i];
        }
        return str;
    }

}
