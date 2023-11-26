package Tests;

import Commerce.Producer;
import Commerce.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProducerTest {
    /**
     * This method tests the updateProducerQuantity method.
     */
    @Test
    void updateProducerQuantity() {
        Producer producer = new Producer("username", "password", "name", new Product("alma","alma"), new ArrayList<>(), 10,10,10);
        Producer newProducer = new Producer("username", "password", "name", new Product("alma","alma"), new ArrayList<>(), 10,10,10);
        LocalDate date = LocalDate.now().minusDays(2);
        newProducer.updateProducerQuantity(date);

        assertNotEquals(producer.getQuantity(), newProducer.getQuantity());
    }
}