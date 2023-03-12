package src.main;
import java.util.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame implements ActionListener {
    static Converter converter;

    static JPanel sendPanel;
    static JPanel receivePanel;

    static JTextField sendAmount;
    static JTextField receiveAmount;

    static JLabel sendLabel;
    static JLabel receiveLabel;

    static JComboBox<Object> sendCurrency;
    static JComboBox<Object> receiveCurrency;

    static JLabel adnotation;

    static JFrame mainFrame;



    GUI(){}
    

    public static void main(String[] args) {
        converter = new Converter();
        GUI al = new GUI();

        sendLabel = new JLabel("You send");
        sendLabel.setBounds(220,50,500,50);
        sendPanel = new JPanel();
        sendPanel.setBounds(0, 100, 500, 50);
        sendCurrency = new JComboBox<>(converter.currencies.toArray());
        sendAmount = new JTextField("0.00",10);
        sendAmount.addActionListener(al);
       
        
        receiveLabel = new JLabel("They receive");
        receiveLabel.setBounds(220,180,500,50);
        receivePanel = new JPanel();
        receivePanel.setBounds(0, 230, 500, 50);
        receiveCurrency = new JComboBox<>(converter.currencies.toArray());
        receiveAmount = new JTextField("0.00",10);
        receiveAmount.addActionListener(al);

       
        sendPanel.add(sendCurrency);
        sendPanel.add(sendAmount);
        
        
        receivePanel.add(receiveCurrency);
        receivePanel.add(receiveAmount);
        
        adnotation = new JLabel("");
        adnotation.setBounds(200, 300, 500,50);


        // Display the window.
        mainFrame = new JFrame("Currency Converter");
        mainFrame.setMinimumSize(new Dimension(500, 400));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);

        mainFrame.add(sendLabel);
        mainFrame.add(sendPanel);
        mainFrame.add(receiveLabel);
        mainFrame.add(receivePanel);
        mainFrame.add(adnotation);
        mainFrame.pack();
        mainFrame.setVisible(true);

        
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(sendAmount) ){
            
            double bidAmt = Double.valueOf(sendAmount.getText());
            String bidCurr = sendCurrency.getItemAt(sendCurrency.getSelectedIndex()).toString();
            String askCurr = receiveCurrency.getItemAt(receiveCurrency.getSelectedIndex()).toString();
            double askAmt = converter.convertUtil(bidAmt, bidCurr, askCurr);
            
            receiveAmount.setText(String.format(Locale.US,"%.2f", askAmt));
            
            adnotation.setText("1.00 " + bidCurr + " = " + String.format(Locale.US,"%.2f",converter.convertUtil( 1,bidCurr, askCurr))+ " "+ askCurr);


        }else if(source.equals(receiveAmount)){
            double askAmt = Double.valueOf(receiveAmount.getText());
            String askCurr = receiveCurrency.getItemAt(receiveCurrency.getSelectedIndex()).toString();
            String bidCurr = sendCurrency.getItemAt(sendCurrency.getSelectedIndex()).toString();
            double bidAmt = converter.requiredAmt(bidCurr,  askAmt, askCurr);
            
            sendAmount.setText(String.format(Locale.US,"%.2f", bidAmt));
            adnotation.setText("1.00 " + askCurr + " = " + String.format(Locale.US,"%.2f",converter.requiredAmt( bidCurr,1, askCurr))+ " "+ bidCurr);

        }
        

    }

}
