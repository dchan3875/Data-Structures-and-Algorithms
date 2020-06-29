/**
 * The OrganismTree class implements a ternary tree of OrganismNode objects with various operations to modify the tree.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #5 CSE214</dd>
 * </dl>
 */

public class OrganismTree
{
    private OrganismNode root; //The root of this tree
    private OrganismNode cursor; //The cursor

    /**
     * Constructor for a new OrganismTree with apexPredator to be set as the root.
     *
     * @param apexPredator
     *     The OrganismNode to act as the root of the tree.
     *
     * <dl>
     * <dt><b>Postonditions:</b></dt>
     * <dd>A new OrganismTree is created, with both root and cursor referencing the node.</dd>
     */
    public OrganismTree(OrganismNode apexPredator)
    {
        root = apexPredator;
        cursor = root;
    }

    /**
     * Moves the cursor back to the root.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The cursor node now references the root.</dd>
     */
    public void cursorReset()
    {
        cursor = root;
    }

    /**
     * Moves the cursor to one of the cursor's children.
     *
     * @param name
     *      Name of the child node the cursor will be moved to.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>name references a valid name of one of the cursor's children.</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The cursor now points to a child of the original cursor reference, else an exception is thrown.</dd>
     *
     * @throws IllegalArgumentException
     *      Indicates that the name does not reference a direct child of the cursor.
     */
    public void moveCursor(String name) throws IllegalArgumentException
    {
        if(cursor.getLeft() != null && cursor.getLeft().getName().equalsIgnoreCase(name))
        {
            cursor = cursor.getLeft();
            System.out.println("Cursor successfully moved to " + cursor.getName() + "!\n");
        }
        else
        {
            if(cursor.getMiddle() != null && cursor.getMiddle().getName().equalsIgnoreCase(name))
            {
                cursor = cursor.getMiddle();
                System.out.println("Cursor successfully moved to " + cursor.getName() + "!\n");
            }
            else
            {
                if(cursor.getRight() != null && cursor.getRight().getName().equalsIgnoreCase(name))
                {
                    cursor = cursor.getRight();
                    System.out.println("Cursor successfully moved to " + cursor.getName() + "!\n");
                }
                else
                {
                    throw new IllegalArgumentException("ERROR: " + "'" + name + "'" + " does not exist as a prey for this predator.\n");
                }
            }

        }
    }

    /**
     * Returns a String of the organism at the cursor and all its possible prey.
     *
     * @return
     *     The String of the name of the cursor and all its possible prey.
     *
     * @throws IsPlantException
     *     Indicates that the cursor is pointing at a plant node, which cannot be a predator and cannot have any prey.
     */
    public String listPrey() throws IsPlantException
    {
        if(cursor.getIsPlant())
            throw new IsPlantException("ERROR: The cursor is at a plant node. Plants cannot be predators.\n");

        if(cursor.getLeft() == null)
            return("The node the cursor is pointing to currently has no prey.\n");

         String output = cursor.getName() + " -> ";

         if(cursor.getLeft() != null)
             output += cursor.getLeft().getName();
         if(cursor.getMiddle() != null)
             output += ", " + cursor.getMiddle().getName();
         if(cursor.getRight() != null)
             output += ", " + cursor.getRight().getName();

         return output;
    }

    /**
     * Returns a string with the path that leads from the root to the cursor.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Cursor has not moved.</dd>
     *
     * @return
     *      A string with the path from root to the cursor.
     */
    public String listFoodChain()
    {
        if(cursor == root)
        {
            System.out.println("The cursor is pointing at the apex predator. Food chain will not be printed.\n");
            return "";
        }
        else
        {
            return findPath(root, cursor.getName()) + "\n";
        }
    }

   // public String recursiveListFoodChain(OrganismNode on)
   // {
      // String output = findPath(root, cursor.getName());
       // return output;
  //  }

