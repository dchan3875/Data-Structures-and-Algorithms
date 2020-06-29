/**
 * The Patient class represents either a organ donor or an organ receiver. Can be serialized and is comparable.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #7 CSE214</dd>
 * </dl>
 */

import java.io.Serializable;

public class Patient implements Comparable, Serializable
{
    private String name;
    private String organ;
    private int age;
    private BloodType bloodType;
    private int ID;
    private boolean isDonor;
    private int numConnections; //Number of connections the patient has
    private String connections; //The connections the patient has

    /**
     * Default constructor that instantiates all variables.
     */
    public Patient()
    {
        name = "";
        organ = "";
        age = -1;
        bloodType = null;
        ID = -1;
        isDonor = false;
        connections = "";
    }

    /**
     * Constructor with various parameters
     *
     * @param name
     *      Name to be set to.
     * @param organ
     *      Organ to be set to.
     * @param age
     *      Age to be set to.
     * @param bloodType
     *      Blood type to be set to.
     * @param ID
     *      ID to be set to.
     * @param isDonor
     *      True if patient is donor, false otherwise.
     */
    public Patient(String name, String organ, int age, BloodType bloodType, int ID, boolean isDonor)
    {
        this.name = name;
        this.organ = organ;
        this.age = age;
        this.bloodType = bloodType;
        this.ID = ID;
        this.isDonor = isDonor;
    }

    /**
     * Compares this Patient to the one passed in.
     *
     * @param o
     *      The patient to be compared to.
     * @return
     *      Returns the difference of the two id's.
     */
    public int compareTo(Object o)
    {
        if(o instanceof Patient)
        {
            return this.getID() - ((Patient) o).getID();
        }
        return -1;
    }

    /**
     * Creates a representation of the Patient object in string form.
     * @return
     *      The string representation of the Patient.
     */
    public String toString()
    {
        return String.format("%-5s | %-30s | %-10s | %15s | %10s | %-15s", ID,
                name, age, organ, bloodType.getBloodType(), connections);
    }

    /**
     * Setter for number of connections.
     *
     * @param num
     *      Number to be set to.
     */
    public void setNumConnections(int num)
    {
        this.numConnections = num;
    }

    /**
     * Setter for connections.
     *
     * @param str
     *      The connections to be set to.
     */
    public void setConnections(String str)
    {
        this.connections = str;
    }

    /**
     * Setter for the ID.
     *
     * @param id
     *      The ID to be set to.
     */
    public void setID(int id)
    {
        ID = id;
    }

    /**
     * Getter for the ID.
     * @return
     *      The ID of the patient.
     */
    public int getID()
    {
        return ID;
    }

    /**
     * Getter for bloodType.
     * @return
     *      Blood type of the patient.
     */
    public BloodType getBloodType() {
        return bloodType;
    }

    /**
     * Getter for name of Patient.
     * @return
     *      The name of the patient.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Getter for the organ to be donated/recieved.
     * @return
     *      The organ listed for the patient.
     */
    public String getOrgan()
    {
        return organ;
    }

    /**
     * Getter for isDonor.
     *
     * @return
     *      True if patient is a donor, false if they are not.
     */
    public boolean getisDonor()
    {
        return isDonor;
    }

    /**
     * Getter for the number of connections of the patient.
     *
     * @return
     *      The number of connections.
     */
    public int getNumConnections()
    {
        return numConnections;
    }

}
