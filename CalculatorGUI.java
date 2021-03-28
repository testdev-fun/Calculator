import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
 
public class CalculatorGUI extends JFrame {
  private static final long serialVersionUID = 1L;
  private JTextField ansDisplay;
   private JTextField equationDisplay;
   private JButton zero;
   private JButton one;
   private JButton two;
   private JButton three;
   private JButton four;
   private JButton five;
   private JButton six;
   private JButton seven;
   private JButton eight;
   private JButton nine;
   private JButton add;
   private JButton sub;
   private JButton mul;
   private JButton div;
   private JButton dot;
   private JButton opnBrack;
   private JButton cloBrack;
   private JButton exponent;
   private JButton eulersNum;
   private JButton equal;
   private JButton clear;
   private String ansString;
   private String equationString;
   private ActionListener actionListener;
   public static JFrame f;
   private boolean justAnswered;
  
   public CalculatorGUI() {
       // create a frame
       f = new JFrame("calculator");
        try {
           // set look and feel
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       }
       catch (Exception e) {
           System.err.println(e.getMessage());
       }
        // create a object of class
       Calculate calculator = new Calculate();
       Calculate.debug = false;
       Calculate.GUI = true;
        // create a textfield
       ansDisplay = new JTextField(20);
       equationDisplay = new JTextField(20);
        // set the textfield to non editable
       ansDisplay.setEditable(false);
       equationDisplay.setEditable(false);
      
       // initialize strings
       ansString = "";
       equationString = "";
       justAnswered = false;
        // create number buttons
       zero = new JButton("0");
       one = new JButton("1");
       two = new JButton("2");
       three = new JButton("3");
       four = new JButton("4");
       five = new JButton("5");
       six = new JButton("6");
       seven = new JButton("7");
       eight = new JButton("8");
       nine = new JButton("9");
        // create operator buttons
       add = new JButton("+");
       sub = new JButton("-");
       div = new JButton("/");
       mul = new JButton("*");
      
       // create special character buttons
       dot = new JButton(".");
       opnBrack = new JButton("(");
       cloBrack = new JButton(")");
       exponent = new JButton("E");
       eulersNum = new JButton("e");
       clear = new JButton("C");
      
       // equals button
       equal = new JButton("=");
        // create ActionListener
       actionListener = new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
               String cmd = evt.getActionCommand();
               if (cmd.equals("=")) { // Compute
                 if (equationString.length() > 0) {
                    ansString = Double.toString(calculator.evaluate(equationString));
                    equationDisplay.setText("Ans: " + ansString);
                    ansDisplay.setText(equationString);
                    justAnswered = true;
                 }
                 equationString = "";
                 return;
               }
               if (justAnswered) {
                 ansDisplay.setText(equationDisplay.getText());
                 equationDisplay.setText("");
                 justAnswered = false;
               }
               if (cmd.equals("C")) {
                 equationString = "";
               } else {
                 equationString += cmd;
               }
               equationDisplay.setText(equationString);
           }
       };
      
       // create a panel
       JPanel p = new JPanel();
        // add action listeners
       mul.addActionListener(actionListener);
       div.addActionListener(actionListener);
       sub.addActionListener(actionListener);
       add.addActionListener(actionListener);
       nine.addActionListener(actionListener);
       eight.addActionListener(actionListener);
       seven.addActionListener(actionListener);
       six.addActionListener(actionListener);
       five.addActionListener(actionListener);
       four.addActionListener(actionListener);
       three.addActionListener(actionListener);
       two.addActionListener(actionListener);
       one.addActionListener(actionListener);
       zero.addActionListener(actionListener);
       dot.addActionListener(actionListener);
       clear.addActionListener(actionListener);
       equal.addActionListener(actionListener);
       opnBrack.addActionListener(actionListener);
       cloBrack.addActionListener(actionListener);
       exponent.addActionListener(actionListener);
       eulersNum.addActionListener(actionListener);
       // add elements to panel
       p.add(ansDisplay);
       p.add(equationDisplay);
       p.add(zero);
       p.add(one);
       p.add(two);
       p.add(three);
       p.add(four);
       p.add(five);
       p.add(six);
       p.add(seven);
       p.add(eight);
       p.add(nine);
       p.add(opnBrack); 
       p.add(cloBrack);
       p.add(div);
       p.add(mul);
       p.add(sub);
       p.add(add);
       p.add(exponent);
       p.add(eulersNum);
       p.add(dot);
       p.add(clear);
       p.add(equal);
       // set Background of panel
       p.setBackground(Color.black);
       // add panel to frame
       f.add(p);
       f.setSize(200, 260);
       f.setResizable(false);
       f.setVisible(true);
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
   }   
};
