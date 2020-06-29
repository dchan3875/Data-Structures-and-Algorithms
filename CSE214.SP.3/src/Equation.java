/**
 * The Equation class represents an Equation for the calculator, with the actual equation being stored in a String.
 * Calculates and stores the answer in decimal, binary, and hexadecimal. The equation is also stored in infix, prefix,
 * and postfix forms.
 *
 * @author Darren Chan
 * <dl>
 * <dt><b>Assignment:</b></dt>
 * <dd>Homework #3 CSE214</dd>
 * </dl>
 */

public class Equation
{
    private String equation; //The equation itself
    private String prefix; //Prefix form of the equation
    private String postfix; //Postfix form of the equation
    private double answer; //The answer in decimal
    private String binary; //The answer in binary
    private String hex; //The answer in hexadecimal
    private boolean balanced; //Determines if the equation is balanced.

    /**
     * Default constructor that instantiates all the variables and sets the equation as unbalanced.
     */
    public Equation(){
        equation = "";
        prefix = "N/A";
        postfix = "N/A";
        answer = 0.0;
        binary = "0";
        hex = "0";
        balanced = false;
    }

    /**
     * Constructor for the Equation with a given String representation of the equation.
     *
     * @param equation
     *      The String that represents the equation.
     *
     * @throws IllegalArgumentException
     *      Indicates that the equation contains alphabets or the equation is blank.
     */
    public Equation(String equation) throws IllegalArgumentException
    {
        if(equation.matches(".*[a-zA-Z]+.*") || equation.isBlank())
            throw new IllegalArgumentException("Invalid equation was attempted to be added.\n");

        this.equation = equation;
        balanced = isBalanced();

        try
        {
            calculateExpression();
            postfix = calculatePostfix(equation);
            prefix = calculatePrefix(equation);

            try
            {
                binary = decToBin((int)Math.round(answer));
                hex = decToHex((int)Math.round(answer));
            }
            catch(IllegalStateException e)
            {
                binary = "0";
                hex = "0";
            }
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
            balanced = false;
        }

    }

    /**
     * Determines if the equation is balanced by checking the parentheses in the equation.
     *
     * @return
     *      True if the equation is balanced, false if it is not.
     */
    public boolean isBalanced()
    {
        for(int i = 0; i < equation.length() - 1; i++)
            if(isOperator(equation.charAt(i)) && isOperator(equation.charAt(i+1)))
                return false;

        EquationStack parentheses = new EquationStack();
        for(int i = 0; i < equation.length(); i++)
        {
            if(equation.charAt(i) == '(')
            {
                parentheses.push(String.valueOf(equation.charAt(i)));
            }
            else
            {
                if(equation.charAt(i) == ')')
                {
                    if(parentheses.isEmpty())
                        return false;
                    else
                        parentheses.pop();
                }
            }
        }
        return parentheses.isEmpty();
    }

    /**
     * Determines if the character passed in is a operator.
     *
     * @param c
     *      The character to be tested.
     *
     * @return
     *      True if c is an operator, false if it is not.
     */
    public boolean isOperator(char c)
    {
        char[] operators = {'+', '-', '/', '^', '*', '%'};
        for(char cr : operators)
            if(c == cr)
                return true;

        return false;
    }

    /**
     * Determines if the character passed is an operand.
     *
     * @param c
     *      The character to be tested.
     *
     * @return
     *      True if c is an operand, false if it is not.
     */
    public boolean isOperand(char c)
    {
        char[] operands = {'0','1','2','3','4','5','6','7','8','9'};

        for(char cr : operands)
            if(c == cr)
                return true;

        return false;
    }

    /**
     * Converts the integer passed in to its binary representation.
     *
     * @param number
     *      The number to be converted.
     *
     * @return
     *      The String that contains the binary representation of the equation.
     *
     * @throws IllegalStateException
     *      Indicates that the equation is not balanced.
     */
    public String decToBin(int number) throws IllegalStateException
    {
        if(!balanced)
            throw new IllegalStateException("The equation is not balanced!");

        String binary = "";

        while(number != 0)
        {
            binary = (number % 2) + binary;
            number/=2;
        }

        return binary;
    }

