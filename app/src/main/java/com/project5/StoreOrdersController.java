package com.project5;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * View All orders, can remove order, and export orders
 * @author Lipika
 */
public class StoreOrdersController {

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private MainMenuController mainController;

    @FXML
    private ListView<String> list;

    @FXML
    private TextField total1;

    private ObservableList<String> names;
    private ObservableList<String> names2;

    /**
     * Initializes the listview and combobox
     */
    @FXML
    public void initialize(){
        if(mainController!= null){
            List<String> number = new ArrayList<>();
            StoreOrders orders = mainController.getList();
            List<Order> orderList = orders.getOrders();
            for(int i = 0; i < orderList.size(); i++){
                number.add(orderList.get(i).getNumber());
            }

            names = FXCollections.observableArrayList(number);
            comboBox.setItems(names);
            String result = comboBox.getSelectionModel().getSelectedItem();
            if(result == null && names.size() > 0) comboBox.setValue(names.get(0));
            if(names != null) result = comboBox.getSelectionModel().getSelectedItem();

            List<Pizza> pizza = new ArrayList<>();
            int i = 0;
            for(i = 0; i < orderList.size(); i++) {
                if (orderList.get(i).getNumber().compareTo(result) == 0) {
                    pizza = orderList.get(i).getPizza();
                    break;
                }
            }
            List<String> result2 = new ArrayList<>();
            String amountTotal = "";
            double amount = 0;

            for(int j = 0; j < pizza.size(); j++){
                String pizzaType ="";
                String name = pizza.get(j).getClass().getName();
                name = name.substring(10);
                if(name.compareTo("DeluxePizza") == 0) pizzaType = "Deluxe";
                else if(name.compareTo("HawaiianPizza") == 0)   pizzaType = "Hawaiian";
                else pizzaType = "Pepperoni";

                result2.add(pizzaType +", " + pizza.get(j).toString());
                amount += pizza.get(j).price();
            }

            if(amount > 0) {
                amount += (amount*6.625/100);
                amountTotal = mainController.formatAmount(amount);
                orderList.get(i).setTotal(Double.parseDouble(mainController.formatAmount(amount)));
            }
            names2 = FXCollections.observableArrayList(result2);
            list.setItems(names2);
            total1.setText(amountTotal);
        }

    }

    /**
     * Exports list view details into txt
     * @param event is event
     */
    @FXML
    void export(ActionEvent event) {
        if(mainController!= null && mainController.getList().getOrders().size() > 0){
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fileChooser.showSaveDialog(stage);
            if (file!= null){
                String name = file.getName();
                System.out.println(name);
                String dir = file.getAbsoluteFile().getParent();
                mainController.getList().export(name, dir);
            }
        }
    }

    /**
     * Remove the list details for assoicated phone no
     * @param event is event
     */
    @FXML
    void remove(ActionEvent event) {
        names.remove(list.getSelectionModel().getSelectedItem());
        list.setItems(names);

        if(mainController != null && mainController.getList().getOrders().size() > 0){
            String result = comboBox.getSelectionModel().getSelectedItem();
            List<Order> orderList = mainController.getList().getOrders();
            for(int i = 0; i < orderList.size(); i++){
                if(orderList.get(i).getNumber().compareTo(result)==0){
                    orderList.remove(orderList.get(i));
                    initialize();
                    break;
                }
            }
        }
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
}