    /**
     * Recursive helper method that finds the path from the root to the cursor.
     *
     * @param on
     *      The OrganismNode to begin from.
     *
     * @param name
     *      The name of the OrganismNode to be found.
     *
     * @return
     *      A String of the concatenated path with the organism names.
     */
    public String findPath(OrganismNode on, String name)
    {
        String path = "";

        if(on == null)
            return null;

        if(on.getName().equalsIgnoreCase(name))
            return path + on.getName();

        if(findPath(on.getLeft(), name) != null)
        {
            path += on.getName() + " -> ";
            return path + findPath(on.getLeft(), name);
        }
        else
        {
            if(findPath(on.getMiddle(), name) != null)
            {
                path += on.getName() + " -> ";
                return path + findPath(on.getMiddle(), name);
            }
            else
            {
                if(findPath(on.getRight(), name) != null)
                {
                    path += on.getName() + " -> ";
                    return path + findPath(on.getRight(), name);
                }
            }
        }
        return null;
    }

    /**
     * Print out a layered and indented tree with a preorder traversal starting at the cursor.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Cursor and root has not moved.</dd>
     *
     */
    public void printOrganismTree()
    {
        System.out.println(recursivePrintOrganismTree(cursor, 0) + "\n");
    }

    /**
     * Recursive helper method that performs a preorder traversal of the tree.
     *
     * @param on
     *     The OrganismNode to begin at.
     *
     * @param lineNum
     *      The current line the node is at, helping with indentation.
     *
     * @return
     *      A neatly formatted string which contains the preorder traversal.
     */
    public String recursivePrintOrganismTree(OrganismNode on, int lineNum)
    {
        String output = "";
        String animalIdentifier = "|- ";
        String plantIndentifier = "- ";
        String indent = "    ";
        output += "\n";

        if(on == null)
            return "";

        int idx = lineNum;

        while(idx > 0)
        {
            output += indent;
            idx--;
        }

        if(!on.getIsPlant())
            output += animalIdentifier + on.getName();
        else
            output += plantIndentifier + on.getName();

        if(on.getLeft() != null)
            output += recursivePrintOrganismTree(on.getLeft(), lineNum + 1);

        if(on.getMiddle() != null)
            output += recursivePrintOrganismTree(on.getMiddle(), lineNum + 1);

        if(on.getRight() != null)
            output += recursivePrintOrganismTree(on.getRight(), lineNum + 1);

        return output;
    }

    /**
     * Returns a list of all plants at the cursor and beneath the cursor in the tree.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Cursor and root has not moved.</dd>
     *
     * @return
     *    A string containing  a list of all the plants in the tree starting from the cursor.
     */
    public String listAllPlants()
    {
        String output =  recursivePrintAllPlants(cursor);

        if(output.length() > 2)
            return recursivePrintAllPlants(cursor).substring(2) + "\n";
        else
            return("There are no plants at or beneath the cursor.\n");
    }

    /**
     * Recursive helper method that finds all the leaves of the tree from the cursor and appends them to a string if it is a plant.
     *
     * @param on
     *      The organismnode to begin at.
     *
     * @return
     *      A string with a list of all the plants.
     */
    public String recursivePrintAllPlants(OrganismNode on)
    {
        String output = "";

        if(on == null)
            return "";

        if(on.getIsPlant() && on.getLeft() == null && on.getMiddle() == null && on.getRight() == null) //found the last plant node of that subtree
            output += ", "  + on.getName();

        output += recursivePrintAllPlants(on.getLeft());
        output += recursivePrintAllPlants(on.getMiddle());
        output += recursivePrintAllPlants(on.getRight());

        return output;
    }

    /**
     * Creates a new OrganismNode that is an animal with a given diet and is added as a child of the cursor node.
     *
     * @param name
     *      Name of the animal child to be added.
     *
     * @param isHerbivore
     *      True if the animal consumes plants, false if it does not.
     *
     * @param isCarnivore
     *      True if the animal consumes other animals, false if it does not.
     *
     * <dl>
     * <dt><b>Pretconditions:</b></dt>
     * <dd>name does not reference a child node of the cursor with the same name. The cursor has an available position open.</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Cursor does not move, and a new animal OrganismNode is added else an exception is thrown.</dd>
     *
     * @throws IllegalArgumentException
     *      Indicates that there is already a child node with the given name.
     *
     * @throws PositionNotAvailableException
     *      Indicates that there are no more free child positions of the cursor node.
     */
    public void addAnimalChild(String name, boolean isHerbivore, boolean isCarnivore) throws IllegalArgumentException, PositionNotAvailableException
    {
        if((cursor.getLeft() != null && cursor.getLeft().getName().equalsIgnoreCase(name)) || (cursor.getMiddle() != null && cursor.getMiddle().getName().equalsIgnoreCase(name)) || (cursor.getRight() != null && cursor.getRight().getName().equalsIgnoreCase(name)))
            throw new IllegalArgumentException("ERROR: This prey already exists for the predator.\n");

        if(cursor.getLeft() != null && cursor.getMiddle() != null && cursor.getRight() != null)
            throw new PositionNotAvailableException("ERROR: There is no more room for more prey for this predator.\n");

        OrganismNode on = new OrganismNode();
        on.setName(name);
        on.setIsHerbivore(isHerbivore);
        on.setIsCarnivore(isCarnivore);

        try
        {
            cursor.addPrey(on);
            System.out.println("A(n) " + name + " has successfully been added as prey for the " + cursor.getName() + "\n");
        }
        catch(PositionNotAvailableException | IsPlantException | DietMismatchException e)
        {
            System.out.println(e.getMessage());
        }


    }

