/**
 * The HistoryStack class extends a Stack that holds Equations. HistoryStack contains the user's history of equations
 * entered into the calculator.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #3 CSE214</dd>
 * </dl>
 */

import java.util.Stack;

public class HistoryStack extends Stack<Equation>
{
    private int size; //Size
    private EquationStack undoHistory; //Stack of undone equations

    /**
     * Default constructor that instantiates a new HistoryStack along with the size and a stack that contains the
     * undone equations.
     */
    public HistoryStack()
    {
        undoHistory = new EquationStack();
        size = 0;
    }

    /**
     * Pushes the Equation object from the parameter to the top of the stack and increments the size counter.
     *
     * @param newEquation
     *      The new Equation object to be added to the stack.
     *
     * @return
     *      The Equation that was added.
     */
    public Equation push(Equation newEquation)
    {
        super.push(newEquation);
        size++;
        return newEquation;
    }

    /**
     * Pops the Equation object from the top of the stack and decrements the size counter.
     *
     * @return
     *      The Equation that was on the top of the stack.
     */
    public Equation pop()
    {
        size--;
        return super.pop();
    }

    /**
     * Removes the Equation that is on top of the HistoryStack and pushes it into the undoHistory stack.
     *
     * @throws IllegalStateException
     *      Indicates that the stack is already empty with nothing to undo.
     */
    public void undo() throws IllegalStateException
    {
        if(isEmpty())
            throw new IllegalStateException("There is nothing left to undo.\n");

        undoHistory.push(String.valueOf(this.pop().getEquation()));
        System.out.println("Equation ' " + undoHistory.peek() + " ' undone.\n");
    }

    /**
     * Removes the equation that is on top of the undoHistory stack and pushes it back into this HistoryStack.
     *
     * @throws IllegalStateException
     *      Indicates that there is nothing in the undoHistory stack.
     */
    public void redo() throws IllegalStateException
    {
        if(undoHistory.isEmpty())
            throw new IllegalStateException("There is nothing left to redo.\n");

        try
        {
            this.push(new Equation(undoHistory.pop()));
            System.out.println("Redoing equation ' " + this.peek().getEquation() + " ' .\n" );
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the Equation at the specified position in the stack, with the bottom of the stack being index = 1.
     *
     * @param position
     *      The position of the Equation to be returned.
     *
     * @return
     *      The Equation object at the specified position.
     *
     * @throws IllegalArgumentException
     *      Indicates that the position given was out of bounds.
     */
    public Equation getEquation(int position) throws IllegalArgumentException
    {
        if(position < 1 || position > size)
            throw new IllegalArgumentException("The given position is invalid.\n");

        if(position == size)
        {
            return this.peek();
        }

        HistoryStack temp = new HistoryStack();
        int counter = size;

        while(counter != position)
        {
            temp.push(this.pop());
            counter--;
        }

        Equation eqn = this.peek();

        while(!temp.isEmpty())
            this.push(temp.pop());

        return eqn;
    }

    /**
     * Empties this HistoryStack and the undoHistory stack. Sets size to 0.
     */
    public void clearHistory()
    {
        while(!isEmpty()) {
            this.pop();
        }
        size = 0;
        undoHistory.clearStack();
    }

    /**
     * Prints the Equation at the top of the stack in a neatly formatted table.
     *
     */
    public void printTop()
    {
        System.out.println(String.format("\n%-5s %-40s %-40s %-40s %25s %25s %15s", "#", "Equation", "Pre-Fix", "Post-Fix", "Answer", "Binary", "Hexadecimal"));
        System.out.println(String.format("%-195s", "----------------------------------------------------------------------------------------" +
                "------------------------------------------------------------------------------------------------------------"));

        System.out.println(String.format("%-5s %-185s", size, peek() + "\n"));
    }

    /**
     * Prints all the Equations in this stack in a neatly formatted table.
     *
     * @return
     *      The string representation of this HistoryStack.
     */
    public String toString()
    {
        String output = String.format("\n%-5s %-40s %-40s %-40s %25s %25s %15s", "#", "Equation", "Pre-Fix", "Post-Fix", "Answer", "Binary", "Hexadecimal");
        output += String.format("%-195s", "\n----------------------------------------------------------------------------------------" +
                "------------------------------------------------------------------------------------------------------------\n");
        int index = size;
        HistoryStack temp = new HistoryStack();

        while(!this.isEmpty())
        {
            output += String.format("%-5s %-185s\n", index, this.peek().toString());
            index--;
            temp.push(this.pop());
        }

        while(!temp.isEmpty())
        {
            this.push(temp.pop());
        }

        return output + "\n";
    }
}
