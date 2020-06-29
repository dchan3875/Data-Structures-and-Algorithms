/**
 * The Item class represents a single item in the grocery store.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #6 CSE214</dd>
 * </dl>
 */

import java.io.Serializable;

public class Item implements Serializable
{
    private String itemCode;
    private String name;
    private int qtyInStore;
    private int averageSalesPerDay;
    private int onOrder;
    private int arrivalDay;
    private double price;

    /**
     * Default constructor for an Item.
     */
    public Item()
    {
        itemCode = "";
        name = "";
        qtyInStore = 0;
        averageSalesPerDay = 0;
        onOrder = 0;
        arrivalDay = 0;
        price = 0;
    }

    /**
     * Constructor with parameters for the various data fields.
     *
     * @param itemCode
     *      Item code for the new Item object.
     * @param name
     *      Name of the new Item.
     * @param averageSalesPerDay
     *      Average sales per day for the new Item.
     * @param qtyInStore
     *      Quantity in the store for the new Item.
     * @param price
     *      Price of the new Item.
     * @param onOrder
     *      Number of units on order for the Item.
     */
    public Item(String itemCode, String name, int averageSalesPerDay, int qtyInStore, double price, int onOrder)
    {
        this.itemCode = itemCode;
        this.name = name;
        this.averageSalesPerDay = averageSalesPerDay;
        this.qtyInStore = qtyInStore;
        this.price = price;
        this.onOrder = onOrder;
        arrivalDay = 0;
    }

    /**
     * Returns a neatly formatted representation of the Item object.
     *
     * @return
     *      String representation of the Item.
     */
    public String toString()
    {
        return String.format("\n%-15s %-30s %-10s %10s %15s %15s %15s",
                itemCode, name, qtyInStore, averageSalesPerDay, price, onOrder, arrivalDay);
    }

    /**
     * Adjusts the quantity of the Item.
     *
     * @param adjustment
     *      The quantity to be adjusted by.
     */
    public void updateQty(int adjustment)
    {
        qtyInStore += adjustment;
    }

    /**
     * Setter for the itemCode.
     *
     * @param itemCode
     *      The itemCode to be set to.
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * Setter for the units onOrder.
     *
     * @param onOrder
     *      The number of units on order to be set to.
     */
    public void setOnOrder(int onOrder) {
        this.onOrder = onOrder;
    }

    /**
     * Setter for the arrival day of the item.
     *
     * @param arrivalDay
     *      The arrivalDay to be set to.
     */
    public void setArrivalDay(int arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    /**
     * Getter for the itemCode.
     *
     * @return
     *      The code for the item.
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Getter for the itemCode.
     *
     * @return
     *      The code for the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the quantity of the item in store.
     *
     * @return
     *      The quantity of the item in the store.
     */
    public int getQtyInStore() {
        return qtyInStore;
    }

    /**
     * Getter for the averageSalesPerDay.
     *
     * @return
     *      The averages sales per day of the item.
     */
    public int getAverageSalesPerDay() {
        return averageSalesPerDay;
    }

    /**
     * Getter for the number of items onOrder.
     *
     * @return
     *      The number of units on order.
     */
    public int getOnOrder() {
        return onOrder;
    }

    /**
     * Getter for the arrival day of the item.
     *
     * @return
     *      The arrival day of the item.
     */
    public int getArrivalDay() {
        return arrivalDay;
    }
}