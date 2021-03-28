Class:
Calculate:
import javax.swing.*;
 
/***
* NOTE: Order of operations -
*      Order of operations are according to the rules of BODMAS (Bracket, Of,
*      Division, Multiplication, Addition and Subtraction) with the exception
*      that addition and subtraction are performed in the left-to-right order.
*/
public class Calculate {
   private String expression;
   private int pos;
   private int error;
   private String operation;
   public static boolean debug = true;
   public static boolean GUI = false;
   private int length; // Used only by readDigits
  
   public Calculate() {
       expression = "";
       pos = 0;
       error = 1; // No error
       operation = Operation.o_none;
   }
  
   public Calculate(String exp) {
       expression = exp;
       pos = 0;
       error = 1; // No error
       operation = Operation.o_none;
   }
  
   public void reset() {
     expression = "";
       pos = 0;
       error = 1; // No error
       operation = Operation.o_none;
   }
  
   public void setExpression(String exp) { // Used only for testing
       expression = exp;
       pos = 0;
       error = 1; // No error
       operation = Operation.o_none;
   }
  
   public String getTestOperation() { // Used only for testing
       return operation;
   }
  
   public double evaluate(String exp) {
       expression = exp;
       pos = 0;
       error = 1; // No error
       operation = Operation.o_none;
       return evaluate(false);
   }
  
   public double getNumber() {
       double result = -1;
       while(true) {
           // Check if we reached end of string
           if (pos == expression.length()) {
               if(debug) System.out.println("Return: Result : " + result);
               operation = Operation.o_end;
               return result;
           }
           char letter = expression.charAt(pos);
           if(debug) System.out.println("Char: " + letter + " At: " + pos);
           // Check if letter can be cast as a number
           if (letter >= '0' && letter <= '9') {
               // Reset the value of result to 0 (valid #)
               if (result == -1) result = 0;
               result = result * 10 + letter - '0';
           } else {
               break;
           }
           ++pos;
       }
       if(debug) System.out.println("Return: Result : " + result);
       return result;
   }
  
   public long readDigits() {
       long result = -1;
       length = 0;
       while(true) {
           // Check if we reached end of string
           if (pos == expression.length()) {
               if(debug) System.out.println("Return: Result : " + result);
               operation = Operation.o_end;
               return result;
           }
          
           // Read the next letter
           char letter = expression.charAt(pos);
           if(debug) System.out.println("Char: " + letter + " At: " + pos);
          
           // Check if letter can be cast as a number
           if (letter >= '0' && letter <= '9') {
               // Reset the value of result to 0 (valid #)
               if (result == -1) result = 0;
               result = result * 10 + letter - '0';
               ++length;
           } else {
               break;
           }
           ++pos;
       }
       return result;
   }
  
