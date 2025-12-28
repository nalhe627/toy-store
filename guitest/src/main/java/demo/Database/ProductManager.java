package demo.Database;

import demo.Products.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

import demo.Exceptions.InvalidInputException;
import demo.Exceptions.SkuNotFoundException;
import java.io.FileNotFoundException;

/** Class that represents the Manager for the products in the database */
public class ProductManager {

    // Attribute
    private ArrayList<Product> productList = new ArrayList<>(); // Stores all of the products into one ArrayList

    /**
     * Constructor for ProductManager. Loads the data from toysData file and adds it into the productList
     * @throws FileNotFoundException
     */
    public ProductManager() throws FileNotFoundException {
        File file = new File("res/toysData.txt");
        Scanner inputFile = new Scanner(file);

        while (inputFile.hasNext()) {
            String[] line = inputFile.nextLine().split(";");
            addProduct(line);
        }
        inputFile.close();
    }
    
    /**
     * Method to search the database using the keyword that is supplied from the user.
     * @param keyword - Keyword to use to find products that contain that keyword
     * @return ArrayList of Products that match the keyword
     */
    public ArrayList<Product> searchInventory(String keyword) {
        ArrayList<Product> productsOfKeyword = new ArrayList<>();
        for (Product product : productList) { // The comparison should be case insensitive
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                productsOfKeyword.add(product);
            }
        }
        return productsOfKeyword;
    }

    /**
     * Method to search the database using the type that is supplied from the user.
     * @param type - The type of product that the user wants to search for
     * @return ArrayList of Products that match the type
     */
    public ArrayList<Product> searchforType(String type) throws InvalidInputException {
        ArrayList<Product> productsOfKeyword = new ArrayList<>();
        for (Product product : productList) { // The comparison should be case insensitive
            switch (type) {
                case "Figure":
                    if (product instanceof Figure) {
                        productsOfKeyword.add(product);
                    }
                    break;
                case "Puzzle":
                    if (product instanceof Puzzle) {
                        productsOfKeyword.add(product);
                    }
                    break;
                case "Board Game":
                    if (product instanceof BoardGame) {
                        productsOfKeyword.add(product);
                    }
                    break;
                case "Video Game":
                    if (product instanceof VideoGame) {
                        productsOfKeyword.add(product);
                    }
                    break;
                default: // User doesn't pick anything
                    throw new InvalidInputException("Type is not selected");
            }
        }
        return productsOfKeyword;
    }

    /**
     * Method that instantiates a product based on its sku number, and adds it to the productlist.
     * @param args - String array of attributes for the product
     * @return Product that was added to the database
     */
    public Product addProduct(String[] args) {
        // For clarification: args[3] could be an int or String, args[4] could be an int or char
        String sku = args[0];
        String name = args[1];
        Float price = Float.parseFloat(args[2]);
        Product product;

        if (sku.startsWith("0") || sku.startsWith("1")) {
            product = new Figure(sku, name, price, Integer.parseInt(args[3]), args[4].charAt(0));
        }
        else if (sku.startsWith("2") || sku.startsWith("3")) {
            product = new VideoGame(sku, name, price, args[3]);
        }
        else if (sku.startsWith("4") || sku.startsWith("5") || sku.startsWith("6")) {
            product = new Puzzle(sku, name, price, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        }
        else { // Sku starts with 7, 8, or 9
            product = new BoardGame(sku, name, price, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        }
        productList.add(product);
        return product;
    }

    /**
     * Validates input when user wants to add a product.
     * @param args - String[] args containing all attributes of the product
     * @throws InvalidInputException Exception to throw when there is a invalid input
     */
    public void validation(String[] args) throws InvalidInputException {
        try { // Check if sku exists in database
            findFromSku(args[0]);
            throw new InvalidInputException("Provided sku already exists");
        } 
        catch (SkuNotFoundException e) {} // If it throws this exception, then the sku is unique

        try { // See if sku contains only digits
            Long.parseLong(args[0]);
        } 
        catch (NumberFormatException e) {
            throw new InvalidInputException("Sku contains a non-digit");
        }

        if (args[0].length() != 10) { // Check if sku is 10 digits long
            throw new InvalidInputException("Sku must be 10 digits long");
        }

        try { // See if price is correct
            Float.parseFloat(args[2]);
        }
        catch (NumberFormatException e) {
            throw new InvalidInputException("Price is not a valid number");
        }

        if (!(args[0].startsWith("2") || args[0].startsWith("3"))) { // Product is phyical
            try { // See if availableCount is correct
                Integer.parseInt(args[3]);
            } 
            catch (NumberFormatException e) {
                throw new InvalidInputException("Available count is not a valid number");
            }

            if (!(args[0].startsWith("0") || args[0].startsWith("1"))) { // Product is NOT a figure
                try { // See if minAge or numPieces is correct
                    Integer.parseInt(args[4]);
                } 
                catch (NumberFormatException e) {
                    throw new InvalidInputException("Minimum age/Number of pieces is not a valid number");
                }
            }
            else { // Product IS a figure
                if (args[4].length() != 1) { // See if the string is a character
                    throw new InvalidInputException("Classification must be one character");
                }
                else if (!(args[4].equalsIgnoreCase("a") || args[4].equalsIgnoreCase("d") || args[4].equalsIgnoreCase("h"))) { // See if character is the letter a, d, or h
                    throw new InvalidInputException("Classification must be either 'A' for Action, 'D' for Doll, or 'H' for Historic");
                }
            }
        }
    }

    /**
     * Method that removes a product from productList based on the supplied sku number.
     * @param sku - Sku number of the product that is to be removed
     * @return Product that was removed
     * @throws SkuNotFoundException If no product was found based on the sku number
     */
    public Product removeProduct(String sku) throws SkuNotFoundException {
        Product product = findFromSku(sku);
        productList.remove(product);
        return product;
    }

    /**
     * Method that finds the product from productList based on the supplied sku number.
     * @param sku - Sku number of the product that is to be removed
     * @return Product that matches sku number
     * @throws SkuNotFoundException If no product was found based on the sku number, or no sku was provided
     */
    public Product findFromSku(String sku) throws SkuNotFoundException {
        if (sku.equals("")) { // If no sku provided, throw exception
            throw new SkuNotFoundException();
        }
        for (Product productinList : productList) {
            if (productinList.getSku().equals(sku)) {
               return productinList; 
            }
        }
        throw new SkuNotFoundException(sku); // If it doesn't exist in database, then throw exception
    }

    /**
     * Method that saves the final productList into the toysData file.
     * @throws FileNotFoundException
     */
    public void save() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter("res/toysData.txt");
        for (Product product : productList) {
            writer.println(product.toDatabase());
        }
        writer.close();
    }

    /**
     * Getter for productList (Specifically made for unit testing)
     * @return ProductList
     */
    public ArrayList<Product> getProductList() {
        return productList;
    }
}
