/**
 * The PlayfairEncryptionEngine class holds the menu driver and generates a KeyTable, allowing users to do various
 * encryptions/decryptions.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #4 CSE214</dd>
 * </dl>
 */
import java.util.Scanner;

public class PlayfairEncryptionEngine
{
    private static KeyTable ktable; //the KeyTable object

    /**
     * Sets up the KeyTable and prompts the user for a key.
     *
     * @param args
     *     A String array argument.
     */
    public static void main(String[] args)
    {
        ktable = new KeyTable();
        Scanner input = new Scanner(System.in);
        System.out.print("Enter key phrase: ");

        Boolean generated = false;

        while(!generated) {
            try {
                ktable = KeyTable.buildFromString(input.nextLine().toUpperCase());
                generated = true;
                System.out.println("Generated successfully!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        showCommands();

    }

    /**
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>A formatted command menu is printed and a scanner is created to record user input.</dd>
     * </dl>
     */
    public static void showCommands()
    {
        System.out.println(String.format("%-10s%-45s","(CK)", "Change Key"));
        System.out.println(String.format("%-10s%-45s","(PK)", "Print Key"));
        System.out.println(String.format("%-10s%-45s","(EN)", "Encrypt"));
        System.out.println(String.format("%-10s%-45s","(DE)", "Decrypt"));
        System.out.println(String.format("%-10s%-45s","(Q)", "Quit"));

        Scanner inputScan = new Scanner(System.in);
        System.out.print("Please enter a command: ");
        enteredCommand(inputScan.nextLine().toUpperCase());
    }

    /**
     * @param cmd
     *     The command entered by the user.
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The inputted command will be executed.</dd>
     * </dl>
     */
    public static void enteredCommand(String cmd)
    {
        if(cmd.equals("CK")) //CHANGE KEY
        {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter key phrase: ");

            try
            {
                ktable = KeyTable.buildFromString(input.nextLine());
                System.out.println("Key was changed successfully!");
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
        }

        if(cmd.equals("PK")) //PRINT KEY
        {
            System.out.println(ktable);
        }

        if(cmd.equals("EN")) //ECRYPT
        {
            Scanner input = new Scanner(System.in);

            System.out.print("Please enter a phrase to encrypt: ");
            Phrase ph = Phrase.buildPhraseFromString(input.nextLine());

            System.out.println("Encrypted text is: " + ph.encrypt(ktable));
        }

        if(cmd.equals("DE")) //DECRYPT
        {
            Scanner input = new Scanner(System.in);

            System.out.print("Please enter a phrase to decrypt: ");
            Phrase ph = Phrase.buildPhraseFromString(input.nextLine());

            System.out.println("Decrypt text is: " + ph.decrypt(ktable));
        }

        if(cmd.equals("Q")) //QUIT
        {
            System.out.println("Program terminating...");
            System.exit(1);
        }
        showCommands();
    }
}
