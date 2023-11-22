package Commerce;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 */
public class Vendor implements Serializable {
    /**
     *
     */
    private String username;
    private String password;
    private String name;
    private double money;
    private ArrayList<Product> products;
    private ArrayList<Producer> producers;

    /**
     * @param username
     * @param password
     * @param name
     * @param money
     * @param products
     * @param producers
     */
    public Vendor(String username, String password, String name, double money, ArrayList<Product> products, ArrayList<Producer> producers) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.money = money;
        this.products = products;
        this.producers = producers;
    }

    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public double getMoney() {
        return money;
    }

    /**
     * @param money
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * @return
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * @param products
     */
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * @return
     */
    public ArrayList<Producer> getProducers() {
        return producers;
    }

    /**
     * @param producers
     */
    public void setProducers(ArrayList<Producer> producers) {
        this.producers = producers;
    }
}
