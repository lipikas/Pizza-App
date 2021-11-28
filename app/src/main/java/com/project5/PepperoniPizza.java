package com.project5;

/**
 * Creates peperroni pizza
 * @author Lipika, Kenisha
 */
public class PepperoniPizza extends Pizza{
    private static final int NUM_FREE_TOPPINGS = 1;
    private static final double BASE_PRICE = 8.99;

    /**
     * Constructor to create Deluxe Pizza object
     */
    public PepperoniPizza(){
        size = Size.Small;
        toppings.add(Toppings.Pepperoni);
    }

    /**
     * Calculates price for the pizza
     * @return - price of the pizza
     */
    public double price(){
        double price = BASE_PRICE;
        int numOfToppings = toppings.size();
        if(numOfToppings > NUM_FREE_TOPPINGS){
            price += (numOfToppings - NUM_FREE_TOPPINGS) * FEE_PER_EXTRA_TOPPING;
        }
        if(size != Size.Small){
            price += (size == Size.Medium) ? FEE_PER_SIZE_INCREASE : (2 * FEE_PER_SIZE_INCREASE);
        }
        return price;
    }
}
