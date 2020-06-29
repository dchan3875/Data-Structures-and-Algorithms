/**
 * The AdventureDesigner class allows the user to construct their SceneTree, storing different scenes for their
 * adventures. Users can change their stories through manipulation of the tree.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #5 CSE214</dd>
 * </dl>
 */
import java.util.Scanner;

public class AdventureDesigner
{
    private static SceneTree st; //The SceneTree

    /**
     * Main driver for the program, initializes the SceneTree and prompts user to enter commands to manipulate their
     * tree.
     *
     * @param args
     *      String array of arguments.
     */
    public static void main(String[] args)
    {
        System.out.println("Creating a story...\n");
       // AdventureDesigner advDes = new AdventureDesigner();
        Scanner input = new Scanner(System.in);

        String title = "";
        String sceneDescription = "";
        while(true) {
            System.out.print("Please enter a title: ");
            title = input.nextLine();

            System.out.print("Please enter a scene: ");
            sceneDescription = input.nextLine();

            if(title.isBlank() || sceneDescription.isBlank())
                System.out.println("Title and description cannot be blank!\n");
            else
                break;
        }
        st = new SceneTree(new SceneNode(title, sceneDescription));
        System.out.println("Scene #1 added.");

        while(true) {
            System.out.println("\nA) Add Scene\n" +
                    "R) Remove Scene\n" +
                    "S) Show Current Scene\n" +
                    "P) Print Adventure Tree\n" +
                    "B) Go Back A Scene\n" +
                    "F) Go Forward A Scene\n" +
                    "G) Play Game\n" +
                    "N) Print Path To Cursor\n" +
                    "M) Move scene\n" +
                    "Q) Quit\n");

            System.out.print("Please enter a selection: ");
            String cmd = input.nextLine().toUpperCase().trim();

            if(cmd.equals("A")) {
                System.out.print("\nPlease enter a title: ");
                title = input.nextLine();

                System.out.print("Please enter a scene: ");
                sceneDescription = input.nextLine();

                if (title.isBlank() || sceneDescription.isBlank()) {
                    System.out.println("Title and description cannot be blank!\n");
                } else {
                    try {
                        st.addNewNode(title, sceneDescription);
                        System.out.println("Scene #" + SceneNode.getNumScenes() + " added.");
                    } catch (FullSceneException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            if(cmd.equals("R"))
            {
                try
                {
                    if(st.getCursor().isEnding())
                        throw new NoSuchNodeException("Nothing to remove.\n");

                    System.out.print("Please enter an option: ");
                    String option = input.nextLine().trim();
                    String removedTitle = "";

                    if (option.trim().equalsIgnoreCase("A")) {
                        removedTitle = st.getCursor().getLeft().getTitle();
                    } else if (option.trim().equalsIgnoreCase("B")) {
                        removedTitle = st.getCursor().getMiddle().getTitle();
                    } else if (option.trim().equalsIgnoreCase("C")) {
                        removedTitle = st.getCursor().getRight().getTitle();
                    }

                    st.removeScene(option);
                    System.out.println(removedTitle + " removed.");
                } catch (NoSuchNodeException e) {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("S")) {
                st.getCursor().displayFullScene();
            }

            if(cmd.equals("P")) {
                System.out.println(st);
            }

            if(cmd.equals("B")) {
                try {
                    st.moveCursorBackwards();
                    System.out.println("Sucessfully moved cursor back to " + st.getCursor().getTitle() + ".");
                } catch (NoSuchNodeException e) {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("F")) {
                try {
                    System.out.print("Which option do you wish to go to: ");
                    String option = input.nextLine();
                    st.moveCursorForwards(option);
                    System.out.println("Sucessfully moved cursor to " + st.getCursor().getTitle() + ".");
                } catch (NoSuchNodeException e) {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("G"))
            {
                System.out.println("NOW BEGINNING GAME...\n");
                playGame();
            }

            if(cmd.equals("N"))
            {
                System.out.println(st.getPathFromRoot());
            }

            if(cmd.equals("M"))
            {
                System.out.print("Move current scene to: ");

                try
                {
                    String idstr = input.nextLine();

                    if(!idstr.matches("[0-9]+"))
                        throw new IllegalArgumentException("Must be moved to a valid index!");

                    int id = Integer.parseInt(idstr);
                    st.moveScene(id);
                    System.out.println("Sucessfully moved scene!");
                }
                catch (IllegalArgumentException | FullSceneException | NoSuchNodeException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("Q"))
            {
                System.out.println("Program terminating normally...");
                System.exit(0);
            }
        }
    }

    /**
     * Plays the "game", going from the root and prompts user to enter different choices until they reach an ending.
     */
    public static void playGame()
    {
        Scanner input = new Scanner(System.in);

        SceneNode originalPosition = st.getCursor();
        st.setCursor(st.getRoot());

        while(!st.getCursor().isEnding())
        {
            while(true)
            {
                try
                {
                    System.out.println();
                    st.getCursor().displayScene();
                    System.out.println("\nPlease enter an option: ");
                    st.moveCursorForwards(input.nextLine());
                    break;
                }
                catch (NoSuchNodeException e)
                {
                    System.out.println("You must enter a valid option!");
                }
            }
        }
        st.getCursor().displayScene();
        st.setCursor(originalPosition);
        System.out.println("\nThe End");
        System.out.println("\nReturning back to creation mode...");
    }
}
