package com.project5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays current order
 * @author Lipika, Kenisha
 */
public class CurrentOrderController {

    private Pizza currPizza;

    @FXML
    private ComboBox<Size> comboBox;

    @FXML
    private MainMenuController mainController;

    @FXML
    private ImageView pizzaImage;

    @FXML
    private Label pizzaTypeLabel;

    @FXML
    private ListView<Toppings> allToppings;

    @FXML
    private ListView<Toppings> chosenToppings;

    @FXML
    private TextField priceTextField;

    /**
     * Initializing the current order screen
     *
     * @param pizzaType - states the type of pizza
     * @param imageURL - reference to the image
     */
    @FXML
    public void initialize(String pizzaType, Image imageURL){
        //set Image
        pizzaImage.setImage(imageURL);
        //set Label
        pizzaTypeLabel.setText(pizzaType + " Pizza");
        //set combo box for Size
        comboBox.getItems().addAll(Size.values());
        comboBox.setValue(Size.Small);
        //create instance of Pizza
        currPizza = PizzaMaker.createPizza(pizzaType);
        //set preset Toppings list view
        chosenToppings.setItems(FXCollections.observableArrayList(currPizza.toppings));
        //set All Topping list view
        List<Toppings> remainingToppings = new ArrayList<>();
        for(Toppings t : Toppings.values()){
            if(!currPizza.toppings.contains(t)) remainingToppings.add(t);
        }
        allToppings.setItems(FXCollections.observableArrayList(remainingToppings));
        //calculate Price for pizza
        updatePrice();
    }


    /**
     * Adds a topping to the current pizza
     */
    @FXML
    public void addTopping() {
        if(chosenToppings.getItems().size() == Pizza.MAX_TOPPINGS){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Maximum toppings of 7 reached.");
            a.show();
            return;
        }
        Toppings selectedTopping = allToppings.getSelectionModel().getSelectedItem();
        if(selectedTopping == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please select a topping to add.");
            a.show();
            return;
        }
        allToppings.getItems().remove(selectedTopping);
        chosenToppings.getItems().add(selectedTopping);
        currPizza.toppings.add(selectedTopping);
        updatePrice();
    }


    /**
     * Removes a topping from the current pizza
     */
    @FXML
    public void removeTopping() {
        if(chosenToppings.getItems().size() == 0){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("All toppings removed.");
            a.show();
            return;
        }
        Toppings selectedTopping = chosenToppings.getSelectionModel().getSelectedItem();
        if(selectedTopping == null){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Please select a topping to remove.");
            a.show();
            return;
        }
        chosenToppings.getItems().remove(selectedTopping);
        currPizza.toppings.remove(selectedTopping);
        allToppings.getItems().add(selectedTopping);
        updatePrice();
    }


    /**
     * Changes the size of the pizza
     */
    @FXML
    public void changeSize() {
        currPizza.size = comboBox.getValue();
        updatePrice();
    }


    /**
     * Updates the price of currPizza when any changes are made
     */
    @FXML
    public void updatePrice(){
        //if(mainController == null)
        priceTextField.setText(mainController.formatAmount(currPizza.price()));
    }


    /**
     * Gets the reference of the Main Menu
     *
     * @param controller - reference to the Main Menu Controller
     */
    @FXML
    public void setMainController(MainMenuController controller){
        mainController = controller;
    }

    /**
     * Adds an order to the list of orders
     */
    @FXML
    public void addOrder() {
        mainController.addPizzaToOrder(currPizza);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Order added!");
        a.show();
    }
}
