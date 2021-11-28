package com.project5;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates Order instance of list of Pizzas
 * @author Lipika, Kenisha
 */
public class Order {
    private List<Pizza> pizza; // pizza list
    private String number; // customer phone number
    private double total;

    /**
     * Instantiates Order classes and updates customer’s phone number, the list of pizzas
     * @param pizza is pizza list
     * @param number is number
     * @param total is order total
     */
    public Order(List <Pizza> pizza, String number, double total){
        this.pizza = pizza;
        this.number = number;
        this.total = total; // subtotal before tax
    }

    /**
     * Gets pizza list
     * @return pizza list
     */
    public List<Pizza> getPizza(){
        return this.pizza;
    }

    /**
     * Sets Pizza List
     * @param pizza is pizza list
     */
    public void setPizza(List<Pizza> pizza){
        this.pizza = pizza;
    }
    /**
     * Gets customer phone number
     * @return customer phone number
     */
    public String getNumber(){
        return this.number;
    }
    /**
     * Gets order subtotal
     * @return order subtotal
     */
    public double getTotal(){
        return this.total;
    }

    /**
     * Sets order total
     * @param total
     */
    public void setTotal(double total){
        this.total = total;
    }

    /**
     * Returns String
     * @return String
     */
    @Override
    public String toString(){
        return pizza.toString() + ", $" + this.total;
    }
}
