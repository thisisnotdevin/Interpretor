# Tokenizer project

# 💻Interpreter Project💻

**NOTE: each variable is assumed to be of the integer type.**

1. detect syntax errors
2. report uninitialized variables
3. perform the assignments if there is no error and print out the values of all the variables after all the assignments are done.

**What this interpreter covers:**

1. file is empty/file not found
2. varialbe initialized twice or more
3. expected letter at start of identifier
4. No assigned values
5. Uneven parenthesis / Cannot start with closing parenthesis
6. Expected semicolon at end of expression (;) / Missing ';’
7. Error, initialized with 2 leading zeros
8. Invalid token
9. Uninitialized variable
10. Misuse of operators

### using hashMap

- hashmaps are just keys and values, one way we could used the hashmap for is to check if the values are initialized more than once

using hashmap to save values so it can be used in the future, for example:

```jsx
x = 1;
y = 2;
z = ---(x+y)*(x+-y);

//output
x = 1
y = 2
z = 3

```

## ⬇ Important functions ⬇

### Assignment()

contains id(var) and operator(+,-,*,/), will System.out.print the keys and values and save it into the hashmap

- id(var) use a stringbuilder to iterate thru each character and find if the id has any errors, return it if it doesnt have any errors. Must build it back using sb.toString(); else if there is error, throw exception
- operator(+,-,*,/) is more complex because we need to use operator precedence first is (,) then *, / and lastly is +, - from a parse tree prespective we need to start from the outside, with +,- first

### using precedence when parsing

- **precedence 1** pass in an Expression function that check if the current token is ‘+’ or ‘-’ in a new function called Exp(), must also include variable x and y, if token is found(+ or -), call a function that calculates x and y using the operator
- **precedence 2** inside the Exp() we parse to check if current token is ‘*’ or ‘/’ if so, similar to previous step call it Term()
- **precedence 3** Fact(**)** set current token into a temp and find out what the token is equal to (also find if its positive or negative value), if it is parenthesis we must call Exp() again. returns the token
    - special case = 0, return 0
    - if key already exist in the hashmap, return the value of that key

### Identifier()

- this method extracts a string from the input that represents an identifier.
- create **Stringbuilder** called sb. It then checks if the currToken variable is a letter using the character.isLetter method.
    - If it is not a letter, throw a runtimeException with the message "Identifier error: expected letter at start of identifier".
- If the **currToken** is a letter, appends it to the StringBuilder . Then calls the nextToken method to move on to the next token.
- The method then enters a loop that continues as long as the currToken variable is a letter, digit, or underscore using the character.isLetterOrDigit method. Finally, the method checks if the currToken variable is equal to an equals sign ('='). If it is not, it throws a runtimeException. If currToken variable is equal to an equals sign, move on to the next token and returns the String ****of the StringBuilder (using toString)

### Expression()

- besides checking if the character is semicolon, is its the start function for the orperator precedence (parsing)

> Operator precedence: Exp() -> Term() -> Fact()
> 

- int Exp()
    - handles addition and subtraction (precedence 1)
- int Term()
    - handles multiplication, division, and modulo (precedence 2)
- int Fact()
    - handles unary minus, parentheses, and number literals (precedence 3)

## Summary

This is a Java program that defines a class called **`Interpreter`**, which can read and interpret simple arithmetic expressions.

The **`Interpreter`** class has a number of methods that are used to parse and evaluate the expressions:

1. **`main`**: This is the entry point of the program. It reads a text file named "sample.txt" and passes each line to the **`readExp`** method.
2. **`readExp`**: This method reads a single line of input and calls the **`Interpreter`** constructor to create a new **`Interpreter`** object for that line. It then calls the **`Assignment`** method to process the assignment statement.
3. **`InterpreterConstructor`**: This is the constructor for the **`InterpreterConstructor`** class. It takes a string as input, removes all spaces from the string, and sets the **`currIndex`** and **`currToken`** variables.
4. **`Assignment`**: This method processes an assignment statement, which consists of an identifier (a variable name) followed by an equals sign (=) and an expression. It calls the **`Identifier`** method to get the identifier, and then calls the **`Expression`** method to get the value of the expression. Finally, it stores the identifier and value in a **`HashMap`** and prints the assignment statement.
5. **`Identifier`**: This method reads an identifier from the input string and returns it as a string. An identifier consists of one or more letters, digits, or underscores, and must start with a letter.
6. **`ValidParenthesis`**: This method checks that the current character is an opening or closing parenthesis, and advances to the next character if it is.
7. **`Expression`**: This method reads and evaluates an expression. It calls the **`Exp`** method to get the value of the expression, and then checks that the current character is a semicolon (;).
8. **`Exp`**, **`Term`**, and **`Fact`**: These methods implement operator precedence, so that expressions are evaluated in the correct order. The **`Exp`** method handles addition and subtraction (precedence 1), the **`Term`** method handles multiplication, division, and modulo (precedence 2), and the **`Fact`** method handles unary minus, parentheses, and number literals (precedence 3).
9. **`Calculate`**: This method takes two integers and an operator as input, and performs the operation on the two integers. It is called by the **`Exp`**, **`Term`**, and **`Fact`** methods to perform the calculations required by the expression.
10. **`nextToken`**: This method advances to the next character in the input string.

### Switch statement default case (from prof. code)

```jsx
while (Character.isDigit(currToken)) {
            x = 10 * x + (currToken - '0');
            nextToken();
          }
```

**Explanation**

This line of code reads a sequence of digits and converts them into an integer value.

Here's how it works:

- The **`while`** loop reads a sequence of digits one by one, as long as **`currToken`** is a digit.
- Inside the loop, the value of **`x`** is updated as follows:
    - **`x`** is multiplied by 10, to shift all its digits one place to the left. For example, if **`x`** is 123, it becomes 1230.
    - The digit represented by **`currToken`** is added to **`x`**. The **`currToken`** value is converted to an integer by subtracting **`'0'`**. This is necessary because **`currToken`** is a character, and we need to convert it to its corresponding integer value. For example, if **`currToken`** is '7', it becomes 7 after subtracting '0'.
    - Finally, **`nextToken()`** is called to advance to the next character.

After the loop ends, **`x`** will contain the integer value of the sequence of digits that was read.
