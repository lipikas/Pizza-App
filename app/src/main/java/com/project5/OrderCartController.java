package com.project5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Displays Order Car
 * @author Lipika, Kenisha
 */
public class OrderCartController {
    @FXML
    private MainMenuController mainController;

    @FXML
    private ListView<String> list;
    private ObservableList<String> names;

    @FXML
    private TextField phoneOutput;

    @FXML
    private TextField subTotal;

    @FXML
    private TextField tax;

    @FXML
    private TextField total;

    private String customerNo;
    private List <String> arr = new ArrayList<>();
    private static double subtotal1;
    private static double tax1;
    private static double total1;

    /**
     * Initializes gui
     */
    public void initialize(){
        if(mainController!= null && mainController.getPizzaList().size() > 0){
            customerNo = mainController.getPhoneNumber();
            List<Pizza> listPizza = mainController.getPizzaList();
            List<String> result = new ArrayList<>();
            for(int i = 0; i < listPizza.size(); i++){
                String pizzaType ="";
                String name = listPizza.get(i).getClass().getName();
                name = name.substring(10);
                if(name.compareTo("DeluxePizza") == 0) pizzaType = "Deluxe";
                else if(name.compareTo("HawaiianPizza") == 0)   pizzaType = "Hawaiian";
                else pizzaType = "Pepperoni";

                result.add(pizzaType +", " + listPizza.get(i).toString());
            }
            arr = result;
            names = FXCollections.observableArrayList(arr);
            list.setItems(names);
            phoneOutput.setText(customerNo);
            subtotal1 = getTotal(mainController.getPizzaList());
            subtotal1 = Double.parseDouble(mainController.formatAmount(subtotal1));
            subTotal.setText("$ " + subtotal1);
            tax1 = subtotal1*(6.625/100);
            tax1 = Double.parseDouble(mainController.formatAmount(tax1));
            tax.setText("$ " + tax1);
            total.setText("$ " +mainController.formatAmount(tax1 +  subtotal1));
            total1 = tax1 + subtotal1;
        }
        else{
            phoneOutput.setText("");
            subTotal.setText("");
            total.setText("");
            tax.setText("");
            names = FXCollections.observableArrayList(new ArrayList<String>());
            list.setItems(names);
        }
    }

    /**
     * gets order total
     * @param pizza is pizza list
     * @return order total
     */
    public double getTotal(List<Pizza> pizza){
        double sum = 0;
        for(int i = 0; i < pizza.size(); i++){
            sum += pizza.get(i).price();
        }

        return sum;
    }

    /**
     * Adds order to Order List
     * @param event is event
     */
    @FXML
    void addOrder(ActionEvent event) {
        if(mainController!= null && mainController.getPizzaList().size() > 0){
            mainController.getList().orderAdd(new Order(mainController.getPizzaList(), customerNo, total1));
            mainController.setPizzaList(new ArrayList<>());
            initialize();
        }
    }

    /**
     * removed order from order List
     * @param event is event
     */
    @FXML
    void removeOrder(ActionEvent event) {
        if(mainController.getPizzaList().size() > 0){
         String result = list.getSelectionModel().getSelectedItem();
         if(result == null){
             Alert a = new Alert(Alert.AlertType.ERROR);
             a.setContentText("No pizza selected.");
             a.show();
             return;
         }

         StringTokenizer token = new StringTokenizer(result, ", ");
         String pizzaType = token.nextToken();
         List<Toppings> toppingList = new ArrayList<>();
         String top = "";
         while(token.hasMoreTokens()){
             top = token.nextToken();
             try{
                 Toppings.valueOf(top);
             } catch(IllegalArgumentException e){
                 break;
             }
             toppingList.add(Toppings.valueOf(top));
         }
         Size size = Size.valueOf(top);
         StringTokenizer amount = new StringTokenizer(token.nextToken(), "$");
         double sum = Double.parseDouble(amount.nextToken());

         Pizza pizza = PizzaMaker.createPizza(pizzaType);
         pizza.price = sum;
         pizza.toppings = toppingList;
         List<Pizza> pizza1 = mainController.getPizzaList();
         remove(pizza, pizza1, false, toppingList, sum, size);

        if(pizza1.size() == 0) mainController.setPizzaList(new ArrayList<>());
        initialize();
        }
    }
    /**
     * Gets the reference of the Main Menu
     *
     * @param controller - reference to the Main Menu Controller
     */
    public void setMainController(MainMenuController controller){
        mainController = controller;
    }

    /**
     * Removes the pizza from pizza List
     * @param pizza is pizza selected by user
     * @param pizza1 is pizza list
     * @param val is boolean val
     * @param toppingList is toppingList
     * @param sum is order total
     * @param size is pizza size
     */
    private void remove(Pizza pizza, List<Pizza> pizza1, boolean val, List<Toppings> toppingList, double sum, Size size){
        for (int j = 0; j < pizza1.size(); j++){
            if(pizza1.get(j).getClass().getName().compareTo(pizza.getClass().getName()) == 0 && pizza1.get(j).price() == sum && pizza1.get(j).size.toString().compareTo(size.toString()) == 0 && pizza1.get(j).toppings.size() == toppingList.size()){
                int y = 0;
                for(y = 0; y < toppingList.size(); y++){
                    if(pizza1.get(j).toppings.get(y).toString().compareTo(toppingList.get(y).toString())!=0){
                        val = true;
                        break;
                    }
                }
                if(y == toppingList.size() && val == false) {
                    pizza1.remove(pizza1.get(j));
                    break;
                }
                val = false;
            }
        }
    }
}