   public double getNumberExp(boolean foundDot, boolean foundSign,
                               boolean foundExp, boolean foundNumber,
                               boolean negativeStartAllow) {
       boolean negative = false;
       double result = -1;
       int startPos = pos;
      
       // Check the sign of the number
       if(debug) System.out.println("Checking sign at pos: " + pos);
       char letter = expression.charAt(pos);
       if (letter == '+' || letter == '-') {
           if (letter == '-') {
               if (! negativeStartAllow) {
                   error = 0;
                   operation = Operation.o_error;
                   if(GUI) {
                     JOptionPane.showMessageDialog(CalculatorGUI.f,
                       ("Number invalid: Includes '-' sign and is not the first number"));
                   } else {
                     System.out.println("Number invalid: Includes '-' sign and is not the first number");
                   }
                   return result;
               }
               negative = true;  
           }
           if (! foundSign) {
               foundSign = true;
               ++pos;
           } else { // Not a valid number
               error = 0;
               operation = Operation.o_error;
               if (GUI) {
                 JOptionPane.showMessageDialog(CalculatorGUI.f,
                   ("Number invalid: Repetition of sign (+/-)"));
               } else {
                 System.out.println("Number invalid: Repetition of sign (+/-)");
               }
               return result;
           }
       }
       if (pos == expression.length()) {
           error = 0;
           operation = Operation.o_error;
           if (GUI) {
             JOptionPane.showMessageDialog(CalculatorGUI.f,
               ("Number invalid: No valid number (only a sign)"));
          } else {
            System.out.println("Number invalid: No valid number (only a sign)");
          }
           return result;
       }
          
       // Create the number
       while(true) {
           // Check if we reached end of string
           if (pos == expression.length()) {
               operation = Operation.o_end;
               if (negative) {
                   if(debug) System.out.println("Return: Result : " + (-1*result));
                   return result * -1;
               }
               if(debug) System.out.println("Return: Result : " + result);
               return result;
           }
          
           // Read the next letter
           letter = expression.charAt(pos);
           if(debug) System.out.println("Char: " + letter + " At: " + pos);
          
           // Check if letter can be cast as a number
           if (letter >= '0' && letter <= '9') {
               // Reset the value of result to 0 (valid #)
               if (result == -1) result = 0;
               result = result * 10 + letter - '0';
               foundNumber = true;
           }
           // Check if letter is 'E' - for exponent
           else if (letter == 'E') {
               if (! foundExp && foundNumber) {
                   foundExp = true;
                   ++pos;
                   if(debug) System.out.println("Checking exponent from pos: " + pos);
                   double ten_exponent = getNumberExp(true, false, true, false, true);
                   if (error == 0) {
                       System.out.println("Number invalid: Exponent error");
                       return ten_exponent;
                   }
                   result *= Math.pow(10, ten_exponent);
                   if (negative) {
                       if(debug) System.out.println("Return: Result : " + (-1*result));
                       return result * -1;
                   }
                   if(debug) System.out.println("Return: Result : " + result);
                   return result;
               } else {
                   error = 0;
                   operation = Operation.o_error;
                   if(GUI) {
                     JOptionPane.showMessageDialog(CalculatorGUI.f,
                       ("Number invalid: Repetition of E-exponent"));
                 } else {
                   System.out.println("Number invalid: Repetition of E-exponent");
                 }
                   return result;
               }
           }
           // Check if letter is 'e' - the value 2.718...
           else if (letter == 'e') {
               if (startPos == pos || startPos == (pos - 1)) {
                   result = 2.718;
                   ++pos;
                   // Check if we reached end of string
                   if (pos == expression.length()) {
                       operation = Operation.o_end;
                   }
                   if (negative) {
                       if(debug) System.out.println("Return: Result : " + (-1*result));
                       return result * -1;
                   }
                   if(debug) System.out.println("Return: Result : " + result);
                   return result;
               } else {
                   error = 0;
                   operation = Operation.o_error;
                   if(GUI) {
                     JOptionPane.showMessageDialog(CalculatorGUI.f,
                       ("Number invalid: e and other tokens present"));
                   } else {
                     System.out.println("Number invalid: e and other tokens present:  start - "
                               + startPos + " current - " + pos);
                   }
                   return result;
               }
           }
           // Check if letter is '.' - the result is a decimal number
           else if (letter == '.') {
               if (! foundDot) {
                   foundDot = true;
                   ++pos;
                   long dec_number = readDigits();
                   result += dec_number / Math.pow(10, length);
                   continue;
               } else {
                   error = 0;
                   operation = Operation.o_error;
                   if(GUI) {
                     JOptionPane.showMessageDialog(CalculatorGUI.f, ("Number invalid: Repetition of '.'"));
                   } else {
                     System.out.println("Number invalid: Repetition of '.'");
                   }
                   return result;
               }
           } else {
               break;
           }
           ++pos;
       }
       if (negative) {
           return result * -1;
       }
       if (! foundNumber) {
           error = 0;
           operation = Operation.o_error;
           if(GUI) {
             JOptionPane.showMessageDialog(CalculatorGUI.f, ("Number invalid: No number found"));
           } else {
             System.out.println("Number invalid: No number found");
           }
           return 0;
       }
       return result;
   }
  
   public void getOperation() {
       switch(expression.charAt(pos)) {
           case ('*') : {
               operation = Operation.o_mul;
               break;
           }
           case ('/') : {
               operation = Operation.o_div;
               break;
           }
           case ('+') : {
               operation = Operation.o_add;
               break;
           }
           case ('-') : {
               operation = Operation.o_sub;
               break;
           }
           case (')') : {
               operation = Operation.o_clo;
               break;
           }
           case ('(') : {
               operation = Operation.o_opn;
               break;
           }
           default : {
               error = 0; // Error!
               if(GUI) {
                 JOptionPane.showMessageDialog(CalculatorGUI.f, ("Unexpected token at pos : " + pos +
                                   " Token : " + expression.charAt(pos)));
               } else {
                 System.out.println("Unexpected token at pos : " + pos +
                                   " Token : " + expression.charAt(pos));
               }
               operation = Operation.o_error;
           }
       }
   }
  