    /**
     * Converts the integer passed in to its hexadecimal representation.
     *
     * @param number
     *      The number to be converted.
     *
     * @return
     *      String that contains the hexadecimal representation.
     *
     * @throws IllegalStateException
     *      Indicates that the equation is not balanced.
     *
     */
    public String decToHex(int number) throws IllegalStateException
    {
        if(!balanced)
            throw new IllegalStateException("The equation is not balanced!");

        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        String hex = "";

        while(number != 0)
        {
            hex = digits[number % 16] + hex;
            number/=16;
        }

        return hex;
    }

    /**
     * Calculates the answer from the infix expression and calls helper methods to convert the equation to binary/hex.
     *
     * @throws IllegalArgumentException
     *      Indicates that the equation is trying to divide by 0.
     */
    public void calculateExpression() throws IllegalArgumentException
    {
        if(!balanced)
        {
            prefix = "N/A";
            postfix = "N/A";
            answer = 0.0;
            binary = "0";
            hex = "0";
        }
        else
        {
            EquationStack operators = new EquationStack();
            EquationStack operands = new EquationStack();

            /* ---------- BEGIN ANSWER FROM INFIX ---------------*/
            for(int i = 0; i < equation.length(); i++)
            {
                if(isOperand(equation.charAt(i)))
                {
                    String opnd = "";
                    if(i == equation.length() - 1 || !isOperand(equation.charAt(i+1)))
                    {
                        opnd = String.valueOf(equation.charAt(i));
                    }
                    else
                    {
                        while(i != equation.length() && isOperand(equation.charAt(i)))
                        {
                            opnd += String.valueOf(equation.charAt(i));
                            i = i+1;

                        }
                    }
                    operands.push(opnd);
                }

                if(i != equation.length() && isOperator(equation.charAt(i)))
                {
                    if(!operators.isEmpty() && operatorPriority(operators.peek().charAt(0)) > operatorPriority(equation.charAt(i)))
                    {
                        double operand2 = Double.parseDouble(operands.pop());
                        double operand1 = Double.parseDouble(operands.pop());
                        String operator = operators.pop();

                        double result = 0.0;
                        switch (operator)
                        {
                            case "+":
                                result = operand1 + operand2;
                                break;
                            case "-":
                                result = operand1 - operand2;
                                break;
                            case "*":
                                result = operand1 * operand2;
                                break;
                            case "/":
                                result = operand1 / operand2;
                                break;
                            case "^":
                                result = Math.pow(operand1, operand2);
                                break;
                            case "%":
                                result = operand1 % operand2;
                                break;
                        }
                        operands.push(String.valueOf(result));
                        operators.push(String.valueOf(equation.charAt(i)));
                    }
                    else
                    {
                        operators.push(String.valueOf(equation.charAt(i)));
                    }
                }

                if(i != equation.length() && equation.charAt(i) == ')' && !operands.isEmpty() && !operators.isEmpty())
                {
                    double operand2 = Double.parseDouble(operands.pop());
                    double operand1 = Double.parseDouble(operands.pop());
                    String operator = operators.pop();

                    double result = 0.0;
                    switch (operator)
                    {
                        case "+":
                            result = operand1 + operand2;
                            break;
                        case "-":
                            result = operand1 - operand2;
                            break;
                        case "*":
                            result = operand1 * operand2;
                            break;
                        case "/":
                            result = operand1 / operand2;
                            break;
                        case "^":
                            result = Math.pow(operand1, operand2);
                            break;
                        case "%":
                            result = operand1 % operand2;
                            break;
                    }
                    operands.push(String.valueOf(result));
                }
            }

            while(!operands.isEmpty() && !operators.isEmpty())
            {
                double operand2 = Double.parseDouble(operands.pop());
                double operand1 = Double.parseDouble(operands.pop());
                String operator = operators.pop();

                double result = 0.0;
                switch (operator)
                {
                    case "+":
                        result = operand1 + operand2;
                        break;
                    case "-":
                        result = operand1 - operand2;
                        break;
                    case "*":
                        result = operand1 * operand2;
                        break;
                    case "/":
                        if(operand2 == 0)
                            throw new IllegalArgumentException("Division by zero is not allowed!\n");
                        result = operand1 / operand2;
                        break;
                    case "^":
                        result = Math.pow(operand1, operand2);
                        break;
                    case "%":
                        result = operand1 % operand2;
                        break;
                }
                operands.push(String.valueOf(result));
            }
            answer = Double.parseDouble(operands.pop());
            answer = (double)Math.round(answer * 1000)/1000;
            /* ---------------- END OF ANSWER FROM INFIX -------------- */
            postfix = calculatePostfix(equation);
            prefix = calculatePrefix(equation);
        }
    }

