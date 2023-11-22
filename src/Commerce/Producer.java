package Commerce;

import java.io.Serializable;

/**
 *
 */
public class Producer implements Serializable {
    private String username;
    private String password;
    private String name;
    Product product;
    private double price;
    private int dailyProduction;
    private int quantity;

    /**
     * @param username
     * @param password
     * @param name
     * @param product
     * @param price
     * @param dailyProduction
     * @param quantity
     */
    public Producer(String username, String password, String name, Product product, double price, int dailyProduction, int quantity) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.product = product;
        this.price = price;
        this.dailyProduction = dailyProduction;
        this.quantity = quantity;
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
    public Product getProduct() {
        return product;
    }

    /**
     * @param product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return
     */
    public int getDailyProduction() {
        return dailyProduction;
    }

    /**
     * @param dailyProduction
     */
    public void setDailyProduction(int dailyProduction) {
        this.dailyProduction = dailyProduction;
    }

    /**
     * @return
     */


    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}
