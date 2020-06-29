/**
 * The EquationStack class implements a Stack that holds Strings. Used for evaluating Equations.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #3 CSE214</dd>
 * </dl>
 */

import java.util.Stack;

public class EquationStack extends Stack<String>
{
    private int size; //Size of the stack

    /**
     * Default constructor that instantiates a new EquationStack class. Calls super constructor and sets the size to 0.
     */
    public EquationStack()
    {
        super();
        size = 0;
    }

    /**
     * Pushes the given String s to the top of the stack.
     *
     * @param s
     *      The String to be added to the stack.
     *
     * @return
     *      The string that was added to the stack.
     */
    public String push(String s)
    {
        super.push(s);
        size++;
        return s;
    }

    /**
     * Removes and returns the String at the top of the stack.
     *
     * @return
     *      The String that was at the top of the stack.
     */
    public String pop()
    {
        size--;
        return super.pop();
    }

    /**
     * Determines if the current stack is empty.
     *
     * @return
     *      True if the stack is empty, false if it is not.
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Empties the stack and sets the size to 0.
     */
    public void clearStack()
    {
        while(!isEmpty())
            this.pop();

        size = 0;
    }
}
