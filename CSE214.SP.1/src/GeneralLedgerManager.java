/**
 * The GeneralLedgerManager class acts as the driver for the GeneralLedger. Allows users to add/remove/search the ledger.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #1 CSE214</dd>
 * </dl>
 */

import java.util.Scanner;

public class GeneralLedgerManager
{
    /**
     * Main method that instantiates a new GeneralLedger for the user.
     *
     * @param args
     *      A String argument array.
     */
    public static void main(String[] args)
    {
        GeneralLedger genLedger = new GeneralLedger();
        GeneralLedger backup = null;
        while(true)
        {
            String cmd = showCommands();
            Scanner input = new Scanner(System.in);
            if(cmd.equals("A")) //Adds a new transaction
            {
                try
                {
                    System.out.print("Enter Date (YYYY/MM/DD): ");
                    String date = input.nextLine();
                    if(date.matches(".*[a-zA-Z]+.*"))
                    {
                        throw new IllegalArgumentException("Transaction not added: Invalid date input.\n");
                    }

                    System.out.print("Enter Amount ($): ");
                    double amount = 0.0;

                    try
                    {
                        amount = Double.parseDouble(input.nextLine());
                    }
                    catch(NumberFormatException e)
                    {
                        throw new IllegalArgumentException("Transaction not added: Invalid amount input.\n");
                    }


                    System.out.print("Enter Description: ");
                    String description = input.nextLine();

                    genLedger.addTransaction(new Transaction(date, amount, description));
                }
                catch(IllegalArgumentException | TransactionAlreadyExistsException | InvalidTransactionException | FullGeneralLedgerException e)
                {
                    System.out.println(e.getMessage());
                }

            }

            if(cmd.equals("G")) //Displays information of a transaction with given position
            {
                System.out.print("Enter a position: ");
                try
                {
                    String pos = input.nextLine();
                    if(!pos.matches("[0-9]+"))
                    {
                        throw new IllegalArgumentException("Could not get transaction. Please enter a number only.\n");
                    }
                    genLedger.printTransaction(genLedger.getTransaction(Integer.parseInt(pos)));
                }
                catch(InvalidLedgerPositionException | InvalidTransactionException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("R")) //Removes a transaction with given position
            {
                System.out.print("Enter a position: ");
                try
                {
                    String pos = input.nextLine();
                    if(!pos.matches("[0-9]+"))
                    {
                        throw new IllegalArgumentException("Transaction not removed: Please enter a number only.\n");
                    }
                    genLedger.removeTransaction(Integer.parseInt(pos));
                }
                catch(InvalidLedgerPositionException|IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("P")) //Displays all transactions in general ledger
            {
                genLedger.printAllTransactions();
            }

            if(cmd.equals("F")) //Displays all transactions from a specified date
            {
                System.out.print("Enter Date (YYYY/MM/DD): ");
                String date = input.nextLine();
                if(date.matches(".*[a-zA-Z]+.*"))
                {
                    throw new IllegalArgumentException("Transaction not found: Invalid date input.\n");
                }
                GeneralLedger.filter(genLedger, date);
            }

            if(cmd.equals("L")) //Look for transaction with specific date/amount/description
            {
                System.out.print("Enter Date (YYYY/MM/DD): ");
                String date = input.nextLine();
                try {
                    if (date.matches(".*[a-zA-Z]+.*")) {
                        throw new IllegalArgumentException("Transaction not found: Invalid date input.\n");
                    }

                    System.out.print("Enter Amount ($): ");
                    double amount = 0.0;
                    try {
                        amount = Double.parseDouble(input.nextLine());
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Transaction not found: Invalid amount input.\n");
                    }

                    System.out.print("Enter Description: ");
                    String description = input.nextLine();

                    Transaction lookFor = new Transaction(date, amount, description);
                    try {
                        genLedger.printTransaction(lookFor);
                    } catch (InvalidTransactionException e) {
                        System.out.println(e.getMessage());
                    }
                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("S")) //Size of ledger
            {
                System.out.println("There are " + genLedger.size() + " transactions currently in the general ledger.\n");
            }

            if(cmd.equals("B")) //Backup
            {
                backup = (GeneralLedger) genLedger.Clone();
                System.out.println("Created a backup of the current general ledger.\n");
            }

            if(cmd.equals("PB")) //Print Transactions in Backup
            {
                if(backup == null)
                    System.out.println("There is currently no backup stored.\n");
                else
                    backup.printAllTransactions();
            }

            if(cmd.equals("RB")) //Revert to Backup
            {
                genLedger = backup;
                System.out.println("General ledger successfully reverted to the backup copy.\n");
            }

            if(cmd.equals("CB")) //Compare Backup with current
            {
                if(genLedger.equals(backup))
                    System.out.println("The current general ledger is the SAME as the backup copy.\n");
                else
                    System.out.println("The current general ledger is DIFFERENT from the backup copy.\n");
            }

            if(cmd.equals("PF")) //Print Financial Information
            {
                genLedger.printFinancialInformation();
            }

            if(cmd.equals("Q")) //Quit
            {
                System.out.println("Program terminating successfully...");
                System.exit(0);
            }
        }
    }

    /**
     * Displays a neatly formatted list of commands for the user.
     *
     * @return
     *      A neatly formatted String list of the commands.
     */
    public static String showCommands()
    {
        System.out.println(String.format("%-5s%-4s%-45s","(A)", " - ", "Add Transaction"));
        System.out.println(String.format("%-5s%-4s%-45s","(G)", " - ", "Get Transaction"));
        System.out.println(String.format("%-5s%-4s%-45s","(R)", " - ", "Remove Transaction"));
        System.out.println(String.format("%-5s%-4s%-45s","(P)", " - ", "Print Transaction in General Ledger"));
        System.out.println(String.format("%-5s%-4s%-45s","(F)", " - ", "Filter by Date"));
        System.out.println(String.format("%-5s%-4s%-45s","(L)", " - ", "Look for Transaction"));
        System.out.println(String.format("%-5s%-4s%-45s","(S)", " - ", "Size"));
        System.out.println(String.format("%-5s%-4s%-45s","(B)", " - ", "Backup"));
        System.out.println(String.format("%-5s%-4s%-45s","(PB)", " - ", "Print Transactions in Backup"));
        System.out.println(String.format("%-5s%-4s%-45s","(RB)", " - ", "Revert to Backup"));
        System.out.println(String.format("%-5s%-4s%-45s","(CB)", " - ", "Compare Backup with Current"));
        System.out.println(String.format("%-5s%-4s%-45s","(PF)", " - ", "Print Financial Information"));
        System.out.println(String.format("%-5s%-4s%-45s","(Q)", " - ", "Quit"));

        Scanner inputScan = new Scanner(System.in);
        System.out.print("Enter a selection: ");
        return inputScan.nextLine().toUpperCase().replaceAll(" ", "");
    }

}
