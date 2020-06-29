/**
 * The SceneTree class represents a ternary tree of SceneNode objects. Contains a reference to the root
 * and a cursor.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #5 CSE214</dd>
 * </dl>
 */
public class SceneTree
{
    private SceneNode root;
    private SceneNode cursor;

    /**
     * Default constructor with no parameters. Sets root and cursor to null.
     */
    public SceneTree()
    {
        root = null;
        cursor = null;
    }

    /**
     * Constructor with a SceneNode parameter as the root. Cursor is set to root.
     *
     * @param root
     *      The root of the tree.
     */
    public SceneTree(SceneNode root)
    {
        this.root = root;
        cursor = root;
    }

    /**
     * Moves the cursor to the parent node.
     *
     * @throws NoSuchNodeException
     *      Indicates that the cursor cannot be moved further back/has no parent.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SceneTree has been instantiated.</dd>
     */
    public void moveCursorBackwards() throws NoSuchNodeException
    {
        if(cursor.getParent() == null)
            throw new NoSuchNodeException("Cursor cannot be moved any further back.");

        cursor = cursor.getParent();
    }

    /**
     * Moves the cursor to the specified child node.
     *
     * @param option
     *      String containing "A", "B", or "C", representing the child to be moved to.
     * @throws NoSuchNodeException
     *      Indicates that the current node does not have the child node.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>SceneTree has been instantiated.</dd>
     */
    public void moveCursorForwards(String option) throws NoSuchNodeException
    {
        if(option.equalsIgnoreCase("A") && cursor.getLeft() != null)
        {
            cursor = cursor.getLeft();
            return;
        }
        else
        {
            if(option.equalsIgnoreCase("B") && cursor.getMiddle() != null)
            {
                cursor = cursor.getMiddle();
                return;
            }
            else
            {
                if(option.equalsIgnoreCase("C") && cursor.getRight() != null)
                {
                    cursor = cursor.getRight();
                    return;
                }
            }
        }
        throw new NoSuchNodeException("That option does not exist!");
    }

    /**
     * Creates a new SceneNode object to be added to the leftmost available child.
     *
     * @param title
     *      Title of the new SceneNode.
     * @param sceneDescription
     *      Description of the new SceneNode.
     * @throws FullSceneException
     *      Indicates that the current node does not have any available positions.
     *
     */
    public void addNewNode(String title, String sceneDescription) throws FullSceneException
    {
        SceneNode scene = new SceneNode(title, sceneDescription);
        scene.setParent(cursor);
        cursor.addSceneNode(scene);
    }

    /**
     * Removes the specified node child.
     *
     * @param option
     *      String containing "A","B", or "C" representing the child nodes.
     * @throws NoSuchNodeException
     *      Indicates that no node exists with the given option.
     */
    public void removeScene(String option) throws NoSuchNodeException
    {
        if(!cursor.isEnding())
        {
            if(option.equalsIgnoreCase("A"))
            {
                cursor.setLeft(cursor.getMiddle());
                cursor.setMiddle(cursor.getRight());
                cursor.setRight(null);
            }
            else
            {
                if(option.equalsIgnoreCase("B"))
                {
                    cursor.setMiddle(cursor.getRight());
                    cursor.setRight(null);
                }
                else
                {
                    if(option.equalsIgnoreCase("C"))
                    {
                        cursor.setRight(null);
                    }
                    else
                    {
                        throw new NoSuchNodeException("Scene not removed: No scene found in the option.");
                    }
                }
            }
        }
    }

    /**
     * Moves the node at cursor (and children) to the node specified by the ID.
     * @param sceneIDToMoveTo
     *      The ID of the node to be moved to.
     * @throws NoSuchNodeException
     *      Indicates that the node does not exist.
     * @throws FullSceneException
     *      Indicates that the new node to be moved to has no space.
     */
    public void moveScene(int sceneIDToMoveTo) throws NoSuchNodeException, FullSceneException
    {
        if(cursor == null || cursor.getSceneID() == sceneIDToMoveTo)
            throw new NoSuchNodeException("There is no scene to be moved!");

        if(cursor == root)
            throw new IllegalArgumentException("The root cannot be moved!");

        if(sceneIDToMoveTo <= 0)
            throw new NoSuchNodeException("The specified ID does not exist!");

        SceneNode found = moveSceneHelper(root, sceneIDToMoveTo);

        if(isChild(cursor, found))
            throw new IllegalArgumentException("The scene cannot be moved as it will break the tree!");

        if(found.getLeft() != null && found.getMiddle() != null && found.getRight() != null)
            throw new FullSceneException("There is no available spot for the scene to be moved!");

        cursor.getParent().removeChild(cursor);
        cursor.setParent(found);

        if(found.getLeft() == null)
        {
            found.setLeft(cursor);
        }
        else
        {
            if(found.getMiddle() == null)
            {
                found.setMiddle(cursor);
            }
            else
            {
                if(found.getRight() == null)
                {
                    found.setRight(cursor);
                }
            }
        }

    }

