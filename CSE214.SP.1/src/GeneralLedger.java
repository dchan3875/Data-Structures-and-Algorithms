/**
 * The GeneralLedger class stores a list of Transaction objects in an array while keeping track of the debit/credit.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #1 CSE214</dd>
 * </dl>
 */

public class GeneralLedger
{
    private static final int MAX_TRANSACTIONS = 50; //Max transactions in a GeneralLedger.
    private Transaction[] ledger; //Array that stores the Transactions.
    private double totalDebitAmount; //Current amount of debit.
    private double totalCreditAmount; //Current amount of credit.
    private int currentSize; //Current number of Transactions in the array.

    /**
     * Default Constructor for a new GeneralLedger object.
     */
    public GeneralLedger()
    {
        ledger = new Transaction[MAX_TRANSACTIONS];
        currentSize = 0;
        totalDebitAmount = 0;
        totalCreditAmount = 0;
    }

    /**
     * Adds a new Transaction object to the ledger and updates the credit/debit.
     *
     * @param newTransaction
     *      The new Transaction object to be added.
     * @throws FullGeneralLedgerException
     *      If there is no more room for another Transaction.
     * @throws InvalidTransactionException
     *      If the Transaction date or amount is invalid.
     * @throws TransactionAlreadyExistsException
     *      If there is a Transaction with the same date/amount/description.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>GeneralLedger has been instantiated</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The Transaction has been removed and the rest of the Transactions are shifted properly.</dd>
     */
    public void addTransaction(Transaction newTransaction) throws FullGeneralLedgerException, InvalidTransactionException, TransactionAlreadyExistsException
    {
        if(currentSize >= MAX_TRANSACTIONS)
            throw new FullGeneralLedgerException("Transaction was not added: Ledger is full.\n");

        if(exists(newTransaction))
            throw new TransactionAlreadyExistsException("Transaction was not added: Transaction already exists.\n");

        if(newTransaction.getDate() == null || newTransaction.getDate().length() != 10 || newTransaction.getDate().charAt(4) != '/' || newTransaction.getDate().charAt(7) != '/')
            throw new InvalidTransactionException("Transaction was not added: Date formatted incorrectly.\n");

        String[] format = newTransaction.getDate().split("/");
        if((Integer.parseInt(format[0]) < 1900 && Integer.parseInt(format[0]) > 2050) || (Integer.parseInt(format[1]) < 1 && Integer.parseInt(format[1]) > 12)
                || (Integer.parseInt(format[2]) < 1 && Integer.parseInt(format[2]) > 30))
        {
            throw new InvalidTransactionException("Transaction was not added: Date formatted incorrectly.\n");
        }

        if(newTransaction.getAmount() == 0)
            throw new InvalidTransactionException("Transaction was not added: Amount cannot be zero.");

        System.out.println("Adding transaction...");

        if(newTransaction.getAmount() < 0)
            totalCreditAmount += (newTransaction.getAmount() * -1);
        else
            totalDebitAmount += newTransaction.getAmount();


        if(currentSize == 0)
        {
            ledger[currentSize] = newTransaction;
            currentSize++;
            System.out.println("New transaction added successfully!\n");
        }
        else
        {
            for(int i = currentSize; i >= 0; i--)
            {
                if(i != 0 && (ledger[i-1].getDate().compareTo(newTransaction.getDate()) > 0))
                {
                    //if the date at position i-1 is newer, shift
                    ledger[i] = ledger[i-1];
                }
                else
                {
                    ledger[i] = newTransaction;
                    System.out.println("New transaction added successfully!\n");
                    break;
                }
            }
            currentSize++;
        }
    }

    /**
     * Removes a Transaction from the ledger given the position.
     *
     * @param position
     *      The position of the Transaction to be removed.
     * @throws InvalidLedgerPositionException
     *      The position is not valid.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>GeneralLedger has been instantiated</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The Transaction has been removed and the rest of the Transactions are shifted properly.</dd>
     */
    public void removeTransaction(int position) throws InvalidLedgerPositionException
    {
        if(position > MAX_TRANSACTIONS || position < 1 || position > currentSize)
            throw new InvalidLedgerPositionException("Transaction was not removed: No such transaction in the general ledger.\n");

        int arrayPosition = position - 1;
        for(int i = arrayPosition; i < currentSize-1; i++)
        {
            ledger[i] = ledger[i+1];
        }
        currentSize--;
        System.out.println("Transaction removed successfully!\n");
    }

