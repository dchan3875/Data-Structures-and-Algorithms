/**
 * The SceneNode class represents a node in the SceneTree, representing a single scene in the adventure.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #5 CSE214</dd>
 * </dl>
 */
public class SceneNode
{
    private static int numScenes = 0; //Total number of scenes
    private String title; //Title of the scene
    private String sceneDescription; //Description of the scene
    private int sceneID; //ID of the scene
    private SceneNode left; //Left child reference
    private SceneNode middle; //Middle child reference
    private SceneNode right; //Right child reference
    private SceneNode parent; //Parent reference

    /**
     * Default constructor without parameters.
     *
     * Instantiates a new node with all node references set to null, empty title/description, and an ID number.
     */
    public SceneNode()
    {
        numScenes+=1;
        title = "";
        sceneDescription = "";
        sceneID = numScenes;
        left = null;
        middle = null;
        right = null;
        parent = null;
    }

    /**
     * Constructor with a title and description parameter.
     *
     * @param title
     *      Title of the scene
     * @param sceneDescription
     *      Description of the scene
     */
    public SceneNode(String title, String sceneDescription)
    {
        numScenes+=1;
        this.title = title;
        this.sceneDescription = sceneDescription;
        sceneID = numScenes;
        left = null;
        middle = null;
        right = null;
        parent = null;
    }

    /**
     * Adds the new node passed in into the first available slot in the child nodes (from left to right).
     *
     * @param scene
     *      The new node to be added.
     * @throws FullSceneException
     *      Indicates that the current node has no available spots open.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SceneNode has been instantiated.</dd>
     */
    public void addSceneNode(SceneNode scene) throws FullSceneException
    {
        if(scene == null)
            throw new IllegalArgumentException("Invalid scene given!");

        if(left == null) {
            left = scene;
        }
        else if(middle == null) {
            middle = scene;
        }
        else if(right == null) {
            right = scene;
        }
        else {
            numScenes--;
            throw new FullSceneException("You cannot add another scene!");
        }
    }

    /**
     * Checks if the node has any child nodes.
     *
     * @return
     *      true if there are no children, false if there is at least one.
     */
    public boolean isEnding()
    {
        return (left == null && middle == null && right == null);
    }

    /**
     * Prints information about the node and its children during gameplay.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SceneNode has been instantiated.</dd>
     */
    public void displayScene()
    {
        System.out.println(title);
        System.out.println(sceneDescription + "\n");

        if(left != null)
        {
            System.out.println("A) " + left.getTitle());
        }
        if(middle != null)
        {
            System.out.println("B) " + middle.getTitle());
        }
        if(right != null)
        {
            System.out.println("C) " + right.getTitle());
        }
    }

    /**
     * Displays all information about the current scene in creation mode.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SceneNode has been instantiated.</dd>
     */
    public void displayFullScene()
    {
        System.out.println("\nScene ID #" + sceneID);
        System.out.println("Title: " + title);
        System.out.println("Scene: " + sceneDescription);

        String leads = "";

        if(this.isEnding())
        {
            leads = "NONE";
        }
        else
        {
            leads += "'" + left.getTitle() + "'" + " (#" + left.getSceneID() + "), ";

            if(middle != null)
                leads += "'" + middle.getTitle() + "'" + " (#" + middle.getSceneID() + "), ";

            if(right != null)
                leads += "'" + right.getTitle() + "'" + " (#" + right.getSceneID() + "), ";
        }
        leads = leads.substring(0, leads.lastIndexOf(", "));
        System.out.println("Leads to: " + leads);
    }

    /**
     * Removes the child of the node and shifts all remaining nodes left.
     *
     * @param node
     *      The child node to be removed.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SceneNode has been instantiated.</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Child node is removed and all remaining nodes are far left as possible.</dd>
     */
    public void removeChild(SceneNode node)
    {
        if(left == node)
        {
            left = middle;
            middle = right;
            right = null;
        }
        else
        {
            if(middle == node)
            {
                middle = right;
                right = null;
            }
        }
    }

    /**
     * Returns a string representation of the SceneNode.
     *
     * @return
     *      String representation of the SceneNode.
     */
    public String toString()
    {
        return title + " (#" + sceneID + ")";
    }

    /**
     * Setter for the parent reference.
     *
     * @param parent
     *      The node to be set to.
     */
    public void setParent(SceneNode parent)
    {
        this.parent = parent;
    }

    /**
     * Setter for the left child reference.
     *
     * @param sceneNode
     *      The node to be set to.
     */
    public void setLeft(SceneNode sceneNode)
    {
        left = sceneNode;
    }

    /**
     * Setter for the middle child reference.
     *
     * @param sceneNode
     *      The node to be set to.
     */
    public void setMiddle(SceneNode sceneNode)
    {
        middle = sceneNode;
    }

    /**
     * Setter for the right child reference.
     *
     * @param sceneNode
     *      The node to be set to.
     */
    public void setRight(SceneNode sceneNode)
    {
        right = sceneNode;
    }

    /**
     * Getter for number of scenes.
     *
     * @return
     *      The number of scenes.
     */
    public static int getNumScenes() {
        return numScenes;
    }

    /**
     * Getter for the title of the SceneNode.
     *
     * @return
     *      The title of the scene.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Getter for the ID of the SceneNode.
     *
     * @return
     *      The ID for the node.
     */
    public int getSceneID()
    {
        return sceneID;
    }

    /**
     * Getter for the left child node reference.
     *
     * @return
     *      The left child reference.
     */
    public SceneNode getLeft()
    {
        return left;
    }

    /**
     * Getter for the middle child node reference.
     *
     * @return
     *      The middle child reference.
     */
    public SceneNode getMiddle()
    {
        return middle;
    }

    /**
     * Getter for right child reference.
     *
     * @return
     *      The right child node reference.
     */
    public SceneNode getRight()
    {
        return right;
    }

    /**
     * Getter for the parent reference.
     *
     * @return
     *      The parent node reference.
     */
    public SceneNode getParent()
    {
        return parent;
    }
}
