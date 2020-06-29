/**
 * The Block class contains a array of type Variable, holding the data for each variable in each block of code.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #3 CSE214</dd>
 * </dl>
 *
 */

public class Block
{
    private Variable[] vars; //Holds all the variables
    private int currentLength = 0; //Number of variables currently in the array

    /**
     * Default constructor
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Creates a new variable array with a size of 10.</dd>
     * </dl>
     *
     */
    public Block()
    {
        vars = new Variable[10];
    }

    /**
     * Adds a new Variable to the array.
     *
     * @param v
     *     The variable to be added to the array.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The Block object has been instantiated.</dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The variable has been added to the array</dd>
     * </dl>
     */
    public void addVariable(Variable v)
    {
        vars[currentLength] = v;
        currentLength++;
    }

    /**
     * Finds a variable in the array given the name.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The Block object has been instantiated.</dd>
     * </dl>
     *
     * @param s
     *     The name of the Variable.
     * @return
     *     True if the variable with the given name is in the block, false if it is not.
     */
    public boolean findVariable(String s)
    {
        if(currentLength == 0)
            return false;

        for(int i = 0 ; i < currentLength; i++)
        {
            if(vars[i].getName().equals(s))
                return true;
        }

        return false;
    }

    /**
     * Prints the variable with the given name.
     *
     * @param s
     *     Name of the variable.
     * @return
     *     True if the variable was printed, false if it was not.
     */
    public boolean printVariable(String s)
    {
        if(findVariable(s) == false) {
            return false;
        }

        System.out.printf("%-25s%-15s\n", "Variable Name", "Initial Value");

        for(int i = 0 ; i < currentLength; i++)
        {
            if(vars[i].getName().equals(s))
            {
                System.out.printf("%-25s%-15s\n", vars[i].getName(), vars[i].getInitialValue());
                return true;
            }
        }

        return false;
    }

    /**
     * Prints all variables currently in the array.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The Block object has been instantiated.</dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>All variables in the array are printed and formatted neatly.</dd>
     * </dl>
     */
    public void printAllVariables()
    {
       if(currentLength == 0) {
            System.out.println("No local variables to print." + "\n");
            return;
        }

        System.out.printf("%-25s%-15s\n", "Variable Name", "Initial Value");
        String output = "";

        for(int i = 0; i < currentLength; i++)
        {
            output += String.format("%-25s%-15s\n", vars[i].getName(), vars[i].getInitialValue());
        }

        System.out.println(output);
    }

    /**
     * Returns the Variable array.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The Block object has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The Variable array
     */
    public Variable[] getVars() {
        return vars;
    }

    /**
     * Sets the current Variable array reference to another.
     *
     * @param vars
     *     The Variable array to be set to.
     */
    public void setVars(Variable[] vars) {
        this.vars = vars;
    }

    /**
     * Returns the current length of the array.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The Block object has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The length of the array.
     */
    public int getCurrentLength() {
        return currentLength;
    }

    /**
     * Sets the current length of the array to the given length.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The Block object has been instantiated.</dd>
     * </dl>
     *
     * @param currentLength
     *     The length of the array to be set to.
     */
    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

}