    /**
     * Prints the Transaction given in a properly formatted table.
     *
     * @param t
     *      The Transaction to be printed.
     * @throws InvalidTransactionException
     *      If the Transaction was not found.
     */
    public void printTransaction(Transaction t) throws InvalidTransactionException {
        if(currentSize == 0 || !exists(t))
            throw new InvalidTransactionException("Transaction not found: No such transaction in the general ledger.\n");

        String header = String.format("%-10s%-15s%-10s%-10s%-50s","No.", "Date", "Debit","Credit","Description");
        String lines = "\n-------------------------------------------" +
                "----------------------------------------------------------" +
                "--------";
        String data = "";

        for(int i = 0; i < currentSize; i++)
        {
            if(ledger[i].equals(t))
            {
                if(ledger[i].getAmount() > 0)
                {
                    data += String.format("\n%-10s%-15s%-10s%-10s%-50s", i+1, ledger[i].getDate(), ledger[i].getAmount(), "", ledger[i].getDescription());
                }
                else
                {
                    data += String.format("\n%-10s%-15s%-10s%-10s%-50s", i+1, ledger[i].getDate(), "", ledger[i].getAmount() * -1, ledger[i].getDescription());
                }
            }
        }
        System.out.println(header + lines + data);
    }

    /**
     * Returns a Transaction at the given position.
     *
     * @param position
     *      The position of the Transaction to be returned.
     * @return
     *      The Transaction object at the given position.
     * @throws InvalidLedgerPositionException
     *      If an invalid position is given.
     *
     */
    public Transaction getTransaction(int position) throws InvalidLedgerPositionException
    {
        if(position < 0 || position >= currentSize || position > MAX_TRANSACTIONS)
            throw new InvalidLedgerPositionException("Invalid transaction position given.\n");

        return ledger[position-1];
    }

    /**
     * Prints all Transactions at a given date in a given GeneralLedger.
     *
     * @param generalLedger
     *      The GeneralLedger which contains the Transactions.
     * @param date
     *      The date for the Transactions to be filtered for.
     */
    public static void filter(GeneralLedger generalLedger, String date)
    {
        String header = String.format("%-10s%-15s%-10s%-10s%-50s","No.", "Date", "Debit","Credit","Description");
        String lines = "\n-------------------------------------------" +
                "----------------------------------------------------------" +
                "--------";
        String data = "";

        for(int i = 0; i < generalLedger.currentSize; i++)
        {
            if(generalLedger.ledger[i].getDate().equals(date))
            {
                if(generalLedger.ledger[i].getAmount() > 0)
                {
                    data += String.format("\n%-10s%-15s%-10s%-10s%-50s", i+1, generalLedger.ledger[i].getDate(), generalLedger.ledger[i].getAmount(), "", generalLedger.ledger[i].getDescription());
                }
                else
                {
                    data += String.format("\n%-10s%-15s%-10s%-10s%-50s", i+1, generalLedger.ledger[i].getDate(), "", generalLedger.ledger[i].getAmount() * -1, generalLedger.ledger[i].getDescription());
                }
            }
        }
        System.out.println(header + lines + data);
    }

    /**
     * Creates a copy of this GeneralLedger.
     *
     * @return
     *      A copy/backup of this GeneralLedger.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>GeneralLedger has been instantiated.</dd>
     */
    public Object Clone()
    {
        GeneralLedger copy = new GeneralLedger();
        copy.totalCreditAmount = totalCreditAmount;
        copy.totalDebitAmount = totalDebitAmount;
        copy.currentSize = currentSize;

        for(int i = 0; i < currentSize; i++)
        {
            copy.ledger[i] = (Transaction) ledger[i].clone();
        }

        return copy;
    }

