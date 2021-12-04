package com.project5;

public class DeluxePizza extends Pizza{
    private static final int NUM_FREE_TOPPINGS = 5;
    private static final double BASE_PRICE = 12.99;

    /**
     * Constructor to create Deluxe Pizza object
     */
    public DeluxePizza(){
        size = Size.Small;
        toppings.add(Toppings.Sausage);
        toppings.add(Toppings.GreenPepper);
        toppings.add(Toppings.Onion);
        toppings.add(Toppings.Pepperoni);
        toppings.add(Toppings.Mushroom);
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
