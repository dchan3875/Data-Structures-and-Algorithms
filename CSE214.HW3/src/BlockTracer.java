/**
 * The BlockTracer class reads a text file with C code and holds the variable information in a stack of Blocks
 * to be printed later.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #3 CSE214</dd>
 * </dl>
 *
 */

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class BlockTracer
{
    /**
     * Begins with a call to nextFile(), prompting the user to enter a file name.
     * @param args
     *     A string array for arguments.
     * @throws IOException
     *     Indicates that the file entered could not be found.
     */
    public static void main(String[] args) throws IOException
    {
        nextFile();
    }

    /**
     * Takes a file and reads through it and traces the blocks of code.
     *
     * @param f
     *     The file to be read through and traced.
     *
     * <dl>
     * <dt><b>Preconditions:</b></dt>
     * <dd>The file exists in the current working directory.</dd>
     * </dl>
     *
     * <dl>
     * <dt><b>Postconditions:</b></dt>
     * <dd>The code in the file is parsed, recording each variable and its initial value. Will also print the variable or all
     * local variables when told to do so. </dd>
     * </dl>
     *
     * @throws IOException
     *     Indicates that the file could not be found.
     */
    public static void trace(File f) throws IOException
    {
        FileInputStream fis = new FileInputStream(f);
        InputStreamReader inStream = new InputStreamReader(fis);
        BufferedReader stdin = new BufferedReader(inStream);

        Stack<Block> blockStack= new Stack<Block>(); //creates new Stack of blocks
        String data;

        while((data = stdin.readLine()) != null) { //reads the file until the end is reached

            if(data.contains("{")) {// Prepare new block
                blockStack.push(new Block());
            }

            if(data.contains("int ")) //add new variable to block
            {
                String temp = data;

                if(temp.contains("/*$print") && temp.contains(";")) //tests if theres a variable and a declaration, if so then it removes the print
                {
                    temp = temp.substring(0, temp.indexOf("/*$print")) + temp.substring(temp.indexOf("*/"));
                }

                if(!temp.contains("/*$print"))
                {
                    String str = temp.split("int ")[1];
                    String[] arr = str.split(",");

                    for(String s : arr)
                    {
                        if(s.contains("/*$print") && s.contains("*/")) //ignores /*$print **/ declaration
                        {
                        }
                        else
                        {
                            if (s.contains("="))
                            {
                                s = s.split(";")[0];
                                blockStack.peek().addVariable(new Variable(s.split("=")[0].trim(), Integer.parseInt(s.split("=")[1].trim()))); //if equals, assign given value.
                            }
                            else
                            {
                                blockStack.peek().addVariable(new Variable(s.split("=")[0].trim())); //if no equals, then variable is using standard constructor.
                            }
                        }
                    }
                }
            }

            if(data.contains("/*$print")) {    //indicates printing to be done
                if((data.contains("LOCAL"))) { //prints all local variables currently at the top of the stack
                    blockStack.peek().printAllVariables();
                }
                else {
                    String variableName = data.trim().split(" ")[1].split("\\*/")[0].trim();
                    Boolean found = false;
                    Stack<Block> tempStack = new Stack<Block>();
                    while(!blockStack.isEmpty()) { //checks from top to bottom of stack to see if variable exists.
                        tempStack.push(blockStack.pop());
                        if(tempStack.peek().printVariable(variableName)) { //indicates variable was found
                            found = true;
                            break;
                        }
                    }
                    while(!tempStack.isEmpty()) //returns blocks back to main stack
                        blockStack.push(tempStack.pop());

                    if(!found)
                        System.out.println("Variable not found: " + variableName + "\n");
                }
            }

            if(data.contains("}")) { //removes the block from the top of the stack
                blockStack.pop();
            }
        }
        nextFile(); //prompts user for another file again
    }

    /**
     * Prompts the user to enter a file name.
     *
     * @throws IOException
     *     Indicates that the file was not found.
     */
    public static void nextFile() throws IOException {
        System.out.print("Please enter a file name or enter QUIT to exit: ");
        Scanner scanIn = new Scanner(System.in);
        String fileName = scanIn.nextLine();
        if(fileName.equals("QUIT")) {
            System.out.println("Exiting...");
            System.exit(0);
        }


        File f = new File(fileName);

        while(!f.exists()) {
            System.out.println("File not found.");
            System.out.print("Please enter a file name or enter QUIT to exit: ");
            fileName = scanIn.nextLine();

            if(fileName.equals("QUIT")) {
                System.out.println("Exiting...");
                System.exit(0);
            }
            else
                f = new File(fileName);
        }
        trace(f);
    }
}


