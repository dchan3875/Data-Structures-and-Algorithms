/**
 * The FollowerGraph class contains a matrix for the connections between the users and an ArrayList storing the users.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #7 CSE214</dd>
 * </dl>
 */
import java.io.*;
import java.util.*;

public class FollowerGraph implements Serializable
{
    private ArrayList<User> users; //Users in the system
    public static final int MAX_USERS = 100; //Max number of users in the system
    private boolean[][] connections; //2D array with 'true' if a connection exists.

    /**
     * Default constructor that instantiates the arraylist and matrix.
     */
    public FollowerGraph()
    {
        users = new ArrayList<>();
        connections = new boolean[MAX_USERS][MAX_USERS];
    }

    /**
     * Adds a user to the arraylist and assigns it an index number.
     *
     * @param username
     *      The name of the user to be added.
     */
    public void addUser(String username)
    {
        if(checkUserExists(username))
        {
            System.out.println(username + " already exists.");
            return;
        }

        User u = new User(username);
        users.add(u);
        users.get(getIndexOf(username)).setIndexPos(getIndexOf(username));
        users.get(users.indexOf(u)).setUserCount(users.get(users.indexOf(u)).getUserCount() + 1);
        System.out.println(username + " has been added.");
    }

    /**
     * Removes a user given the username.
     *
     * @param username
     *      The name of the user to be removed.
     *
     * @throws IllegalArgumentException
     *      Indicates that the user was not found.
     */
    public void removeUser(String username) throws IllegalArgumentException
    {
        if(!checkUserExists(username))
            throw new IllegalArgumentException("User not found.");

        int idx = getIndexOf(username);
        users.get(idx).setUserCount(users.get(idx).getUserCount() - 1);
        users.set(idx, null);
        removeAllConnections(username);

        System.out.println(username + " has been removed.");
    }

    /**
     * Adds a connection between two given users.
     *
     * @param userFrom
     *      The user the connection will start from.
     * @param userTo
     *      The user the connection will lead to.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>userFrom and userTo have established a connection.</dd>
     */
    public void addConnection(String userFrom, String userTo)
    {
        if(checkUserExists(userFrom) && checkUserExists(userTo))
        {
            int idxFrom = getIndexOf(userFrom);
            int idxTo = getIndexOf(userTo);

            if(connections[idxFrom][idxTo])
            {
                System.out.println("There is already a connection.");
            }
            else
            {
                connections[idxFrom][idxTo] = true;
                System.out.println(userFrom + ", " + userTo + " added.");
            }
        }
        else
        {
            if(!checkUserExists(userFrom))
                System.out.println(userFrom + " does not exist.");
            else
                System.out.println(userTo + " does not exist.");
        }
    }

