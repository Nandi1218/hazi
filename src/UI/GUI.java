package UI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.TableRowSorter;

import Commerce.Order;
import Commerce.Product;
import Commerce.Vendor;
import Commerce.Producer;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import net.miginfocom.swing.*;
import org.jdesktop.beansbinding.*;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import tableModels.*;

/*
 * Created by JFormDesigner on Wed Nov 22 09:34:20 CET 2023
 */


/** The main GUI class
 *  Contains the login screen
 *
 */

public class GUI extends JFrame {
    /** if the user is a vendor*/
    Vendor vendorUser = null;
    /** the producer that the vendor is ordering from*/
    Producer orderProducer = null;
    /** The list of producers*/
    ArrayList<String> prodList;
    /** table model for the order history table*/
    public static final OrderHistoryTableModel dataModel = new OrderHistoryTableModel();
    /** table model for the vendor order table*/
    public static final VendorOrderModel vendorOrderModel = new VendorOrderModel();
    /** table model for the vendor table*/
    public static final VendorTableModel vendorTableModel = new VendorTableModel(Main.vendors);
    /** table model for the producer table*/
    public static final ProducerTableModel producerTableModel = new ProducerTableModel(Main.producers);
    /** table model for the product table*/
    public static final ProductTableModel productTableModel = new ProductTableModel(Main.products);
    /** if the user is a producer*/
    Producer producerUser = null;
    productQuantityCellRenderer renderer = new productQuantityCellRenderer();

