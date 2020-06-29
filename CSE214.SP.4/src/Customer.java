/**
 * The Customer class represents a Customer that will dine at a Restaurant.
 *
 * @author Darren Chan

 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #4 CSE214</dd>
 * </dl>
 */

public class Customer
{
    private static int totalCustomers = 0; //Number of customers
    private int orderNumber; //Order number for the customer
    private String food; //Food that customer ordered
    private int priceOfFood; //Price of the food
    private int timeArrived; //When the customer arrived
    private int timeToServe; //How long to serve the customer (decreases)
    private int initalTimeToServe; //How long to serve the customer

    /**
     * Default constructor for a new Customer object.
     */
    public Customer()
    {
        orderNumber = -1;
        food = "";
        priceOfFood = -1;
        timeArrived = -1;
        timeToServe = -1;
        initalTimeToServe = -1;
    }

    /**
     * Constructor with a parameter that contains the Customer's order number.
     *
     * @param orderNumber
     *      The order number for the Customer.
     */
    public Customer(int orderNumber)
    {
        this.orderNumber = orderNumber;
        food = "";
        priceOfFood = -1;
        timeArrived = -1;
        timeToServe = -1;
        initalTimeToServe = -1;
    }

    /**
     * Creates a neatly formatted string representation of the Customer object.
     *
     * @return
     *      A string reprsentation of the Customer object.
     */
    public String toString()
    {
        return "[#" + orderNumber + ", " + food + ", " + timeToServe + " min.]";
    }

    /**
     * Getter for totalCustomers.
     *
     * @return
     *      The total number of customers.
     */
    public static int getTotalCustomers() {
        return totalCustomers;
    }

    /**
     * Setter for totalCustomers.
     *
     * @param totalCustomers
     *      The total number of customers to be set to.
     */
    public static void setTotalCustomers(int totalCustomers) {
        Customer.totalCustomers = totalCustomers;
    }

    /**
     * Getter for the Customer's order number.
     *
     * @return
     *      The customer's order number.
     */
    public int getOrderNumber()
    {
        return orderNumber;
    }

    /**
     * Setter for the Customer's food.
     *
     * @param food
     *      The food to be set to.
     *
     */
    public void setFood(String food) {
        this.food = food;
    }

    /**
     * Getter for the price of the Customer's food.
     *
     * @return
     *      The price of the Customer's food.
     */
    public int getPriceOfFood() {
        return priceOfFood;
    }

    /**
     * Setter for the price of the Customer's food.
     *
     * @param priceOfFood
     *      The price of the food to be set to.
     */
    public void setPriceOfFood(int priceOfFood) {
        this.priceOfFood = priceOfFood;
    }

    /**
     * Setter for the arrival time for the Customer's food.
     *
     * @param timeArrived
     *      The time of arrival to be set to.
     */
    public void setTimeArrived(int timeArrived) {
        this.timeArrived = timeArrived;
    }

    /**
     * Getter for the time left to serve the Customer.
     *
     * @return
     *      The time left to serve the Customer's food.
     */
    public int getTimeToServe() {
        return timeToServe;
    }

    /**
     * Setter for the time left to serve the Customer's food.
     *
     * @param timeToServe
     *      The time to be set to.
     */
    public void setTimeToServe(int timeToServe) {
        this.timeToServe = timeToServe;
    }

    /**
     * Setter for the initial time to serve the Customer's food.
     *
     * @param timeToServe
     *      The time to be set to.
     */
    public void setInitalTimeToServe(int timeToServe)
    {
        this.initalTimeToServe = timeToServe;
    }

    /**
     * Getter for the initial time to serve the Customer's food.
     *
     * @return
     *      The initial time to serve the Customer's food.
     */
    public int getInitialTimeToServe()
    {
        return this.initalTimeToServe;
    }

}
