package com.project5;
/**
 * Creates PizzaMaker
 * @author Lipika, Kenisha
 */
public class PizzaMaker {
    /**
     * Creates a Pizza object based on Pizza flavour
     *
     * @param flavor - Deluxe, Hawaiian, or Pepperoni
     * @return Pizza object
     */
    public static Pizza createPizza(String flavor){
        Pizza currPizza = null;
        switch(flavor){
            case("Deluxe"): currPizza = new DeluxePizza();
                            break;
            case("Hawaiian"): currPizza = new HawaiianPizza();
                              break;
            case("Pepperoni"): currPizza = new PepperoniPizza();
                               break;
        }
        return currPizza;
    }
}
