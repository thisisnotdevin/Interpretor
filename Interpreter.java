// PROGRAMMING LANGUAGES
// INTERPRETER PROJECT
// DEVIN LIN
// FALL 22

import java.util.*;
import java.io.*;

public class Interpreter {

    private String inputString;
    private int currIndex;
    private char currToken;
  
    HashMap<String, Integer> myMap = new HashMap<String, Integer>(); // linkedhashmap could be a better choice

    public static void main(String[] args) {
    try {
        // reads a text file named "sample.txt" and passes each line to the readExp method
        File text = new File("sample.txt");

        if (text.length() == 0) { 
            throw new RuntimeException("THERE IS NOTHING IN THE FILE");
        }

        Scanner scr =  new Scanner(new FileInputStream(text));
        Interpreter expr = new Interpreter();
        expr.readExp(scr);
        
    } catch (FileNotFoundException e) {
        System.out.println("FILE NOT FOUND: " + e.getMessage());
    } catch (RuntimeException e) {
        System.out.println("ERROR: " + e.getMessage());
    }
}

    // replace the single space with "nothing", and sets the currIndex and currToken variables.
    void InterpreterConstructor(String inputString){
        this.inputString = inputString.replaceAll("\\s", ""); 
        currIndex = 0;
        nextToken();
    }

    // create an assignment, identify the exp
    public void readExp(Scanner scr){
        while (scr.hasNextLine()){
            InterpreterConstructor(scr.nextLine()); // reads single line and calls assignment method
            Assignment();
        }
    }

    void Assignment(){
        String id = Identifier(); // get identifier
        int op = Expression(); // get expression value

        // check if variable is already in hashmap
        if(myMap.containsKey(id)){
            throw new RuntimeException("Variable " + id + " already initialized"); 
        }
        myMap.put(id, op); // add variable to hashmap

        System.out.print(id + " = " + op + "\n");
    }

    // This method reads and returns an identifier from the input string. 
    // An ID consists of one or more letters, digits, or underscores, starting with a letter.
    String Identifier(){
        StringBuilder sb = new StringBuilder();

        if(Character.isLetter(currToken)){
            sb.append(currToken);
        }
        else{
            throw new RuntimeException("Identifier error: expected letter at start of identifier");
        }
        nextToken();
        while (Character.isLetterOrDigit(currToken) || currToken == '_') {
            sb.append(currToken);
            nextToken();
        }
        if (currToken != '='){
            throw new RuntimeException("No assigned values");
        }
        nextToken();
        
        return sb.toString();
    }

    // Checks if the parenthesis are correct
    void ValidParenthesis(char token) {
        if (currToken != token) {
            throw new RuntimeException("Uneven parenthesis"); 
        }
    nextToken();
}

    // return exp value, and then checks that the current character is a semicolon
    int Expression() {
    int x = Exp();
        if (currToken != ';') {
            throw new RuntimeException("Expected semicolon at end of expression");
        }
    return x;
}

    // Operator precedence: Exp() -> Term() -> Fact()

    // handles addition and subtraction (precedence 1)
    int Exp() {
        int x = Term();
        while (currToken == '+' || currToken == '-' ){ 
            char operation = currToken;
            nextToken();
            int y = Term();
            x = Calculate(operation, x, y);
        }
        return x;
    }
    
    // handles multiplication, division, and modulo (precedence 2)
    int Term() {
        int x = Fact();
        while(currToken == '*' || currToken == '/' || currToken == '%' ){ 
            char operation = currToken;
            nextToken();
            int y = Fact();
            x = Calculate(operation, x, y);
        }
        return x;
    }

    // handles unary minus, parentheses, and number literals (precedence 3)
    int Fact() {
        int x = 0;
        String temp = String.valueOf(currToken);

        switch (currToken) {
        case '-':
            nextToken();
            x = Fact();
            return -x;
        case '+':
            nextToken();
            x = Fact();
            return x;
        case '(':
            nextToken();
            x = Exp();
            ValidParenthesis(')');
            return x;
        case ')':
            throw new RuntimeException("Cannot start with closing parenthesis");
        case '0':
            nextToken();
            if (Character.isDigit(currToken))
            throw new RuntimeException("Error, initialized with 2 leading zeros");
            return 0;
        default:
            if (myMap.containsKey(temp)) {
                x = myMap.get(temp);
                nextToken();
                return x;
            } else if (Character.isDigit(currToken)) {
                // using code from professor
                while (Character.isDigit(currToken)) {
                    x = 10 * x + (currToken - '0'); // covert the character to integer, substract ASCII value
                    nextToken();
                }
                return x;
            }
            throw new RuntimeException("Invalid token");
        }
    }

    // This method advances to the next character in the input string.
    void nextToken(){
        char c;
        
        if (!inputString.endsWith(";")){
            throw new RuntimeException("Missing ';' ");
        }
        // check if variable is uninitialzied "x=;"
        if (inputString.charAt(currIndex) == ';' && inputString.charAt(currIndex-1) == '='){
            throw new RuntimeException("Uninitialized variable");
        }
        // check if misuse of operators example: "x = +;"
        if (inputString.charAt(currIndex) == ';' && "+-*/%".indexOf(inputString.charAt(currIndex-1)) != -1) {
            throw new RuntimeException("Misuse of operators");
        }
        
        c = inputString.charAt(currIndex++);
        currToken = c;

    }

    // calculates Exp and Terms
    static int Calculate(char operation, int x, int y) {
    switch (operation) {
        case '+': return x + y;
        case '-': return x - y;
        case '*': return x * y;
        case '/': return x / y;
        case '%': return x % y;
    }
    return 0;
    }
}