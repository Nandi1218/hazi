package Tests;

import Commerce.Order;
import Commerce.Producer;
import Commerce.Product;
import Commerce.Vendor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class OrderTest {
    /**
     * This method tests the updateOrder method.
     */
    @Test
    void updateOrder() {
        Order order = new Order(new Vendor("username", "password", "name", 10),
                new Producer("username", "password", "name", new Product("alma","alma"), new ArrayList<>(), 10,10,10),
                new Product("alma","alma"),
                10);
        Order newOrder = new Order(new Vendor("username", "password", "name", 10),
                new Producer("username", "password", "name", new Product("alma","alma"), new ArrayList<>(), 10,10,10),
                new Product("alma","alma"),
                10);
        newOrder.updateOrder();
        assertNotEquals(order, newOrder);
    }

    /**
     * This method tests the isDelivered method.
     */
    @Test
    void isDelivered() {
        Order order = new Order(new Vendor("username", "password", "name", 10),
                new Producer("username", "password", "name", new Product("alma","alma"), new ArrayList<>(), 10,10,10),
                new Product("alma","alma"),
                10);
        assertTrue(order.isDelivered());
    }
}