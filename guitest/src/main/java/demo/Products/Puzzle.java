package demo.Products;

/** Class that represents a puzzle product */
public class Puzzle extends Physical {
    
    // Attribute
    private int numPieces;

    /**
     * Construct for the Puzzle class.
     * @param sku - Sku number of product
     * @param name  - Name of product
     * @param price - Price of product
     * @param availableCount - The available stock of the product
     * @param numPieces - The number of pieces included with the puzzle
     */
    public Puzzle(String sku, String name, float price, int availableCount, int numPieces) {
        super(sku, name, price, availableCount);
        this.numPieces = numPieces;
    }

    /**
     * Getter for numPieces
     * @return Number of pieces
     */
    public int getNumPieces() {
        return numPieces;
    }

    @Override
    public String toString() {
        return super.getSku() + ": " + super.getName() + ", " + numPieces + " Pieces. $" + String.format("%.2f", super.getPrice()) + " (Stock: " + super.getAvailableCount() + ")";
    }

    @Override
    public String toDatabase() {
        return super.getSku() + ";" + super.getName() + ";" + String.format("%.2f", super.getPrice()) + ";" + super.getAvailableCount() + ";" + numPieces;
    }
}
