/**
 * The TransplantGraph class represents a graph for the connections of all the Patients in an adjacency matrix and is
 * serializable.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #7 CSE214</dd>
 * </dl>
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

public class TransplantGraph implements Serializable
{
    private ArrayList<Patient> donors;
    private ArrayList<Patient> recipients;
    private static final int MAX_PATIENTS = 100;
    private boolean[][] connections;
    private int nextDonorId = 0;
    private int nextRecipId = 0;

    /**
     * Default constructor
     */
    public TransplantGraph() {
        donors = new ArrayList<>();
        recipients = new ArrayList<>();
        connections = new boolean[MAX_PATIENTS][MAX_PATIENTS];
    }

    /**
     * Static method that builds a new TransplantGraph from a donor and recipient text file.
     *
     * @param donorFile
     *      File that contains the list of donors.
     * @param recipientFile
     *      File that contians the list of recipients.
     * @return
     *      A new TransplantGraph object with data from the files.
     * @throws FileNotFoundException
     *      Indicates that the file was not found.
     */
    public static TransplantGraph buildFromFiles(String donorFile, String recipientFile) throws FileNotFoundException
    {
        TransplantGraph tg = new TransplantGraph();

        Scanner scanner = new Scanner(new File(donorFile));
        while(scanner.hasNextLine())
        {
            String[] s = scanner.nextLine().split(", ");
            tg.addDonor(new Patient(s[1], s[3].toUpperCase(), Integer.parseInt(s[2]), new BloodType(s[4].toUpperCase()), Integer.parseInt(s[0]), true));
        }
        scanner.close();

        scanner = new Scanner(new File(recipientFile));
        while(scanner.hasNextLine())
        {
            String[] s = scanner.nextLine().split(", ");
            tg.addRecipient(new Patient(s[1], s[3].toUpperCase(), Integer.parseInt(s[2]), new BloodType(s[4].toUpperCase()), Integer.parseInt(s[0]), false));
        }
        scanner.close();

        return tg;
    }

    /**
     * Adds a new patient that is a recipient to the graph and updates connections in the adjacency matrix.
     *
     * @param patient
     *      A new patient
     *
     * @throws IllegalArgumentException
     *      Indicates that the patient was invalid.
     */
    public void addRecipient(Patient patient) throws IllegalArgumentException {
        if (patient == null)
            throw new IllegalArgumentException("Invalid patient given!");

        recipients.add(patient);
        nextRecipId++;
        updateConnections();
    }

    /**
     * Adds a new patient that is a donor to the graph and updates connections in the adjacency matrix.
     *
     * @param patient
     *      A new Patient.
     *
     * @throws IllegalArgumentException
     *      Indicates that the patient was invalid.
     */
    public void addDonor(Patient patient)  throws IllegalArgumentException
    {
        if (patient == null)
            throw new IllegalArgumentException("Invalid patient given!");

        donors.add(patient);
        nextDonorId++;
        updateConnections();
    }

    /**
     * Updates all the connections in the adjacency matrix.
     */
    public void updateConnections()
    {
        for(int d = 0; d < donors.size(); d++)
        {
            for(int r = 0; r < recipients.size(); r++)
            {
                connections[d][r] = recipients.get(r).getOrgan().equalsIgnoreCase(donors.get(d).getOrgan()) &&
                        BloodType.isCompatible(recipients.get(r).getBloodType(), donors.get(d).getBloodType());
            }
        }

        int conn1 = 0;
        for(int d = 0; d < donors.size(); d++)
        {
            for(int r = 0; r < recipients.size(); r++)
            {
                if(connections[d][r])
                    conn1++;
            }
            donors.get(d).setNumConnections(conn1);
            getCompatiable(donors.get(d));
        }

        int conn2 = 0;
        for(int r = 0; r < recipients.size(); r++)
        {
            for(int d = 0; d < donors.size(); d++)
            {
                if(connections[d][r])
                    conn2++;
            }
            recipients.get(r).setNumConnections(conn2);
            getCompatiable(recipients.get(r));
        }
    }

    /**
     * Removes a patient with a given name from the matrix.
     *
     * @param name
     *      Name of the patient to be removed.
     *
     * @throws IllegalArgumentException
     *      Indicates that the patient was not found in the recipient list.
     */
    public void removeRecipient(String name) throws IllegalArgumentException
    {
        Patient p = null;
        boolean found = false;

        for (Patient r : recipients)
        {
            if (r.getName().equalsIgnoreCase(name))
            {
                p = r;
                found = true;
                break;
            }
        }

        if(!found)
            throw new IllegalArgumentException(name + " was not found on the recipient list!");

        int idx = recipients.indexOf(p);
        for(int i = idx+1; i < recipients.size(); i++)
        {
            recipients.get(i).setID(recipients.get(i).getID()-1);
        }

        recipients.remove(p);
        updateConnections();
        nextRecipId--;

        System.out.println(name + " was removed from the organ transplant waitlist!");
    }

    /**
     * Removes the donor with the given name from the donor list and updates the adjacency matrix.
     *
     * @param name
     *      Name of the donor to be removed.
     */
    public void removeDonor(String name)
    {
        Patient p = null;
        boolean found = false;

        for (Patient d : donors)
        {
            if (d.getName().equalsIgnoreCase(name))
            {
                p = d;
                found = true;
                break;
            }
        }

        if(!found)
            throw new IllegalArgumentException(name + " was not found on the donor list!");

        int idx = donors.indexOf(p);
        for(int i = idx+1; i < donors.size(); i++)
        {
            donors.get(i).setID(donors.get(i).getID()-1);
        }

        donors.remove(p);
        updateConnections();
        nextDonorId--;

        System.out.println(name + " was removed from the donor list!");
    }

    /**
     * Creates a string that contains the connections that the patient has.
     *
     * @param p
     *      The patient to be checked.
     *
     * @return
     *      The string that represents the connections.
     */
    public String getCompatiable(Patient p) {
        String s = "";
        int conns = 0;

        if (!p.getisDonor()) {
            int idx = recipients.indexOf(p);

            for (int r = 0; r < donors.size(); r++) {
                if (connections[r][idx])
                {
                    s += r + ", ";
                    conns++;
                }

            }
        } else {
            int idx = donors.indexOf(p);

            for (int c = 0; c < recipients.size(); c++) {
                if (connections[idx][c])
                {
                    s += c + ", ";
                    conns++;
                }

            }
        }
        s = s.replaceAll(", $", "");
        p.setConnections(s);
        p.setNumConnections(conns);
        return s;
    }

    /**
     * Prints all recipients in the graph in standard order.
     */
    public void printAllRecipients()
    {
        String data = String.format("%-5s | %-30s | %-10s | %15s | %10s | %-15s", "Index",
                "Recipient Name", "Age", "Organ Needed", "Blood Type", "Donor IDs");

        data += "\n==============================" +
                "======================================================================";

        for (int i = 0; i < recipients.size(); i++) {
            updateConnections();
            data += "\n" + recipients.get(i).toString();
        }
        System.out.println(data);
    }

    /**
     * Prints all donors in the graph in standard order.
     */
    public void printAllDonors()
    {
        String data = String.format("%-5s | %-30s | %-10s | %15s | %10s | %-15s", "Index",
                "Donor Name", "Age", "Organ Donated", "Blood Type", "Recipient IDs");

        data += "\n==============================" +
                "======================================================================";

        for (int i = 0; i < donors.size(); i++) {
            updateConnections();
            data += "\n" + donors.get(i).toString();
        }
        System.out.println(data);
    }

    /**
     * Prints the recipients in the graph after being sorted by the comparator.
     *
     * @param c
     *      The comparator to be used.
     */
    public void sortedRecipientPrint(Comparator c)
    {
        Collections.sort(recipients, c);
        updateConnections();

        printAllRecipients();
    }

    /**
     * Prints the donors in the graph after being sorted by the comparator.
     *
     * @param c
     *      The comparator to be used.
     */
    public void sortedDonorPrint(Comparator c)
    {
        Collections.sort(donors, c);
        updateConnections();

        printAllDonors();
    }

    /**
     * Getter for the next available ID of the donor.
     *
     * @return
     *      The value of the next ID for donor.
     */
    public int getNextDonorId() {
        return nextDonorId;
    }

    /**
     * Getter for the next available ID for the recipient.
     *
     * @return
     *      The value of the next ID for recipient.
     */
    public int getNextRecipId() {
        return nextRecipId;
    }

    /**
     * Getter for the list of donors.
     *
     * @return
     *      Arraylist of donors.
     */
    public ArrayList<Patient> getDonors() {
        return donors;
    }

    /**
     * Getter for the recipients list.
     *
     * @return
     *      Arraylist of recipients.
     */
    public ArrayList<Patient> getRecipients() {
        return recipients;
    }

    /**
     * Setter for the donor arraylist.
     *
     * @param donors
     *      The ArrayList to be set to.
     */
    public void setDonors(ArrayList<Patient> donors) {
        this.donors = donors;
    }

    /**
     * Setter for the recipient ArrayList.
     *
     * @param recipients
     *      The ArrayList to be set to.
     */
    public void setRecipients(ArrayList<Patient> recipients) {
        this.recipients = recipients;
    }
}