    /**
     * Checks if a user exists in the graph given their username.
     *
     * @param name
     *      Name of the user.
     * @return
     *      True if the user exists, false if the user does not.
     */
    public boolean checkUserExists(String name)
    {
        for(User u : users)
        {
            if(u.getUserName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    /**
     * Returns the index of the user in the matrix given their username.
     *
     * @param name
     *      The name of the user.
     * @return
     *      The index of the user.
     */
    public int getIndexOf(String name)
    {
        for(int i = 0; i < users.size(); i++)
        {
            if(users.get(i).getUserName().equalsIgnoreCase(name))
                return i;
        }
        return -1;
    }

    /**
     * Removes a connection given two users.
     *
     * @param userFrom
     *      The user the connection starts from.
     * @param userTo
     *      The user the connection goes to.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>userFrom and userTo have their connection removed.</dd>
     */
    public void removeConnection(String userFrom, String userTo)
    {
        int idxFrom = getIndexOf(userFrom);
        int idxTo = getIndexOf(userTo);
        connections[idxFrom][idxTo] = false;
    }

    /**
     * Removes all connections given a username.
     *
     * @param username
     *      The username of the user whose connections are to be removed.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The user with the username has had all their connections removed.</dd>
     */
    public void removeAllConnections(String username)
    {
        int idx = getIndexOf(username);

        for(int i = 0; i < connections[i].length; i++)
        {
            connections[idx][i] = false;
        }

        for(int i = 0; i < connections[i].length; i++)
        {
            connections[i][idx] = false;
        }
    }

    /**
     * Prints the shortest path between two users.
     *
     * @param userFrom
     *      The username of the user the path will start from.
     * @param userTo
     *      The username of the user the path will end at.
     */
    public void shortestPath(String userFrom, String userTo) //using BFS
    {
        if(User.userCount <= 0 || getIndexOf(userFrom) == -1 || getIndexOf(userTo) == -1)
        {
            System.out.println("One or both of the users in the connection is not in the system.");
            return;
        }

        System.out.println(getIndexOf(userFrom) + " to " + getIndexOf(userTo));
        boolean[] visited = new boolean[User.userCount];
        LinkedList<Integer> queue = new LinkedList<>();
        visited[getIndexOf(userFrom)] = true;
        queue.add(getIndexOf(userFrom));
        String output = "";

        while(!queue.isEmpty())
        {
            int following = queue.poll();

            output += users.get(following).getUserName() + " -> ";

            for(int i = 0; i < User.userCount; i++)
            {
                if(connections[following][i] && users.get(i).getUserName().equalsIgnoreCase(userTo))
                {
                    output += users.get(i).getUserName();
                    System.out.println(output);
                    return;
                }
                if(connections[following][i] && !visited[i])
                {
                    visited[i] = true;
                    queue.add(i);
                }

            }
        }
        System.out.println("No path was found.");
    }

    /**
     * Returns a list of all the paths connecting two users.
     *
     * @param userFrom
     *      The user the path list will start from.
     * @param userTo
     *      The user the path list will end at.
     * @return
     *      A String list containing the paths between the two given users.
     */
    public List<String> allPaths(String userFrom, String userTo)
    {
        ArrayList<String> paths = new ArrayList<>();
        if(getIndexOf(userFrom) == -1 || getIndexOf(userTo) == -1)
        {
            System.out.println("One or both of the users in the connection is not in the system.");
            return paths;
        }
       // if(getIndexOf(userFrom) == getIndexOf(userTo))
     //   {
       //     String path = userFrom + " -> " + userTo;
      //      paths.add(path);
      //      return paths;
    //    }

        boolean[] visited = new boolean[User.userCount];
        LinkedList<Integer> queue = new LinkedList<>();
        visited[getIndexOf(userFrom)] = true;

        queue.add(getIndexOf(userFrom));
        String output = "";

        while(!queue.isEmpty())
        {
            int following = queue.poll();

            output += users.get(following).getUserName() + " -> ";

            for(int i = 0; i < User.userCount; i++)
            {
                if(connections[following][i] && users.get(i).getUserName().equalsIgnoreCase(userTo))
                {
                    String newpath = output;
                    newpath += users.get(i).getUserName();
                    paths.add(newpath);
                }

                if(connections[following][i] && !visited[i])
                {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
        return paths;
    }

    /**
     * Prints all users given a specific comparator.
     *
     * @param comp
     *      The comparator the list will be sorted with.
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>If it is a NameComparator, then it is printed after being sorted alphabetically. If it is a
     * FollowingComparator, it is sorted from greatest to least of amount of following, and if it is a FollowersComparator,
     * it is sorted from greatest to least amount of followers.</dd>
     *
     */
    public void printAllUsers(Comparator comp)
    {
       users.sort(comp);

       for(User u : users)
       {
            System.out.printf("%-50s%-50s%-50s\n", u.getUserName(), getFollowerCount(u), getFollowingCount(u));
       }
    }

    /**
     * Prints all followers of a given user.
     *
     * @param username
     *      The username of the user whose followers will be printed.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>FollowerGraph is instantiated.</dd>
     */
    public void printAllFollowers(String username)
    {
        String output = "";

        for(int i = 0; i < connections.length; i++)
        {
            if(connections[i][users.get(getIndexOf(username)).getIndexPos()])
                output += connections[i] + ", ";
        }
        System.out.println(output.replaceAll(", $", ""));
    }

    /**
     * Prints all followings of a given user.
     *
     * @param username
     *      The username of the user whose following will be printed.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>FollowerGraph is instantiated.</dd>
     */
    public void printAllFollowing(String username)
    {
        String output = "";

        for(int i = 0; i < connections.length; i++)
        {
            if(connections[users.get(getIndexOf(username)).getIndexPos()][i])
                output += connections[i] + ", ";
        }
        System.out.println(output.replaceAll(", $", ""));
    }

    /**
     * Returns the number of followers of a given user.
     *
     * @param u
     *      The user whose number of followers will be returned.
     *
     * @return
     *      The number of followers.
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>FollowerGraph is instantiated.</dd>
     */
    public int getFollowerCount(User u)
    {
        int count = 0;

        for(int i = 0; i < connections.length; i++)
        {
            if(connections[i][u.getIndexPos()])
                count++;
        }
        return count;
    }

    /**
     * Returns the number of following of a given user.
     *
     * @param u
     *      The user whose number of following will be returned.
     * @return
     *      The number of following.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>FollowerGraph is instantiated.</dd>
     */
    public int getFollowingCount(User u)
    {
        int following = 0;

        for(int i = 0; i < connections[0].length; i++)
        {
            if(connections[u.getIndexPos()][i])
                following++;
        }
        return following;
    }

    /**
     * Returns all the paths that cause a loop in graphs.
     *
     * @return
     *      A List of String that contains all the paths with loops.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>FollowerGraph is instantiated.</dd>
     */
    public List<String> findAllLoops()
    {
        ArrayList<ArrayList<String>> coll = new ArrayList<>();
        ArrayList<String> loops = new ArrayList<>();
        for(int i = 0; i < User.userCount; i++)
        {
            if( allPaths(users.get(i).getUserName(), users.get(i).getUserName()).size() != 0)
                coll.add((ArrayList<String>) allPaths(users.get(i).getUserName(), users.get(i).getUserName()));
        }

        for(int r = 0; r < coll.size(); r++)
        {
            for(int c = 0; c < coll.get(r).size(); c++)
            {
                loops.add(coll.get(r).get(c));
            }
        }
        return loops;
    }

    /**
     * Loads all the users into the graph given the file name.
     *
     * @param filename
     *      The name of the file.
     *
     * @throws IOException
     *      Indicates that the file was not found.
     */
    public void loadAllUsers(String filename) throws IOException
    {
        File f = new File(filename);

        if(!f.exists())
            throw new IOException("File not found.");

        String s = "";
        BufferedReader stdin = new BufferedReader(new FileReader(filename));
        while((s = stdin.readLine()) != null)
            addUser(s);

    }

    /**
     * Fixes the user indicies.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>FollowerGraph is instantiated.</dd>
     *
     */
    public void setUsers()
    {
        int count = 0;

        for(User u : users)
        {
            if(u != null)
                count++;
        }
        User.userCount = count;
    }

    /**
     * Loads all connections between users given a file name.
     *
     * @param filename
     *      The name of the file.
     *
     * @throws IOException
     *      Indicates that the file was not found.
     */
    public void loadAllConnections(String filename) throws IOException
    {
        File f = new File(filename);

        if(!f.exists())
            throw new IOException("File not found.");

        String s = "";
        BufferedReader stdin = new BufferedReader(new FileReader(filename));

        try
        {
            while((s = stdin.readLine()) != null)
            {
                String[] arr = s.split(", ");
                addConnection(arr[0], arr[1]);
            }
        }
        catch(IndexOutOfBoundsException e)
        {
            System.out.println("File was not formatted properly.");
        }
    }
}
