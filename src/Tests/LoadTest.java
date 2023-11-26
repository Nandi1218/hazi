package Tests;

import Commerce.Order;
import Commerce.Producer;
import Commerce.Product;
import Commerce.Vendor;
import Serialize.Load;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoadTest {
    /**
     * This method tests the loadVendor method.
     */
    @Test
    void loadVendor() {
        ArrayList<Vendor> vendors = Load.loadVendor("src/Tests/Vendor.ser");
        assertFalse(vendors.isEmpty());
    }

    /**
     * This method tests the loadProducer method.
     */
    @Test
    void loadProducer() {
        ArrayList<Producer> producers = Load.loadProducer("src/Tests/Producer.ser");
        assertFalse(producers.isEmpty());
    }

    /**
     *  This method tests the loadProduct method.
     */
    @Test
    void loadProduct() {
        ArrayList<Product> products = Load.loadProduct("src/Tests/Product.ser");
        assertFalse(products.isEmpty());
    }

    /**
     *  This method tests the loadOrder method.
     */
    @Test
    void loadOrder() {
        ArrayList<Order> orders = Load.loadOrder("src/Tests/Order.ser");
        assertFalse(orders.isEmpty());
    }
}