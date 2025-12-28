package demo.Exceptions;

public class NoPhysicalStockException extends Exception {
    
    public NoPhysicalStockException() {
        super("Unable to add stock to a product with no stock");
    }
}
