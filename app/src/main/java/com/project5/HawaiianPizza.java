package com.project5;

/**
 *
 * Creates peperroni pizza
 * @author Lipika, Kenisha
 *
 */
public class HawaiianPizza extends Pizza{
    private static final int NUM_FREE_TOPPINGS = 2;
    private static final double BASE_PRICE = 10.99;

    /**
     * Constructor to create Hawaiian Pizza object
     */
    public HawaiianPizza(){
        size = Size.Small;
        toppings.add(Toppings.Pineapple);
        toppings.add(Toppings.Chicken);
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
