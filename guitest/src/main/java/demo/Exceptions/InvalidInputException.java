package demo.Exceptions;

public class InvalidInputException extends Exception {
    
    public InvalidInputException(String message) {
        super("Invalid input for new product: " + message);
    }
}
