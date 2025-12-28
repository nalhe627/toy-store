package demo.Products;

/** Class that represents a board game product */
public class BoardGame extends Physical {

    // Attribute
    private int minAge;

    /**
     * Construct for the BoardGame class.
     * @param sku - Sku number of product
     * @param name  - Name of product
     * @param price - Price of product
     * @param availableCount - The available stock of the product
     * @param minAge - The minimum age assoicated with the product
     */
    public BoardGame(String sku, String name, float price, int availableCount, int minAge) {
        super(sku, name, price, availableCount);
        this.minAge = minAge;
    }

    /**
     * Getter for minAge
     * @return int of the minAge
     */
    public int getMinAge() {
        return minAge;
    }

    @Override
    public String toString() {
        return super.getSku() + ": " + super.getName() + ", Ages " + minAge + " and up. $" + String.format("%.2f", super.getPrice()) + " (Stock: " + super.getAvailableCount() + ")";
    }

    @Override
    public String toDatabase() {
        return super.getSku() + ";" + super.getName() + ";" + String.format("%.2f", super.getPrice()) + ";" + super.getAvailableCount() + ";" + minAge;
    }
}