   public double getNumberAndOperation() {
       // 1) Extract number
       boolean allowNegative = false;
       if (pos == 0) {
           allowNegative = true;
       }
       double retNum = getNumberExp(false, false, false, false, allowNegative);
       if (operation.equals(Operation.o_end)) {
         if(debug) System.out.println("Reached end of string after reading number");
           return retNum;
       }
       // 2) Extract operation
       getOperation();
       ++pos;
       if (error == 0) {
           assert(operation.equals(Operation.o_error));
           return -1;
       }
       if (operation.equals(Operation.o_opn)) {
         if(debug) System.out.println("Reached end of string after reading operation");
           return retNum;
       }
       return retNum;
   }
  
   public double getRNumAndOperation() {
       double rNum = -1;
       // 1) Check for brackets
       if (expression.charAt(pos) == ')') {
           error = 0; // Error!
           if(GUI) {
             JOptionPane.showMessageDialog(CalculatorGUI.f, ("Error: Unexpected closing bracket at position " + pos));
           } else {
             System.out.println("Error: Unexpected closing bracket at position " + pos);
           }
           return -1;
       }
       if (expression.charAt(pos) == '(') {
           ++pos;
           if (debug) System.out.println("subExpression start at : " + pos);
           rNum = evaluate(true);
           if (error == 0) {
               return -1;
           }
          
           if (debug) System.out.println("Result of subExpression : " + rNum);
           if (debug) System.out.println("Next pos : " + pos);
          
           if (! operation.equals(Operation.o_end)) {
               getOperation();
               ++pos;
           }
           return rNum;
       }
       // 2) Call getNumberAndOperation()
       return getNumberAndOperation();
   }
  
   public double operationDiv(double lNum) {
       double rNum;
       do {
           if(debug) System.out.println("Doing division with LHS: " + lNum);
           rNum = getRNumAndOperation();
           if(debug) System.out.println("and RHS: " + rNum);
          
           if (rNum == 0) {
             if(GUI) {
                 JOptionPane.showMessageDialog(CalculatorGUI.f, ("Error: Division by zero at " + pos));
               } else {
                 System.out.println("Error: Division by zero at " + pos);
               }
               error = 0;
               return lNum;
           } else {
               if (debug) System.out.println("Division LHS : " + lNum +
                   " RHS : " + rNum + " result : " + lNum/rNum);
               lNum /= rNum;
           }
          
           if (error == 0) {
               return lNum;
           }
          
           if (operation.equals(Operation.o_opn)) {
             if(GUI) {
                 JOptionPane.showMessageDialog(CalculatorGUI.f,
                     ("Error. Unexpected opening bracket at position " + pos));
               } else {
                 System.out.println("Error. Unexpected opening bracket at position " + pos);
               }
               error = 0;
               return lNum;
           }
          
       } while(operation.equals(Operation.o_div));
       return lNum;
   }
  
   public double operationMul(double lNum) {
       double rNum;
       do {
           if(debug) System.out.println("Doing multiplication with LHS: " + lNum);
           rNum = getRNumAndOperation();
           if (operation.equals(Operation.o_div)) {
               rNum = operationDiv(rNum);
               if (error == 0) {
                   return lNum;
               }
           }
           if(debug) System.out.println("and RHS: " + rNum);
           if (debug) System.out.println("Multiply LHS : " + lNum + " RHS : "
                   + rNum + " result : " + lNum*rNum);
           lNum *= rNum;
          
          
           if (error == 0) {
               return lNum;
           }
          
           if (operation.equals(Operation.o_opn)) {
             if(GUI) {
                 JOptionPane.showMessageDialog(CalculatorGUI.f,
                     ("Error. Unexpected opening bracket at position " + pos));
               } else {
                 System.out.println("Error. Unexpected opening bracket at position " + pos);
               }
               error = 0;
               return lNum;
           }
          
       } while(operation.equals(Operation.o_mul));
       return lNum;
   }
  
   public double operationAdd(double lNum) {
       double rNum;
       do {
           if(debug) System.out.println("Doing addition with LHS: " + lNum);
           rNum = getRNumAndOperation();
           if (operation.equals(Operation.o_div)) {
               rNum = operationDiv(rNum);
               if (error == 0) {
                   return lNum;
               }
           }
           if (operation.equals(Operation.o_mul)) {
               rNum = operationMul(rNum);
               if (error == 0) {
                   return lNum;
               }
           }
           if(debug) System.out.println("and RHS: " + rNum);
           if (debug) System.out.println("Add LHS : " + lNum + " RHS : "
                   + rNum + " result : " + (lNum+rNum));
           lNum += rNum;
          
          
           if (error == 0) {
               return lNum;
           }
          
           if (operation.equals(Operation.o_opn)) {
             if(GUI) {
                 JOptionPane.showMessageDialog(CalculatorGUI.f,
                     ("Error. Unexpected opening bracket at position " + pos));
               } else {
                 System.out.println("Error. Unexpected opening bracket at position " + pos);
               }
             error = 0;
               return lNum;
           }
          
       } while(operation.equals(Operation.o_add));
       return lNum;
   }
  
