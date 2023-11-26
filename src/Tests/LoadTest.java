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

    @Test
    void loadVendor() {
        ArrayList<Vendor> vendors = Load.loadVendor("src/Tests/Vendor.ser");
        assertFalse(vendors.isEmpty());
    }

    @Test
    void loadProducer() {
        ArrayList<Producer> producers = Load.loadProducer("src/Tests/Producer.ser");
        assertFalse(producers.isEmpty());
    }

    @Test
    void loadProduct() {
        ArrayList<Product> products = Load.loadProduct("src/Tests/Product.ser");
        assertFalse(products.isEmpty());
    }

    @Test
    void loadOrder() {
        ArrayList<Order> orders = Load.loadOrder("src/Tests/Order.ser");
        assertFalse(orders.isEmpty());
    }
}