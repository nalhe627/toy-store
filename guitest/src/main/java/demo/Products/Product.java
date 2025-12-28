package demo.Products;

import demo.Exceptions.OutOfStockException;

/** Abstract class representing a general product */
public abstract class Product {
    
    // Attributes
    private String sku;
    private String name;
    private float price;

    /**
     * Constructor for the Product class.
     * @param sku - Sku number of product
     * @param name - Name of the product
     * @param price - Price of the product
     */
    public Product(String sku, String name, float price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
    }

    /**
     * Getter for sku
     * @return sku number as a String
     */
    public String getSku() {
        return sku;
    }

    /**
     * Getter for name
     * @return Name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for price
     * @return Price of the product
     */
    public float getPrice() {
        return price;
    }

    /**
     * Method that purchases the given product.
     * @return Message confirming that the purchase has been made
     * @throws OutOfStockException Throws exception when stock reaches 0 (product has to be physical)
     */
    public String purchase() throws OutOfStockException {
        return "Purchased Product " + sku + ": " + name;
    }

    /**
     * toString Method
     */
    public abstract String toString();

    /**
     * Method that returns a String of the product specifically for the database (different from toString)
     * @return String to print into the database
     */
    public abstract String toDatabase();
}
