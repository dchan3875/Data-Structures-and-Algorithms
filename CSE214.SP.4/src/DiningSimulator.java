/**
 * The DiningSimulator class contains the main method to run the dining simulation, allowing users to customize some
 * aspects of the simulation.
 *
 * @author Darren Chan

 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #4 CSE214</dd>
 * </dl>
 */

import java.util.ArrayList;
import java.util.Scanner;

public class DiningSimulator
{
    private ArrayList<Restaurant> restaurants = new ArrayList<>(); //Holds the restaurant objects
    private int chefs; //number of chefs in simulation
    private final int AVERAGE_CHEFS = 3; //The average used for time calculation
    private final int MAX_CUSTOMERS_PER_ROUND = 3; //Max customers allowed to enter per restaurant per simulation unit
    private final int SIMUL_UNIT_TIME = 5; //Number of minutes each simulation unit is worth
    private int duration; //How long the simulation will last
    private double arrivalProb; //Probability of customer arrival
    private int maxCustomerSize; //Max number of customers per restaurant
    private int numRestaurants; //Number of restaurants for the simulation
    private int customersLost; //Number of lost customers
    private int totalServiceTime; //Time taken to sit, serve, and have customers finish eating
    private int customersServed; //Number of customers served
    private int profit; //Profit made
    private String[] foodabrv = {"S","CW","CT","GC","C"}; //Contains abbreviations for the food
    private String[] food = {"Steak","Chicken Wings", "Chicken Tenders", "Grilled Cheese", "Cheeseburger"}; //Each food
    private int[] foodTime = {30, 30, 25, 25, 15}; //Time in minutes to cook food
    private int[] costs = {25, 20, 15, 10, 10};  //Costs for food

