package com.project5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    public static final String INTENT_MESSAGE = "com.project5.MESSAGE";
    private static ArrayList<Pizza> orders = new ArrayList<>();
    private static StoreOrders orderList = new StoreOrders();
    public static String number = "";
    public static ArrayList<String> list = new ArrayList<>();

    //Initializes Main Page Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("lifecycle", "onCreate() invoked");
        setContentView(R.layout.activity_main);
        Button btn6 = (Button) findViewById(R.id.cart);
        btn6.setEnabled(false);
        Button btn7 = (Button) findViewById(R.id.view);
        btn7.setEnabled(false);
    }

    //Checks if customer phone number is valid on click of the Enter button
    public void enterButton(View view){
        TextView phoneNum = (TextView) findViewById(R.id.textInput);
        if(phoneNum.getText().length() != 10){
            createAlert("Please Enter a Valid 10-digit Phone Number!", "Invalid Phone Number");
        }
        else isEnabled(true);
    }

    //Enables changing the customer phone number
    public void changeButton(View view){
        isEnabled(false);
    }

    //Enables or disables fields depending on the boolean parameter
    public void isEnabled(boolean b){
        Button btn1 = (Button) findViewById(R.id.deluxe);
        btn1.setEnabled(b);
        Button btn2 = (Button) findViewById(R.id.hawaiian);
        btn2.setEnabled(b);
        Button btn3 = (Button) findViewById(R.id.pepperoni);
        btn3.setEnabled(b);
        Button btn4 = (Button) findViewById(R.id.change);
        btn4.setEnabled(b);
        Button btn5 = (Button) findViewById(R.id.enter);
        btn5.setEnabled(!b);
        TextView phoneNum = (TextView) findViewById(R.id.textInput);
        phoneNum.setEnabled(!b);
        Button btn6 = (Button) findViewById(R.id.cart);
        btn6.setEnabled(b);
        Button btn7 = (Button) findViewById(R.id.view);
        btn7.setEnabled(b);
    }

    //starts the Current Order activity in Deluxe Pizza type
    public void createDeluxeOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra(INTENT_MESSAGE, "deluxe");
        startActivity(intent);
    }

    //starts the Current Order activity in Hawaiian Pizza type
    public void createHawaiianOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra(INTENT_MESSAGE, "hawaiian");
        startActivity(intent);
    }

    //starts the Current Order activity in Pepperoni Pizza type
    public void createPepperoniOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra(INTENT_MESSAGE, "pepperoni");
        startActivity(intent);
    }

    //Adds new pizza to the order
    public static void addPizza(Pizza newPizza){
        orders.add(newPizza);
        String pizzaType ="";
        String name = newPizza.getClass().getName();
        name = name.substring(10);
        if(name.compareTo("DeluxePizza") == 0) pizzaType = "Deluxe";
        else if(name.compareTo("HawaiianPizza") == 0)   pizzaType = "Hawaiian";
        else pizzaType = "Pepperoni";
        list.add(pizzaType +", " + newPizza.toString());
        Log.i("Order Added", "Pizza added to customer order")
    }

    //Formats the price amount to 2 decimal places
    public static String formatAmount(double amount){
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

    //States what to do on resuming the function
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("lifecycle","onResume() invoked");
        System.out.println("orders: \n");
        for(Pizza o : orders){
            Log.i("Current Orders", o.toString());
        }
    }

    //States what to do when pausing the function
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("lifecycle","onPause() invoked");
    }

    //Creates alert with the message and title passed as params
    public void createAlert(String message, String title){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message);
        alert.setTitle(title);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }


    public void orderCart1(View view){
//        PizzaApplication app = (PizzaApplication) getApplicationContext();
//        app.setList(list);
        Intent intent = new Intent(this, OrderCart2.class);
        intent.putExtra("number", number);
//        intent.putExtra("order", orders);
        intent.putExtra("list", list);
        startActivity(intent);
    }
    public void orderView1(View view){
        Intent intent = new Intent(this, OrderView.class);
//        intent.putExtra("orderList", orderList);
        startActivity(intent);
    }
    public static void setPizza(ArrayList<Pizza> order){
        orders = order;
        list = new ArrayList<>();
    }
    public static void addOrder(Order order){
        orderList.orderAdd(order);
    }
    public static void removePizza(Pizza pizza, boolean val, List<Toppings> toppingList, double sum, Size size){
        for (int j = 0; j < orders.size(); j++){
            if(orders.get(j).getClass().getName().compareTo(pizza.getClass().getName()) == 0 && orders.get(j).price() == sum && orders.get(j).size.toString().compareTo(size.toString()) == 0 && orders.get(j).toppings.size() == toppingList.size()){
                int y = 0;
                for(y = 0; y < toppingList.size(); y++){
                    if(orders.get(j).toppings.get(y).toString().compareTo(toppingList.get(y).toString())!=0){
                        val = true;
                        break;
                    }
                }
                if(y == toppingList.size() && val == false) {
                    orders.remove(orders.get(j));
                    break;
                }
                val = false;
            }
        }
    }

    public static void removePizzaList(String pizza){
        list.remove(pizza);
    }
    public static ArrayList<Pizza> getPizzaList(){
        return orders;
    }

    /**
     * gets order total
     * @return order total
     */
    public static double getTotal(){
        double sum = 0;
        for(int i = 0; i < orders.size(); i++){
            sum += orders.get(i).price();
        }

        return sum;
    }




}