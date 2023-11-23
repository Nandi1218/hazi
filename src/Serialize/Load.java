package Serialize;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import Commerce.Producer;
import Commerce.Product;
import Commerce.Vendor;


/** Class that loads the data from the serialized files
 * @see Save
 */
public class Load {
    /** Method that loads the Vendor data from the serialized file
     * @return an ArrayList of the loaded data
     * @see Save
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

    /** Method that loads the Producer data from the serialized file
     * @return an ArrayList of the loaded data
     * @see Save
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

    /** Method that loads the Product data from the serialized file
     * @return an ArrayList of the loaded data
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
    public static ArrayList<Commerce.Order> loadOrder() {
        ArrayList<Commerce.Order> orders = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/Serialize/" + "Order.ser"))) {
            orders = (ArrayList<Commerce.Order>) ois.readObject();
            System.out.println("Successfully loaded!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return orders;
    }

}
