package com.project5;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Displays Main Menu
 * @author Lipika, Kenisha
 */
public class MainMenuController {

    private List<Pizza> orders = new ArrayList<>();
    private StoreOrders list = new StoreOrders();

    @FXML
    private Button changeButton;

    @FXML
    private TextField customerPhoneNumber;

    @FXML
    private Button deluxeButton;

    @FXML
    private ImageView deluxeImg;

    @FXML
    private Button enterButton;

    @FXML
    private Button hawaiianButton;

    @FXML
    private ImageView hawaiianImg;

    @FXML
    private Button pepperoniButton;

    @FXML
    private ImageView pepperoniImg;

    @FXML
    private String pizzaType;
    @FXML
    private Button cart;
    @FXML
    private Button storeOrder;
    @FXML
    private ImageView storeList;

    @FXML
    private ImageView customerOrder1;

    /**
     * Initializes the images for ordercart and order-view fmxls
     */
    @FXML
    public void initialize(){
        Image im = new Image("https://media.istockphoto.com/vectors/shopping-cart-icon-isolated-on-white-background-vector-id1206806317?k=20&m=1206806317&s=612x612&w=0&h=waK8qOHV2Fgz2ntEWHWBQtXpNDAQ_wdhd4tkTUz6tfE=");
        storeList.setImage(new Image("https://www.pinclipart.com/picdir/middle/336-3360441_how-to-buy-a-domain-name-online-shop.png"));
        customerOrder1.setImage(im);
    }
    /**
     * Lets the user enter a new customer number
     */
    @FXML
    void changeCustomerNumber() {
        customerPhoneNumber.setText("");
        orders = new ArrayList<>();
        changeButton.setDisable(true);
        disableOnEnter(false);
    }

    /**
     * Disables the customer number text field hence preventing the user from changing customer number during the order
     *
     * @param b - boolean variable true - disable and false - enable
     */
    @FXML
    void disableOnEnter(boolean b){
        enterButton.setDisable(b);
        customerPhoneNumber.setDisable(b);
        double opacity = b ? 1 : 0.5;
        deluxeImg.setOpacity(opacity);
        deluxeButton.setDisable(!b);
        hawaiianImg.setOpacity(opacity);
        hawaiianButton.setDisable(!b);
        pepperoniImg.setOpacity(opacity);
        pepperoniButton.setDisable(!b);
        storeList.setDisable(!b);
        storeOrder.setDisable(!b);
        cart.setDisable(!b);
        customerOrder1.setDisable(!b);
    }

    /**
     * Lets the user enter a customer number
     */
    @FXML
    void enterCustomerNumber() {
        if(customerPhoneNumber.getText().length() != 10){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Invalid Phone Number. Enter a 10-digit valid phone number.");
            a.show();
            return;
        }
        disableOnEnter(true);
        changeButton.setDisable(false);
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText("Start the order - Pick the pizza flavour.");
        a.show();
    }

    /**
     * Calls the current order GUI to order a Deluxe Pizza
     * @throws IOException throws exception
     */
    @FXML
    void orderDeluxePizza() throws IOException {
        pizzaType = "Deluxe";
        orderPizza(deluxeImg.getImage());
    }

    /**
     *  Calls the current order GUI to order a Hawaiian Pizza
     * @throws IOException throws exception
     */
    @FXML
    void orderHawaiianPizza() throws IOException {
        pizzaType = "Hawaiian";
        orderPizza(hawaiianImg.getImage());
    }

    /**
     * Calls the current order GUI to order a Pepperoni Pizza
     * @throws IOException throws exception
     */
    @FXML
    void orderPepperoniPizza() throws IOException {
        pizzaType = "Pepperoni";
        orderPizza(pepperoniImg.getImage());
    }

    /**
     * Sets the scene and stage to call the current order GUI
     *
     * @throws IOException throws exception
     * @param imageURL - Image object of the specific pizza
     */
    @FXML
    void orderPizza(Image imageURL) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("current-order.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 610, 578);
        CurrentOrderController currentOrder = fxmlLoader.getController();
        currentOrder.setMainController(this);
        currentOrder.initialize(pizzaType, imageURL);
        Stage stage = new Stage();
        stage.setTitle("Current Order");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds pizza to pizza list
     * @param newPizza is pizza
     */
    @FXML
    void addPizzaToOrder(Pizza newPizza){
        orders.add(newPizza);
    }

    /**
     * Displays all the orders placed in the store.
     *
     * @param event is event
     * @throws IOException if fxml not launched
     */
    @FXML
    void orderPlaced(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("order-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 610, 578);
        StoreOrdersController orderList1 = fxmlLoader.getController();
        orderList1.setMainController(this);
        orderList1.initialize();

        Stage stage = new Stage();
        stage.setTitle("Placed Order List");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays all orders of current customer.
     * @param event event
     * @throws IOException if fxml not launched
     */
    @FXML
    void orderCart(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cart-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 610, 578);
        OrderCartController currentOrder = fxmlLoader.getController();
        currentOrder.setMainController(this);
        currentOrder.initialize();

        Stage stage = new Stage();
        stage.setTitle("Order Cart");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Gets String version of phone number
     * @return String version of phone number
     */
    public String getPhoneNumber() {
        return customerPhoneNumber.getText();
    }

    /**
     * Gets StoreOrder Instance
     * @return StoreOrder Instance
     */
    public StoreOrders getList(){
        return list;
    }

    /**
     * Gets Pizza List
     * @return Pizza List
     */
    public List<Pizza> getPizzaList(){
        return this.orders;
    }

    /**
     * Sets pizza list
     * @param list is pizza list
     */
    public void setPizzaList(List<Pizza> list) {
        orders = list;
    }

    /**
     * formats the amount
     * @param amount is the amount given by user
     * @return string format of amount
     */
    public String formatAmount(double amount){
        int amountInInt = (int)amount;
        String intPart = Integer.toString(amountInInt);
        String formattedAmount = "";
        int len = intPart.length();
        if(len <= 3) {
            formattedAmount = intPart;
        }
        else if(len % 3 == 0) {
            int beg = 0, end = 3;
            for(int i = 1; i < len/3; i++){
                formattedAmount += intPart.substring(beg, end) + ",";
                beg = end;
                end += 3;
            }
            formattedAmount += intPart.substring(beg, len);
        }
        else{
            int remainder = len % 3;
            formattedAmount = intPart.substring(0, remainder) + ",";
            int beg = remainder, end = beg + 3;
            for(int i = 1; i < len/3; i++){
                formattedAmount += intPart.substring(beg, end) + ",";
                beg = end;
                end += 3;
            }
            formattedAmount += intPart.substring(beg, len);
        }
        String amountAsString = Double.toString(amount);
        amountAsString += "0";
        int indexOfDecimalPoint = amountAsString.indexOf(".");
        int endOfDecimal = Math.min(indexOfDecimalPoint + 3, amountAsString.length());
        formattedAmount += "." + amountAsString.substring(indexOfDecimalPoint + 1, endOfDecimal);
        return formattedAmount;
    }
}
