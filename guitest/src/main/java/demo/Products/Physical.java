package demo.Products;

import demo.Exceptions.OutOfStockException;

/** Abstract class representing a physical product */
public abstract class Physical extends Product {
    
    // Attribute
    private int availableCount;

    /**
     * Construct for the Physical class.
     * @param sku - Sku number of product
     * @param name  - Name of product
     * @param price - Price of product
     * @param availableCount - The available stock of the product
     */
    public Physical(String sku, String name, float price, int availableCount) {
        super(sku, name, price);
        this.availableCount = availableCount;
    }    

    /**
     * Method that adds an amount to the stock.
     * @param amount - Amount to be added
     * @return Message telling the user the amount has been added to availableCount
     */
    public String addStock(int amount) {
        this.availableCount += amount;
        return "Added " + amount + " to the stock of " + super.getName() + ". Stock is now " + availableCount;
    }

    /**
     * Getter for the availableCount attribute
     * @return The amount left in stock of the product
     */
    public int getAvailableCount() {
        return availableCount;
    }

    /**
     * Method that purchases the physical product, and decreases the availableCount by 1.
     * @throws OutOfStockException If the stock of the product is zero
     */
    @Override
    public String purchase() throws OutOfStockException {
        if (availableCount > 0) {
            availableCount -= 1;
            return "Purchased Product " + super.getSku() + ": " + super.getName();
        }
        else {
            throw new OutOfStockException();
        }
    }
}