    /**
     * Checks if a Transaction is in the ledger.
     * @param transaction
     *      The Transaction object to be checked.
     * @return
     *      True if the Transaction exists in the GeneralLedger, False if not.
     * @throws InvalidTransactionException
     *      If there is invalid formatting for the date/amount for the Transaction.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>GeneralLedger has been instantiated.</dd>
     */
    public boolean exists(Transaction transaction) throws InvalidTransactionException
    {
        if(transaction.getAmount() == 0)
            throw new InvalidTransactionException("Invalid transaction: Amount cannot be zero.\n");
        if(!transaction.getDate().contains("/"))
            throw new InvalidTransactionException("Invalid transaction: Date formatted improperly.\n");

        String year = transaction.getDate().split("/")[0];
        String month = transaction.getDate().split("/")[1];
        String day = transaction.getDate().split("/")[2];

        if(Integer.parseInt(year) < 1900 || Integer.parseInt(year) > 2050)
            throw new InvalidTransactionException("Year was out of bounds. Please enter a year between 1900 and 2050.\n");
        if(Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12)
            throw new InvalidTransactionException("Month was out of bounds. Please enter a month between 1 and 12.\n");
        if(Integer.parseInt(day) < 0 || Integer.parseInt(day) > 30)
            throw new InvalidTransactionException("Day was out of bounds. Please enter a day between 0 and 30.\n");

        if(currentSize != 0)
        {
            for(int i = 0; i < currentSize; i++)
            {
                if(ledger[i].equals(transaction))
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of Transactions currently in the ledger.
     *
     * @return
     *      The number of Transactions currently in the ledger.
     */
    public int size()
    {
        return currentSize;
    }

    /**
     * Prints a neatly formatted table of the Transactions in the GeneralLedger using the toString() method.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>GeneralLedger has been instantiated.</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>All Transactions in the ledger are printed in a neatly formatted table.</dd>
     */
    public void printAllTransactions()
    {
        if(currentSize > 0)
            System.out.println(this.toString());
        else
            System.out.println("No transactions are currently in the general ledger.\n");
    }

    /**
     * Prints a neatly formatted list showing the total assets/liabilities and networth for the GeneralLedger.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>GeneralLedger has been instantiated.</dd>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The financial information is printed in a neatly formatted list.</dd>
     */
    public void printFinancialInformation()
    {
        String header = String.format("%-95s","Financial Data for Jack's Account");
        String lines = "\n-------------------------------------------" +
                "----------------------------------------------------------" +
                "--------";
        String assets = "\nAssets: $" + totalDebitAmount;
        String liabilities = "\nLiabilities: $" + totalCreditAmount;
        String networth = "\nNetworth: $" + (totalCreditAmount - totalDebitAmount) * -1 + "\n";

        System.out.println(header + lines + assets + liabilities + networth);
    }

    /**
     * Checks if the given object is equivalent to the current GeneralLedger.
     *
     * @param obj
     *      The object to be checked.
     * @return
     *      True if obj is a GeneralLedger with the same attributes, otherwise it is False.
     *
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof GeneralLedger)
        {
            GeneralLedger candidate = (GeneralLedger)obj;
            if(this.totalCreditAmount != candidate.totalCreditAmount || this.totalDebitAmount != candidate.totalDebitAmount || this.currentSize != candidate.currentSize)
                return false;

            for(int i = 0; i < currentSize; i++)
            {
                if(!ledger[i].equals(candidate.ledger[i]))
                    return false;
            }
        }
        return true;
    }

    /**
     * Formats the information of GeneralLedger and its Transactions neatly into a String.
     *
     * @return
     *      The formatted String representing the GeneralLedger.
     */
    public String toString()
    {
        String header = String.format("%-10s%-15s%-10s%-10s%-50s","No.", "Date", "Debit","Credit","Description");
        String lines = "\n-------------------------------------------" +
                "----------------------------------------------------------" +
                "--------";
        String data = "";

        for(int i = 0; i < currentSize; i++)
        {
            if(ledger[i].getAmount() > 0)
            {
                data += String.format("\n%-10s%-15s%-10s%-10s%-50s", i+1, ledger[i].getDate(),ledger[i].getAmount(),"",ledger[i].getDescription());
            }
            else
            {
                data += String.format("\n%-10s%-15s%-10s%-10s%-50s", i+1, ledger[i].getDate(), "", ledger[i].getAmount() * -1, ledger[i].getDescription());
            }
        }
        return header + lines + data;
    }
}
