/**
 * The HiringSystem class is where the system is set up. Interactions and main control of the table are handled here.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #1 CSE214</dd>
 * </dl>
 *
 */
import java.util.Scanner;

public class HiringSystem
{

    private static HiringTable table; // The HiringTable to be instantiated.
    private static HiringTable backup; // Backup of the HiringTable if the user chooses to create one.

    /**
     * Sets up the application by instantiating the table and calling a method to print the command menu.
     * @param args
     *     String arguments
     */
    public static void main(String[] args)
    {
        table = new HiringTable();
        showCommands();
    }

    /**
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>A formatted command menu is printed and a scanner is created to record user input.</dd>
     * </dl>
     */
    public static void showCommands()
    {
        System.out.println(String.format("%-10s%-20s","(A)", "Add Applicant"));
        System.out.println(String.format("%-10s%-20s","(R)", "Remove Applicant"));
        System.out.println(String.format("%-10s%-20s","(G)", "Get Applicant"));
        System.out.println(String.format("%-10s%-20s","(P)", "Print List"));
        System.out.println(String.format("%-10s%-20s","(RS)", "Refine Search"));
        System.out.println(String.format("%-10s%-20s","(S)", "Size"));
        System.out.println(String.format("%-10s%-20s","(D)", "Backup"));
        System.out.println(String.format("%-10s%-20s","(Q)", "Quit"));

        Scanner inputScan = new Scanner(System.in);
        System.out.print("Enter an option: ");
        enteredCommand(inputScan.nextLine().toUpperCase());
    }

    /**
     * @param command
     *     The command the user entered that was recorded by the scanner.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The new HiringTable is set up.</dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The user input will be processed and execute a command.</dd>
     * </dl>
     *
     */
    public static void enteredCommand(String command)
    {
        String cmd = command;
        if(cmd.isEmpty()) //if empty, redisplay the command menu
        {
            showCommands();
        }
        if(cmd.equals("A")) //Add Applicant
        {
            Applicant newApp = new Applicant();

            Scanner inputScan = new Scanner(System.in);
            System.out.print("Enter Applicant Name: ");
            try
            {
                newApp.setApplicantName(inputScan.nextLine());
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }

            System.out.print("Enter Applicant GPA: ");
            try
            {
                newApp.setApplicantGPA(Double.valueOf(inputScan.nextLine()));
            }
            catch(IllegalArgumentException e)
            {
                System.out.println("GPA must be a non negative number.");
            }

            System.out.print("Enter Applicant College: ");
            try{
                newApp.setApplicantCollege(inputScan.nextLine());
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
                showCommands();
            }

            String[] companies = new String[table.MAX_COMPANIES];
            for(int i = table.MAX_COMPANIES; i > 0; i--) //Counts down number of companies the user can input.
            {
                System.out.print("Enter up to " + i + " Companies: ");
                String currCompany = inputScan.nextLine();
                try
                {
                    if (currCompany.isEmpty() && i == HiringTable.MAX_COMPANIES)
                    {
                        throw new IllegalArgumentException("You must enter at least one company.");
                    }
                    if (!currCompany.isEmpty())
                        companies[HiringTable.MAX_COMPANIES - i] = currCompany;
                    else
                        break;
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                    showCommands();
                }
            }
            newApp.setCompanyName(companies);

            String[] skills = new String[table.MAX_SKILLS];
            for(int i = table.MAX_SKILLS; i > 0; i--) //Counts down number of skills the user can input.
            {
                System.out.print("Enter up to " + i + " skills: ");
                String currSkill = inputScan.nextLine();
                try {
                    if (currSkill.isEmpty() && i == HiringTable.MAX_SKILLS)
                    {
                        throw new IllegalArgumentException("You must enter at least one skill.");
                    }
                    if (!currSkill.isEmpty())
                        skills[HiringTable.MAX_SKILLS - i] = currSkill;
                    else
                        break;
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                    showCommands();
                }
            }
            newApp.setApplicantSkills(skills);

            try {
                table.addApplicant(newApp);
            }
            catch(FullTableException e)
            {
                System.out.println(e.getMessage());
            }

            showCommands();
        }


        if(cmd.equals("R")) //Remove Applicant
        {
            Scanner inputScan = new Scanner(System.in);
            System.out.print("Enter applicant name: ");
            try{
                table.removeApplicant(inputScan.next());
            }
            catch(ApplicantNotFoundException e)
            {
                e.printStackTrace();
            }
            showCommands();
        }

        if(cmd.equals("G")) //Get Applicant - retrieves an Applicant from the system
        {
            Scanner inputScan = new Scanner(System.in);
            System.out.print("Enter Applicant Name: ");
            try
            {
                System.out.println(table.getApplicant(inputScan.nextLine()));
            } catch (ApplicantNotFoundException e) {
                System.out.println(e.getMessage());
            }
            showCommands();
        }

        if(cmd.equals("P")) //Print Table - prints formatted table
        {
            table.printApplicantTable();
            showCommands();
        }

        if(cmd.equals("RS")) //Refine Search - Search for Applicant with filters
        {
            Scanner inputScan = new Scanner(System.in);
            String companyFilter = "";
            String skillFilter = "";
            String collegeFilter = "";
            Double minGPA = 0.0;

            System.out.print("Enter a company to filter for: ");
            companyFilter = inputScan.nextLine();

            System.out.print("Enter a skill to filter for: ");
            skillFilter = inputScan.nextLine();

            System.out.print("Enter a college to filter for: ");
            collegeFilter = inputScan.nextLine();

            System.out.print("Enter a minimum GPA to filter for: ");
            String possibleGpa = inputScan.nextLine();
            if(possibleGpa.isEmpty())
                minGPA = null;
            else
                minGPA = Double.valueOf(possibleGpa);


            HiringTable.refineSearch(table, companyFilter, skillFilter, collegeFilter, minGPA);

            showCommands();


        }

        if(cmd.equals("S")) //Size - the number of Applicants currently in the system.
        {
            System.out.println("There are " + table.size() + " applicants in the hiring system.");
            showCommands();
        }

        if(cmd.equals("B")) //Backup - creates backup of current system.
        {
            backup = (HiringTable) table.clone();
            System.out.println("Successfully created backup.");
            showCommands();
        }

        if(cmd.equals("CB")) //Check Backup - Checks if backup is same as current table.
        {
            if(table.checkBackup(backup))
                System.out.println("Current list is the same as the backup copy.");
            else
                System.out.println("Current list is not the same as the backup copy.");

            showCommands();
        }

        if(cmd.equals("RB")) //Revert Backup - Reverts system to the backup.
        {
            table = (HiringTable) backup.clone();
            System.out.println("Successfully reverted to the backup copy.");

            showCommands();
        }

        if(cmd.equals("Q")) //Quit - Exit program.
        {
            System.out.println("Quitting program . . .");
            System.exit(0);
        }
    }
}
