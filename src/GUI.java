import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import Commerce.Product;
import Commerce.Vendor;
import Commerce.Producer;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Wed Nov 22 09:34:20 CET 2023
 */


/** The main GUI class
 *  Contains the login screen
 *
 */

public class GUI extends JFrame {
    Vendor vendorUser = null;
    Producer producerUser = null;
    public GUI() {
        FlatMacDarkLaf.setup();
        initComponents();

        addWindowListener(new WindowAdapter() {
            /** Invoked when a window is in the process of being closed.
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
        this.addKeyListener(new KeyAdapter() {
            /** Invoked when a key has been pressed.
             * @param e the event to be processed
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                    System.out.println("minus");
                    System.out.println(Main.vendors);
                    System.out.println(Main.producers);
                    System.out.println(Main.products);
                }
            }
        });
    }
    /** Sets the data in the Producer and Product lists
     */
    public void setListData(){
        if(vendorUser.getProducts() == null || vendorUser.getProducers() == null){
            return;
        }
        String[] productNames = (String[]) vendorUser.getProducts().toArray();
        productsList.setListData(productNames);
        String[] producerNames = (String[]) vendorUser.getProducers().toArray();
        vendorList.setListData(producerNames);

    }

    /** When the login button is pressed
     * Checks if the username and password are correct
     * If they are, logs in the user
     * If not, displays an error message
     * @param e
     */
    private void button2(ActionEvent e) {
        if(usernameText.getText().equals("") || passwordText.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Username or password is empty");
            return;
        }
        /** Login as vendor, sets producer functions to disabled
         */
        for (Vendor vendor : Main.vendors) {
            if (vendor.getUsername().equals(usernameText.getText()) && vendor.getPassword().equals(passwordText.getText())) {
                vendorUser = vendor;
                JOptionPane.showMessageDialog(null, "Logged in as vendor");
                this.setListData();
                loginScreen.setVisible(false);
                MainPanel.setVisible(true);
                producerMenu.setEnabled(false);
                return;
            }
        }
        /** Login as producer, sets vendor functions to disabled*/
        for (Producer producer : Main.producers) {
            if (producer.getUsername().equals(usernameText.getText()) && producer.getPassword().equals(passwordText.getText())) {
                producerUser = producer;
                JOptionPane.showMessageDialog(null, "Logged in as producer");
                loginScreen.setVisible(false);
                MainPanel.setVisible(true);
                vendorMenu.setEnabled(false);
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "wrong username or password");

    }

    /** When the register button is pressed
     * Checks if the username already exists
     * If it does, displays an error message
     * If not, asks the user if they want to register as a vendor or producer
     * If they choose vendor, asks for the starting money and creates a new vendor
     * If they choose producer, asks for the product name, starting price and daily production and creates a new producer
     * If the product name doesn't exist, displays an error message
     * @param e the event to be processed
     */
    private void button3(ActionEvent e) {
        if(usernameText.getText().equals("") || passwordText.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Username or password is empty");
            return;
        }
        for (Vendor vendor : Main.vendors) {
            if (vendor.getUsername().equals(usernameText.getText())) {
                JOptionPane.showMessageDialog(null, "Username already exists");
                passwordText.setText("");
                usernameText.setText("");
                return;
            }
        }
        for (Producer producer : Main.producers) {
            if (producer.getUsername().equals(usernameText.getText())) {
                JOptionPane.showMessageDialog(null, "Username already exists");
                passwordText.setText("");
                usernameText.setText("");
                return;
            }
        }                      //0        1
        String[] options = {"Vendor", "Producer"};
        int x = JOptionPane.showOptionDialog(null, "Register as vendor or producer?", "Register", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        String name = JOptionPane.showInputDialog("Enter name");
        if (x == 0) {
            double startMoney = Double.parseDouble(JOptionPane.showInputDialog("Enter starting money"));
            vendorUser = new Vendor(usernameText.getText(), passwordText.getText(), name, startMoney, null, null);
            Main.vendors.add(vendorUser);
            this.setListData();
            loginScreen.setVisible(false);
            MainPanel.setVisible(true);
            producerMenu.setEnabled(false);
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
                JOptionPane.showMessageDialog(null, "No such product");
                return;
            }

            double startingPrice = Double.parseDouble(JOptionPane.showInputDialog("Enter starting price"));
            int dailyProduction = Integer.parseInt(JOptionPane.showInputDialog("Enter daily production"));
            producerUser = new Producer(usernameText.getText(), passwordText.getText(), name, prod, startingPrice, dailyProduction, 0);
            Main.producers.add(producerUser);
            loginScreen.setVisible(false);
            MainPanel.setVisible(true);
            vendorMenu.setEnabled(false);

        }

    }

    /** When the logout button is pressed
     * Logs out the user and displays the login screen
     * @param e the event to be processed
     */
    private void logout(ActionEvent e) {
        loginScreen.setVisible(true);
        MainPanel.setVisible(false);
        vendorMenu.setEnabled(true);
        producerMenu.setEnabled(true);
        passwordText.setText("");
        usernameText.setText("");
    }
    /** When the vendor data menu is pressed
     * Displays the vendor data menu
     * @param e the event to be processed
     */
    private void VendorDataMenu(ActionEvent e) {
        setMenuPanelsToFalse();
        vendorDataMenuPanel.setVisible(true);
    }
    /** When the vendor order menu is pressed
     * Displays the vendor order menu
     * @param e the event to be processed
     */
    private void VendorOrderMenu(ActionEvent e) {
        setMenuPanelsToFalse();
        vendorOrderPanel.setVisible(true);
    }
    /** When the vendor producer menu is pressed
     * Displays the vendor producer menu
     * @param e the event to be processed
     */
    private void vendorProducerMenu(ActionEvent e) {
        setMenuPanelsToFalse();
        vendorProducerPanel.setVisible(true);
    }

    /** Sets all the menu panels to false
     */
    public void setMenuPanelsToFalse(){
        vendorDataMenuPanel.setVisible(false);
        vendorOrderPanel.setVisible(false);
        vendorProducerPanel.setVisible(false);
    }
    /** Initializes the components
     * @formatter:off
     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        MainPanel = new JPanel();
        menuBar1 = new JMenuBar();
        vendorMenu = new JMenu();
        VendorDataMenu = new JMenuItem();
        vendorProducerMenu = new JMenuItem();
        VendorOrderMenu = new JMenuItem();
        producerMenu = new JMenu();
        ProducerDataMenu = new JMenuItem();
        menu3 = new JMenu();
        hSpacer1 = new JPanel(null);
        logoutButton = new JButton();
        vendorOrderPanel = new JPanel();
        label8 = new JLabel();
        vendorProducerPanel = new JPanel();
        label9 = new JLabel();
        vendorDataMenuPanel = new JPanel();
        vendorDataJPanel = new JPanel();
        label7 = new JLabel();
        label6 = new JLabel();
        simplePanel = new JPanel();
        label3 = new JLabel();
        nameOfUserLabel = new JLabel();
        label4 = new JLabel();
        fundsLabel = new JLabel();
        vendorScrollPane = new JScrollPane();
        vendorList = new JList();
        productsScrollPane = new JScrollPane();
        productsList = new JList();
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
        setMinimumSize(new Dimension(640, 420));
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "fill,novisualpadding,hidemode 3,align center center",
            // columns
            "0[640,fill]0",
            // rows
            "0[374]0"));

        //======== panel1 ========
        {
            panel1.setBackground(new Color(0x3c3f41));
            panel1.setForeground(new Color(0xcc0033));
            panel1.setPreferredSize(new Dimension(640, 420));
            panel1.setLayout(new MigLayout(
                "fill,novisualpadding,hidemode 3",
                // columns
                "0[fill]0" +
                "[grow,fill]0",
                // rows
                "[]"));

            //======== MainPanel ========
            {
                MainPanel.setPreferredSize(new Dimension(640, 420));
                MainPanel.setVisible(false);
                MainPanel.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "0[fill]0" +
                    "[fill]0",
                    // rows
                    "0[]0" +
                    "[]0"));

                //======== menuBar1 ========
                {
                    menuBar1.setPreferredSize(new Dimension(640, 30));

                    //======== vendorMenu ========
                    {
                        vendorMenu.setText("Vendor");
                        vendorMenu.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 14));

                        //---- VendorDataMenu ----
                        VendorDataMenu.setText("Data");
                        VendorDataMenu.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));
                        VendorDataMenu.setBackground(new Color(0x1f1e1e));
                        VendorDataMenu.addActionListener(e -> VendorDataMenu(e));
                        vendorMenu.add(VendorDataMenu);

                        //---- vendorProducerMenu ----
                        vendorProducerMenu.setText("Producers");
                        vendorProducerMenu.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));
                        vendorProducerMenu.addActionListener(e -> vendorProducerMenu(e));
                        vendorMenu.add(vendorProducerMenu);

                        //---- VendorOrderMenu ----
                        VendorOrderMenu.setText("Order");
                        VendorOrderMenu.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));
                        VendorOrderMenu.setBackground(new Color(0x1f1e1e));
                        VendorOrderMenu.addActionListener(e -> VendorOrderMenu(e));
                        vendorMenu.add(VendorOrderMenu);
                    }
                    menuBar1.add(vendorMenu);

                    //======== producerMenu ========
                    {
                        producerMenu.setText("Producer");
                        producerMenu.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 14));

                        //---- ProducerDataMenu ----
                        ProducerDataMenu.setText("Data");
                        ProducerDataMenu.setBackground(new Color(0x4a4a4a));
                        producerMenu.add(ProducerDataMenu);
                    }
                    menuBar1.add(producerMenu);

                    //======== menu3 ========
                    {
                        menu3.setText("text");
                        menu3.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 14));
                    }
                    menuBar1.add(menu3);

                    //---- hSpacer1 ----
                    hSpacer1.setOpaque(false);
                    menuBar1.add(hSpacer1);

                    //---- logoutButton ----
                    logoutButton.setText("Log out");
                    logoutButton.setAlignmentX(0.5F);
                    logoutButton.addActionListener(e -> logout(e));
                    menuBar1.add(logoutButton);
                }
                MainPanel.add(menuBar1, "cell 0 0");

                //======== vendorOrderPanel ========
                {
                    vendorOrderPanel.setPreferredSize(new Dimension(640, 390));
                    vendorOrderPanel.setVisible(false);
                    vendorOrderPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[fill]" +
                        "[fill]",
                        // rows
                        "[]" +
                        "[]"));

                    //---- label8 ----
                    label8.setText("Order");
                    vendorOrderPanel.add(label8, "cell 0 0");
                }
                MainPanel.add(vendorOrderPanel, "cell 0 1");

                //======== vendorProducerPanel ========
                {
                    vendorProducerPanel.setPreferredSize(new Dimension(640, 390));
                    vendorProducerPanel.setVisible(false);
                    vendorProducerPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[fill]" +
                        "[fill]",
                        // rows
                        "[]" +
                        "[]"));

                    //---- label9 ----
                    label9.setText("Producer panel");
                    vendorProducerPanel.add(label9, "cell 0 0");
                }
                MainPanel.add(vendorProducerPanel, "cell 0 1");

                //======== vendorDataMenuPanel ========
                {
                    vendorDataMenuPanel.setMinimumSize(new Dimension(640, 200));
                    vendorDataMenuPanel.setVisible(false);
                    vendorDataMenuPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "0[fill]0",
                        // rows
                        "0[]0"));

                    //======== vendorDataJPanel ========
                    {
                        vendorDataJPanel.setPreferredSize(new Dimension(640, 390));
                        vendorDataJPanel.setLayout(new MigLayout(
                            "hidemode 3",
                            // columns
                            "30[fill]0" +
                            "[430,right]",
                            // rows
                            "[200]0" +
                            "[322]0"));

                        //---- label7 ----
                        label7.setText("Producers:");
                        label7.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 16));
                        label7.setHorizontalAlignment(SwingConstants.CENTER);
                        vendorDataJPanel.add(label7, "pad 0 5 0 0,cell 1 0,aligny bottom,grow 100 0");

                        //---- label6 ----
                        label6.setText("Products:");
                        label6.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 16));
                        label6.setHorizontalAlignment(SwingConstants.LEFT);
                        vendorDataJPanel.add(label6, "pad 0 -5 0 0,cell 1 0,aligny bottom,grow 100 0");

                        //======== simplePanel ========
                        {
                            simplePanel.setPreferredSize(new Dimension(299, 390));
                            simplePanel.setLayout(new MigLayout(
                                "hidemode 3,aligny top",
                                // columns
                                "[70,fill]" +
                                "[70,fill]",
                                // rows
                                "[center]" +
                                "[]" +
                                "[]"));

                            //---- label3 ----
                            label3.setText("Name:");
                            label3.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 22));
                            simplePanel.add(label3, "cell 0 0");

                            //---- nameOfUserLabel ----
                            nameOfUserLabel.setText("name");
                            nameOfUserLabel.setFont(new Font("JetBrains Mono ExtraLight", Font.PLAIN, 22));
                            nameOfUserLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                            simplePanel.add(nameOfUserLabel, "cell 1 0");

                            //---- label4 ----
                            label4.setText("Available funds:");
                            label4.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 22));
                            simplePanel.add(label4, "cell 0 1");

                            //---- fundsLabel ----
                            fundsLabel.setText("money");
                            fundsLabel.setFont(new Font("JetBrains Mono ExtraLight", Font.PLAIN, 22));
                            fundsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                            simplePanel.add(fundsLabel, "cell 1 1");
                        }
                        vendorDataJPanel.add(simplePanel, "cell 0 1,growy");

                        //======== vendorScrollPane ========
                        {
                            vendorScrollPane.setPreferredSize(new Dimension(120, 150));

                            //---- vendorList ----
                            vendorList.setPreferredSize(new Dimension(120, 54));
                            vendorScrollPane.setViewportView(vendorList);
                        }
                        vendorDataJPanel.add(vendorScrollPane, "pad 0,cell 1 1,align center top,grow 0 0");

                        //======== productsScrollPane ========
                        {
                            productsScrollPane.setPreferredSize(new Dimension(120, 150));

                            //---- productsList ----
                            productsList.setPreferredSize(new Dimension(120, 54));
                            productsScrollPane.setViewportView(productsList);
                        }
                        vendorDataJPanel.add(productsScrollPane, "pad 0,cell 1 1,aligny top,growy 0");
                    }
                    vendorDataMenuPanel.add(vendorDataJPanel, "cell 0 0");
                }
                MainPanel.add(vendorDataMenuPanel, "cell 0 1");
            }
            panel1.add(MainPanel, "cell 0 0");

            //======== loginScreen ========
            {
                loginScreen.setBackground(new Color(0x4c4d4e));
                loginScreen.setPreferredSize(new Dimension(280, 200));
                loginScreen.setMaximumSize(new Dimension(400, 400));
                loginScreen.setMinimumSize(new Dimension(0, 0));
                loginScreen.setLayout(new MigLayout(
                    "novisualpadding,hidemode 3,align center center",
                    // columns
                    "0[left]0",
                    // rows
                    "0[]0" +
                    "[]0" +
                    "[]0" +
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
    private JPanel MainPanel;
    private JMenuBar menuBar1;
    private JMenu vendorMenu;
    private JMenuItem VendorDataMenu;
    private JMenuItem vendorProducerMenu;
    private JMenuItem VendorOrderMenu;
    private JMenu producerMenu;
    private JMenuItem ProducerDataMenu;
    private JMenu menu3;
    private JPanel hSpacer1;
    private JButton logoutButton;
    private JPanel vendorOrderPanel;
    private JLabel label8;
    private JPanel vendorProducerPanel;
    private JLabel label9;
    private JPanel vendorDataMenuPanel;
    private JPanel vendorDataJPanel;
    private JLabel label7;
    private JLabel label6;
    private JPanel simplePanel;
    private JLabel label3;
    private JLabel nameOfUserLabel;
    private JLabel label4;
    private JLabel fundsLabel;
    private JScrollPane vendorScrollPane;
    private JList vendorList;
    private JScrollPane productsScrollPane;
    private JList productsList;
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
