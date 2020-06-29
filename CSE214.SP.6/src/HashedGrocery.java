/**
 * The HashedGrocery class contains methods which allow users to have various controls over the inventory of the
 * grocery store. Data is stored in a hash table.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #6 CSE214</dd>
 * </dl>
 */

import java.io.*;
import java.util.Hashtable;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HashedGrocery implements Serializable
{
    private int businessDay;
    private Hashtable<String, Item> groceryTable;
    private final int RESTOCK_DAYS = 2;
    private final int STOCK_DAYS = 3;

    /**
     * Default constructor. Instantiates the Hashtable and sets the business day to 1.
     */
    public HashedGrocery()
    {
        groceryTable = new Hashtable<>();
        businessDay = 1;
    }

    /**
     * Adds an item to the hash table.
     *
     * @param item
     *      Item object to be added to the table.
     *
     * @throws IllegalArgumentException
     *      Indicates that the code for the Item already exists.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HashedGrocery has been instantiated.</dd>
     */
    public void addItem(Item item) throws IllegalArgumentException
    {
        if(groceryTable.containsKey(item.getItemCode()))
            throw new IllegalArgumentException(item.getItemCode() + ": Cannot add item as item code already exists!");

        groceryTable.put(item.getItemCode(), item);
        System.out.println(item.getItemCode() + ": " + item.getName() + " is added to inventory.");
    }

    /**
     * Updates the quantity of the given item.
     *
     * @param item
     *      Item object to be updated.
     *
     * @param adjustByQty
     *      Quantity for the object to be adjusted by.
     *
     * @throws IllegalArgumentException
     *      Indicates that either the Item does not exist in the table or there is not enough stock to be
     *      updated.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>HashedGrocery has been instantiated.</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Item is updated in the table with a new quantity.</dd>
     */
    public void updateItem(Item item, int adjustByQty) throws IllegalArgumentException
    {
        if(!groceryTable.containsKey(item.getItemCode()))
            throw new IllegalArgumentException(item.getItemCode() + ": Cannot buy as it is not in the grocery store!");

        if(item.getQtyInStore() + adjustByQty < 0)
            throw new IllegalArgumentException(item.getItemCode() + ": Not enough stock for sale. Not updated.");

        groceryTable.get(item.getItemCode()).updateQty(adjustByQty);
    }

    /**
     * Checks to see if the Item in the table needs to be restocked.
     *
     * @param itemCode
     *      The itemCode of the Item to be checked.
     *
     * @return
     *      True if it needs to be restocked, else it is false.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>If an item needs to be restocked, an order is placed.</dd>
     */
    public boolean restockCheck(String itemCode)
    {
        Item item = groceryTable.get(itemCode);

        if(item.getQtyInStore() < STOCK_DAYS * item.getAverageSalesPerDay() && item.getOnOrder() <= 0)
        {
            item.setOnOrder(RESTOCK_DAYS * item.getAverageSalesPerDay());
            item.setArrivalDay(businessDay + RESTOCK_DAYS + 1);

            return true;
        }
        return false;
    }


    /**
     * Adds all items in a text file to the hash table.
     *
     * @param filename
     *      File name of the text file to be parsed.
     *
     * @throws IllegalArgumentException
     *      Indicates that the file was not found.
     */
    public void addItemCatalog(String filename) throws IllegalArgumentException
    {
        try
        {
            if(filename.isBlank())
                throw new FileNotFoundException("Invalid file name given!");

            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            JSONParser parser = new JSONParser();
            JSONArray objArr = (JSONArray) parser.parse(isr);

            for(Object obj : objArr)
            {
                JSONObject jsonObj = (JSONObject) obj;
                String itemCode = (String) jsonObj.get("itemCode");
                String itemName = (String) jsonObj.get("itemName");
                int avgSales = Integer.parseInt((String) jsonObj.get("avgSales"));
                int qtyInStore = Integer.parseInt((String) jsonObj.get("qtyInStore"));
                double price = Double.parseDouble((String) jsonObj.get("price"));
                int amtOnOrder = Integer.parseInt((String) jsonObj.get("amtOnOrder"));

                try
                {
                    addItem(new Item(itemCode, itemName, avgSales, qtyInStore, price, amtOnOrder));
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch(IllegalArgumentException | IOException e)
        {
            throw new IllegalArgumentException("File not found!");
        }
        catch(ParseException e)
        {
            System.out.println("Error while parsing!");
        }
    }

    /**
     * Processes all of the sales in a text file.
     *
     * @param filename
     *      Name of the file to be parsed.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>All orders are processed and stocks are updated.</dd>
     *
     * @throws IllegalArgumentException
     *      Indicates that the file was not found.
     */
    public void processSales(String filename) throws IllegalArgumentException
    {
        try
        {
            if(filename.isBlank())
                throw new FileNotFoundException("Invalid file name given!");

            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            JSONParser parser = new JSONParser();
            JSONArray objArr = (JSONArray) parser.parse(isr);

            for(Object obj : objArr)
            {
                JSONObject jsonObj = (JSONObject) obj;
                String itemCode = (String) jsonObj.get("itemCode");
                int qtySold = Integer.parseInt((String) jsonObj.get("qtySold"));


                try
                {
                    Item item = groceryTable.get(itemCode);
                    if(item == null)
                        throw new IllegalArgumentException(itemCode + ": Cannot buy as it is not in the grocery store.");

                    updateItem(item, -qtySold);
                    String output = itemCode + ": " + qtySold + " units of " + item.getName() + " are sold.";
                    if(restockCheck(itemCode))
                        output += " Order has been placed for " + RESTOCK_DAYS * item.getAverageSalesPerDay() + " more units.";

                    System.out.println(output);
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch(IllegalArgumentException | IOException e)
        {
            throw new IllegalArgumentException("File not found!");
        }
        catch(ParseException e2)
        {
            System.out.println("Error while parsing!");
        }
    }

    /**
     * Advances the business day by 1 and checks for any arriving orders and updates those items.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>Business day is incremented by 1. If orders have arrived, stocks are updated and the on order and arrival
     * dates for the items are set to 0.</dd>
     */
    public void nextBusinessDay()
    {
        businessDay++;
        String orders = "";

        for(String key : groceryTable.keySet())
        {
            Item item = groceryTable.get(key);

            if(item.getArrivalDay() > 0)
            {
                if(item.getArrivalDay() == businessDay)
                {
                    orders += item.getItemCode() + ": " + item.getOnOrder() + " units of " + item.getName() + ".\n";
                    updateItem(item, item.getOnOrder());
                    item.setOnOrder(0);
                    item.setArrivalDay(0);
                }
            }
        }

        if(!orders.isBlank())
            System.out.println(orders);
        else
            System.out.print("No orders have arrived.\n");
    }

    /**
     * Neatly formats the hash table into a String.
     *
     * @return
     *      String representation of the HashedGrocery object.
     */
    public String toString()
    {
        String output = String.format("%-15s %-30s %-10s %10s %15s %15s %15s\n",
            "Item Code", "Name", "Qty", "AvgSales", "Price", "OnOrder", "ArrOnBusDay");

        for(int i = 0; i < 116; i++)
        {
            output += "-";
        }

        for(String key : groceryTable.keySet())
        {
            output += groceryTable.get(key);
        }

        return output;
    }

    /**
     * Getter for the current business day.
     *
     * @return
     *      The current business day.
     */
    public int getBusinessDay()
    {
        return businessDay;
    }
}