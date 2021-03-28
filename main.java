public class Main {
  public static void main(String[] args) {
    CalculatorGUI calc = new CalculatorGUI();
  }
  
  // Used for testing/debugging
  public static void main1() {
      String num = "0";
    Calculate calculator = new Calculate(num);
    Calculate.debug = false;
    double retNum = -1;
 	                  
      /**********************************************************************
       * Test #1: Test Calculate.getNumberExp()
       **********************************************************************/
    num = "657";
      calculator.setExpression(num);
    retNum = calculator.getNumberExp(false, false, false, false, true);
    System.out.println("Number should be \"" + num + "\" and it is : " +
 	                   retNum);
 	                  
    num = "-657";
      calculator.setExpression(num);
    retNum = calculator.getNumberExp(false, false, false, false, false);
    System.out.println("Number should be \"" + num + "\" and it is : " +
 	                   retNum);
     
      num = "0001000";
      calculator.setExpression(num);
    retNum = calculator.getNumberExp(false, false, false, false, true);
    System.out.println("Number should be \"" + num + "\" and it is : " +
 	                   retNum);
     
      num = "e";
      calculator.setExpression(num);
    retNum = calculator.getNumberExp(false, false, false, false, true);
    System.out.println("Number should be \"" + num + "\" and it is : " +
 	                   retNum);
 	                  
      num = "2e";
      calculator.setExpression(num);
    retNum = calculator.getNumberExp(false, false, false, false, true);
    System.out.println("Number should be \"" + num + "\" and it is : " +
 	                   retNum);
 	   
    num = "7.4E-10";
      calculator.setExpression(num);
    retNum = calculator.getNumberExp(false, false, false, false, true);
    System.out.println("Number should be \"" + num + "\" and it is : " +
 	                   retNum);
     
      /**********************************************************************
       * Test #2: Test Calculate.getNumberAndOperation()
       **********************************************************************/
      num = "657*90";
      calculator.setExpression(num);
    retNum = calculator.getNumberAndOperation();
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum + calculator.getTestOperation());
     
      num = "0001000&";
      calculator.setExpression(num);
    retNum = calculator.getNumberAndOperation();
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum + calculator.getTestOperation());
     
      /**********************************************************************
       * Test #3: Test Calculate.operationDiv()
       **********************************************************************/
    num = "6/(3/2)";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
 	                  
    num = "6/3/2";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
     
      /**********************************************************************
       * Test #4: Test Calculate.operationMul()
       **********************************************************************/
    num = "7*3*4";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
 	                  
    num = "7*8/(4/2*8)";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
     
    /**********************************************************************
       * Test #5: Test Calculate.operationAdd()
       **********************************************************************/
    num = "7+3+4";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
 	                  
    num = "(7+8)/(4/(2*8))";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
 	                  
    num = "(7+8)/4/(2*8)";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
     
    /**********************************************************************
       * Test #6: Test Calculate.operationSub()
       **********************************************************************/
    num = "7-3+4";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
 	                  
    num = "(7-8)/(4/(2*8-10))";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
 	                  
    num = "(7+8)/4/(2*8)-2";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
     
    /**********************************************************************
       * Test #7: Final Test: Calculate.evaluate()
       **********************************************************************/
    num = "7.4E-5+67/5*9*2-1*e/6/2E2+10.12292";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
 
    num = "-8E20/4E-20+2E40";
      calculator.setExpression(num);
    retNum = calculator.evaluate(false);
    System.out.println("Result should be \"" + num + "\" and it is : " +
 	                   retNum);
     
  }
}
