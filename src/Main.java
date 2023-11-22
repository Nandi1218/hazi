import Commerce.*;
import Serialize.*;

import java.util.ArrayList;

public class Main {
    public static Save<Vendor> saveVendor = new Save<>();
    static Save<Producer> saveProducer = new Save<>();
    static Save<Product> saveProduct = new Save<>();
    public static ArrayList<Product> products = Load.loadProduct();
    public static ArrayList<Vendor> vendors = Load.loadVendor();
    public static ArrayList<Producer> producers = Load.loadProducer();

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