    /**
     * Creates a new plant OrganismNode and is added as a child of the cursor node.
     *
     * @param name
     *      Name of the OrganismNode to be added.
     *
     * <dl>
     * <dt><b>Pretconditions:</b></dt>
     * <dd>name does not reference a child node of the cursor with the same name. The cursor has an available position open.</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Cursor does not move, and a new plant OrganismNode is added else an exception is thrown.</dd>
     *
     * @throws IllegalArgumentException
     *      Indicates that there is already a child node with the given name.
     *
     * @throws PositionNotAvailableException
     *      Indicates that there are no more available child positions for the cursor node.
     */
    public void addPlantChild(String name) throws IllegalArgumentException, PositionNotAvailableException
    {
        if(cursor.getLeft() != null && cursor.getLeft().getName().equalsIgnoreCase(name) || cursor.getMiddle() != null && cursor.getMiddle().getName().equalsIgnoreCase(name) || cursor.getRight() != null && cursor.getRight().getName().equalsIgnoreCase(name))
            throw new IllegalArgumentException("ERROR: This prey already exists for the predator.\n");

        if(cursor.getLeft() != null && cursor.getMiddle() != null && cursor.getRight() != null)
            throw new PositionNotAvailableException("ERROR: There is no more room for more prey for this predator.\n");

        OrganismNode on = new OrganismNode();
        on.setName(name);
        on.setIsPlant(true);
        try
        {
            cursor.addPrey(on);
            System.out.println("A(n) " + name + " has successfully been added as prey for the " + cursor.getName() + ".\n");
        }
        catch(PositionNotAvailableException | IsPlantException | DietMismatchException e)
        {
            System.out.println(e.getMessage());
        }


    }

    /**
     * Removes the child of a cursor node with the given name.
     *
     * @param name
     *      The name of the child of the cursor node to be removed.
     *
     * <dl>
     * <dt><b>Pretconditions:</b></dt>
     * <dd>name references a direct child node of the cursor.</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The cursor does not move, and all descendants of the deleted node are removed as well. The deleted node's siblings are shifted accordingly.</dd>
     *
     * @throws IllegalArgumentException
     *      Indicates that the name given was either blank or it is does not reference any child node of the cursor.
     */
    public void removeChild(String name) throws IllegalArgumentException
    {
        if(name.isBlank())
            throw new IllegalArgumentException("Name of the organism cannot be blank. Nothing was removed.\n");

        if(cursor.getLeft() == null)
            throw new IllegalArgumentException(name + " is not a child of the cursor. Nothing was removed.\n");

        if(cursor.getLeft().getName().equalsIgnoreCase(name))
        {
            cursor.setLeft(cursor.getMiddle());
            cursor.setMiddle(cursor.getRight());
            cursor.setRight(null);
        }
        else
        {
            if(cursor.getMiddle().getName().equalsIgnoreCase(name))
            {
                cursor.setMiddle(cursor.getRight());
                cursor.setRight(null);
            }
            else
            {
                if(cursor.getRight().getName().equalsIgnoreCase(name))
                {
                    cursor.setRight(null);
                }
                else
                {
                    throw new IllegalArgumentException(name + " is not a child of the cursor. Nothing was removed.\n");
                }
            }
        }
        System.out.println("A(n) " + name + " has successfully been removed as prey for the " + cursor.getName() + ".\n");
    }
}