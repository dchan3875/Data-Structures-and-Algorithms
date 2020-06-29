/**
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #1 CSE214</dd>
 * </dl>
 */
public class Transaction
{
    private String date; //Date of this Transaction
    private double amount; //Amount for this transaction
    private String description; //Description for the Transaction

    /**
     * Default Constructor for a new Transaction object.
     */
    public Transaction()
    {
        date = "";
        amount = 0.0;
        description = "";
    }

    /**
     * Constructor for a new Transaction object with date, amount, and description parameters.
     *
     * @param date
     *      The date the transaction was made.
     * @param amount
     *      The amount of the transaction.
     * @param description
     *      The description of the transaction.
     */
    public Transaction(String date, double amount, String description)
    {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    /**
     * Creates a copy of the current Transaction object.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Transaction has been instantiated</dd>
     *
     * @return
     *      A copy of this Transaction object.
     */
    public Object clone()
    {
        Transaction newTrans = new Transaction();
        newTrans.date = date;
        newTrans.amount = amount;
        newTrans.description = description;

        return newTrans;
    }

    /**
     * Compares an object with this Transaction object.
     * @param obj
     *      The object to be tested for equality with.
     * @return
     *      True if obj is a Transaction and has the same data as this Transaction object, else it returns false.
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof Transaction)
        {
            Transaction candidate = (Transaction) obj;
            return(candidate.date.equals(date) && candidate.description.equals(description) && candidate.amount == amount);
        }
        else
        {
            return false;
        }
    }

    /**
     * Getter for the date of this Transaction object.
     *
     * @return
     *      The date of this Transaction as a string.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Transaction has been instantiated.</dd>
     */
    public String getDate()
    {
        return date;
    }

    /**
     * Getter for the amount of this Transaction object.
     *
     * @return
     *      The amount for this Transaction as a double.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Transaction has been instantiated.</dd>
     */
    public double getAmount()
    {
        return amount;
    }

    /**
     * Getter for the description for this Transaction object.
     *
     * @return
     *      The description for this Transaction as a string.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>Transaction has been instantiated.</dd>
     */
    public String getDescription()
    {
        return description;
    }
}
