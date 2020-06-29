/**
 * The Calculator class represents a calculator and provides the user with a menu to add/remove/edit calculations. Using
 * a HistoryStack, previous expressions entered can also be interacted with.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #3 CSE214</dd>
 * </dl>
 */

import java.util.Scanner;

public class Calculator
{
    /**
     * Generates a new HistoryStack and prompts the user for a command for the calculator.
     *
     * @param args
     *      String array of arguments.
     */
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        HistoryStack hs = new HistoryStack();

        System.out.println(String.format("%-54s","Welcome to Calculator\n"));
        while(true)
        {
            System.out.println(String.format("%-5s %-45s","[A]","Add new equation"));
            System.out.println(String.format("%-5s %-45s","[F]","Change equation from history"));
            System.out.println(String.format("%-5s %-45s","[B]","Print previous equation"));
            System.out.println(String.format("%-5s %-45s","[P]","Print full history"));
            System.out.println(String.format("%-5s %-45s","[U]","Undo"));
            System.out.println(String.format("%-5s %-45s","[R]","Redo"));
            System.out.println(String.format("%-5s %-45s","[C]","Clear History"));
            System.out.println(String.format("%-5s %-45s","[Q]","Quit"));

            System.out.print("Select an option: ");
            String cmd = input.nextLine().toUpperCase().trim();

            if(cmd.equals("A")) //Add new equation
            {
                System.out.print("Please enter an equation (in-fix notation): ");
                try
                {
                    Equation eqn = new Equation(input.nextLine());
                    hs.push(eqn);

                    if(!eqn.getBalance())
                        System.out.println("The equation is not balanced.\n");
                    else
                        System.out.println("The equation is balanced and the answer is " + eqn.getAnswer() + "\n");

                }
                catch(IllegalArgumentException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("F")) //Change equation from history
            {
                if(hs.isEmpty())
                    System.out.println("There are no equations to edit.\n");
                else
                {
                    System.out.print("Which equation would you like to change? ");
                    try
                    {
                        String pos = input.nextLine();
                        if(!pos.matches("^[0-9]*$"))
                            throw new IllegalArgumentException("Invalid position: Position must be a number.");

                        int numPos = Integer.parseInt(pos);
                        Equation e = hs.getEquation(numPos);
                        System.out.println("\nEquation at position " + numPos + ": " + e.getEquation());
                        boolean undergoing = true;
                        String replacementEqn = e.getEquation();

                        while(undergoing)
                        {
                            System.out.print("What would you like to do to the equation (Replace / Remove / Add)? ");

                            String cmdF = input.nextLine();

                            if(!cmdF.matches("[a-zA-Z]+"))
                                throw new IllegalArgumentException("Invalid option selected.");

                            /* REPLACING */
                            if(cmdF.equalsIgnoreCase("Replace"))
                            {
                                System.out.print("What position would you like to change? ");
                                String charPos = input.nextLine();
                                if (!charPos.matches("^[0-9]*$"))
                                    throw new IllegalArgumentException("Invalid position: Position must be a valid number.\n");

                                int changePos = Integer.parseInt(charPos);
                                if(changePos < 1 || changePos > replacementEqn.length())
                                    throw new IllegalArgumentException("Invalid position: Position must be a valid number.\n");


                                System.out.print("What would you like you replace it with? ");
                                String replacementChar = input.nextLine();

                                replacementEqn = replacementEqn.substring(0, changePos - 1) + replacementChar + replacementEqn.substring(changePos);

                                System.out.println("\nEquation: " + replacementEqn);
                            }

                            if(cmdF.equalsIgnoreCase("Remove"))
                            {
                                System.out.print("What position would you like to remove? ");
                                String charPos = input.nextLine();
                                if (!charPos.matches("^[0-9]*$"))
                                    throw new IllegalArgumentException("Invalid position: Position must be a valid number.\n");

                                int changePos = Integer.parseInt(charPos);
                                if(changePos < 1 || changePos > replacementEqn.length())
                                    throw new IllegalArgumentException("Invalid position: Position must be a valid number.\n");

                                replacementEqn = replacementEqn.substring(0, changePos - 1) + replacementEqn.substring(changePos);
                                System.out.println("\nEquation: " + replacementEqn);
                            }

                            if(cmdF.equalsIgnoreCase("Add"))
                            {
                                System.out.print("What position would you like to add something? ");
                                String charPos = input.nextLine();
                                if (!charPos.matches("^[0-9]*$"))
                                    throw new IllegalArgumentException("Invalid position: Position must be a valid number.\n");

                                int changePos = Integer.parseInt(charPos);
                                if(changePos < 1 || changePos > replacementEqn.length() + 1)
                                    throw new IllegalArgumentException("Invalid position: Position must be a valid number.\n");


                                System.out.print("What would you like to add? ");
                                String addChar = input.nextLine();

                                replacementEqn = replacementEqn.substring(0, changePos-1) + addChar + replacementEqn.substring(changePos-1);
                                System.out.println("\nEquation: " + replacementEqn);

                            }

                            System.out.print("Would you like to make any more changes? ");
                            String cont = input.nextLine();

                            if(!(cont.equalsIgnoreCase("Y") || cont.equalsIgnoreCase("Yes")))
                            {
                                undergoing = false;
                                Equation edited = new Equation(replacementEqn);
                                hs.push(edited);
                                if(edited.getBalance())
                                    System.out.println("The equation is balanced and the answer is " + edited.getAnswer() + "\n");
                                else
                                    System.out.println("The equation is not balanced.\n");
                            }
                        }
                    }
                    catch(IllegalArgumentException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }

            }

            if(cmd.equals("B")) //Print previous equation
            {
                if(hs.isEmpty())
                    System.out.println("There is nothing to print!\n");
                else
                {
                    hs.printTop();
                }
            }

            if(cmd.equals("P")) //Print full history
            {
                System.out.println(hs);
            }

            if(cmd.equals("U")) //Undo
            {
                try
                {
                    hs.undo();
                }
                catch(IllegalStateException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("R")) //Redo
            {
                try
                {
                    hs.redo();
                }
                catch(IllegalStateException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            if(cmd.equals("C")) //Clear history
            {
                hs.clearHistory();
                System.out.println("Calculator has been reset.\n");
            }

            if(cmd.equals("Q")) //Quit
            {
                System.out.println("Program terminating successfully...");
                System.exit(0);
            }
        }
    }
}
