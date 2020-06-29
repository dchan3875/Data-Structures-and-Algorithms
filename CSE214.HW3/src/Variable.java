/**
 * The Variable class holds the information of each variable, specifically name and value.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #3 CSE214</dd>
 * </dl>
 *
 */

public class Variable
{
    private String name; //name of the variable
    private int initialValue; //initial value of the variable

    /**
     * Default constructor
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The Variable object has been instantiated with an empty name and a value of 0.</dd>
     * </dl>
     */
    public Variable()
    {
        name = "";
        initialValue = 0;
    }

    /**
     * Constructor with given name passed in.
     *
     * @param name
     *     The name of the variable to be created.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The Variable object has been instantiated with the given name and an initial value of 0.</dd>
     * </dl>
     */
    public Variable(String name)
    {
        this.name = name.replaceAll(" ", "").split(";")[0];
        initialValue = 0;
    }

    /**
     * Constructor with name and value.
     *
     * @param name
     *     The name of the variable to be created.
     *
     * @param value
     *     The value of the variable to be created.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The Variable object has been instantiated with the given name and value..</dd>
     * </dl>
     */
    public Variable(String name, int value)
    {
        this.name = name;
        initialValue = value;
    }

    /**
     * Returns the name of the variable
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The Variable object has been instantiated with the given name and value..</dd>
     *
     * @return
     *     The name of the Variable.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Variable to the given name.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The Variable object has been instantiated with the given name and value..</dd>
     *
     * @param name
     *     The name the Variable will be set to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the initial value of the variable.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The Variable object has been instantiated with the given name and value..</dd>
     *
     * @return
     *     The initial value of the Variable.
     */
    public int getInitialValue() {
        return initialValue;
    }

    /**
     * Sets the initialValue of the Variable to the given value.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The Variable object has been instantiated with the given name and value..</dd>
     *
     * @param initialValue
     *     The initial value to be set to.
     */
    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }

}
