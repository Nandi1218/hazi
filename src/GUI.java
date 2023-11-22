import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Commerce.Product;
import Commerce.Vendor;
import Commerce.Producer;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Wed Nov 22 09:34:20 CET 2023
 */


/**
 * The type Gui.
 */
public class GUI extends JFrame {
    public GUI() {
        initComponents();

        /**
         * @param WindowAdapter()
         */
        addWindowListener(new WindowAdapter() {
            /**
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                Main.saveVendor.save(Main.vendors);
                Main.saveProducer.save(Main.producers);
                Main.saveProduct.save(Main.products);
                System.exit(0);
            }
        });
    }

    /**
     * @param e
     */
    private void button2(ActionEvent e) {

        for (Vendor vendor : Main.vendors) {
            if (vendor.getUsername().equals(usernameText.getText()) && vendor.getPassword().equals(passwordText.getText())) {
                JOptionPane.showMessageDialog(null, "Logged in as vendor");
                return;
            }
        }
        for (Producer producer : Main.producers) {
            if (producer.getUsername().equals(usernameText.getText()) && producer.getPassword().equals(passwordText.getText())) {
                JOptionPane.showMessageDialog(null, "Logged in as producer");
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "wrong username or password");

    }

    /**
     * @param e
     */
    private void button3(ActionEvent e) {
        for (Vendor vendor : Main.vendors) {
            if (vendor.getUsername().equals(usernameText.getText())) {
                JOptionPane.showMessageDialog(null, "Username already exists");
                return;
            }
        }
        for (Producer producer : Main.producers) {
            if (producer.getUsername().equals(usernameText.getText())) {
                JOptionPane.showMessageDialog(null, "Username already exists");
                return;
            }
        }                      //0        1
        String[] options = {"Vendor", "Producer"};
        int x = JOptionPane.showOptionDialog(null, "Register as vendor or producer?", "Register", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        String name = JOptionPane.showInputDialog("Enter name");
        if (x == 0) {
            double startMoney = Double.parseDouble(JOptionPane.showInputDialog("Enter starting money"));
            Main.vendors.add(new Vendor(usernameText.getText(), passwordText.getText(), name, startMoney, null, null));
            Main.saveVendor.save(Main.vendors);
        } else {
            String productName = JOptionPane.showInputDialog("Enter product name");
            Product prod = null;
            for (Product product : Main.products) {
                if (product.getName().equals(productName)) {
                    prod = product;
                    break;
                }
            }
            if (prod == null) {
                // error message no such product
                JOptionPane.showMessageDialog(null, "No such product");
                return;
            }

            double startingPrice = Double.parseDouble(JOptionPane.showInputDialog("Enter starting price"));
            int dailyProduction = Integer.parseInt(JOptionPane.showInputDialog("Enter daily production"));
            Main.producers.add(new Producer(usernameText.getText(), passwordText.getText(), name, prod, startingPrice, dailyProduction, 0));
            Main.saveProducer.save(Main.producers);

        }

    }

    /**
     *
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        loginScreen = new JPanel();
        label1 = new JLabel();
        usernameText = new JTextField();
        label2 = new JLabel();
        passwordText = new JPasswordField();
        panel2 = new JPanel();
        button2 = new JButton();
        button3 = new JButton();

        //======== this ========
        setPreferredSize(new Dimension(640, 420));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "novisualpadding,hidemode 3,align center center",
            // columns
            "[640,fill]",
            // rows
            "[374]"));

        //======== panel1 ========
        {
            panel1.setBackground(new Color(0x3c3f41));
            panel1.setForeground(new Color(0xcc0033));
            panel1.setPreferredSize(new Dimension(640, 420));
            panel1.setLayout(new MigLayout(
                "fill,novisualpadding,hidemode 3",
                // columns
                "[fill]" +
                "[grow,fill]",
                // rows
                "[]"));

            //======== loginScreen ========
            {
                loginScreen.setBackground(new Color(0x4c4d4e));
                loginScreen.setPreferredSize(new Dimension(280, 200));
                loginScreen.setMaximumSize(new Dimension(400, 400));
                loginScreen.setMinimumSize(new Dimension(0, 0));
                loginScreen.setLayout(new MigLayout(
                    "novisualpadding,hidemode 3,align center center",
                    // columns
                    "[left]",
                    // rows
                    "[]" +
                    "[]" +
                    "[]" +
                    "[]0" +
                    "[]0"));

                //---- label1 ----
                label1.setText("Username:");
                label1.setFont(new Font("JetBrains Mono Light", Font.BOLD, 16));
                label1.setForeground(new Color(0x203074));
                loginScreen.add(label1, "cell 0 0");

                //---- usernameText ----
                usernameText.setPreferredSize(new Dimension(200, 30));
                loginScreen.add(usernameText, "cell 0 1");

                //---- label2 ----
                label2.setText("Password:");
                label2.setFont(new Font("JetBrains Mono Light", Font.BOLD, 16));
                label2.setForeground(new Color(0x203074));
                loginScreen.add(label2, "cell 0 2");

                //---- passwordText ----
                passwordText.setPreferredSize(new Dimension(200, 30));
                loginScreen.add(passwordText, "cell 0 3,align center center,grow 0 0");

                //======== panel2 ========
                {
                    panel2.setPreferredSize(new Dimension(200, 34));
                    panel2.setBackground(new Color(0x4c4d4e));
                    panel2.setLayout(new MigLayout(
                        "fillx,hidemode 3,align center center",
                        // columns
                        "[fill]" +
                        "[fill]",
                        // rows
                        "[]"));

                    //---- button2 ----
                    button2.setText("Login");
                    button2.setFont(new Font("JetBrains Mono Light", Font.BOLD, 13));
                    button2.setPreferredSize(new Dimension(94, 30));
                    button2.setBackground(new Color(0x25292e));
                    button2.addActionListener(e -> button2(e));
                    panel2.add(button2, "cell 0 0,alignx left,growx 0");

                    //---- button3 ----
                    button3.setText("Register");
                    button3.setFont(new Font("JetBrains Mono Light", Font.BOLD, 13));
                    button3.setPreferredSize(new Dimension(94, 30));
                    button3.setBackground(new Color(0x25292e));
                    button3.addActionListener(e -> button3(e));
                    panel2.add(button3, "cell 1 0,alignx right,growx 0");
                }
                loginScreen.add(panel2, "cell 0 4");
            }
            panel1.add(loginScreen, "cell 1 0,align center center,grow 0 0");
        }
        contentPane.add(panel1, "pad 0,cell 0 0,grow");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JPanel loginScreen;
    private JLabel label1;
    private JTextField usernameText;
    private JLabel label2;
    private JPasswordField passwordText;
    private JPanel panel2;
    private JButton button2;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
