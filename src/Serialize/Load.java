package Serialize;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import Commerce.Producer;
import Commerce.Product;
import Commerce.Vendor;


/**
 *
 */
public class Load {
    /**
     * @return
     */
    public static ArrayList<Vendor> loadVendor() {
        ArrayList<Vendor> vendors = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/Serialize/" + "Vendor.ser"))) {
            vendors = (ArrayList<Vendor>) ois.readObject();
            System.out.println("Successfully loaded!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return vendors;
    }

    /**
     * @return
     */
    public static ArrayList<Producer> loadProducer() {
        ArrayList<Producer> producers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/Serialize/" + "Producer.ser"))) {
            producers = (ArrayList<Producer>) ois.readObject();
            System.out.println("Successfully loaded!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return producers;
    }

    /**
     * @return
     */
    public static ArrayList<Product> loadProduct() {
        ArrayList<Product> products = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/Serialize/" + "Product.ser"))) {
            products = (ArrayList<Product>) ois.readObject();
            System.out.println("Successfully loaded!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

}
