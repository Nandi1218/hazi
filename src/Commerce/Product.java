package Commerce;

import java.io.Serializable;

/**
 *
 */

public class Product implements Serializable {

    private String name;
    private String description;
    private int id;
    static int idCounter = 0;

    /**
     * @param name
     * @param description
     */
    public Product(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = idCounter++;
    }

    /**
     * @return
     */
    public int getId() {
        return id;
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
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     */
    public static int getIdCounter() {
        return idCounter;
    }

    /**
     * @param idCounter
     */
    public static void setIdCounter(int idCounter) {
        Product.idCounter = idCounter;
    }
}
