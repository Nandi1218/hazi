package Commerce;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Basic producer class. Represents a single producer
 * that produces a single product
 */
public class Producer implements Serializable {
    private String username;
    private String password;
    private String name;
    Product product;
    ArrayList<Vendor> blackListed = new ArrayList<>();
    private double price;
    private int dailyProduction;
    private int quantity;

    /**
     * Constructor for the class Producer
     *
     * @param username        the producer's username
     * @param password        the producer's password
     * @param name            the producer's name
     * @param product         the product that the producer is selling
     * @param price           the price of the product
     * @param dailyProduction the daily production of the producer
     * @param quantity        the current quantity of the product
     */
    public Producer(String username, String password, String name, Product product, ArrayList<Vendor> blackListed, double price, int dailyProduction, int quantity) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.product = product;
        this.blackListed = blackListed;
        this.price = price;
        this.dailyProduction = dailyProduction;
        this.quantity = quantity;
    }


    /**
     * getter method for the producer's name
     *
     * @return the producer's name
     */
    public String getName() {
        return name;
    }

    /**
     * setter method for the producer's name
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter method for the product
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * setter method for the product
     *
     * @param product the new product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * getter method for the product's price
     *
     * @return the product's price
     */
    public double getPrice() {
        return price;
    }

    /**
     * setter method for the product's price
     *
     * @param price the new price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getter method for the daily production
     *
     * @return the daily production
     */
    public int getDailyProduction() {
        return dailyProduction;
    }

    /**
     * setter method for the daily production
     *
     * @param dailyProduction the new daily production
     */
    public void setDailyProduction(int dailyProduction) {
        this.dailyProduction = dailyProduction;
    }

    /**
     * getter method for the producer's current quantity
     *
     * @return the producer's current quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * setter method for the quantity
     *
     * @param quantity the new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * getter method for the producer's username
     *
     * @return the producer's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter method for the producer's username
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter method for the producer's password
     *
     * @return the producer's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter method for the producer's password
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * toString method for the producer
     *
     * @return a string representation of the producer
     */
    @Override
    public String toString() {
        return "Producer{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", product=" + product +
                ", price=" + price +
                ", dailyProduction=" + dailyProduction +
                ", quantity=" + quantity +
                '}' + '\n';
    }

    /**
     * getter method for the producer's preferred vendors
     *
     * @return the producer's preferred vendors
     */
    public ArrayList<Vendor> getBlackListed() {
        return blackListed;
    }

    /**
     * setter method for the producer's preferred vendors
     *
     * @param blackListed the new preferred vendors
     */
    public void setBlackListed(ArrayList<Vendor> blackListed) {
        this.blackListed = blackListed;
    }

    public void updateProducerQuantity(LocalDate lastCheck) {
        int days = lastCheck.until(LocalDate.now()).getDays();
        System.out.println(days);
        quantity += days * dailyProduction;
    }

}
