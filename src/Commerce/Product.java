package Commerce;

import java.io.Serializable;

/**
 * Basic product class. Represents a single product
 */

public class Product implements Serializable {
    /**
     * The product's name
     */
    private String name;
    /**
     * the product's description
     */
    private String description;
    /**
     * the product's id
     */
    private int id;
    /**
     * static counter for generating the product's id
     */
    static int idCounter = 0;

    /**
     * Constructor for the Class Product
     *
     * @param name        The product's name
     * @param description The product's description
     */
    public Product(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = idCounter++;
    }

    /**
     * Getter method for the product's ID
     *
     * @return the product's id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method for the product's name
     *
     * @return the product's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for the product's name
     *
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for the product's description
     *
     * @return The description of the product
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for the product's description
     *
     * @param description the new description for the product
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter method for the product's ID
     *
     * @param id The product's new ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * toString method for the product
     *
     * @return a string representation of the product
     */
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                "}\n";
    }
}

