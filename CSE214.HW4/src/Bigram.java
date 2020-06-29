/**
 * The Bigram class represents two characters. Contains two char variables for each that it represents.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #4 CSE214</dd>
 * </dl>
 */
public class Bigram
{
    private char first, second; // the chars

    /**
     * Default constructor with no parameters.
     *
     * Instantiates a new char
     */
    public Bigram()
    {
        first = 0;
        second = 0;
    }

    /**
     * Constructor with two given chars.
     *
     * @param a
     *     The first char to bet set to.
     * @param b
     *     The second char to be set to.
     */
    public Bigram(char a, char b)
    {
        first = a;
        second = b;
    }

    /**
     * Returns the first char in the Bigram.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Bigram has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The first char in the Bigram.
     */
    public char getFirst()
    {
        return first;
    }

    /**
     * Sets the first char in the Bigram to the given char.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Bigram has been instantiated.</dd>
     * </dl>
     *
     */
    public void setFirst(char first)
    {
        this.first = first;
    }

    /**
     * Returns the second char in the Bigram.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Bigram has been instantiated.</dd>
     * </dl>
     *
     * @return
     *     The second char in the Bigram.
     */
    public char getSecond()
    {
        return second;
    }

    /**
     * Sets the second char in the Bigram to the given char.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Bigram has been instantiated.</dd>
     * </dl>
     *
     */
    public void setSecond(char second)
    {
        this.second = second;
    }

    /**
     * Returns the characters in a concatenated String.
     *
     * @return
     *     A string of the concatenated letters.
     */
    public String toString()
    {
        String output = "";
        output = "(" + first + "," + second +")";
        return output;
    }
}
