package demo;

import demo.Database.ProductManager;
import demo.Products.*;

import java.io.FileNotFoundException;
import demo.Exceptions.*;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Line;

public class Controller {

    private ProductManager productManager;
    private ToggleGroup radioGroup = new ToggleGroup();
    private ArrayList<Product> productList;

    @FXML
    private Label addToyMessageLabel;

    @FXML
    private Tab addToyTab;

    @FXML
    private MenuItem boardGame;

    @FXML
    private Button buyButton;

    @FXML
    private Button clearButton;

    @FXML
    private Label fieldOneLabel;

    @FXML
    private Label fieldTwoLabel;

    @FXML
    private MenuItem figure;

    @FXML
    private Tab homeTab;

    @FXML
    private TextField nameField;

    @FXML
    private RadioButton nameRadioButton;

    @FXML
    private MenuItem newBoardGame;

    @FXML
    private MenuItem newFigure;

    @FXML
    private MenuItem newPuzzle;

    @FXML
    private TextField newSkuField;

    @FXML
    private MenuButton newTypeMenu;

    @FXML
    private MenuItem newVideoGame;

    @FXML
    private TextField priceField;

    @FXML
    private MenuItem puzzle;

    @FXML
    private Button removeButton;

    @FXML
    private ListView<Product> removeListView;

    @FXML
    private Label removeMessageLabel;

    @FXML
    private TextField removeSerialField;

    @FXML
    private Tab removeToyTab;

    @FXML
    private Button saveButton;

    @FXML
    private Button searchAllButton;

    @FXML
    private Button searchAllToRemoveButton;

    @FXML
    private Button searchButton;

    @FXML
    private ListView<Product> searchListView;

    @FXML
    private Label searchMessageLabel;

    @FXML
    private Button searchToRemoveButton;

    @FXML
    private RadioButton serialRadioButton;

    @FXML
    private TextField skuField;

    @FXML
    private TextField typeField1;

    @FXML
    private TextField typeField2;

    @FXML
    private Label typeLabel;

    @FXML
    private Line typeLine;

    @FXML
    private MenuButton typeMenu;

    @FXML
    private RadioButton typeRadioButton;

    @FXML
    private MenuItem videoGame;

    @FXML
    private TextField newNameField;

    /** Things to do on startup */
    public void initialize() throws FileNotFoundException, InvalidInputException {
        productManager = new ProductManager();
        productList = productManager.getProductList();
        searchListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        radioGroup.getToggles().add(serialRadioButton);
        radioGroup.getToggles().add(nameRadioButton);
        radioGroup.getToggles().add(typeRadioButton);
    }

    /////////////////// HOME TAB /////////////////////
    /** Enables sku field for searching and disables other fields */
    public void serialRadioButtonHandler() {
        skuField.setDisable(false);
        nameField.setDisable(true);
        typeMenu.setDisable(true);
        searchButton.setDisable(false);
    }

    /** Enables name field for searching and disables other fields */
    public void nameRadioButtonHandler() {
        nameField.setDisable(false);
        skuField.setDisable(true);
        typeMenu.setDisable(true);
        searchButton.setDisable(false);
    }

    /** Enables type menu for searching and disables other fields */
    public void typeRadioButtonHandler() {
        typeMenu.setDisable(false);
        skuField.setDisable(true);
        nameField.setDisable(true);
        searchButton.setDisable(false);
    }

    /** Displays all products in the database to the user in the Home tab */
    public void searchAllButtonHandler() {
        searchListView.getItems().clear(); // Clear list if theres something inside already
        searchMessageLabel.setText("");

        for (Product item : productList) {
            searchListView.getItems().add(item);
        }
    }

