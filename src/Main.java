import Commerce.*;
import Serialize.*;

import java.util.ArrayList;

public class Main {
    /** The save object for the Product class*/
    static Save<Product> saveProduct = new Save<>();
    /** The save object for the Vendor class*/
    static Save<Producer> saveProducer = new Save<>();
    /** The save object for the Producer class*/
    public static Save<Vendor> saveVendor = new Save<>();
    /** The list of all the products*/
    public static ArrayList<Product> products =Load.loadProduct();
    /** The list of all the vendors*/
    public static ArrayList<Vendor> vendors =Load.loadVendor();
    /** The list of all the producers*/
    public static ArrayList<Producer> producers =Load.loadProducer();
    /**
     * The main method of the program
     * Creates the GUI object and makes it visible
     * @param args the arguments of the main method
     */
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);

        /*for (int i = 0; i < 10; i++) {
            products.add(new Product("product"+i, "desc"+i));
            vendors.add(new Vendor("vendorUsername"+i, "vendorPassword"+i, "vendor"+i, i, null, null));
            producers.add(new Producer("producerUsername"+i,"prodcerPassword"+i,"producer"+i, products.get(i), i*1000, i*10, i*100));

        }*/

    }
}