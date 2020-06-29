/**
 * The HiringTable class implements a table of Applicant objects for the HiringSystem.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #1 CSE214</dd>
 * </dl>
 *
 */
public class HiringTable
{
    private Applicant[] data;  // An array that holds all the Applicants

    public static final int MAX_SKILLS = 3; // Maximum number of skills that each Applicant can have
    public static final int MAX_COMPANIES = 3; // Maximum number of companies that each Applicant can have
    public static final int MAX_APPLICANTS = 50; // Maximum number of Applicant objects that the array can hold
    private int numApplicants; // Counter that keeps track of the current number of Applicants in the array

    /**
     * Default constructor with no parameters.
     *
     *
     * Instantiates data array with size of MAX_APPLICANTS.
     */
    public HiringTable()
    {
        data = new Applicant[MAX_APPLICANTS];
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HiringTable has been instantiated.</dd>
     * </dl>
     *
     * @return
     *    Current number of Applicant objects in this HiringTable.
     */
    public int size()
    {
        return numApplicants;
    }

    /**
     *@param newApplicant
     *    The new Applicant object to be added to the data array.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HiringTable and the new Applicant has been instantiated. numApplicants must be less than MAX_APPLICANTS.</dd>
     * </dl>
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The new Applicant is added to the end of the list.</dd>
     * </dl>
     * @throws FullTableException
     *    Indicates that there is no more room in the HiringTable for new Applicants.
     */
    public void addApplicant(Applicant newApplicant) throws FullTableException
    {
        if(numApplicants == MAX_APPLICANTS)
        {
            throw new FullTableException("There is no more room for new applicants.");
        }
        else
        {
            data[numApplicants] = newApplicant;
            numApplicants++;
            System.out.println("Applicant " + newApplicant.getApplicantName() + " has been successfully added to the hiring system.");
        }
    }

    /**
     *
     * @param name
     *    The name of the Applicant to be removed from the list.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HiringTable has been instantiated.</dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The specified Applicant has been removed and the list is shifted so that there are no gaps.</dd>
     * </dl>
     *
     * @throws ApplicantNotFoundException
     *    Indicates that the Applicant with the given name has not been found.
     */
    public void removeApplicant(String name) throws ApplicantNotFoundException
    {
        Applicant[] copy = new Applicant[MAX_APPLICANTS];
        int j = 0;
        for(int i = 0; i < numApplicants; i++)
        {
            if(!(data[j].getApplicantName().equals(name)))
            {
                copy[i] = data[j];
            }
        }
        throw new ApplicantNotFoundException("Applicant not found.");
    }

    /**
     *
     * @param name
     *     The name the of the Applicant to retrieve.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HiringTable has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The Applicant with the corresponding name.
     * @throws ApplicantNotFoundException
     *     Indicates that the applicant with the given name was not found.
     */
    public Applicant getApplicant(String name) throws ApplicantNotFoundException
    {
        for(int i = 0; i < numApplicants; i++)
        {
            if(data[i].getApplicantName().equals(name))
            {
                return data[i];
            }
        }
        throw new ApplicantNotFoundException("Applicant not found.");
    }

    /**
     *
     * @param table
     *     The list of applicants to search in
     * @param company
     *     The company that must be included in the Applicant's application.
     * @param skill
     *     The skill that must be included in the Applicant's application.
     * @param college
     *     The college that must be included in the Applicant's application.
     * @param GPA
     *     The minimum GPA that must be included in the Applicant's application.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HiringTable has been instantiated.</dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Displays a formatted table of each Applicant filtered from the HiringTable.</dd>
     * </dl>
     */
    public static void refineSearch(HiringTable table, String company, String skill, String college, Double GPA)
    {
        int numFiltered = 0;
        Applicant[] filtered = new Applicant[table.getNumApplicants()];
        for(int i = 0; i < table.getNumApplicants(); i++) //Checks if the applicant matches user input data, or if the string is empty, the filter is ignored.
        {
            Applicant currentApp = table.data[i];

            if ((company.isEmpty() || filterInclude(currentApp.getCompanyName(), company)) && (skill.isEmpty() || filterInclude(currentApp.getApplicantSkills(), skill)) &&
                        (college.isEmpty() || currentApp.getApplicantCollege().equals(college)) && ((GPA == null || currentApp.getApplicantGPA() == GPA)))
            {
                    filtered[numFiltered] = currentApp;
                    numFiltered++;
            }



        }

        System.out.printf("%-30s%-20s%-10s%-25s%-20s\n","Company Name", "Applicant", "GPA", "College", "Skills");
        System.out.println("------------------------------"+
                "----------------------------------------" +
                "--------------------------------" + "--------------");
        if(numFiltered != 0)
        {
            for (int i = 0; i < numFiltered; i++) {
                System.out.printf("%-30s%-20s%-10s%-25s%-20s\n", strArrayFormat(filtered[i].getCompanyName()),
                        filtered[i].getApplicantName(), filtered[i].getApplicantGPA(), filtered[i].getApplicantCollege(),
                        strArrayFormat(filtered[i].getApplicantSkills()));
            }
        }
        else {
            System.out.println("No applicants were found with the given filters.");
        }
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HiringTable has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     A copy of this HiringTable object.
     */
    public Object clone()
    {
        HiringTable copy = new HiringTable();
        copy.setNumApplicants(this.numApplicants);

        for(int i = 0; i < this.numApplicants; i++)
        {
            try {
                copy.addApplicant((Applicant)data[i].clone());
            } catch (FullTableException e) {
                e.printStackTrace();
            }
        }

        return copy;
    }

    /**
     * @param backup
     *     A previous version of the HiringTable.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HiringTable and backuphas been instantiated.</dd>
     * </dl>
     *
     * @return
     *     True if the backup has the same list of Applicants as this HiringTable.
     */
    public boolean checkBackup(HiringTable backup)
    {
        if(this.getNumApplicants() != backup.getNumApplicants()) //if the number of applicants isn't the same, the table has been changed.
        {
            return false;
        }
        else
        {
            for(int i = 0; i < numApplicants; i++)
            {
                if(!(data[i].equals(backup.data[i])))
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HiringTable has been instantiated.</dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Prints a formatted table of each item in the list.</dd>
     * </dl>
     */
    public void printApplicantTable()
    {
        System.out.printf("%-30s%-20s%-10s%-25s%-20s\n","Company Name", "Applicant", "GPA", "College", "Skills");
        System.out.println("------------------------------"+
                "----------------------------------------" +
                "--------------------------------"
                +"--------------");

        for(int i = 0; i < numApplicants; i++)
        {
            System.out.printf("%-30s%-20s%-10s%-25s%-20s\n",strArrayFormat(data[i].getCompanyName()), data[i].getApplicantName(),
                    data[i].getApplicantGPA(), data[i].getApplicantCollege(), strArrayFormat(data[i].getApplicantSkills()));
        }

    }

    /**
     * @param arr
     *     The array to be formatted into a string to be printed.
     *
     * @return
     *     A string with all the elements in the array formatted with commas.
     */
    public static String strArrayFormat(String[] arr)
    {
        String str = arr[0];

        for(int i = 1; i < arr.length; i++ )
        {
            if(arr[i] != null)
                str += ", " + arr[i];
        }
        return str;
    }

    /**
     *
     * @param arr
     *     The array to be searched through.
     * @param s
     *     The string to be looked for in the array.
     * @return
     *     True if the array contains the string, False if it does not.
     */
    public static boolean filterInclude(String[] arr, String s)
    {
        for(String str : arr)
        {
            if(str.equals(s))
                return true;
        }
        return false;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HiringTable has been instantiated.</dd>
     * </dl>
     *
     * @param num
     *     The number to set the counter of Applicants to.
     */
    public void setNumApplicants(int num)
    {
        this.numApplicants = num;
    }

    /**
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HiringTable has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The number of Applicants that are currently in the table.
     */
    public int getNumApplicants()
    {
        return numApplicants;
    }
}