    /** Event handler for search button */
    public void searchButtonHandler() {
        searchListView.getItems().clear();
        searchMessageLabel.setText("");

        if (nameRadioButton.isSelected()) {
            String name = nameField.getText();
            ArrayList<Product> searchedItems = productManager.searchInventory(name);
            
            for (Product item : searchedItems) {
                searchListView.getItems().add(item);
            }
        }
        else if (serialRadioButton.isSelected()) {
            try {
                Product product = productManager.findFromSku(skuField.getText());
                searchListView.getItems().add(product);
            } 
            catch (SkuNotFoundException e) {
                searchMessageLabel.setText(e.getMessage());
            }
        }
        else  { // User chooses to search based on type of product
            try {
                ArrayList<Product> typeList = productManager.searchforType(typeMenu.getText());
                for (Product product : typeList) {
                    searchListView.getItems().add(product);
                }
            }
             catch (InvalidInputException e) {
                searchMessageLabel.setText("Type has not been specified");
            }
            
        }
    }

    /** Clears all fields and clears products on ListView */
    public void clearButtonHandler() {
        typeMenu.setText("Select Toy");

        skuField.clear();
        nameField.clear();
        searchMessageLabel.setText("");

        searchListView.getItems().clear();
    }

    /** Handler for Figure menu item */
    public void choosefigureHandler() {
        typeMenu.setText("Figure");
    }

    /** Handler for Puzzle menu item */
    public void choosePuzzleHandler() {
        typeMenu.setText("Puzzle");
    }

    /** Handler for Board Game menu item */
    public void chooseBoardGameHandler() {
        typeMenu.setText("Board Game");
    }

    /** Handler for Video Game menu item */
    public void chooseVideoGameHandler() {
        typeMenu.setText("Video Game");
    }

    /** Handler for the Buy Button */
    public void buyButtonHandler() {
        if (searchListView.getSelectionModel().isEmpty()) {
            searchMessageLabel.setText("No product has been selected");
        }
        else {
            Product product = searchListView.getSelectionModel().getSelectedItem();
            try {
                searchMessageLabel.setText(product.purchase());
                searchListView.refresh();
            } 
            catch (OutOfStockException e) {
                searchMessageLabel.setText("Product " + product.getSku() + " is out of stock");
            }
        }
    }

    /////////////////// REMOVE TOY TAB /////////////////////
    /** Displays all products in the database to the user in the Remove tab */
    public void searchAllToRemoveButtonHandler() {
        removeMessageLabel.setText("");
        removeListView.getItems().clear();

        for (Product item : productList) {
            removeListView.getItems().add(item);
        }
        removeButton.setDisable(false);
    }

    /** Searches product list from provided Sku number and displays it in the removeListView */
    public void searchToRemoveButtonHandler() {
        removeListView.getItems().clear();
        
        try {
            Product product = productManager.findFromSku(removeSerialField.getText());
            removeListView.getItems().add(product);
            removeMessageLabel.setText("");
        } 
        catch (SkuNotFoundException e) {
            removeMessageLabel.setText(e.getMessage());
        }
        removeButton.setDisable(false);
    }

    /**
     * Removes selected product of removeListView from the database
     * @throws SkuNotFoundException This exception won't be thrown ever
     */
    public void removeButtonHandler() throws SkuNotFoundException {
        if (removeListView.getSelectionModel().isEmpty()) {
            removeMessageLabel.setText("No product has been selected");
        }
        else {
            Product product = removeListView.getSelectionModel().getSelectedItem();
            productManager.removeProduct(product.getSku());
            removeMessageLabel.setText("Removed " + product.getSku() + ": \"" + product.getName() + "\" from inventory");
            removeListView.getItems().remove(product);
        }
    }

    /////////////////// ADD TOY TAB /////////////////////
    /** Handler for Figure menu item in Add toy tab */
    public void chooseNewfigureHandler() {
        newTypeMenu.setText("Figure");
        saveButton.setDisable(false);

        typeLabel.setText("Figure");
        typeLine.setOpacity(1);

        fieldOneLabel.setText("Available Count");
        fieldOneLabel.setOpacity(1);
        typeField1.setDisable(false);
        typeField1.setOpacity(1);

        fieldTwoLabel.setText("Classification");
        fieldTwoLabel.setOpacity(1);
        typeField2.setDisable(false);
        typeField2.setOpacity(1);
    }