    /**
     * Converts the equation to its postfix representation.
     *
     * @param equation
     *      The equation to be converted.
     *
     * @return
     *      String that contains that represents the postfix form of the equation.
     */
    public String calculatePostfix(String equation)
    {
        if(!balanced)
        {
            return "N/A";
        }

        EquationStack operators = new EquationStack();
        String postfix = "";

        for(int i = 0; i < equation.length(); i++)
        {
            if(equation.charAt(i) == '(')
                operators.push("(");

            if(isOperand(equation.charAt(i)))
            {
                String opnd = "";
                if(i == equation.length() - 1 || !isOperand(equation.charAt(i+1)))
                {
                    opnd = String.valueOf(equation.charAt(i));
                }
                else
                {
                    while(i != equation.length() && isOperand(equation.charAt(i)))
                    {
                        opnd += String.valueOf(equation.charAt(i));
                        i = i+1;
                    }
                }
                postfix += opnd + " ";
            }

            if(i != equation.length() && isOperator(equation.charAt(i)))
            {
                if(operators.isEmpty())
                {
                    operators.push(String.valueOf(equation.charAt(i)));
                }
                else
                {
                    while(!operators.isEmpty() && operatorPriority(operators.peek().charAt(0)) >= operatorPriority(equation.charAt(i)))
                    {
                        postfix += operators.pop() + " ";
                    }
                    operators.push(String.valueOf(equation.charAt(i)));
                }
            }

            if(i != equation.length() && equation.charAt(i) == ')')
            {
                while(!operators.peek().equals("("))
                {
                    postfix += operators.pop() + " ";
                }
                operators.pop();
            }
        }

        while(!operators.isEmpty())
            postfix += operators.pop() + " ";
        return postfix.trim();
    }

    /**
     * Converts the equation to its prefix representation.
     *
     * @param equation
     *      The equation to be converted.
     *
     * @return
     *      String that represents the equation in its prefix form.
     */
    public String calculatePrefix(String equation)
    {
        if(!balanced)
            return "N/A";

        String reversedInfix = equationReverse(equation);
        reversedInfix = calculatePostfix(reversedInfix);
        reversedInfix = equationReverse(reversedInfix);
        return reversedInfix.trim();
    }

    /**
     * Helper method for creating the prefix form of the equation by reversing the equation.
     *
     * @param s
     *      The equation to be reversed.
     *
     * @return
     *      String representation of the reversed equation.
     */
    public String equationReverse(String s)
    {
        String reversed = "";
        for(int i = s.length() - 1; i >= 0; i--)
        {
            if(s.charAt(i) == '(')
            {
                reversed += ")";
            }
            else
            {
                if(s.charAt(i) == ')')
                {
                    reversed += "(";
                }
                else
                {
                    if(s.charAt(i) == ' ')
                    {
                        reversed += " ";
                    }
                    else
                    {
                        reversed += s.charAt(i);
                    }
                }
            }
        }
        return reversed;
    }

    /**
     * Determines the priority of the operator passed in.
     *
     * @param c
     *      The operator to be tested.
     *
     * @return
     *      3 if the operator is ^, 2 if its either * / or %, 1 if + or -, else -1.
     */
    public int operatorPriority(char c)
    {
        //P E M/D A/S
        if(c == '^')
            return 3;
        if(c == '*' || c == '/' || c == '%')
            return 2;
        if(c == '+' || c== '-')
            return 1;

        return -1;
    }

    /**
     * Getter for the answer of this Equation.
     *
     * @return
     *      The decimal representation of the answer.
     */
    public double getAnswer()
    {
        return answer;
    }

    /**
     * Getter for whether or not the Equation is balanced.
     *
     * @return
     *      True if the equation is balanced, false if it is not.
     */
    public boolean getBalance()
    {
        return balanced;
    }

    /**
     * Getter for the string that represents this equation.
     *
     * @return
     *      The infix representation of the equation.
     */
    public String getEquation()
    {
        return equation;
    }

    /**
     * Returns a neatly formatted representation of this Equation object.
     *
     * @return
     *      A String that contains a formatted representation of this Equation object.
     */
    public String toString()
    {
        return String.format("%-40s %-40s %-40s %25s %25s %15s", equation, prefix, postfix, answer, binary, hex);
    }
}
