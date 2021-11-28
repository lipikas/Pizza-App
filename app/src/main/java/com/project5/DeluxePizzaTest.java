package com.project5;

import org.junit.Test;
import static org.junit.Assert.*;

class DeluxePizzaTest {

    @Test
    void price() {
        MainMenuController mainMenuController = new MainMenuController();

        //TestCase#1 - delux pizza with standard toppings and Small size
        Pizza pizza = PizzaMaker.createPizza("Deluxe");
        pizza.size = Size.Small;
        assertEquals(12.99, pizza.price(), 0.0);

        //TestCase#2 - delux Medium pizza with one of standard toppings removed
        Pizza pizza2 = PizzaMaker.createPizza("Deluxe");
        pizza2.removeTopping(Toppings.valueOf("Sausage"));
        pizza2.size = Size.Medium;
        assertEquals(14.99, pizza2.price(), 0.0);

        // TestCase#3 - delux Small pizza with one extra topping added
        Pizza pizza3 = PizzaMaker.createPizza("Deluxe");
        pizza3.addTopping(Toppings.GreenPepper);
        pizza3.size = Size.Small;
        assertEquals(14.48, pizza3.price(), 0.0);

        // TestCase #4 - all toppings removed and pizza is small size
        Pizza pizza4 = PizzaMaker.createPizza("Deluxe");
        pizza4.removeTopping(Toppings.Pepperoni);
        pizza4.removeTopping(Toppings.Sausage);
        pizza4.removeTopping(Toppings.Sausage);
        pizza4.removeTopping(Toppings.GreenPepper);
        pizza4.removeTopping(Toppings.Onion);
        pizza4.removeTopping(Toppings.Pepperoni);
        pizza4.removeTopping(Toppings.Mushroom);
        pizza4.size = Size.Small;
        assertEquals(12.99, pizza4.price(), 0.0);


        // TestCase#5 - added topping until maxToppings reached for large pizza
        Pizza pizza5 = PizzaMaker.createPizza("Deluxe");
        pizza5.addTopping(Toppings.Chicken);
        pizza5.addTopping(Toppings.BlackOlive);
        pizza5.size = Size.Large;
        assertEquals(19.97, pizza5.price(), 0.0);


        //TestCase #6 delux pizza with standard toppings and Medium size
        Pizza pizza6 = PizzaMaker.createPizza("Deluxe");
        pizza6.size = Size.Medium;
        assertEquals(14.99, pizza6.price(), 0.0);


        //TestCase #7 delux pizza with standard toppings and Medium size
        Pizza pizza7 = PizzaMaker.createPizza("Deluxe");
        pizza7.size = Size.Large;
        assertEquals(16.99, Double.parseDouble(mainMenuController.formatAmount(pizza7.price())), 0.0);

    }

//    public static void main(String[] args) {
//        DeluxePizzaTest list = new DeluxePizzaTest();
//        list.price();
//    }
}