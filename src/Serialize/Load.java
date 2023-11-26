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
    public static ArrayList<Vendor> loadVendor(String filename) {
        ArrayList<Vendor> vendors = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            vendors = (ArrayList<Vendor>) ois.readObject();
            System.out.println("Successfully loaded \t " + filename +"!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load. File not found or corrupted.");
        }
        return vendors;
    }

    /** Method that loads the Producer data from the serialized file
     * @return an ArrayList of the loaded data
     * @see Save
     */
    public static ArrayList<Producer> loadProducer(String filename) {
        ArrayList<Producer> producers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            producers = (ArrayList<Producer>) ois.readObject();
            System.out.println("Successfully loaded \t " + filename +"!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load. File not found or corrupted.");
        }
        return producers;
    }

    /** Method that loads the Product data from the serialized file
     * @return an ArrayList of the loaded data
     */
    public static ArrayList<Product> loadProduct(String filename) {
        ArrayList<Product> products = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            products = (ArrayList<Product>) ois.readObject();
            System.out.println("Successfully loaded \t " + filename +"!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load. File not found or corrupted.");
        }
        return products;
    }
    /** Method that loads the Order data from the serialized file
     * @return an ArrayList of the loaded data
     */
    public static ArrayList<Commerce.Order> loadOrder(String filename) {
        ArrayList<Commerce.Order> orders = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            orders = (ArrayList<Commerce.Order>) ois.readObject();
            System.out.println("Successfully loaded \t " + filename +"!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed to load. File not found or corrupted.");
        }
        return orders;
    }

}
