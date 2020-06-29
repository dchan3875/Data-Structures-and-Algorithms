/**
 * The FollowGraphDriver class allows users to interact with the graph by adding users, connections, and printing data.
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

public class FollowGraphDriver
{
    /**
     * Main method that starts the program. Checks if there is a FollowerGraph.obj to load.
     *
     * @param args
     *      String array of arguments.
     *
     * @throws IOException
     *      Indicates no saved FollowerGraph.obj was found.
     */
    public static void main(String[] args) throws IOException {
        FollowerGraph fg = null;
        boolean exists = false;

        try
        {
            FileInputStream file = new FileInputStream("FollowerGraph.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            fg = (FollowerGraph) inStream.readObject();
            System.out.println("FollowerGraph.obj loaded.");
            inStream.close();
            file.close();
            fg.setUsers();

            exists = true;
        }
        catch (ClassNotFoundException | IOException e)
        {
            System.out.println("FollowerGraph.obj not found. Creating empty FollowerGraph.");
        }

        if(!exists)
        {
            fg = new FollowerGraph();
        }

        Scanner input = new Scanner(System.in);

        while(true)
        {
            String cmd = showCommands();

            if(cmd.equals("U")) //Adds new user
            {
                System.out.print("Please enter the name of the user: ");
                String name = input.nextLine();

                try
                {
                    fg.addUser(name);
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }

            }

            if(cmd.equals("C")) //Adds connection between two users
            {
                System.out.print("Please enter the source of the connection to add: ");
                String from = input.nextLine();

                System.out.print("Please enter the destination of the connection to add: ");
                String to = input.nextLine();

                try
                {
                    fg.addConnection(from, to);
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("AU")) //Loads all users from a text file.
            {
                System.out.print("Enter the file name: ");
                String filename = input.nextLine();
                try
                {
                    fg.loadAllUsers(filename);
                }
                catch(IOException | IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("AC")) //Loads all connections from a text file.
            {
                System.out.print("Enter the file name: ");
                String filename = input.nextLine();
                try
                {
                    fg.loadAllConnections(filename);
                }
                catch(IOException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("P")) //Print menu
            {
                while(true)
                {
                    String option = showPrintOptions();
                    if(option.equals("SA")) //Prints table sorted by name
                    {
                        System.out.printf("\n%-50s%-50s%-50s\n", "User Name", "Number of Followers", "Number of Following");
                        fg.printAllUsers(new NameComparator());
                    }
                    if(option.equals("SB")) //Prints table sorted by followers
                    {
                        System.out.printf("\n%-50s%-50s%-50s\n", "User Name", "Number of Followers", "Number of Following");
                        fg.printAllUsers(new FollowersComparator(fg));
                    }
                    if(option.equals("SC")) //Prints table sorted by following
                    {
                        System.out.printf("\n%-50s%-50s%-50s\n", "User Name", "Number of Followers", "Number of Following");
                        fg.printAllUsers(new FollowingComparator(fg));
                    }
                    if(option.equals("Q")) //Leaves print menu
                    {
                        break;
                    }
                }
            }

            if(cmd.equals("L")) //Prints all loops
            {
                ArrayList<String> arrlist = (ArrayList<String>) fg.findAllLoops();
                System.out.println("There are " + arrlist.size() + " loops:");
                for(String s : arrlist)
                {
                    System.out.println(s);
                }
            }

            if(cmd.equals("RU")) //Removes a user
            {
                System.out.print("Please enter the user to remove: ");
                String name = input.nextLine();
                try
                {
                    fg.removeUser(name);
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("RC")) //Removes a connection
            {
                System.out.print("Please enter the source of the connection to remove: ");
                String from = input.nextLine();

                System.out.print("Please enter the destination of the connection to remove: ");
                String to = input.nextLine();

                try
                {
                    fg.removeConnection(from, to);
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("SP")) //Finds the shortest path between two users
            {
                System.out.print("Please enter the desired source: ");
                String from = input.nextLine();

                System.out.print("Please enter the desired destination: ");
                String to = input.nextLine();

                try
                {
                   // System.out.println(fg.shortestPath(from,to));
                    fg.shortestPath(from,to);
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }


            }

            if(cmd.equals("AP")) //Find all paths between two users
            {
                System.out.print("Please enter the desired source: ");
                String from = input.nextLine();

                System.out.print("Please enter the desired destination: ");
                String to = input.nextLine();

                try
                {
                    ArrayList<String> arrlist = (ArrayList<String>)fg.allPaths(from,to);
                    System.out.println("There are a total of " + arrlist.size() + " paths:");

                    for(String s : arrlist)
                    {
                        System.out.println(s);
                    }
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("Q"))//Saves the current FollowerGraph.obj and exits.
            {
                System.out.println("Saving to \"FollowerGraph.obj\" and exiting...");

                FileOutputStream file = new FileOutputStream("FollowerGraph.obj");
                ObjectOutputStream outStream = new ObjectOutputStream(file);
                outStream.writeObject(fg);
                outStream.close();
                file.close();;

                System.exit(0);
            }
        }
    }

    /**
     * Displays print menu and takes user command
     *
     * @return
     *      The command inputted
     */
    public static String showCommands()
    {
        System.out.println(String.format("%-5s%-4s%-45s","(U)", " - ", "Add User"));
        System.out.println(String.format("%-5s%-4s%-45s","(C)", " - ", "Add Connection"));
        System.out.println(String.format("%-5s%-4s%-45s","(AU)", " - ", "Load All Users"));
        System.out.println(String.format("%-5s%-4s%-45s","(AC)", " - ", "Load all Connections"));
        System.out.println(String.format("%-5s%-4s%-45s","(P)", " - ", "Print all Users"));
        System.out.println(String.format("%-5s%-4s%-45s","(L)", " - ", "Print all Loops"));
        System.out.println(String.format("%-5s%-4s%-45s","(RU)", " - ", "Remove User"));
        System.out.println(String.format("%-5s%-4s%-45s","(RC)", " - ", "Remove Connection"));
        System.out.println(String.format("%-5s%-4s%-45s","(SP)", " - ", "Find Shortest Path"));
        System.out.println(String.format("%-5s%-4s%-45s","(AP)", " - ", "Find All Paths"));
        System.out.println(String.format("%-5s%-4s%-45s","(Q)", " - ", "Quit"));

        Scanner inputScan = new Scanner(System.in);
        System.out.print("Please select an option: ");
        return inputScan.nextLine().toUpperCase().replaceAll(" ", "");
    }

    /**
     * Displays print menu for the print option.
     *
     * @return
     *      The inputted command.
     */
    public static String showPrintOptions()
    {
        System.out.println(String.format("%-5s%-4s%-45s","(SA)", " - ", "Sort Users by Name"));
        System.out.println(String.format("%-5s%-4s%-45s","(SB)", " - ", "Sort Users by Number of Followers"));
        System.out.println(String.format("%-5s%-4s%-45s","(SC)", " - ", "Sort Users by Number of Following"));
        System.out.println(String.format("%-5s%-4s%-45s","(Q)", " - ", "Quit"));

        Scanner inputScan = new Scanner(System.in);
        System.out.print("Please select an option: ");
        return inputScan.nextLine().toUpperCase().replaceAll(" ", "");
    }
}
