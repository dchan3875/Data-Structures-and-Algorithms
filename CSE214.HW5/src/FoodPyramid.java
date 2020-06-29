/**
 * The FoodPyramid class contains an OrganismTree and will be used as a food pyramid for the user to interact with.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #5 CSE214</dd>
 * </dl>
 */

import java.util.Scanner;

public class FoodPyramid
{
    private OrganismTree tree; //The OrganismTree the user will be interacting with

    /**
     * Default constructor for a new FoodPyramid, containing a tree.
     */
    public FoodPyramid()
    {
        boolean generated = false;
        Scanner input = new Scanner(System.in);

        while(!generated)
        {
            try {
                OrganismNode on = new OrganismNode();

                System.out.print("What is the name of the apex predator?: ");
                String name = input.nextLine();

                if(name.isBlank()) {
                    throw new IllegalArgumentException("The name of the organism cannot be blank.\n");
                }
                on.setName(name);
                System.out.print("Is the organism a herbivore / a carnivore / an omnivore? (H / C / O): ");
                String type = input.nextLine().toUpperCase();
                type = type.replaceAll(" ", "");

                if(type.length() > 1 || type.isBlank()) {
                    throw new IllegalArgumentException("The classification of the organism was unknown. Please try again.\n");
                }

                if(type.equals("H")) {
                    on.setIsHerbivore(true);
                }
                else {
                    if(type.equals("C")) {
                        on.setIsCarnivore(true);
                    }
                    else
                    {
                        if(type.equals("O"))
                        {
                            on.setIsHerbivore(true);
                            on.setIsCarnivore(true);
                        }
                        else
                        {
                            throw new IllegalArgumentException("The classification of the organism was unknown. Please try again.\n");
                        }
                    }
                }

                System.out.println("Constructing food pyramid. . .\n");
                tree = new OrganismTree(on);
                generated = true;
            }
            catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * Main method where commands will be interpreted and allow users to manipulate the tree.
     *
     * @param args
     *      A string array of arguments.
     */
    public static void main(String[] args) {
        FoodPyramid fp = new FoodPyramid();
        boolean done = false;

        while (!done) {
            String cmd = showCommands();

            if (cmd.equals("PC")) //Creates a new plant child
            {
                Scanner input = new Scanner(System.in);

                System.out.print("What is the name of the organism?: ");
                String name = input.nextLine();

                try {
                    fp.tree.addPlantChild(name);
                } catch (IllegalArgumentException | PositionNotAvailableException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (cmd.equals("AC")) //Creates a new animal child
            {
                Scanner input = new Scanner(System.in);
                boolean isHerbivore = false;
                boolean isCarnivore = false;

                System.out.print("What is the name of the organism?: ");
                String name = input.nextLine();

                try
                {
                    if (name.isBlank()) {
                        throw new IllegalArgumentException("The name of the organism cannot be blank. Please try again.\n");
                    }

                    System.out.print("Is the organism a herbivore / a carnivore / an omnivore? (H / C / O): ");

                    String type = input.nextLine().toUpperCase();
                    type = type.replaceAll(" ", "");

                    if (type.equals("H")) {
                        isHerbivore = true;
                    } else {
                        if (type.equals("C")) {
                            isCarnivore = true;
                        } else {
                            if (type.equals("O")) {
                                isHerbivore = true;
                                isCarnivore = true;
                            } else {
                                throw new IllegalArgumentException("The classification of the organism was unknown. Please try again.\n");
                            }
                        }
                    }
                    try
                    {
                        fp.tree.addAnimalChild(name, isHerbivore, isCarnivore);
                    }
                    catch (IllegalArgumentException | PositionNotAvailableException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if (cmd.equals("RC")) //Removes a child
            {
                Scanner input = new Scanner(System.in);
                System.out.print("What is the name of the organism to be removed?: ");
                String name = input.nextLine();

                try
                {
                    fp.tree.removeChild(name);
                }
                catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (cmd.equals("P")) //Prints out the cursor and its prey
            {
                try {
                    System.out.println(fp.tree.listPrey() + "\n");
                } catch (IsPlantException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (cmd.equals("C")) //Prints out the food chain
            {
                System.out.println(fp.tree.listFoodChain());
            }

            if (cmd.equals("F")) //Prints out the Food Pyramid at the cursor.
            {
                fp.tree.printOrganismTree();
            }

            if (cmd.equals("LP")) //Lists all plants supporting the cursor.
            {
                String str = fp.tree.listAllPlants();
                if(str.isBlank())
                    System.out.println("There are currently no plants supporting the cursor.\n");
                else
                    System.out.println(str);
            }

            if (cmd.equals("R")) //Resets the cursor to root
            {
                fp.tree.cursorReset();
                System.out.println("Cursor successfully set to root!\n");
            }

            if (cmd.equals("M")) //Moves the cursor
            {
                Scanner input = new Scanner(System.in);
                System.out.print("Move to?: ");
                try {
                    fp.tree.moveCursor(input.nextLine());
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (cmd.equals("Q")) //Quits the program
            {
                done = true;
                System.out.println("Terminating Program. . .\n");
                System.exit(1);
            }

        }
    }

    /**
     * Displays a neatly formatted list of all the commands.
     *
     * @return
     *      The entered command.
     */
    public static String showCommands()
    {
        System.out.println(String.format("%-5s%-4s%-45s","(PC)", " - ", "Create New Plant Child"));
        System.out.println(String.format("%-5s%-4s%-45s","(AC)", " - ", "Create New Animal Child"));
        System.out.println(String.format("%-5s%-4s%-45s","(RC)", " - ", "Remove Child"));
        System.out.println(String.format("%-5s%-4s%-45s","(P)", " - ", "Print Out Cursor's Prey"));
        System.out.println(String.format("%-5s%-4s%-45s","(C)", " - ", "Print Out Food Chain"));
        System.out.println(String.format("%-5s%-4s%-45s","(F)", " - ", "Print Out Food Pyramid at Cursor"));
        System.out.println(String.format("%-5s%-4s%-45s","(LP)", " - ", "List All Plants Supporting Cursor"));
        System.out.println(String.format("%-5s%-4s%-45s","(R)", " - ", "Reset Cursor to Root"));
        System.out.println(String.format("%-5s%-4s%-45s","(M)", " - ", "Move Cursor to Child"));
        System.out.println(String.format("%-5s%-4s%-45s","(Q)", " - ", "Quit"));

        Scanner inputScan = new Scanner(System.in);
        System.out.print("Please select an option: ");
        return inputScan.nextLine().toUpperCase().replaceAll(" ", "");
    }
}
