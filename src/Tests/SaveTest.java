package Tests;

import Commerce.Order;
import Commerce.Producer;
import Commerce.Product;
import Commerce.Vendor;
import Serialize.Save;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class SaveTest {

    @Test
    public void producerSave() {
        ArrayList<Producer> producers = new ArrayList<>();
        Producer producer = new Producer("username", "password", "name", new Product("alma","alma"), new ArrayList<>(), 10,10,10);
        producers.add(producer);
        assertTrue(new Save<Producer>().save(producers, "src/Tests/Producer.ser"));
    }
    @Test
    public void vendorSave() {
        ArrayList<Vendor> vendors = new ArrayList<>();
        Vendor vendor = new Vendor("username", "password", "name", 10);
        vendors.add(vendor);
        assertTrue(new Save<Vendor>().save(vendors, "src/Tests/Vendor.ser"));
    }
    @Test
    public void productSave() {
        ArrayList<Product> products = new ArrayList<>();
        Product product = new Product("alma","alma");
        products.add(product);
        assertTrue(new Save<Product>().save(products , "src/Tests/Product.ser"));
    }
    @Test
    public void orderSave() {
        ArrayList<Order> orders = new ArrayList<>();
        Order order = new Order(new Vendor("username", "password", "name", 10), new Producer("username", "password", "name", new Product("alma","alma"), new ArrayList<>(), 10,10,10), new Product("alma","alma"), 10);
        orders.add(order);
        assertTrue(new Save<Order>().save(orders , "src/Tests/Order.ser"));
    }
    @Test
    public void orderSaveEmpty() {
        ArrayList<Order> orders = new ArrayList<>();
        assertFalse(new Save<Order>().save(orders , "src/Tests/Order.ser"));
    }
    @Test
    public void productSaveEmpty() {
        ArrayList<Product> products = new ArrayList<>();
        assertFalse(new Save<Product>().save(products , "src/Tests/Product.ser"));
    }
    @Test
    public void vendorSaveEmpty() {
        ArrayList<Vendor> vendors = new ArrayList<>();
        assertFalse(new Save<Vendor>().save(vendors , "src/Tests/Vendor.ser"));
    }
    @Test
    public void producerSaveEmpty() {
        ArrayList<Producer> producers = new ArrayList<>();
        assertFalse(new Save<Producer>().save(producers , "src/Tests/Producer.ser"));
    }

}