    /** Handler for Puzzle menu item in Add toy tab*/
    public void chooseNewPuzzleHandler() {
        newTypeMenu.setText("Puzzle");
        saveButton.setDisable(false);

        typeLabel.setText("Puzzle");
        typeLine.setOpacity(1);

        fieldOneLabel.setText("Available Count");
        fieldOneLabel.setOpacity(1);
        typeField1.setDisable(false);
        typeField1.setOpacity(1);

        fieldTwoLabel.setText("Pieces");
        fieldTwoLabel.setOpacity(1);
        typeField2.setDisable(false);
        typeField2.setOpacity(1);
    }

    /** Handler for Board Game menu item in Add toy tab */
    public void chooseNewBoardGameHandler() {
        newTypeMenu.setText("Board Game");
        saveButton.setDisable(false);

        typeLabel.setText("Board Game");
        typeLine.setOpacity(1);

        fieldOneLabel.setText("Available Count");
        fieldOneLabel.setOpacity(1);
        typeField1.setDisable(false);
        typeField1.setOpacity(1);

        fieldTwoLabel.setText("Minimum Age");
        fieldTwoLabel.setOpacity(1);
        typeField2.setDisable(false);
        typeField2.setOpacity(1);
    }

    /** Handler for Video Game menu item in Add toy tab */
    public void chooseNewVideoGameHandler() {
        newTypeMenu.setText("Video Game");
        saveButton.setDisable(false);

        typeLabel.setText("Video Game");
        typeLine.setOpacity(1);

        fieldOneLabel.setText("Team");
        fieldOneLabel.setOpacity(1);
        typeField1.setDisable(false);
        typeField1.setOpacity(1);

        fieldTwoLabel.setOpacity(0);
        typeField2.setDisable(true);
        typeField2.setOpacity(0);
    }

    /** Saves product into productList (does not save it into file) */
    public void saveButtonHandler() throws FileNotFoundException {
        ArrayList<String> inputList = new ArrayList<>();
        String sku = newSkuField.getText(); // Assign variable to make life easier

        inputList.add(sku);
        inputList.add(newNameField.getText());
        inputList.add(priceField.getText());
        inputList.add(typeField1.getText());
        
        if (!typeField2.isDisable()) {
            inputList.add(typeField2.getText());
        }
        
        String[] args = inputList.toArray(new String[inputList.size()]);
        try {
            switch (newTypeMenu.getText()) { // See which product type user picked from menu and validate sku number
                case "Figure":
                if (!(sku.startsWith("0") || sku.startsWith("1"))) {
                    throw new InvalidInputException("Figure products must start with either 0 or 1");
                }
                break;
                case "Video Game":
                if (!(sku.startsWith("2") || sku.startsWith("3"))) {
                    throw new InvalidInputException("Video Game products must start with either 2 or 3");
                }
                break;
                case "Puzzle":
                if (!(sku.startsWith("4") || sku.startsWith("5") || sku.startsWith("6"))) {
                    throw new InvalidInputException("Puzzle products must start with either 4, 5, or 6");
                }
                break;
                case "Board Game":
                if (!(sku.startsWith("7") || sku.startsWith("8") || sku.startsWith("9"))) {
                    throw new InvalidInputException("Board Game products must start with either 7, 8, or 9");
                }
                break;
            }
            productManager.validation(args); // Validate all of the input values 

            // Add product once all validation is complete
            Product product = productManager.addProduct(args);
            addToyMessageLabel.setText("Added Product " + product.getSku() + ": \"" + product.getName() + "\" to inventory");
        } 
        catch (InvalidInputException e) {
            addToyMessageLabel.setText(e.getMessage());
        }
    }
}