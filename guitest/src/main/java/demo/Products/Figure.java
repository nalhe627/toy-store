package demo.Products;

/** Class that represents a figure product */
public class Figure extends Physical {
    
    // Attribute
    private char classification;

    /**
     * Construct for the Figure class.
     * @param sku - Sku number of product
     * @param name  - Name of product
     * @param price - Price of product
     * @param availableCount - The available stock of the product
     * @param classification - char of the type of figure the product is 
     */
    public Figure(String sku, String name, float price, int availableCount, char classification) {
        super(sku, name, price, availableCount);
        this.classification = Character.toUpperCase(classification); // Make sure character is uppercase
    }

    /**
     * Getter for classification
     * @return char of the classification
     */
    public char getClassification() {
        return classification;
    }

    @Override
    public String toString() {
        return super.getSku() + ": " + super.getName() + ". $" + String.format("%.2f", super.getPrice()) + " (Stock: " + super.getAvailableCount() + ")";
    }

    @Override
    public String toDatabase() {
        return super.getSku() + ";" + super.getName() + ";" + String.format("%.2f", super.getPrice()) + ";" + super.getAvailableCount() + ";" + classification;
    }
}
