/**
 * The GroceryDriver class allows the user to set up a HashedGrocery object which holds the data for the inventory
 * management.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #6 CSE214</dd>
 * </dl>
 */

import java.io.*;
import java.util.Scanner;

public class GroceryDriver
{
    /**
     * Main method which checks to see if there is an existing grocery object to load. If there is, data is loaded and
     * the user can continue where they left off. Else, a new object is created and saved upon exit. Shows commands
     * that users can select.
     *
     * @param args
     *      String array of arguments
     */
    public static void main(String[] args)
    {
        HashedGrocery hg;

        try
        {
            FileInputStream file = new FileInputStream("grocery.obj");
            ObjectInputStream fin  = new ObjectInputStream(file);
            hg = (HashedGrocery) fin.readObject();
            fin.close();
            System.out.println("HashedGrocery is loaded from grocery.obj.\n");
        }
        catch(IOException | ClassNotFoundException e)
        {
            System.out.println("Grocery.obj does not exist. Creating new HashedGrocery object.\n");
            hg = new HashedGrocery();
        }

        System.out.println("Business Day " + hg.getBusinessDay() + ".");
        Scanner input = new Scanner(System.in);
        String cmd;

        while(true)
        {
            System.out.println("\nMenu :" +
                    " \n" +
                    "(L) Load item catalog\n" +
                    "(A) Add items\n" +
                    "(B) Process Sales\n" +
                    "(C) Display all items\n" +
                    "(N) Move to next business day\n" +
                    "(Q) Quit\n");

            System.out.print("Select an option: ");
            cmd = input.nextLine();

            if(cmd.trim().equalsIgnoreCase("L"))
            {
                System.out.print("Enter file to load: ");
                String file = input.nextLine();

                try
                {
                    hg.addItemCatalog(file);
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.trim().equalsIgnoreCase("A"))
            {
                try
                {
                    System.out.print("Enter item code: ");
                    String itemCode = input.nextLine();

                    System.out.print("Enter item name: ");
                    String name = input.nextLine();

                    System.out.print("Enter quantity in store: ");
                    int quantity = Integer.parseInt(input.nextLine());

                    System.out.print("Enter average sales per day: ");
                    int average = Integer.parseInt(input.nextLine());

                    System.out.print("Enter price: ");
                    double price = Double.parseDouble(input.nextLine());

                    try
                    {
                        hg.addItem(new Item(itemCode, name, average, quantity, price, 0));
                    }
                    catch(IllegalArgumentException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println("Invalid data given!");
                }
            }

            if(cmd.trim().equalsIgnoreCase("B"))
            {
                System.out.print("Enter file to process: ");
                String file = input.nextLine();

                try
                {
                    hg.processSales(file);
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.trim().equalsIgnoreCase("C"))
            {
                System.out.println(hg);
            }

            if(cmd.trim().equalsIgnoreCase("N"))
            {
                System.out.println("\nAdvancing business day...");
                System.out.println("Business Day " + (hg.getBusinessDay() + 1) + ".\n");
                hg.nextBusinessDay();
            }

            if(cmd.trim().equalsIgnoreCase("Q"))
            {
                try
                {
                    FileOutputStream file = new FileOutputStream("grocery.obj");
                    ObjectOutputStream fout = new ObjectOutputStream(file);
                    fout.writeObject(hg);
                    fout.close();

                    System.out.println("HashedGrocery is saved in grocery.obj.");
                }
                catch(IOException e)
                {
                    System.out.println("There was an error creating and saving object!");
                }

                System.out.println("Program terminating normally...");
                System.exit(0);
            }
        }
    }
}