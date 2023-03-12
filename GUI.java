import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener {
    static Converter converter;

    static JPanel sendPanel;
    static JPanel receivePanel;

    static JTextField sendAmount;
    static JLabel receiveAmount;

    static JLabel sendLabel;
    static JLabel receiveLabel;

    static JComboBox<Object> sendCurrency;
    static JComboBox<Object> receiveCurrency;

    static JFrame mainFrame;

    static JButton convertButton;

    GUI(){}
    

    public static void main(String[] args) {
        converter = new Converter();


        sendLabel = new JLabel("You send");
        sendPanel = new JPanel();
        sendPanel.setBounds(0, 100, 500, 100);
        sendCurrency = new JComboBox<>(converter.currencies.toArray());
        sendAmount = new JTextField(10);
        sendAmount.setText("0.00");
        
        receiveLabel = new JLabel("They receive");
        receivePanel = new JPanel();
        receivePanel.setBounds(0, 200, 500, 100);
        receiveCurrency = new JComboBox<>(converter.currencies.toArray());
        receiveAmount = new JLabel("0.00");


        convertButton = new JButton("convert");
        GUI al = new GUI();
        convertButton.addActionListener(al);

        sendPanel.add(sendLabel);
        sendPanel.add(sendCurrency);
        sendPanel.add(sendAmount);
        
        receivePanel.add(receiveLabel);
        receivePanel.add(receiveCurrency);
        receivePanel.add(receiveAmount);
        receivePanel.add(convertButton);
       

        // Display the window.
        mainFrame = new JFrame("Curency Converter");
        mainFrame.setMinimumSize(new Dimension(500, 400));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);

        mainFrame.add(sendPanel);
        mainFrame.add(receivePanel);
        mainFrame.pack();
        mainFrame.setVisible(true);

        
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(convertButton)){
            
            double bidAmt = Double.valueOf(sendAmount.getText());
            String bidCurr = sendCurrency.getItemAt(sendCurrency.getSelectedIndex()).toString();
            String askCurr = receiveCurrency.getItemAt(receiveCurrency.getSelectedIndex()).toString();
            double askAmt = converter.convertUtil(bidAmt, bidCurr, askCurr);
            receiveAmount.setText(String.format(Locale.US,"%.2f", askAmt));
            
        }
        

    }

}