   public double operationSub(double lNum) {
       double rNum;
       do {
           if(debug) System.out.println("Doing substraction with LHS: " + lNum);
           rNum = getRNumAndOperation();
           if (operation.equals(Operation.o_div)) {
               rNum = operationDiv(rNum);
               if (error == 0) {
                   return lNum;
               }
           }
           if (operation.equals(Operation.o_mul)) {
               rNum = operationMul(rNum);
               if (error == 0) {
                   return lNum;
               }
           }
           if(debug) System.out.println("and RHS: " + rNum);
           if (debug) System.out.println("Substract LHS : " + lNum + " RHS : "
                   + rNum + " result : " + (lNum-rNum));
           lNum -= rNum;
          
          
           if (error == 0) {
               return lNum;
           }
          
           if (operation.equals(Operation.o_opn)) {
             if(GUI) {
                 JOptionPane.showMessageDialog(CalculatorGUI.f,
                     ("Error. Unexpected opening bracket at position " + pos));
               } else {
                 System.out.println("Error. Unexpected opening bracket at position " + pos);
               }
             error = 0;
               return lNum;
           }
          
       } while(operation.equals(Operation.o_sub));
       return lNum;
   }
  
   public double doOperation(double num) {
       switch(operation) {
           case Operation.o_div:
               return operationDiv(num);
           case Operation.o_mul:
               return operationMul(num);
           case Operation.o_add:
               return operationAdd(num);
           case Operation.o_sub:
               return operationSub(num);
           default:
             if(GUI) {
                 JOptionPane.showMessageDialog(CalculatorGUI.f,
                     ("Error: Unknown operation " + operation + " at position " +  pos));
               } else {
                 System.out.println("Error: Unknown operation " + operation + " at position " +  pos);
               }
       }
       assert(false);
       return -1;
   }
  
   public double evaluate(boolean subExp) {
       double acc = -1;
       boolean hadSub = false;
       if (expression.substring(pos, pos + 1).equals(Operation.o_opn)) {
           hadSub = true;
           ++pos;
           if (debug) System.out.println("Subexpression is equal to true");
           acc = evaluate(true);
           if (pos == expression.length()) {
             return acc;
           }
       }
       if (debug) System.out.println("Hi!");
       if (error == 0) {
           if (debug) System.out.println("First error after subexpression is equal to true");
           return acc;
       }
 
       if (hadSub) {
           getOperation();
           ++pos;
           if (debug) System.out.println("Excited from early subExp with new operation: " + operation);
       } else {
           acc = getNumberAndOperation();
       }
       if (error == 0) {
           return acc;
       }
       if (operation.equals(Operation.o_end)) {
           return acc;
       }
       if (operation.equals(Operation.o_none) ||
           operation.equals(Operation.o_clo) ||
           operation.equals(Operation.o_opn)) {
         if(GUI) {
             JOptionPane.showMessageDialog(CalculatorGUI.f,
                 ("Error processing input. Unexpected operator : "
                               + operation + " at position : " + pos));
           } else {
             System.out.println("Error processing input. Unexpected operator : "
                       + operation + " at position : " + pos);
           }
           return acc;  
       }
      
       while (pos < expression.length()) {
           // All operators should be valid (+, -, /, *) here
           assert(operation.equals(Operation.o_div) ||
                   operation.equals(Operation.o_mul) ||
                   operation.equals(Operation.o_add) ||
                   operation.equals(Operation.o_sub));
           if(debug) System.out.println("POS: " + pos + " MAX: " + expression.length());
           acc = doOperation(acc);
           if (error == 0) {
               return acc;
           }
           if (operation.equals(Operation.o_clo) ||
               operation.equals(Operation.o_opn)) {
               if (subExp && operation.equals(Operation.o_clo)) {
                   if (pos >= expression.length()) {
                       operation = Operation.o_end;
                   }
                   return acc;
               }
              
               JOptionPane.showMessageDialog(CalculatorGUI.f, ("Error: Unexpected " + operation + " at position : "
                       + pos));
               if(GUI) {
                 JOptionPane.showMessageDialog(CalculatorGUI.f,
                     ("Error: Unexpected " + operation + " at position : " + pos));
               } else {
                 System.out.println("Error: Unexpected " + operation + " at position : " + pos);
               }
               error = 0;
               return acc;
           }
       }
       if (debug) System.out.println("Returning result as " + acc + " with pos " + pos);
       return acc;
   }
}
