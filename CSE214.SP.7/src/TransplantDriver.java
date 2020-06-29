/**
 * The TransplantDriver class creates/loads a new TransplantGraph to be used for the application. Allows users
 * to manipulate the graph and print in various ways.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #7 CSE214</dd>
 * </dl>
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class TransplantDriver
{
    public static final String DONOR_FILE = "donors.txt"; //File for the donors
    public static final String RECIPIENT_FILE = "recipients.txt"; //File for the recipients

    /**
     *
     * @param args
     *      String array of arguments.
     */
    public static void main(String[] args)
    {
        TransplantGraph tg;

        try
        {
            FileInputStream file = new FileInputStream("transplant.obj");
            ObjectInputStream fin  = new ObjectInputStream(file);
            tg = (TransplantGraph) fin.readObject();
            fin.close();
            System.out.println("TransplantGraph is loaded from transplant.obj.\n");
        }
        catch(IOException | ClassNotFoundException e)
        {
            System.out.println("transplant.obj does not exist. Creating new TransplantGraph object...\n");

            System.out.println("Loading data from " + "'" + DONOR_FILE + "'...");
            System.out.println("Loading data from " + "'" + RECIPIENT_FILE + "'...\n");

            try
            {
                tg = TransplantGraph.buildFromFiles(DONOR_FILE, RECIPIENT_FILE);
            }
            catch (FileNotFoundException fileNotFoundException)
            {
                System.out.println("Could not find files to build from. Creating one from scratch...");
                tg = new TransplantGraph();
            }
        }

        Scanner input = new Scanner(System.in);
        String cmd;

        while(true)
        {
            System.out.println("Menu: \n" +
                    "    (LR) - List all recipients\n" +
                    "    (LO) - List all donors\n" +
                    "    (AO) - Add new donor\n" +
                    "    (AR) - Add new recipient\n" +
                    "    (RO) - Remove donor\n" +
                    "    (RR) - Remove recipient\n" +
                    "    (SR) - Sort recipients\n" +
                    "    (SO) - Sort donors\n" +
                    "    (Q) - Quit");

            System.out.print("Select an option: ");
            cmd = input.nextLine();

            if(cmd.trim().equalsIgnoreCase("LR"))
            {
                tg.printAllRecipients();
            }

            if(cmd.trim().equalsIgnoreCase("LO"))
            {
                tg.printAllDonors();
            }

            if(cmd.trim().equalsIgnoreCase("AO"))
            {
                try
                {
                    System.out.print("Please enter the organ donor's name: ");
                    String name = input.nextLine();

                    System.out.print("Please enter the organ being donated: ");
                    String organ = input.nextLine().toUpperCase();

                    System.out.print("Please enter the blood type: ");
                    String bloodType = input.nextLine().toUpperCase();

                    System.out.print("Please enter the age: ");
                    String age = input.nextLine();

                    Patient p = new Patient(name, organ, Integer.parseInt(age), new BloodType(bloodType), tg.getNextDonorId(), true);

                    tg.addDonor(p);
                    System.out.println(name + " with ID " + p.getID() + " was successfully added to the donor list!");
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Donor not added: Age must be a number!\n");
                }
            }

            if(cmd.trim().equalsIgnoreCase("AR"))
            {
                try
                {
                    System.out.print("Please enter the recipient's name: ");
                    String name = input.nextLine();

                    System.out.print("Please enter the recipient's blood type: ");
                    String bloodType = input.nextLine().toUpperCase();

                    System.out.print("Please enter the recipient's age: ");
                    String age = input.nextLine();

                    System.out.print("Please enter the organ needed: ");
                    String organ = input.nextLine().toUpperCase();


                    Patient p = new Patient(name, organ, Integer.parseInt(age), new BloodType(bloodType), tg.getNextRecipId(), false);

                    tg.addRecipient(p);
                    System.out.println(name + " with ID " + p.getID() + " is now on the organ transplant waitlist!");
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Recipient not added: Age must be a number!\n");
                }
            }

            if(cmd.trim().equalsIgnoreCase("RO"))
            {
                System.out.print("Enter the name of the organ donor to remove: ");
                try
                {
                    tg.removeDonor(input.nextLine());
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.trim().equalsIgnoreCase("RR"))
            {
                System.out.print("Enter the name of the recipient to remove: ");
                try
                {
                    tg.removeRecipient(input.nextLine());
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.trim().equalsIgnoreCase("SR"))
            {
                boolean sorting = true;
                String sortingcmd;

                ArrayList<Patient> recipientBackup = new ArrayList<>(tg.getRecipients());

                while(sorting)
                {
                    System.out.println("    (I) Sort by ID\n" +
                            "    (N) Sort by Number of Donors\n" +
                            "    (B) Sort by Blood Type\n" +
                            "    (O) Sort by Organ Needed\n" +
                            "    (Q) Back to Main Menu");

                    System.out.print("Select an option :");
                    sortingcmd = input.nextLine();

                    if(sortingcmd.equalsIgnoreCase("I"))
                    {
                        tg.setRecipients(new ArrayList<>(recipientBackup));
                        tg.printAllRecipients();
                    }

                    if(sortingcmd.equalsIgnoreCase("N"))
                    {
                        tg.sortedRecipientPrint(new NumConnectionsComparator());
                    }

                    if(sortingcmd.equalsIgnoreCase("B"))
                    {
                        tg.sortedRecipientPrint(new BloodTypeComparator());
                    }

                    if(sortingcmd.equalsIgnoreCase("O"))
                    {
                        tg.sortedRecipientPrint(new OrganComparator());
                    }

                    if(sortingcmd.equalsIgnoreCase("Q"))
                    {
                        tg.setRecipients(recipientBackup);
                        sorting = false;
                    }
                }
            }

            if(cmd.trim().equalsIgnoreCase("SO"))
            {
                boolean sorting = true;
                String sortingcmd;

                ArrayList<Patient> donorBackup = new ArrayList<>(tg.getDonors());

                while(sorting)
                {
                    System.out.println("    (I) Sort by ID\n" +
                            "    (N) Sort by Number of Recipients\n" +
                            "    (B) Sort by Blood Type\n" +
                            "    (O) Sort by Organ Donated\n" +
                            "    (Q) Back to Main Menu");

                    System.out.print("Select an option :");
                    sortingcmd = input.nextLine();

                    if(sortingcmd.equalsIgnoreCase("I"))
                    {
                        tg.setDonors(new ArrayList<>(donorBackup));
                        tg.printAllDonors();
                    }

                    if(sortingcmd.equalsIgnoreCase("N"))
                    {
                        tg.sortedDonorPrint(new NumConnectionsComparator());
                    }

                    if(sortingcmd.equalsIgnoreCase("B"))
                    {
                        tg.sortedDonorPrint(new BloodTypeComparator());
                    }

                    if(sortingcmd.equalsIgnoreCase("O"))
                    {
                        tg.sortedDonorPrint(new OrganComparator());
                    }

                    if(sortingcmd.equalsIgnoreCase("Q"))
                    {
                        tg.setDonors(donorBackup);
                        sorting = false;
                    }
                }
            }

            if(cmd.trim().equalsIgnoreCase("Q"))
            {
                try
                {
                    FileOutputStream file = new FileOutputStream("transplant.obj");
                    ObjectOutputStream fout = new ObjectOutputStream(file);
                    fout.writeObject(tg);
                    fout.close();

                    System.out.println("TransplantGraph is saved in transplant.obj.");
                }
                catch(IOException e)
                {
                    System.out.println("There was an error creating and saving object!");
                }

                System.exit(0);
            }
        }
    }
}
