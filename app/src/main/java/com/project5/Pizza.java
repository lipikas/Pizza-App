package com.project5;
import java.util.ArrayList;
import java.util.List;
/**
 * Features diff pizza methods
 * @author Lipika, Kenisha
 */
public abstract class Pizza {
    protected List<Toppings> toppings = new ArrayList<>();
    protected Size size;
    protected double price;
    protected static final int MAX_TOPPINGS = 7;
    protected static final double FEE_PER_SIZE_INCREASE = 2.00;
    protected static final double FEE_PER_EXTRA_TOPPING = 1.49;

    /**
     * Adds a topping to the list of toppings
     * @param toppings - topping to be added
     * @return - true if added, false otherwise
     */
    public boolean addTopping(Toppings toppings){
        if(this.toppings.size() == MAX_TOPPINGS)    return false;
        this.toppings.add(toppings);
        return true;
    }

    /**
     * Remove a topping form the topping list
     * @param toppings - topping to be removed
     * @return - true if added, false otherwise
     */
    public boolean removeTopping(Toppings toppings){
        if(this.toppings.contains(toppings)){
            this.toppings.remove(toppings);
            return true;
        }
        return false;
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

    /**
     * Abstract method that calculates the price of a pizza
     * @return the price
     */
    public abstract double price();

    /**
     *
     * @return String with pizza and toppings
     */
    public String toString(){
        String topping = "";
        for (int i = 0; i < toppings.size(); i++){
            topping += toppings.get(i).toString() + ", ";
        }
        return topping + this.size.toString() + ", $" + price();
    }

}