    /**
     * Main method to begin creating simulation.
     *
     * @param args
     *      String array of arguments
     */
    public static void main(String[] args)
    {
        System.out.println("Starting simulator...\n");

        DiningSimulator ds = new DiningSimulator();
        Scanner input = new Scanner(System.in);
        boolean correctSetup = false;

        while(!correctSetup)
        {
            try
            {
                System.out.print("Enter the number of restaurants: ");
                ds.numRestaurants = Integer.parseInt(input.nextLine());

                System.out.print("Enter the maximum number of customers a restaurant can serve: ");
                ds.maxCustomerSize = Integer.parseInt(input.nextLine());

                System.out.print("Enter the arrival probability of a customer: ");
                ds.arrivalProb = Double.parseDouble(input.nextLine());

                System.out.print("Enter the number of chefs: ");
                ds.chefs = Integer.parseInt(input.nextLine());

                System.out.print("Enter the number of simulation units: ");
                ds.duration = Integer.parseInt(input.nextLine());

                if(ds.numRestaurants <= 0 || ds.maxCustomerSize <= 0 || ds.arrivalProb < 0.0 || ds.arrivalProb > 1.0 ||
                        ds.duration <= 0 || ds.chefs <= 0)
                    throw new IllegalArgumentException();

                correctSetup = true;
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Invalid argument was given!");
            }

            try
            {
                double avgTime = ds.simulate();

                System.out.println("\nSimulation Ending...");
                System.out.println("Total customer time: " + ds.totalServiceTime);
                System.out.println("Total customers served: " +  ds.customersServed);
                System.out.println("Average customer time lapse: " + String.format("%.2f", avgTime) + " minutes per order");
                System.out.println("Total Profit: $" + ds.profit);
                System.out.println("Customers that left: " + ds.customersLost);
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Begins the simulation.
     *
     * @return
     *      The average of the average time for serving customers.
     *
     * @throws IllegalArgumentException
     *      Indicates that an argument was invalid, and that there was no simulation.
     */
    public double simulate() throws IllegalArgumentException
    {
        if(numRestaurants <= 0 || maxCustomerSize <= 0 || arrivalProb < 0.0 || arrivalProb > 1.0 || duration <= 0 || chefs <= 0)
            throw new IllegalArgumentException("No Simulation!\n");

        for(int i = 1; i <= numRestaurants; i++)
            restaurants.add(new Restaurant(i));

        for(int currSimul = 1; currSimul <= duration; currSimul++)
        {
            System.out.println("\nTIME: " + currSimul);

            /* EVENT 1 - DONE EATING */
            for(Restaurant r : restaurants)
            {
                ArrayList<Customer> finished = r.dequeueAllDone();

                if(finished != null)
                {
                    for(Customer c : finished)
                    {
                        System.out.println("Customer #" + c.getOrderNumber() + " has enjoyed their food! $" +
                                c.getPriceOfFood() + " profit.");

                        profit += c.getPriceOfFood();
                        totalServiceTime += c.getInitialTimeToServe();
                        customersServed++;
                    }
                }
            }
            /* END EVENT 1*/

            /* EVENT 2 - CUSTOMER ARRIVAL */
            Restaurant[] potentialCustomers = new Restaurant[numRestaurants];

            for (int rest = 0; rest < numRestaurants; rest++)
            {
                Restaurant temp = new Restaurant();
                for (int i = 0; i < MAX_CUSTOMERS_PER_ROUND; i++) {
                    if (Math.random() < arrivalProb) {
                        Customer.setTotalCustomers(Customer.getTotalCustomers() + 1);
                        System.out.println("Customer #" + Customer.getTotalCustomers() + " has entered Restaurant " + (rest + 1) + ".");
                        temp.enqueue(new Customer(Customer.getTotalCustomers()));
                    }
                }
                potentialCustomers[rest] = temp;
            }

            for (int i = 0; i < potentialCustomers.length; i++)
            {
                while (!potentialCustomers[i].isEmpty())
                {
                    if (restaurants.get(i).size() == maxCustomerSize)
                    {
                        System.out.println("Customer #" + potentialCustomers[i].dequeue().getOrderNumber() + " cannot be seated! They have left the restaurant.");
                        customersLost++;
                    }
                    else
                    {
                        Customer c = new Customer(potentialCustomers[i].dequeue().getOrderNumber());
                        c.setTimeArrived(currSimul);
                        int menuitem = randInt(0, food.length - 1);
                        c.setFood(foodabrv[menuitem]);
                        c.setPriceOfFood(costs[menuitem]);
                        c.setTimeToServe(calculateTime(menuitem));
                        c.setInitalTimeToServe(calculateTime(menuitem));
                        System.out.println("Customer #" + c.getOrderNumber() + " has been seated with the order \"" + food[menuitem] + "\".");

                        restaurants.get(i).enqueue(c);
                    }
                }
            }
            /* END OF EVENT 2 */

            /* PRINT RESTAURANTS */
            for(Restaurant r : restaurants)
            {
                System.out.println(r);
                for(Customer c : r)
                {
                    c.setTimeToServe(c.getTimeToServe() - SIMUL_UNIT_TIME);
                }
            }
            /* END PRINT */
        }
        return (double)totalServiceTime/customersServed; //avg time
    }

    /**
     * Generates a random integer given the min and max values.
     *
     * @param minVal
     *      The minimum value, inclusive.
     * @param maxVal
     *      The maximum value, inclusive.
     * @return
     *      The randomly generated integer.
     */
    public int randInt(int minVal, int maxVal)
    {
        if(maxVal <= minVal)
            throw new IllegalArgumentException("Minimum value must be less than maximum value.");

        return minVal + (int)(Math.random() * ((maxVal - minVal) + 1));
    }

    /**
     * Calculates the time needed for the particular menu item.
     *
     * @param menuitem
     *      The index for the menu item.
     *
     * @return
     *      The time to cook the food and eat it.
     */
    public int calculateTime(int menuitem)
    {
        int modifier;

        if(chefs > AVERAGE_CHEFS)
            modifier = chefs % 3;
        else
            modifier = -chefs % 3;

        return(foodTime[menuitem] - 5 * modifier + 15);
    }

}
