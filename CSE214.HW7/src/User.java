/**
 * The User class holds data for each person to be used in the graph. Contains the name, position, and number of users.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #7 CSE214</dd>
 * </dl>
 */

import java.io.Serializable;

public class User implements Serializable
{
    private String userName; //Name of the user
    private int indexPos; //Index of the user in the arraylist
    static int userCount; //Number of users currently in the graph
    /**
     * Default constructor with a parameter for the username.
     *
     * @param s
     *      The name of the user.
     */
    public User(String s)
    {
        userName = s;
    }

    /**
     * Setter for the username.
     *
     * @param s
     *      The name to be set to.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>User has been instantiated.</dd>
     *
     */
    public void setUserName(String s)
    {
        userName = s;
    }

    /**
     * Setter for the position of the user.
     *
     * @param idx
     *      The index of the user to be set to.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>User has been instantiated.</dd>
     */
    public void setIndexPos(int idx)
    {
        indexPos = idx;
    }

    /**
     * Setter for the count of number of users in the system.
     *
     * @param count
     *      The number of users to be set to.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>User has been instantiated.</dd>
     *
     */
    public void setUserCount(int count)
    {
        userCount = count;
    }

    /**
     * Getter for the count of number of users in the system.
     *
     * @return
     *     The number of users in the system.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>User has been instantiated.</dd>
     *
     */
    public int getUserCount()
    {
        return userCount;
    }

    /**
     * Getter for the name of the user.
     *
     * @return
     *      The name of the user.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>User has been instantiated.</dd>
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * Getter for the position of the user.
     *
     * @return
     *      The position of the user.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>User has been instantiated.</dd>
     */
    public int getIndexPos()
    {
        return indexPos;
    }

    /**
     * Formats the
     * @return
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>User has been instantiated.</dd>
     */
    public String toString()
    {
        return userName;
    }


}
