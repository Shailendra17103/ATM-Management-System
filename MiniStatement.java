package bank.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class MiniStatement extends JFrame {

    MiniStatement(String pinnumber){
        setTitle("Mini Statement");
        setLayout(null);

        JLabel bank = new JLabel("Bharat Bank");
        bank.setBounds(150, 20, 200, 30);
        add(bank);

        JLabel card = new JLabel();
        card.setBounds(20, 50, 350, 20); // Moved closer to the top
        add(card);

        JLabel mini = new JLabel();
        mini.setBounds(20, 80, 400, 250); // Adjusted position
        add(mini);

        JLabel balance = new JLabel();
        balance.setBounds(20, 350, 350, 20); // Moved slightly lower
        add(balance);

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from login where pin='" + pinnumber + "'");
            while(rs.next()) {
                card.setText("Card Number: " + rs.getString("cardnumber").substring(0, 4) + "XXXXXXXX" + rs.getString("cardnumber").substring(12));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            Conn conn = new Conn();
            int bal = 0;
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            ResultSet rs = conn.s.executeQuery("select * from bank where pin='" + pinnumber + "'");
            while (rs.next()) {
                String date = rs.getString("date");
                String type = rs.getString("type");
                String amount = rs.getString("amount");

                sb.append(date).append(" &emsp; ").append(type).append(" &emsp; ").append(amount).append("<br><br>");

                if(type.equals("Deposit")) {
                    bal += Integer.parseInt(amount);
                } else {
                    bal -= Integer.parseInt(amount);
                }
            }
            sb.append("</html>");
            mini.setText(sb.toString());
            balance.setText("Your current account balance is Rs: " + bal);

        } catch (Exception e) {
            System.out.println(e);
        }

        setSize(400, 600);
        setLocation(20, 20);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MiniStatement("").setVisible(true);
    }
}
