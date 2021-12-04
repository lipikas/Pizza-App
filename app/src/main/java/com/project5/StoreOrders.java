package com.project5;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Prints and cancel the Store Orders
 * @author Lipika, Kenisha
 */
public class StoreOrders {
    private List<Order> orders;

    /**
     * Instantiates orders List
     */
    public StoreOrders() {
        orders = new ArrayList<>();
    }

    /**
     * Adds order to list
     * @param order is given order
     */
    public void orderAdd(Order order){ //remove order
        int i = 0;

        for (i = 0; i < orders.size(); i++){
            if (orders.get(i).getNumber().compareTo(order.getNumber()) == 0){
                break;
            }
        }
        if(i!=orders.size()){
            List<Pizza> pizza = orders.get(i).getPizza();
            for (int j = 0; j < order.getPizza().size(); j++){
                pizza.add(order.getPizza().get(i));
            }
            return;
        }
        orders.add(order);
    }

    /**
     * Save the store orders to an external text file
     * @param filename is filename
     * @param path is pathname
     */
    public void export(String filename, String path){
        File file = null;
        try {
            file = new File(path+ "/" +filename);
            if (!file.createNewFile()) {
                return;
            }

            PrintWriter pw = new PrintWriter(file);
            for(int i = 0; i < orders.size(); i++){
                pw.println(orders.get(i).getNumber() + ":");
                List<Pizza> pizza = orders.get(i).getPizza();
                for(int j = 0; j < pizza.size(); j++){
                    String pizzaType ="";
                    String name = pizza.get(j).getClass().getName();
                    name = name.substring(10);
                    if(name.compareTo("DeluxePizza") == 0) pizzaType = "Deluxe";
                    else if(name.compareTo("HawaiianPizza") == 0)   pizzaType = "Hawaiian";
                    else pizzaType = "Pepperoni";
                    pw.println(pizzaType + ", " + pizza.get(i).toString());
                }
                pw.println("Order total: $" + orders.get(i).getTotal());
                pw.println();
            }
            pw.close();
        } catch (IOException e) {
            return;
        }
    }

    /**
     * Gets order list
     * @return order list
     */
    public List<Order> getOrders(){
        return this.orders;
    }
}