    /** Constructor for the GUI class
     * Sets the look and feel to FlatMacDarkLaf
     * Initializes the components
     */
    public GUI() {
        FlatMacDarkLaf.setup();
        initComponents();

        orderHistoryTable.setModel(dataModel);
        vendorOrderTable.setModel(vendorOrderModel);
        vendorAdminTable.setModel(vendorTableModel);
        adminProducerTable.setModel(producerTableModel);
        adminProductTable.setModel(productTableModel);
        TableRowSorter<OrderHistoryTableModel> sorter = new TableRowSorter<>(dataModel);
        TableRowSorter<VendorOrderModel> sorter2 = new TableRowSorter<>(vendorOrderModel);
        TableRowSorter<VendorTableModel> sorter3 = new TableRowSorter<>(vendorTableModel);
        TableRowSorter<ProducerTableModel> sorter4 = new TableRowSorter<>(producerTableModel);
        TableRowSorter<ProductTableModel> sorter5 = new TableRowSorter<>(productTableModel);

        orderHistoryTable.setRowSorter(sorter);
        vendorOrderTable.setRowSorter(sorter2);
        vendorAdminTable.setRowSorter(sorter3);
        adminProducerTable.setRowSorter(sorter4);
        adminProductTable.setRowSorter(sorter5);

        productsList.setCellRenderer(renderer);

        addWindowListener(new WindowAdapter() {
            /** Invoked when a window is in the process of being closed.
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                Main.saveVendor.save(Main.vendors,"src/Serialize/Vendor.ser");
                Main.saveProducer.save(Main.producers, "src/Serialize/Producer.ser");
                Main.saveProduct.save(Main.products, "src/Serialize/Product.ser");
                Main.saveOrder.save(Main.orders, "src/Serialize/Order.ser");
                System.exit(0);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            /**
             * Invoked when a key has been pressed.
             *
             * @param e the event to be processed
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_MINUS) {
                    System.out.println(Main.vendors);
                    System.out.println(Main.producers);
                    System.out.println(Main.products);
                }
            }
        });
    }
    /**
     * Sets the data in the Producer and Product lists
     */
    public void setListData(){
        ArrayList<String> vendorListData = new ArrayList<>();
        ArrayList<String> productListData = new ArrayList<>();
        for (Producer producer : vendorUser.getProducers()) {
            vendorListData.add(producer.getName());
        }
        for (Product product : vendorUser.getProducts()) {
            System.out.println(product.getName());
            productListData.add(product.getName());
        }
        vendorList.setModel(new DefaultComboBoxModel(vendorListData.toArray()));
        productsList.setModel(new DefaultComboBoxModel(productListData.toArray()));
        renderer.updateQuantity();

    }
    /**
     * When the login button is pressed
     * Checks if the username and password are correct
     * If they are, logs in the user
     * If not, displays an error message
     */
    private void login(){
        if(usernameText.getText().equals("admin")&&passwordText.getText().equals("admin")){
            loginScreen.setVisible(false);
            MainPanel.setVisible(true);
            setMenuPanelsToFalse();
            vendorMenu.setVisible(false);
            producerMenu.setVisible(false);
            adminMenu.setEnabled(true);
            adminMenu.setVisible(true);
            return;
        }else adminMenu.setVisible(false);
        if(usernameText.getText().isEmpty() || passwordText.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Username or password is empty");
            return;
        }

        for (Vendor vendor : Main.vendors) {
            if (vendor.getUsername().equals(usernameText.getText()) && vendor.getPassword().equals(passwordText.getText())) {
                vendorUser = vendor;
                for (Producer producer : vendorUser.getProducers()) {
                    System.out.println(producer.getName());
                }
                JOptionPane.showMessageDialog(null, "Logged in as vendor");
                prodList = new ArrayList<>();
                this.setListData();
                for (Producer producer : vendorUser.getProducers()) {
                    prodList.add(producer.getName());
                }
                try{fillVendorProdComboBoxes();}catch (NullPointerException exception){exception.printStackTrace();}
                loginScreen.setVisible(false);
                MainPanel.setVisible(true);
                vendorMenu.setEnabled(true);
                producerMenu.setEnabled(false);
                setMenuPanelsToFalse();

                return;
            }
        }
        for (Producer producer : Main.producers) {
            if (producer.getUsername().equals(usernameText.getText()) && producer.getPassword().equals(passwordText.getText())) {
                producerUser = producer;
                JOptionPane.showMessageDialog(null, "Logged in as producer");
                loginScreen.setVisible(false);
                MainPanel.setVisible(true);
                vendorMenu.setEnabled(false);
                producerMenu.setEnabled(true);
                setMenuPanelsToFalse();
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "wrong username or password");
    }

    /**
     * When the login button is pressed
     * Checks if the username and password are correct
     * If they are, logs in the user
     * If not, displays an error message
     *
     * @param e the event to be processed
     */

    private void loginButton(ActionEvent e) {login();}

    /**
     * When the register button is pressed
     * Checks if the username already exists
     * If it does, displays an error message
     * If not, asks the user if they want to register as a vendor or producer
     * If they choose vendor, asks for the starting money and creates a new vendor
     * If they choose producer, asks for the product name, starting price and daily production and creates a new producer
     * If the product name doesn't exist, displays an error message
     *
     * @param e the event to be processed
     */
    private void registerButton(ActionEvent e) {
        if(usernameText.getText().equals("admin"))
            return;
        if(usernameText.getText().isEmpty() || passwordText.getText().isEmpty()){
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
            vendorUser = new Vendor(usernameText.getText(), passwordText.getText(), name, startMoney);
            Main.vendors.add(vendorUser);
            this.setListData();
            loginScreen.setVisible(false);
            MainPanel.setVisible(true);
            producerMenu.setEnabled(false);
            vendorMenu.setEnabled(true);
            producerMenu.setVisible(true);
            vendorMenu.setVisible(true);
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
            producerUser = new Producer(usernameText.getText(), passwordText.getText(), name, prod,new ArrayList<>(), startingPrice, dailyProduction, 0);
            Main.producers.add(producerUser);
            loginScreen.setVisible(false);
            MainPanel.setVisible(true);
            vendorMenu.setEnabled(false);
            producerMenu.setEnabled(true);
            producerMenu.setVisible(true);
            vendorMenu.setVisible(true);

        }

    }

    /**
     * When the logout button is pressed
     * Logs out the user and displays the login screen
     *
     * @param e the event to be processed
     */
    private void logout(ActionEvent e) {
        setMenuPanelsToFalse();
        vendorMenu.setVisible(true);
        producerMenu.setVisible(true);
        loginScreen.setVisible(true);
        MainPanel.setVisible(false);
        passwordText.setText("");
        usernameText.setText("");
        System.out.println(prodList);
    }
    /**
     * When the vendor data menu is pressed
     * Displays the vendor data menu
     *
     * @param e the event to be processed
     */
    private void VendorDataMenu(ActionEvent e) {
        setMenuPanelsToFalse();
        vendorDataMenuPanel.setVisible(true);
        nameOfUserLabel.setText(vendorUser.getName());
        fundsLabel.setText(vendorUser.getMoney() +"$");
        setListData();
    }
    /**
     * When the vendor order menu is pressed
     * Displays the vendor order menu
     *
     * @param e the event to be processed
     */
    private void VendorOrderMenu(ActionEvent e) {
        setMenuPanelsToFalse();
        for (Order order : Main.orders) {
            if(order.getBuyer().getName().equals(vendorUser.getName())) {
                vendorOrderModel.addOrder(order);
            }
        }
        if(vendorUser.getProducers().isEmpty())
            orderProducers.setModel(new DefaultComboBoxModel(new String[]{"No producers"}));
        else {
            ArrayList<String> producerNames = new ArrayList<>();
            for (Producer producer : vendorUser.getProducers()) {
                producerNames.add(producer.getName());
            }
            orderProducers.setModel(new DefaultComboBoxModel(producerNames.toArray()));
        }
        vendorOrderPanel.setVisible(true);

    }

    /**
     * Fills the vendor producer menu combo boxes
     */
    public void fillVendorProdComboBoxes()throws NullPointerException{
        if(vendorUser.getProducers().isEmpty())
        {
            String[] currentProducers = {"No producers"};
            ArrayList<String> mainProdList = new ArrayList<>();
            for (Producer producer : Main.producers)
                mainProdList.add(producer.getName());
            vendorMenuCB.setModel(new DefaultComboBoxModel(mainProdList.toArray()));
            VendorMenuCBCurrent.setModel(new DefaultComboBoxModel(currentProducers));
        }
        else {
            prodList = new ArrayList<>();
            ArrayList<String> mainProdList = new ArrayList<>();
            for (Producer producer : vendorUser.getProducers()) {
                prodList.add(producer.getName());
            }
            for (Producer producer : Main.producers) {
                if(!producer.getBlackListed().contains(vendorUser))
                    mainProdList.add(producer.getName());
            }
            mainProdList.removeAll(prodList);
            if(mainProdList.isEmpty())
                mainProdList.add("No producers");
            //sort prodList
            Collections.sort(prodList);
            Collections.sort(mainProdList);

            vendorMenuCB.setModel(new DefaultComboBoxModel(mainProdList.toArray()));
            VendorMenuCBCurrent.setModel(new DefaultComboBoxModel(prodList.toArray()));
        }
    }
    /**
     * When the vendor producer menu is pressed
     * Displays the vendor producer menu
     * If the vendor doesn't have any producers, displays all the producers
     * If the vendor has producers, displays the producers that the vendor doesn't have
     *
     * @param e the event to be processed
     */
    private void vendorProducerMenu(ActionEvent e) {
        setMenuPanelsToFalse();
        vendorProducerPanel.setVisible(true);

        try{fillVendorProdComboBoxes();}catch (NullPointerException exception){exception.printStackTrace();}
    }

    /**
     * Sets all the menu panels to false
     */
    public void setMenuPanelsToFalse(){
        vendorDataMenuPanel.setVisible(false);
        vendorOrderPanel.setVisible(false);
        vendorProducerPanel.setVisible(false);
        prodDataMenuPanel.setVisible(false);
        producerVendorMenuPanel.setVisible(false);
        OrderHistoryMenuPanel.setVisible(false);
        adminVendorPanel.setVisible(false);
        adminProducersPanel.setVisible(false);
        adminProductPanel.setVisible(false);
    }

    private void passwordTextKeyPressed(KeyEvent e) {
        //if enter is pressed calls login
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            login();
        }
    }
    /**
     * When the vendor producer menu combo box is pressed
     * Displays the producer's name, product and daily production
     *
     * @param e the event to be processed
     */
    private void vendorMenuCB(ActionEvent e) {
        String producerName = (String) vendorMenuCB.getSelectedItem();
        Producer producer = findProducer(producerName);

        if (producer == null)
            return;
        vendorProdMenuProdName.setText(producer.getName());
        vendorProdMenuProduct.setText(producer.getProduct().getName());
        vendorProdMenuAmount.setText(String.valueOf(producer.getQuantity()));
    }
    /**
     * Find a producer by name in Main.producers
     *
     * @param name the name of the producer
     * @return the producer with the given name
     */
    public Producer findProducer(String name){
        for (Producer prod : Main.producers) {
            if (prod.getName().equals(name)) {
                return prod;
            }
        }
        return null;
    }

    /**
     * When the add producer button is pressed
     * Adds the producer to the vendor's producers
     *
     * @param e the event to be processed
     */
    private void addProd(ActionEvent e) {
        String name = (String) vendorMenuCB.getSelectedItem();
        for (Producer producer : Main.producers){
            if (producer.getName().equals(name)){
                vendorUser.getProducers().add(producer);
                break;
            }
        }
        try{fillVendorProdComboBoxes();}catch (NullPointerException exception){exception.printStackTrace();}
    }
    /**
     * When the remove producer button is pressed
     * Removes the producer from the vendor's producers
     *
     * @param e the event to be processed
     */

    private void removeProd(ActionEvent e) {
        String name = (String) VendorMenuCBCurrent.getSelectedItem();
        for (Producer producer : vendorUser.getProducers()){
            if (producer.getName().equals(name)){
                vendorUser.getProducers().remove(producer);
                break;
            }
        }
        try{fillVendorProdComboBoxes();}catch (NullPointerException exception){exception.printStackTrace();}
    }

    private void VendorMenuCBCurrent(ActionEvent e) {
        String producerName = (String) VendorMenuCBCurrent.getSelectedItem();
        Producer producer = findProducer(producerName);
        if(producer == null)
            return;
        vendorProdMenuProdName.setText(producer.getName());
        vendorProdMenuProduct.setText(producer.getProduct().getName());
        vendorProdMenuAmount.setText(String.valueOf(producer.getQuantity()));
    }

    /**
     * When the producer data menu is pressed
     * Displays the producer data menu
     * Displays the producer's name, product, product description, product id, price, quantity and daily production
     *
     * @param e the event to be processed
     */
    private void ProducerDataMenu(ActionEvent e) {
        setMenuPanelsToFalse();
        prodDataMenuPanel.setVisible(true);
        producerName.setText(producerUser.getName());
        producerProdName.setText(producerUser.getProduct().getName());
        producerProdDesc.setText(producerUser.getProduct().getDescription());
        producerProdId.setText(String.valueOf(producerUser.getProduct().getId()));
        producerQantity.setText(String.valueOf(producerUser.getQuantity()));
        producerDailyProd.setText(String.valueOf(producerUser.getDailyProduction()));
        prodPrice.setText(producerUser.getPrice() +"$");
    }

    /**
     * When the edit producer button is pressed
     * Asks the user which fields they want to edit
     * If the user chooses to edit the name, asks for the new name and sets it
     * If the user chooses to edit the product, asks for the new product name and description
     * If the product doesn't exist, creates a new product
     * If the user chooses to edit the quantity, asks for the new quantity and sets it
     * If the user chooses to edit the daily production, asks for the new daily production and sets it
     * If the user chooses to edit the price, asks for the new price and sets it
     *
     * @param e the event to be processed
     */
    private void editProd(ActionEvent e) {
        if(checkBoxName.isSelected()){
            String newName = JOptionPane.showInputDialog("Enter new name");
            producerUser.setName(newName);
            producerName.setText(newName);
        }
        if(checkBoxProd.isSelected()){
            String newName = JOptionPane.showInputDialog("Enter new product name");
            for (Product product : Main.products){
                if(product.getName().equals(newName)) {
                    producerUser.setProduct(product);
                    producerProdName.setText(product.getName());
                    producerProdDesc.setText(product.getDescription());
                    producerProdId.setText(String.valueOf(product.getId()));
                    break;
                }
            }
            Product newProduct;
            if(!producerUser.getProduct().getName().equals(newName)) {
                newProduct = new Product(newName, JOptionPane.showInputDialog("Enter new product description"));
                Main.products.add(newProduct);
                producerUser.setProduct(newProduct);
                producerProdName.setText(newName);
                producerProdDesc.setText(newProduct.getDescription());
                producerProdId.setText(String.valueOf(newProduct.getId()));
            }

        }
        if(checkBoxQuantity.isSelected()){
            int newQuantity = Integer.parseInt(JOptionPane.showInputDialog("Enter new quantity"));
            producerUser.setQuantity(newQuantity);
            producerQantity.setText(String.valueOf(newQuantity));
        }
        if(checkBoxDProd.isSelected()){
            int newDaily = Integer.parseInt(JOptionPane.showInputDialog("Enter new daily production"));
            producerUser.setDailyProduction(newDaily);
            producerDailyProd.setText(String.valueOf(newDaily));
        }
        if(checkBoxPrice.isSelected()){
            double newPrice = Double.parseDouble(JOptionPane.showInputDialog("Enter new price"));
            producerUser.setPrice(newPrice);
            prodPrice.setText(newPrice +"$");
        }
        checkBoxName.setSelected(false);
        checkBoxProd.setSelected(false);
        checkBoxQuantity.setSelected(false);
        checkBoxDProd.setSelected(false);
        checkBoxPrice.setSelected(false);
    }
    /**
     * When the producer vendors menu is pressed
     * Displays the producer vendors menu
     * Displays the producer's vendors
     *
     * @param e the event to be processed
     */
    private void producerVendorsMenu(ActionEvent e) {
        setMenuPanelsToFalse();
        ArrayList<String> vendorNames = new ArrayList<>();
        for (Vendor vendor : Main.vendors) {
            vendorNames.add(vendor.getName());
        }
        comboBoxVendors.setModel(new DefaultComboBoxModel(vendorNames.toArray()));
        
        producerVendorMenuPanel.setVisible(true);

    }
    /**
     * When the vendor data menu is pressed
     * Displays the vendor data menu
     * Displays the vendor's name, money, products and producers
     *
     * @param e the event to be processed
     */
    private void vendors(ActionEvent e)throws NullPointerException {
        prodVendorName.setText(comboBoxVendors.getSelectedItem().toString());
        Vendor tempVendor;
        for (Vendor vendor:Main.vendors){
            if(vendor.getName().equals(comboBoxVendors.getSelectedItem().toString())){
                tempVendor = vendor;
                checkBoxBlackListed.setSelected(producerUser.getBlackListed().contains(tempVendor));
                break;
            }
        }

    }
    /**
     * When the vendor data menu is pressed
     * Displays the vendor data menu
     * Displays the vendor's name, money, products and producers
     *
     * @param e the event to be processed
     */
    private void blackListed(ActionEvent e) throws NullPointerException{
        try {
            if (checkBoxBlackListed.isSelected()) {
                for (Vendor vendor : Main.vendors) {
                    if (vendor.getName().equals(comboBoxVendors.getSelectedItem().toString())) {
                        if (!producerUser.getBlackListed().contains(vendor)) {
                            producerUser.getBlackListed().add(vendor);
                            vendor.getProducers().remove(producerUser);
                        }
                        break;
                    }
                }
            } else {
                for (Vendor vendor : Main.vendors) {
                    if (vendor.getName().equals(comboBoxVendors.getSelectedItem().toString())) {
                        if (producerUser.getBlackListed().contains(vendor)) {
                            producerUser.getBlackListed().remove(vendor);
                            System.out.println("unblacklisted " + vendor.getName());
                        }
                        break;
                    }
                }
            }
        }catch (NullPointerException exception){exception.printStackTrace(); throw exception;}
    }
    /**
     * When the order history menu is pressed
     * Displays the order history menu
     * Displays the vendor's order history
     *
     * @param e the event to be processed
     */
    private void miscOrderMenu(ActionEvent e) {
        setMenuPanelsToFalse();
        OrderHistoryMenuPanel.setVisible(true);
        for (Order order :Main.orders) {
            dataModel.addOrder(order);
        }

    }
    /**
     * When the vendor edit button is pressed
     * Asks the user which fields they want to edit
     * If the user chooses to edit the name, asks for the new name and sets it
     * If the user chooses to edit the money, asks for the new amount and sets it
     *
     * @param e the event to be processed
     */
    private void vendorEdit(ActionEvent e) {
        if(checkBoxVendorName.isSelected()){
            String newName = JOptionPane.showInputDialog("Enter new name");
            vendorUser.setName(newName);
            nameOfUserLabel.setText(newName);
        }
        if(checkBoxVendorMoney.isSelected()){
            double newMoney = Double.parseDouble(JOptionPane.showInputDialog("Enter new amount"));
            vendorUser.setMoney(newMoney);
            fundsLabel.setText(newMoney +"$");
        }
        checkBoxVendorName.setSelected(false);
        checkBoxVendorMoney.setSelected(false);
    }

    /**
     * Sets all the componenet's data to the selected producer's data
     *
     * @param e the event to be processed
     */

    private void orderProducers(ActionEvent e) {

        for (Producer producer1:Main.producers) {
            if(producer1.getName().equals(orderProducers.getSelectedItem().toString())){
                orderProducer = producer1;
                orderProducerProduct.setText(orderProducer.getProduct().getName());
                orderAmountSlider.setMaximum(orderProducer.getDailyProduction()*10);
                orderAmountSlider.setMinimum(1);
                orderAmountSlider.setValue(orderProducer.getQuantity());
                System.out.println(orderProducer.getName() + " " + orderProducer.getQuantity());
                orderAmountTextfield.setText(String.valueOf(orderProducer.getQuantity()));
                orderProductPrice.setText(orderProducer.getPrice()*Integer.parseInt(orderAmountTextfield.getText()) +"$");
                break;
            }
        }
    }
    /**
     * When the order button is pressed
     * Creates a new order
     * If the vendor doesn't have enough money, displays an error message
     *
     * @param e the event to be processed
     */

    private void order(ActionEvent e) {
        if(orderProducer == null){
            JOptionPane.showMessageDialog(null, "Select a producer");
            return;
        }
        int quantity = Integer.parseInt(orderAmountTextfield.getText());
        if(quantity*orderProducer.getPrice() > vendorUser.getMoney()){
            JOptionPane.showMessageDialog(null, "Not enough money");
            return;
        }
        Main.orders.add(new Order( vendorUser, orderProducer, orderProducer.getProduct(), quantity));
        vendorUser.setMoney(vendorUser.getMoney()-quantity*orderProducer.getPrice());
        vendorOrderModel.addOrder(Main.orders.getLast());

    }
    /**
     * When the order amount slider is pressed
     * Sets the order amount text field to the slider's value
     *
     * @param e the event to be processed
     */

    private void orderAmountSliderStateChanged(ChangeEvent e) {
        if (orderProducer == null)
            return;
        orderProductPrice.setText(String.format("%.2f",orderProducer.getPrice()*Integer.parseInt(orderAmountTextfield.getText())) +"$");
    }

    /**
     * Admin vendor menu is pressed
     * Displays the admin vendor panel
     * @param e the event to be processed
     */
    private void adminVendors(ActionEvent e) {
        setMenuPanelsToFalse();

        adminVendorPanel.setVisible(true);
    }

    /**
     * Admin remove vendor button is pressed
     * Removes the selected vendor
     * @param e the event to be processed
     */

    private void removeVendor(ActionEvent e) {
        int row = vendorAdminTable.getSelectedRow();
        if(row == -1)
            return;
        String name = (String) vendorAdminTable.getValueAt(row, 0);
        vendorTableModel.removeVendor(name);
        Main.producers.forEach(producer -> producer.getBlackListed().removeIf(vendor -> vendor.getName().equals(name)));
        Main.orders.removeIf(order -> order.getBuyer().getName().equals(name));
        Main.vendors.removeIf(vendor -> vendor.getName().equals(name));
        System.out.println(name);
    }

    /**
     * Admin producers menu is pressed
     * Displays the admin producers panel
     * @param e the event to be processed
     */
    private void adminProducersMenu(ActionEvent e) {
        setMenuPanelsToFalse();
        adminProducersPanel.setVisible(true);
    }

    /**
     * Admin products menu is pressed
     * Displays the admin products panel
     * @param e  the event to be processed
     */
    private void adminProductsMenu(ActionEvent e) {
        setMenuPanelsToFalse();
        adminProductPanel.setVisible(true);
    }

    /**
     * Admin add vendor button is pressed
     * Adds a new vendor
     * @param e
     */
    private void removeProducer(ActionEvent e) {
        int row = adminProducerTable.getSelectedRow();
        if(row == -1)
            return;
        String name = (String) adminProducerTable.getValueAt(row, 0);
        producerTableModel.removeProducer(name);
        Main.vendors.forEach(vendor -> vendor.getProducers().removeIf(producer -> producer.getName().equals(name)));
        Main.orders.removeIf(order -> order.getSeller().getName().equals(name));
        Main.producers.removeIf(producer -> producer.getName().equals(name));
    }

    /**
     * Admin add vendor button is pressed
     * Adds a new vendor to the table
     * @param e the event to be processed
     */
    private void adminAddProduct(ActionEvent e) {
        String name = JOptionPane.showInputDialog("Enter product name");
        String description = JOptionPane.showInputDialog("Enter product description");
        if(name == null || description == null)
            return;
        Product product = new Product(name, description);
        Main.products.add(product);
        productTableModel.refresh();
    }

    /**
     * Admin remove vendor button is pressed
     * Removes the selected product
     * @param e the event to be processed
     */
    private void adminRemoveProduct(ActionEvent e) {
        int row = adminProductTable.getSelectedRow();
        if(row == -1)
            return;
        String name = (String) adminProductTable.getValueAt(row, 0);
        productTableModel.removeProduct(name);
        Main.orders.removeIf(order -> order.getProduct().getName().equals(name));
        Main.products.removeIf(product -> product.getName().equals(name));

    }

    /**
     * A cell renderer for the products list that displays the quantity of the product in the tooltip
     */
    class productQuantityCellRenderer extends DefaultListCellRenderer{
        /** the quantity of the product*/
        private int[] quantity;
        /**
         * Updates the quantity array
         */
        public void updateQuantity(){
            int[] tempQuantity = new int[vendorUser.getProducts().size()];
            for (int i = 0; i < vendorUser.getProducts().size(); i++) {
                tempQuantity[i] = vendorUser.getQuantity(vendorUser.getProducts().get(i));
            }
            this.quantity = tempQuantity;
        }

        /**
         * Returns a component that has been configured to display the specified value.
         * @param list The JList we're painting.
         * @param value The value returned by list.getModel().getElementAt(index).
         * @param index The cells index.
         * @param isSelected True if the specified cell was selected.
         * @param cellHasFocus True if the specified cell has the focus.
         * @return A component whose paint() method will render the specified value.
         */
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Set the tooltip for the current item based on its index
            if (index >= 0 && index < quantity.length) {
                list.setToolTipText(String.format(String.valueOf(quantity[index])));
            }

            return this;
        }

    }

    /**
     * JFormDesigner initialization of the components
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
        producerVendorsMenu = new JMenuItem();
        misc = new JMenu();
        miscOrderMenu = new JMenuItem();
        adminMenu = new JMenu();
        adminVendors = new JMenuItem();
        adminProducersMenu = new JMenuItem();
        adminProductsMenu = new JMenuItem();
        hSpacer1 = new JPanel(null);
        logoutButton = new JButton();
        adminProductPanel = new JPanel();
        scrollPane5 = new JScrollPane();
        adminProductTable = new JTable();
        panel3 = new JPanel();
        adminAddProduct = new JButton();
        adminRemoveProduct = new JButton();
        adminVendorPanel = new JPanel();
        scrollPane3 = new JScrollPane();
        vendorAdminTable = new JTable();
        removeVendorButton = new JButton();
        adminProducersPanel = new JPanel();
        scrollPane4 = new JScrollPane();
        adminProducerTable = new JTable();
        removeProducerButton = new JButton();
        vendorOrderPanel = new JPanel();
        scrollPane2 = new JScrollPane();
        vendorOrderTable = new JTable();
        orderBottomPanel = new JPanel();
        orderProducers = new JComboBox();
        orderProducerProduct = new JLabel();
        orderProductPrice = new JLabel();
        orderAmountSlider = new JSlider();
        orderAmountTextfield = new JTextField();
        orderButton = new JButton();
        OrderHistoryMenuPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        orderHistoryTable = new JTable();
        producerVendorMenuPanel = new JPanel();
        label22 = new JLabel();
        prodVendorName = new JLabel();
        label25 = new JLabel();
        label24 = new JLabel();
        checkBoxBlackListed = new JCheckBox();
        comboBoxVendors = new JComboBox();
        prodDataMenuPanel = new JPanel();
        name = new JLabel();
        producerName = new JLabel();
        checkBoxName = new JCheckBox();
        label14 = new JLabel();
        producerProdName = new JLabel();
        producerProdDesc = new JLabel();
        producerProdId = new JLabel();
        checkBoxProd = new JCheckBox();
        label21 = new JLabel();
        prodPrice = new JLabel();
        checkBoxPrice = new JCheckBox();
        label18 = new JLabel();
        producerQantity = new JLabel();
        checkBoxQuantity = new JCheckBox();
        label19 = new JLabel();
        producerDailyProd = new JLabel();
        checkBoxDProd = new JCheckBox();
        editProd = new JButton();
        vendorProducerPanel = new JPanel();
        label9 = new JLabel();
        vendorProdMenuProdName = new JLabel();
        label12 = new JLabel();
        label5 = new JLabel();
        vendorProdMenuProduct = new JLabel();
        addProd = new JButton();
        vendorMenuCB = new JComboBox();
        label10 = new JLabel();
        vendorProdMenuAmount = new JLabel();
        label13 = new JLabel();
        removeProd = new JButton();
        VendorMenuCBCurrent = new JComboBox();
        vendorDataMenuPanel = new JPanel();
        vendorDataJPanel = new JPanel();
        label7 = new JLabel();
        label6 = new JLabel();
        simplePanel = new JPanel();
        label3 = new JLabel();
        nameOfUserLabel = new JLabel();
        checkBoxVendorName = new JCheckBox();
        label4 = new JLabel();
        fundsLabel = new JLabel();
        checkBoxVendorMoney = new JCheckBox();
        vendorEditButton = new JButton();
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
        loginButton = new JButton();
        registerButton = new JButton();

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
                "novisualpadding,hidemode 3",
                // columns
                "0[fill]0" +
                "[grow,fill]0",
                // rows
                "0[grow]0"));

            //======== MainPanel ========
            {
                MainPanel.setPreferredSize(new Dimension(640, 420));
                MainPanel.setMinimumSize(new Dimension(640, 350));
                MainPanel.setMaximumSize(new Dimension(640, 420));
                MainPanel.setVisible(false);
                MainPanel.setLayout(new MigLayout(
                    "hidemode 3",
                    // columns
                    "0[fill]0" +
                    "[right]0",
                    // rows
                    "0[30,fill]0" +
                    "[390]0"));

                //======== menuBar1 ========
                {
                    menuBar1.setPreferredSize(new Dimension(640, 30));
                    menuBar1.setMinimumSize(new Dimension(100, 30));
                    menuBar1.setMargin(new Insets(0, 10, 0, 15));
                    menuBar1.setMaximumSize(new Dimension(640, 32768));
                    menuBar1.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));

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
                        ProducerDataMenu.addActionListener(e -> ProducerDataMenu(e));
                        producerMenu.add(ProducerDataMenu);

                        //---- producerVendorsMenu ----
                        producerVendorsMenu.setText("Vendors");
                        producerVendorsMenu.addActionListener(e -> producerVendorsMenu(e));
                        producerMenu.add(producerVendorsMenu);
                    }
                    menuBar1.add(producerMenu);

                    //======== misc ========
                    {
                        misc.setText("History");
                        misc.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 14));

                        //---- miscOrderMenu ----
                        miscOrderMenu.setText("Orders");
                        miscOrderMenu.addActionListener(e -> miscOrderMenu(e));
                        misc.add(miscOrderMenu);
                    }
                    menuBar1.add(misc);

                    //======== adminMenu ========
                    {
                        adminMenu.setText("Admin");

                        //---- adminVendors ----
                        adminVendors.setText("Vendors");
                        adminVendors.addActionListener(e -> adminVendors(e));
                        adminMenu.add(adminVendors);

                        //---- adminProducersMenu ----
                        adminProducersMenu.setText("Producers");
                        adminProducersMenu.addActionListener(e -> adminProducersMenu(e));
                        adminMenu.add(adminProducersMenu);

                        //---- adminProductsMenu ----
                        adminProductsMenu.setText("Products");
                        adminProductsMenu.addActionListener(e -> adminProductsMenu(e));
                        adminMenu.add(adminProductsMenu);
                    }
                    menuBar1.add(adminMenu);

                    //---- hSpacer1 ----
                    hSpacer1.setOpaque(false);
                    hSpacer1.setMaximumSize(new Dimension(350000, 32767));
                    menuBar1.add(hSpacer1);

                    //---- logoutButton ----
                    logoutButton.setText("Log out");
                    logoutButton.setAlignmentX(0.5F);
                    logoutButton.setPreferredSize(new Dimension(90, 0));
                    logoutButton.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 12));
                    logoutButton.setMaximumSize(null);
                    logoutButton.setMinimumSize(null);
                    logoutButton.addActionListener(e -> logout(e));
                    menuBar1.add(logoutButton);
                }
                MainPanel.add(menuBar1, "pad 0,cell 0 0,aligny top,grow 100 0");

                //======== adminProductPanel ========
                {
                    adminProductPanel.setPreferredSize(new Dimension(640, 390));
                    adminProductPanel.setVisible(false);
                    adminProductPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[400,fill]" +
                        "[200,fill]",
                        // rows
                        "[330]"));

                    //======== scrollPane5 ========
                    {
                        scrollPane5.setViewportView(adminProductTable);
                    }
                    adminProductPanel.add(scrollPane5, "cell 0 0");

                    //======== panel3 ========
                    {
                        panel3.setLayout(new MigLayout(
                            "hidemode 3",
                            // columns
                            "[156,fill]",
                            // rows
                            "[top]" +
                            "[]" +
                            "[]" +
                            "[]"));

                        //---- adminAddProduct ----
                        adminAddProduct.setText("Add");
                        adminAddProduct.addActionListener(e -> adminAddProduct(e));
                        panel3.add(adminAddProduct, "cell 0 1");

                        //---- adminRemoveProduct ----
                        adminRemoveProduct.setText("Remove");
                        adminRemoveProduct.addActionListener(e -> adminRemoveProduct(e));
                        panel3.add(adminRemoveProduct, "cell 0 2");
                    }
                    adminProductPanel.add(panel3, "cell 1 0");
                }
                MainPanel.add(adminProductPanel, "cell 0 1");

                //======== adminVendorPanel ========
                {
                    adminVendorPanel.setPreferredSize(new Dimension(640, 390));
                    adminVendorPanel.setVisible(false);
                    adminVendorPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[400,fill]" +
                        "[200,fill]",
                        // rows
                        "[center]" +
                        "[]" +
                        "[]"));

                    //======== scrollPane3 ========
                    {
                        scrollPane3.setViewportView(vendorAdminTable);
                    }
                    adminVendorPanel.add(scrollPane3, "cell 0 0");

                    //---- removeVendorButton ----
                    removeVendorButton.setText("Remove");
                    removeVendorButton.addActionListener(e -> removeVendor(e));
                    adminVendorPanel.add(removeVendorButton, "cell 1 0");
                }
                MainPanel.add(adminVendorPanel, "cell 0 1");

                //======== adminProducersPanel ========
                {
                    adminProducersPanel.setPreferredSize(new Dimension(640, 390));
                    adminProducersPanel.setVisible(false);
                    adminProducersPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[400,fill]" +
                        "[200,fill]",
                        // rows
                        "[330,center]"));

                    //======== scrollPane4 ========
                    {
                        scrollPane4.setViewportView(adminProducerTable);
                    }
                    adminProducersPanel.add(scrollPane4, "cell 0 0");

                    //---- removeProducerButton ----
                    removeProducerButton.setText("Remove");
                    removeProducerButton.addActionListener(e -> removeProducer(e));
                    adminProducersPanel.add(removeProducerButton, "cell 1 0");
                }
                MainPanel.add(adminProducersPanel, "cell 0 1");

                //======== vendorOrderPanel ========
                {
                    vendorOrderPanel.setMaximumSize(new Dimension(640, 420));
                    vendorOrderPanel.setVisible(false);
                    vendorOrderPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "[fill]",
                        // rows
                        "[]" +
                        "[]" +
                        "[]"));

                    //======== scrollPane2 ========
                    {
                        scrollPane2.setPreferredSize(new Dimension(640, 300));

                        //---- vendorOrderTable ----
                        vendorOrderTable.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));
                        scrollPane2.setViewportView(vendorOrderTable);
                    }
                    vendorOrderPanel.add(scrollPane2, "cell 0 0");

                    //======== orderBottomPanel ========
                    {
                        orderBottomPanel.setPreferredSize(new Dimension(640, 34));
                        orderBottomPanel.setLayout(new MigLayout(
                            "hidemode 3",
                            // columns
                            "[fill]" +
                            "[fill]" +
                            "[fill]" +
                            "[fill]" +
                            "[fill]" +
                            "[fill]",
                            // rows
                            "[]"));

                        //---- orderProducers ----
                        orderProducers.setPreferredSize(new Dimension(120, 26));
                        orderProducers.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));
                        orderProducers.addActionListener(e -> orderProducers(e));
                        orderBottomPanel.add(orderProducers, "cell 0 0");

                        //---- orderProducerProduct ----
                        orderProducerProduct.setText("prodName");
                        orderProducerProduct.setPreferredSize(new Dimension(120, 16));
                        orderProducerProduct.setToolTipText("Product");
                        orderProducerProduct.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));
                        orderBottomPanel.add(orderProducerProduct, "cell 1 0");

                        //---- orderProductPrice ----
                        orderProductPrice.setText("0$");
                        orderProductPrice.setToolTipText("The amount to be payed");
                        orderProductPrice.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));
                        orderBottomPanel.add(orderProductPrice, "cell 2 0");

                        //---- orderAmountSlider ----
                        orderAmountSlider.setMaximum(1000);
                        orderAmountSlider.setToolTipText("maximum order = on stock * 3");
                        orderAmountSlider.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));
                        orderAmountSlider.addChangeListener(e -> orderAmountSliderStateChanged(e));
                        orderBottomPanel.add(orderAmountSlider, "cell 3 0");

                        //---- orderAmountTextfield ----
                        orderAmountTextfield.setPreferredSize(new Dimension(40, 26));
                        orderAmountTextfield.setToolTipText("precise order or more than max order");
                        orderAmountTextfield.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));
                        orderBottomPanel.add(orderAmountTextfield, "cell 4 0");

                        //---- orderButton ----
                        orderButton.setText("Order");
                        orderButton.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));
                        orderButton.addActionListener(e -> order(e));
                        orderBottomPanel.add(orderButton, "cell 5 0");
                    }
                    vendorOrderPanel.add(orderBottomPanel, "cell 0 1");
                }
                MainPanel.add(vendorOrderPanel, "cell 0 1");

                //======== OrderHistoryMenuPanel ========
                {
                    OrderHistoryMenuPanel.setMaximumSize(new Dimension(640, 420));
                    OrderHistoryMenuPanel.setMinimumSize(new Dimension(600, 300));
                    OrderHistoryMenuPanel.setVisible(false);
                    OrderHistoryMenuPanel.setLayout(new MigLayout(
                        "hidemode 3",
                        // columns
                        "10[fill]10",
                        // rows
                        "0[fill]0"));

                    //======== scrollPane1 ========
                    {
                        scrollPane1.setPreferredSize(new Dimension(640, 390));

                        //---- orderHistoryTable ----
                        orderHistoryTable.setPreferredSize(new Dimension(390, 300));
                        orderHistoryTable.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 12));
                        scrollPane1.setViewportView(orderHistoryTable);
                    }
                    OrderHistoryMenuPanel.add(scrollPane1, "cell 0 0");
                }
                MainPanel.add(OrderHistoryMenuPanel, "cell 0 1");

                //======== producerVendorMenuPanel ========
                {
                    producerVendorMenuPanel.setVisible(false);
                    producerVendorMenuPanel.setMaximumSize(new Dimension(640, 420));
                    producerVendorMenuPanel.setLayout(new MigLayout(
                        "hidemode 3,alignx left",
                        // columns
                        "20[249,fill]30" +
                        "[281,right]0",
                        // rows
                        "[]" +
                        "[]" +
                        "[]"));

                    //---- label22 ----
                    label22.setText("Name:");
                    label22.setPreferredSize(new Dimension(100, 16));
                    label22.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 22));
                    producerVendorMenuPanel.add(label22, "cell 0 0,alignx left,growx 0");

                    //---- prodVendorName ----
                    prodVendorName.setText("text");
                    prodVendorName.setPreferredSize(new Dimension(30, 16));
                    prodVendorName.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 22));
                    producerVendorMenuPanel.add(prodVendorName, "cell 0 0,alignx right,growx 0");

                    //---- label25 ----
                    label25.setText("Vendors:");
                    label25.setFont(new Font("JetBrains Mono Medium", Font.PLAIN, 22));
                    producerVendorMenuPanel.add(label25, "cell 1 0");

                    //---- label24 ----
                    label24.setText("Blacklisted:");
                    label24.setPreferredSize(new Dimension(160, 16));
                    label24.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 22));
                    producerVendorMenuPanel.add(label24, "cell 0 1,alignx left,growx 0");

                    //---- checkBoxBlackListed ----
                    checkBoxBlackListed.setPreferredSize(new Dimension(30, 19));
                    checkBoxBlackListed.addActionListener(e -> blackListed(e));
                    producerVendorMenuPanel.add(checkBoxBlackListed, "cell 0 1,alignx right,growx 0");

                    //---- comboBoxVendors ----
                    comboBoxVendors.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 16));
                    comboBoxVendors.setPreferredSize(new Dimension(140, 32));
                    comboBoxVendors.addActionListener(e -> vendors(e));
                    producerVendorMenuPanel.add(comboBoxVendors, "cell 1 1");
                }
                MainPanel.add(producerVendorMenuPanel, "cell 0 1");

                //======== prodDataMenuPanel ========
                {
                    prodDataMenuPanel.setPreferredSize(new Dimension(640, 420));
                    prodDataMenuPanel.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 22));
                    prodDataMenuPanel.setVisible(false);
                    prodDataMenuPanel.setMaximumSize(new Dimension(640, 420));
                    prodDataMenuPanel.setLayout(new MigLayout(
                        "hidemode 3,aligny center",
                        // columns
                        "30[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]" +
                        "[fill]",
                        // rows
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]" +
                        "[]"));

                    //---- name ----
                    name.setText("Name:");
                    name.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 22));
                    prodDataMenuPanel.add(name, "cell 0 0");

                    //---- producerName ----
                    producerName.setText("prodName");
                    producerName.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 18));
                    prodDataMenuPanel.add(producerName, "cell 1 0");
                    prodDataMenuPanel.add(checkBoxName, "cell 4 0");

                    //---- label14 ----
                    label14.setText("Product:");
                    label14.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 22));
                    prodDataMenuPanel.add(label14, "cell 0 1");

                    //---- producerProdName ----
                    producerProdName.setText("name");
                    producerProdName.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 18));
                    prodDataMenuPanel.add(producerProdName, "cell 1 1");

                    //---- producerProdDesc ----
                    producerProdDesc.setText("descr");
                    producerProdDesc.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 18));
                    prodDataMenuPanel.add(producerProdDesc, "cell 2 1");

                    //---- producerProdId ----
                    producerProdId.setText("id");
                    producerProdId.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 18));
                    prodDataMenuPanel.add(producerProdId, "cell 3 1");
                    prodDataMenuPanel.add(checkBoxProd, "cell 4 1");

                    //---- label21 ----
                    label21.setText("Price:");
                    label21.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 22));
                    prodDataMenuPanel.add(label21, "cell 0 2");

                    //---- prodPrice ----
                    prodPrice.setText("price");
                    prodPrice.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 18));
                    prodDataMenuPanel.add(prodPrice, "cell 1 2");
                    prodDataMenuPanel.add(checkBoxPrice, "cell 4 2");

                    //---- label18 ----
                    label18.setText("Quantity:");
                    label18.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 22));
                    prodDataMenuPanel.add(label18, "cell 0 3");

                    //---- producerQantity ----
                    producerQantity.setText("qan");
                    producerQantity.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 18));
                    prodDataMenuPanel.add(producerQantity, "cell 1 3");
                    prodDataMenuPanel.add(checkBoxQuantity, "cell 4 3");

                    //---- label19 ----
                    label19.setText("Daily Production:");
                    label19.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 22));
                    prodDataMenuPanel.add(label19, "cell 0 4");

                    //---- producerDailyProd ----
                    producerDailyProd.setText("daily");
                    producerDailyProd.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 18));
                    prodDataMenuPanel.add(producerDailyProd, "cell 1 4");
                    prodDataMenuPanel.add(checkBoxDProd, "cell 4 4");

                    //---- editProd ----
                    editProd.setText("Edit selected");
                    editProd.addActionListener(e -> editProd(e));
                    prodDataMenuPanel.add(editProd, "cell 0 5");
                }
                MainPanel.add(prodDataMenuPanel, "cell 0 1");

                //======== vendorProducerPanel ========
                {
                    vendorProducerPanel.setPreferredSize(new Dimension(640, 390));
                    vendorProducerPanel.setMaximumSize(new Dimension(640, 420));
                    vendorProducerPanel.setVisible(false);
                    vendorProducerPanel.setLayout(new MigLayout(
                        "fillx,hidemode 3,aligny center",
                        // columns
                        "20[fill]" +
                        "[fill]" +
                        "[400,right]20",
                        // rows
                        "100[]0" +
                        "[]0" +
                        "[]" +
                        "[]" +
                        "[120]"));

                    //---- label9 ----
                    label9.setText("Name:");
                    label9.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 22));
                    vendorProducerPanel.add(label9, "cell 0 0");

                    //---- vendorProdMenuProdName ----
                    vendorProdMenuProdName.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 22));
                    vendorProducerPanel.add(vendorProdMenuProdName, "cell 1 0");

                    //---- label12 ----
                    label12.setText("Available Producers:");
                    label12.setPreferredSize(new Dimension(120, 16));
                    label12.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 22));
                    vendorProducerPanel.add(label12, "cell 2 0");

                    //---- label5 ----
                    label5.setText("Produces:");
                    label5.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 22));
                    vendorProducerPanel.add(label5, "cell 0 1");

                    //---- vendorProdMenuProduct ----
                    vendorProdMenuProduct.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 22));
                    vendorProducerPanel.add(vendorProdMenuProduct, "cell 1 1");

                    //---- addProd ----
                    addProd.setText("Add");
                    addProd.setPreferredSize(new Dimension(100, 30));
                    addProd.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 16));
                    addProd.addActionListener(e -> addProd(e));
                    vendorProducerPanel.add(addProd, "cell 2 1");

                    //---- vendorMenuCB ----
                    vendorMenuCB.setPreferredSize(new Dimension(180, 26));
                    vendorMenuCB.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 16));
                    vendorMenuCB.addActionListener(e -> vendorMenuCB(e));
                    vendorProducerPanel.add(vendorMenuCB, "cell 2 1");

                    //---- label10 ----
                    label10.setText("Quantity:");
                    label10.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 22));
                    vendorProducerPanel.add(label10, "cell 0 2");

                    //---- vendorProdMenuAmount ----
                    vendorProdMenuAmount.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 22));
                    vendorProducerPanel.add(vendorProdMenuAmount, "cell 1 2");

                    //---- label13 ----
                    label13.setText("Current Producers:");
                    label13.setPreferredSize(new Dimension(120, 16));
                    label13.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 22));
                    vendorProducerPanel.add(label13, "cell 2 2");

                    //---- removeProd ----
                    removeProd.setText("Remove");
                    removeProd.setPreferredSize(new Dimension(100, 30));
                    removeProd.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 16));
                    removeProd.addActionListener(e -> removeProd(e));
                    vendorProducerPanel.add(removeProd, "cell 2 3");

                    //---- VendorMenuCBCurrent ----
                    VendorMenuCBCurrent.setPreferredSize(new Dimension(180, 26));
                    VendorMenuCBCurrent.setFont(new Font("JetBrains Mono Light", Font.PLAIN, 16));
                    VendorMenuCBCurrent.addActionListener(e -> VendorMenuCBCurrent(e));
                    vendorProducerPanel.add(VendorMenuCBCurrent, "cell 2 3");
                }
                MainPanel.add(vendorProducerPanel, "cell 0 1");

                //======== vendorDataMenuPanel ========
                {
                    vendorDataMenuPanel.setMinimumSize(new Dimension(640, 200));
                    vendorDataMenuPanel.setMaximumSize(new Dimension(640, 420));
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
                                "[70,fill]" +
                                "[fill]",
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
                            nameOfUserLabel.setMaximumSize(new Dimension(80, 30));
                            simplePanel.add(nameOfUserLabel, "cell 1 0");
                            simplePanel.add(checkBoxVendorName, "cell 2 0");

                            //---- label4 ----
                            label4.setText("Available funds:");
                            label4.setFont(new Font("JetBrains Mono SemiBold", Font.PLAIN, 22));
                            simplePanel.add(label4, "cell 0 1");

                            //---- fundsLabel ----
                            fundsLabel.setText("money");
                            fundsLabel.setFont(new Font("JetBrains Mono ExtraLight", Font.PLAIN, 22));
                            fundsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
                            simplePanel.add(fundsLabel, "cell 1 1");
                            simplePanel.add(checkBoxVendorMoney, "cell 2 1");

                            //---- vendorEditButton ----
                            vendorEditButton.setText("Edit");
                            vendorEditButton.addActionListener(e -> vendorEdit(e));
                            simplePanel.add(vendorEditButton, "cell 0 2");
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
                passwordText.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        passwordTextKeyPressed(e);
                    }
                });
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

                    //---- loginButton ----
                    loginButton.setText("Login");
                    loginButton.setFont(new Font("JetBrains Mono Light", Font.BOLD, 13));
                    loginButton.setPreferredSize(new Dimension(94, 30));
                    loginButton.setBackground(new Color(0x25292e));
                    loginButton.addActionListener(e -> loginButton(e));
                    panel2.add(loginButton, "cell 0 0,alignx left,growx 0");

                    //---- registerButton ----
                    registerButton.setText("Register");
                    registerButton.setFont(new Font("JetBrains Mono Light", Font.BOLD, 13));
                    registerButton.setPreferredSize(new Dimension(94, 30));
                    registerButton.setBackground(new Color(0x25292e));
                    registerButton.addActionListener(e -> registerButton(e));
                    panel2.add(registerButton, "cell 1 0,alignx right,growx 0");
                }
                loginScreen.add(panel2, "cell 0 4");
            }
            panel1.add(loginScreen, "cell 1 0,align center center,grow 0 0");
        }
        contentPane.add(panel1);
        pack();
        setLocationRelativeTo(getOwner());

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            orderAmountSlider, BeanProperty.create("value"),
            orderAmountTextfield, BeanProperty.create("text")));
        bindingGroup.bind();
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
    private JMenuItem producerVendorsMenu;
    private JMenu misc;
    private JMenuItem miscOrderMenu;
    private JMenu adminMenu;
    private JMenuItem adminVendors;
    private JMenuItem adminProducersMenu;
    private JMenuItem adminProductsMenu;
    private JPanel hSpacer1;
    private JButton logoutButton;
    private JPanel adminProductPanel;
    private JScrollPane scrollPane5;
    private JTable adminProductTable;
    private JPanel panel3;
    private JButton adminAddProduct;
    private JButton adminRemoveProduct;
    private JPanel adminVendorPanel;
    private JScrollPane scrollPane3;
    private JTable vendorAdminTable;
    private JButton removeVendorButton;
    private JPanel adminProducersPanel;
    private JScrollPane scrollPane4;
    private JTable adminProducerTable;
    private JButton removeProducerButton;
    private JPanel vendorOrderPanel;
    private JScrollPane scrollPane2;
    private JTable vendorOrderTable;
    private JPanel orderBottomPanel;
    private JComboBox orderProducers;
    private JLabel orderProducerProduct;
    private JLabel orderProductPrice;
    private JSlider orderAmountSlider;
    private JTextField orderAmountTextfield;
    private JButton orderButton;
    private JPanel OrderHistoryMenuPanel;
    private JScrollPane scrollPane1;
    private JTable orderHistoryTable;
    private JPanel producerVendorMenuPanel;
    private JLabel label22;
    private JLabel prodVendorName;
    private JLabel label25;
    private JLabel label24;
    private JCheckBox checkBoxBlackListed;
    private JComboBox comboBoxVendors;
    private JPanel prodDataMenuPanel;
    private JLabel name;
    private JLabel producerName;
    private JCheckBox checkBoxName;
    private JLabel label14;
    private JLabel producerProdName;
    private JLabel producerProdDesc;
    private JLabel producerProdId;
    private JCheckBox checkBoxProd;
    private JLabel label21;
    private JLabel prodPrice;
    private JCheckBox checkBoxPrice;
    private JLabel label18;
    private JLabel producerQantity;
    private JCheckBox checkBoxQuantity;
    private JLabel label19;
    private JLabel producerDailyProd;
    private JCheckBox checkBoxDProd;
    private JButton editProd;
    private JPanel vendorProducerPanel;
    private JLabel label9;
    private JLabel vendorProdMenuProdName;
    private JLabel label12;
    private JLabel label5;
    private JLabel vendorProdMenuProduct;
    private JButton addProd;
    private JComboBox vendorMenuCB;
    private JLabel label10;
    private JLabel vendorProdMenuAmount;
    private JLabel label13;
    private JButton removeProd;
    private JComboBox VendorMenuCBCurrent;
    public JPanel vendorDataMenuPanel;
    private JPanel vendorDataJPanel;
    private JLabel label7;
    private JLabel label6;
    private JPanel simplePanel;
    private JLabel label3;
    private JLabel nameOfUserLabel;
    private JCheckBox checkBoxVendorName;
    private JLabel label4;
    private JLabel fundsLabel;
    private JCheckBox checkBoxVendorMoney;
    private JButton vendorEditButton;
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
    private JButton loginButton;
    private JButton registerButton;
    private BindingGroup bindingGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
