package demo.Exceptions;

public class OutOfStockException extends Exception {
    
    public OutOfStockException() {
        super("Product is out of stock");
    }
}
