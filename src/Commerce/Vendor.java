package Commerce;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Basic vendor class. Represents a single vendor
 * that sells multiple products
 * can have multiple producers to buy products from
 */
public class Vendor implements Serializable {
/** The vendor's username*/
    private String username;
    /** The vendor's password*/
    private String password;
    /** The vendor's name*/
    private String name;
    /** The vendor's money*/
    private double money;
    /** The vendor's products*/
    private ArrayList<Product> products;
    private HashMap<Product, Integer> productQuantity = new HashMap<>();
    /** The vendor's producers*/
    private ArrayList<Producer> producers;

    /**
     * Constructor for the class Vendor
     * @param username The vendor's username
     * @param password The vendor's password
     * @param name The vendor's name
     * @param money The vendor's money
     * @param products The vendor's products
     * @param producers The vendor's producers
     */
    public Vendor(String username, String password, String name, double money) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.money = money;
        this.products = new ArrayList<>();
        this.producers = new ArrayList<>();
        productQuantity = new HashMap<>();
    }

    /** Getter method for the vendor's username
     * @return the vendor's username
     */
    public String getUsername() {
        return username;
    }

    /** Setter method for the vendor's username
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Getter method for the vendor's password
     * @return the vendor's password
     */
    public String getPassword() {
        return password;
    }

    /** Setter method for the vendor's password
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Getter method for the vendor's name
     * @return the vendor's name
     */
    public String getName() {
        return name;
    }

    /** Setter method for the vendor's name
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter method for the vendor's money
     * @return the vendor's money
     */
    public double getMoney() {
        return money;
    }

    /** Setter method for the vendor's money
     * @param money the new money
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /** Getter method for the vendor's products
     * @return the vendor's products
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**  Setter method for the vendor's products
     * @param products the new products
     */
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /** Getter method for the vendor's producers
     * @return the vendor's producers
     */
    public ArrayList<Producer> getProducers() {
        return producers;
    }

    /** Setter method for the vendor's producers
     * @param producers the new producers
     */
    public void setProducers(ArrayList<Producer> producers) {
        this.producers = producers;
    }

    /** toString method for the vendor
     * @return a string representation of the vendor
     */
    @Override
    public String toString() {
        return "Vendor{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", products=" + products +
                ", producers=" + producers +
                '}' + "\n";
    }
    public void addToProductQuantity(Product product, int quantity){
        if (productQuantity.containsKey(product)){
            productQuantity.put(product, productQuantity.get(product)+quantity);
        }
        else{
            productQuantity.put(product, quantity);
            products.add(product);
        }
    }
}
