package demo.Exceptions;

public class SkuNotFoundException extends Exception {
    
    public SkuNotFoundException(String sku) {
        super("Sku " + sku + " does not exist in database");
    }

    public SkuNotFoundException() {
        super("No sku has been provided");
    }
}
