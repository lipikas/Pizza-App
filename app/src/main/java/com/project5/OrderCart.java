package com.project5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class OrderCart extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    private TextView text;
    private TextView tax;
    private TextView total;
    private TextView subtotal;
    public static ListView pizzaView;
    public static ArrayAdapter<String> adapter = null;
    public static String selectedPizza = null;
    public static ArrayList<String> pizzaList = new ArrayList<>();
    public static String number = "";
    private static double subtotal1;
    private static double tax1;
    private static double total1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        text = findViewById(R.id.phone);
        tax = findViewById(R.id.taxAmount);
        total = findViewById(R.id.totalAmount);
        subtotal = findViewById(R.id.subTotal);
        if(b!=null) {
            pizzaList = (ArrayList<String>) iin.getSerializableExtra("list");
            number = (String) b.get("number");
            text.setText(number);
            update();
            setAmount();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        System.out.println("ID: " + parent.equals(selectedToppings));
        if(parent.equals(pizzaView)) selectedPizza = (String) pizzaView.getItemAtPosition(position);
        System.out.println("Selected Pizza: " + selectedPizza);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }


    @Override
    public void onBackPressed(){
        System.out.println("on back pressed");
        finish();// calls main activity's onresume
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("on AB pressed");
        finish();
        System.out.println("method finished");
        return true;
    }

    public void removePizza(View view){
        if(pizzaList.size() == 0){
            createAlert("No pizzas in the list.", "Error!");
            return;
        }
        if(selectedPizza == null){
           createAlert("Please select a pizza to remove.", "Error!");
            return;
        }
        ((ArrayAdapter) pizzaView.getAdapter()).remove(selectedPizza);

        MainActivity.removePizzaList(selectedPizza);
        StringTokenizer token = new StringTokenizer(selectedPizza, ", ");
        String pizzaType = token.nextToken();
        System.out.println("ppizza " + pizzaType);
        List<Toppings> toppingList = new ArrayList<>();
        String top = "";
        while(token.hasMoreTokens()){
            top = token.nextToken();
            try{
                Toppings.valueOf(top);
            } catch(IllegalArgumentException e){
                break;
            }
            toppingList.add(Toppings.valueOf(top));
        }
        Size size = Size.valueOf(top);
        StringTokenizer amount = new StringTokenizer(token.nextToken(), "$");
        double sum = Double.parseDouble(amount.nextToken());
//        System.out.println("Sum is " + sum);
        Pizza pizza = PizzaMaker.createPizza(pizzaType);
        selectedPizza = null;
        pizza.price = sum;
        pizza.toppings = toppingList;
        MainActivity.removePizza(pizza, false, toppingList, sum, size);
        setAmount();
    }

    public void placeOrder(View view){
        if(pizzaList.size() == 0){
            createAlert("No pizzas in the list.", "Error!");
            return;
        }
        MainActivity.addOrder(new Order(MainActivity.getPizzaList(), number, total1));// add to order list
        MainActivity.setPizza(new ArrayList<>());
        pizzaList = new ArrayList<>();
        update();
        setAmount();
    }

    //Creates an alert with the message and title passed as an argument
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

    public void update(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pizzaList);
        pizzaView = (ListView) findViewById(R.id.listView);
        pizzaView.setOnItemClickListener(this);
        pizzaView.setAdapter(adapter);
        subtotal1 = MainActivity.getTotal();
    }

    public void setAmount(){
        if(subtotal1 == 0){
            subtotal.setText("");
            tax.setText("");
            total.setText("");
            return;
        }

        subtotal1 = Double.parseDouble(MainActivity.formatAmount(subtotal1));
        subtotal.setText("$ " + subtotal1);
        tax1 = subtotal1*(6.625/100);
        tax1 = Double.parseDouble(MainActivity.formatAmount(tax1));
        tax.setText("$ " + tax1);
        total.setText("$ " +MainActivity.formatAmount(tax1 +  subtotal1));
        total1 = tax1 + subtotal1;
    }
}