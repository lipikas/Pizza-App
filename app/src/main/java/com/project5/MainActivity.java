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
    public static ArrayList<String> numberList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("lifecycle", "on create invoked");

        setContentView(R.layout.activity_main);
        Button btn6 = (Button) findViewById(R.id.cart);
        btn6.setEnabled(false);
        Button btn7 = (Button) findViewById(R.id.view);
        btn7.setEnabled(false);
    }

    public void enterButton(View view){
        TextView phoneNum = (TextView) findViewById(R.id.textInput);

        if(phoneNum.getText().length() != 10){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Invalid Phone Number");
            builder.setMessage("Please Enter a Valid 10-digit Phone Number!");
            builder.setCancelable(false);
            builder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            number = phoneNum.getText().toString();
            isEnabled(true);
            orders = new ArrayList<>();
            list = new ArrayList<>();
        }
    }

    public void changeButton(View view){
        isEnabled(false);
    }

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

    public void createDeluxeOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra(INTENT_MESSAGE, "deluxe");
        startActivity(intent);
    }

    public void createHawaiianOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra(INTENT_MESSAGE, "hawaiian");
        startActivity(intent);
    }

    public void createPepperoniOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra(INTENT_MESSAGE, "pepperoni");
        startActivity(intent);
    }

    public static void addPizza(Pizza newPizza){
        orders.add(newPizza);
        String pizzaType ="";
        String name = newPizza.getClass().getName();

        name = name.substring(13);
        System.out.println("pizzzza: " + name);
        if(name.compareTo("DeluxePizza") == 0) pizzaType = "Deluxe";
        else if(name.compareTo("HawaiianPizza") == 0)   pizzaType = "Hawaiian";
        else pizzaType = "Pepperoni";

        list.add(pizzaType +", " + newPizza.toString());

//        for(int i = 0; i < orders.size(); i++){
//            System.out.println(orders.get(i).toString());
//        }
//        System.out.println("size: " + orders.size());
    }

    /**
     * formats the amount
     * @param amount is the amount given by user
     * @return string format of amount
     */
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume invoked");
        System.out.println("orders: \n");
        for(Pizza o : orders){
            Log.d("Current Orders", o.toString());
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause invoked");
    }
    public void orderCart1(View view){
        Intent intent = new Intent(this, OrderCart.class);
        intent.putExtra("number", number);
        intent.putExtra("list", list);
        startActivity(intent);
    }
    public void orderView1(View view){
        Intent intent = new Intent(this, OrderView.class);
        intent.putExtra("numberList", numberList);
        startActivity(intent);
    }
    public static void setPizza(ArrayList<Pizza> order){
        orders = order;
        list = new ArrayList<>();
    }
    public static void addOrder(Order order){
        orderList.orderAdd(order);
        if(!numberList.contains(number)) numberList.add(number);
//        List<Order> order1 = orderList.getOrders();
//        List<Pizza> pizza = order1.get(0).getPizza();
//        System.out.println("Hiiii");
//        for(int i=0; i < pizza.size(); i++){
//            System.out.println(pizza.get(i).toString());
//        }
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
     * @param pizza is pizza list
     * @return order total
     */
    public static double getTotal(){
        double sum = 0;
        for(int i = 0; i < orders.size(); i++){
            sum += orders.get(i).price();
        }

        return sum;
    }

    public static ArrayList<String> printList(String phone){
        String amountTotal = "";
        double amount = 0;
        List<Order> list1 = orderList.getOrders();
        List<Pizza> pizza = new ArrayList<>();
        int i = 0;
        for(i = 0; i < list1.size(); i++) {
            if (list1.get(i).getNumber().compareTo(phone) == 0) {
                pizza = list1.get(i).getPizza();
                break;
            }
        }
        ArrayList<String> result2 = new ArrayList<>();

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
            amountTotal = formatAmount(amount);
            list1.get(i).setTotal(Double.parseDouble(formatAmount(amount)));
        }
        result2.add(""+amount);
        return result2;
    }

    public static void removeOrderList(String result){
        List<Order> order = orderList.getOrders();
        for(int i = 0; i < order.size(); i++){
            if(order.get(i).getNumber().compareTo(result)==0){
                order.remove(order.get(i));
                break;
            }
        }
        numberList.remove(result);
    }



}