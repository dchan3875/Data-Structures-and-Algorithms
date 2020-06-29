/**
 * The Restaurant class extends a LinkedList and implements a Queue, functioning as one for Customer objects.
 *
 * @author Darren Chan

 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #4 CSE214</dd>
 * </dl>
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Restaurant extends LinkedList<Customer> implements Queue<Customer>
{
    private int restaurantNum; //The number for this restaurant

    /**
     * Blank constructor that instantiates a new Restaurant object.
     */
    public Restaurant()
    {
        restaurantNum = -1;
    }

    /**
     * Constructor that includes a restaurant number
     *
     * @param restaurantNum
     *      The number for the Restaurant object to be set to.
     */
    public Restaurant(int restaurantNum)
    {
        this.restaurantNum = restaurantNum;
    }

    /**
     * Adds a new Customer object to the Restaurant.
     *
     * @param c
     *      The Customer object to be added.
     */
    public void enqueue(Customer c)
    {
        this.add(c);
    }

    /**
     * Removes the first Customer object from the Restaurant.
     *
     * @return
     *      The Customer that was removed.
     */
    public Customer dequeue()
    {
        return this.remove();
    }

    /**
     * Dequeues all Customer(s) from the Restaurant that are done eating and adds them to a ArrayList.
     *
     * @return
     *      An ArrayList that contains all the Customers that were dequeued, if there are none, then it returns null.
     */
    public ArrayList<Customer> dequeueAllDone()
    {
        if(isEmpty())
            return null;

        ArrayList<Customer> removed = new ArrayList<>();
        int length = this.size();

        for(int i = 0; i < length; i++)
        {
            if(get(i).getTimeToServe() <= 0)
            {
                removed.add(remove(i));
                length--;
                i--;
            }
        }
        return removed;
    }

    /**
     * Neatly formats a String representation of the Restaurant object.
     *
     * @return
     *      A String representation of the Restaurant object.
     */
    public String toString()
    {
        if(this.isEmpty())
            return "R" + restaurantNum + ":" + " {}";

        String data = "R" + restaurantNum + ":" + " {";

        for(Customer c: this)
        {
            data += c.toString() + ", ";
        }

        data = data.trim();
        data = data.substring(0, data.lastIndexOf(","));
        data += "}";

        return data;
    }
}