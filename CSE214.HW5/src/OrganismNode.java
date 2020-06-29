/**
 * The OrganismNode class represents a node in the OrganismTree, with each node representing a species (either plant or animal).
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #5 CSE214</dd>
 * </dl>
 */

public class OrganismNode
{
    private String name; //name of the organism
    private boolean isPlant; //true if it is a plant, false if it is not
    private boolean isHerbivore; //true if the organism can only consume plants
    private boolean isCarnivore; //true if the organism can only consume meat
    private OrganismNode left; //left child node
    private OrganismNode middle; //middle child node
    private OrganismNode right; //right child node

    /**
     * Default constructor for an OrganismNode.
     *
     * Instantiates a new node with all child nodes and name set to null. Booleans are all set to false.
     */
    public OrganismNode()
    {
        name = null;
        isPlant = false;
        isHerbivore = false;
        isCarnivore = false;
        left = null;
        middle = null;
        right = null;
    }

    /**
     * Setter for the name of the OrganismNode.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * @param name
     *     The name of the node to be set to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the isPlant boolean.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * @param isPlant
     *     True if the node is to be set to be a plant, false if it will not be a plant.
     */
    public void setIsPlant(boolean isPlant) {
        this.isPlant = isPlant;
    }

    /**
     * Setter for the isCarnivore boolean.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * @param isCarnivore
     *    True if the node is going to be carnivorous or omnivorous in diet, false if will not.
     */
    public void setIsCarnivore(boolean isCarnivore) {
        this.isCarnivore = isCarnivore;
    }

    /**
     * Setter for the isHerbivore boolean.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * @param isHerbivore
     *    True if the node is going to be carnivorous or omnivorous in diet, false if will not.
     */
    public void setIsHerbivore(boolean isHerbivore) {
        this.isHerbivore = isHerbivore;
    }

    /**
     * Setter for the left child node.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>A OrganismNode will be set to be the left child.</dd>
     *
     * @param left
     *    The OrganismNode to be set to be the left child.
     */
    public void setLeft(OrganismNode left)
    {
        this.left = left;
    }

    /**
     * Setter for the middle child node.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>A OrganismNode will be set to be the middle child.</dd>
     *
     * @param middle
     *    The OrganismNode to be set to be the middle child.
     */
    public void setMiddle(OrganismNode middle)
    {
        this.middle = middle;
    }

    /**
     * Setter for the right child node.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>A OrganismNode will be set to be the right child.</dd>
     *
     * @param right
     *    The OrganismNode to be set to be the right child.
     */
    public void setRight(OrganismNode right)
    {
        this.right = right;
    }

    /**
     * Returns the name of the OrganismNode
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * @return
     *     The name of the node.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether or not the node is a plant.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * @return
     *     True if the node is a plant, false if it is not.
     */
    public boolean getIsPlant() {
        return isPlant;
    }

    /**
     * Returns whether or not the node is a herbivore.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * @return
     *     True if the node is a herbivore or omnivore, false if it is not.
     */
    public boolean getIsHerbivore() {
        return isHerbivore;
    }

    /**
     * Returns whether or not the node is a carnivore
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * @return
     *     True if the node is a carnivore or omnivore, false if it is not.
     */
    public boolean getIsCarnivore() {
        return isCarnivore;
    }

    /**
     * Returns the left child of the node.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * @return
     *     The left child OrganismNode.
     */
    public OrganismNode getLeft()
    {
        return left;
    }

    /**
     * Returns the middle child of the node.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * @return
     *     The middle child OrganismNode.
     */
    public OrganismNode getMiddle()
    {
        return middle;
    }

    /**
     * Returns the right child of the node.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>OrganismNode has been instantiated.</dd>
     *
     * @return
     *     The right child OrganismNode.
     */
    public OrganismNode getRight()
    {
        return right;
    }

    /**
     * Adds a new prey to this node. Fills in the left, middle, or right child in that order.
     *
     * @param preyNode
     *     The OrganismNode to be added as the prey of this node.
     *
     * @throws PositionNotAvailableException
     *      Indicates that all three of the child positions are full.
     *
     * @throws IsPlantException
     *      Indicates that the this node is a plant, therefore it cannot be a predator and cannot have any prey.
     *
     * @throws DietMismatchException
     *      Indicates that the preyNode does not correctly correspond to the diet of this node.
     */
    public void addPrey(OrganismNode preyNode) throws PositionNotAvailableException, IsPlantException, DietMismatchException
    {
        if(left != null && middle != null && right != null)
            throw new PositionNotAvailableException("ERROR: There is no more room for more prey for this predator.\n");

        if(this.isPlant)
            throw new IsPlantException("ERROR: The cursor is at a plant node. Plants cannot be predators.\n");

        if(!this.isHerbivore && preyNode.isPlant) //if the parent does not eat plants but the prey is a plant
            throw new DietMismatchException("ERROR: This prey cannot be added as it does not match the diet of the predator.\n");

        if(!this.isCarnivore && !preyNode.isPlant)//if the parent does not eat meat but the prey is a meat
            throw new DietMismatchException("ERROR: This prey cannot be added as it does not match the diet of the predator.\n");

        if(left == null)
            left = preyNode;
        else
            if(middle == null)
                middle = preyNode;
            else
                if(right == null)
                    right = preyNode;
    }
}