    /**
     * Recursive helper method for moveScene(). Searches and returns the reference for the
     * id of the given SceneNode.
     *
     * @param node
     *      Node to start searching at.
     *
     * @param id
     *      ID of the node to find.
     *
     * @return
     *      SceneNode reference of the node with id.
     */
    public SceneNode moveSceneHelper(SceneNode node, int id)
    {
        if(node.getSceneID() == id)
            return node;

        SceneNode found = null;

        if(node.getLeft() != null)
        {
            found = moveSceneHelper(node.getLeft(), id);
        }

        if(node.getMiddle() != null && found == null)
        {
            found = moveSceneHelper(node.getMiddle(), id);
        }

        if(node.getRight() != null && found == null)
        {
            found = moveSceneHelper(node.getRight(), id);
        }

        return found;
    }

    /**
     * Returns the path of SceneNodes to travel from the root to the cursor.
     *
     * @return
     *      String representation of the path from root to cursor.
     */
    public String getPathFromRoot()
    {
        if(cursor == root) {
            return ("Cursor is already at the root.");
        }
        else
        {
            return pathFinder(root, cursor);
        }
    }

    /**
     * Recursive helper method to find path from root.
     *
     * @param node
     *      Node to start from.
     * @param node2
     *      Node to find.
     * @return
     *      String representation of the path between node and node2.
     */
    public String pathFinder(SceneNode node, SceneNode node2)
    {
        String path = "";

        if(node == null)
            return null;

        if(node == node2)
            return node.getTitle();

        if(pathFinder(node.getLeft(), node2) != null)
        {
            path += node.getTitle() + ", ";
            return path + pathFinder(node.getLeft(), node2);
        }
        else
        {
            if(pathFinder(node.getMiddle(), node2) != null)
            {
                path += node.getTitle() + ", ";
                return path + pathFinder(node.getMiddle(), node2);
            }
            else
            {
                if(pathFinder(node.getRight(), node2) != null)
                {
                    path += node.getTitle() + ", ";
                    return path + pathFinder(node.getRight(), node2);
                }
            }
        }
        return null;
    }

    /**
     * Checks if a node is a child of another.
     *
     * @param node
     *      The parent node.
     *
     * @param possibleChild
     *      SceneNode to be tested for being a possible child.
     *
     * @return
     *      true if possibleChild is a descendant of node.
     */
    public boolean isChild(SceneNode node, SceneNode possibleChild)
    {
        if(node == possibleChild)
            return true;

        boolean found = false;

        if(node.getLeft() != null)
        {
            found = isChild(node.getLeft(), possibleChild);
        }

        if(node.getMiddle() != null && !found)
        {
            found = isChild(node.getMiddle(), possibleChild);
        }

        if(node.getRight() != null && !found)
        {
            found = isChild(node.getRight(), possibleChild);
        }

        return found;
    }

    /**
     * Returns a formatted string representation of the tree.
     *
     * @return
     *      String representation of the tree.
     */
    public String toString()
    {
        return recursiveStringRep(root, 0, "");
    }

    /**
     * Recursive helper method for toString(). Turns entire tree into a string representation.
     *
     * @param node
     *      The node to start at.
     *
     * @param lineNum
     *      The current depth of the tree.
     *
     * @param option
     *      The option "A","B",or "C" for the child nodes.
     *
     * @return
     *      String representation of the tree.
     */
    public String recursiveStringRep(SceneNode node, int lineNum, String option)
    {
        if(node == null)
            return "";

        String output = "";
        output += "\n";
        String indent = "    ";
        int idx = lineNum;

        while(idx > 0)
        {
            output += indent;
            idx--;
        }

        if(cursor == node)
            output += option + " " + node.toString() + " <---";
        else
            output += option + " " + node.toString();

        if(node.getLeft() != null)
            output += recursiveStringRep(node.getLeft(), lineNum+1, "A)");

        if(node.getMiddle() != null)
            output += recursiveStringRep(node.getMiddle(), lineNum+1, "B)");

        if(node.getRight() != null)
            output += recursiveStringRep(node.getRight(), lineNum+1, "C)");

        return output;
    }

    /**
     * Setter for the cursor.
     *
     * @param cursor
     *      The node the cursor to be set to.
     */
    public void setCursor(SceneNode cursor) {
        this.cursor = cursor;
    }

    /**
     * Getter for the root reference.
     *
     * @return
     *      Reference to the root of the tree.
     */
    public SceneNode getRoot() {
        return root;
    }

    /**
     * Getter for the node referenced by cursor.
     *
     * @return
     *      Reference to the cursor.
     */
    public SceneNode getCursor() {
        return cursor;
    }
}
