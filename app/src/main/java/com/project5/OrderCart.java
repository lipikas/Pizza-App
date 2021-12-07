package com.project5;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
//View Order Cart features

public class OrderCart extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    private static TextView text;
    private static TextView tax;
    private static TextView total;
    private static TextView subtotal;
    private static ListView pizzaView;
    private static ArrayAdapter<String> adapter = null;
    private static String selectedPizza = null;
    private static ArrayList<String> pizzaList = new ArrayList<>();
    private static String number = "";
    private static double subtotal1;
    private static double tax1;
    private static double total1;
    private static double taxAmount2 = 0.06625;

    //Initializes the order cart activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart);
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        text = findViewById(R.id.phone);
        tax = findViewById(R.id.taxAmount);
        total = findViewById(R.id.totalAmount);
        subtotal = findViewById(R.id.subTotal);
        if(bundle != null) {
            pizzaList = (ArrayList<String>) intent.getSerializableExtra("list");
            number = (String) bundle.get("number");
            text.setText(number);
            update();
            setAmount();
        }
    }
    //Sets the selectedPizza when back button in action bar is clicked
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent.equals(pizzaView)) selectedPizza = (String) pizzaView.getItemAtPosition(position);
    }

    //Nothing is changed when no listview selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
    }
    //does nothing when nothing is selected in the listview
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    //When back button at the bottom the screen is pressed
    @Override
    public void onBackPressed(){
        finish();// calls main activity's onresume
    }

    //When back button in ActionBar is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    //remove Pizza from Order Cart
    public void removePizza(View view){
        if(pizzaList.size() == 0){
            displayMessage("No pizzas in the list.");
            return;
        }
        if(selectedPizza == null){
            displayMessage("Please select a pizza to remove.");
            return;
        }
        ((ArrayAdapter) pizzaView.getAdapter()).remove(selectedPizza);

        MainActivity.removePizzaList(selectedPizza);
        StringTokenizer token = new StringTokenizer(selectedPizza, ", ");
        String pizzaType = token.nextToken();

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

        Pizza pizza = PizzaMaker.createPizza(pizzaType);
        selectedPizza = null;
        pizza.price = sum;
        pizza.toppings = toppingList;
        MainActivity.removePizza(pizza, false, toppingList, sum, size);
        setAmount();
    }

    //adds Pizza List to Order
    public void placeOrder(View view){
        if(pizzaList.size() == 0){
            displayMessage("No pizzas in the list.");
            return;
        }
        MainActivity.addOrder(new Order(MainActivity.getPizzaList(), number, total1));// add to order list
        MainActivity.setPizza(new ArrayList<>());
        pizzaList = new ArrayList<>();
        update();
        setAmount();
        displayMessage("Placed order.");
    }

    //updates listview
    public void update(){
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pizzaList);
        pizzaView = (ListView) findViewById(R.id.listView);
        pizzaView.setOnItemClickListener(this);
        pizzaView.setAdapter(adapter);
        subtotal1 = MainActivity.getTotal();
    }

    //Updates tax, total and subtotal
    public void setAmount(){
        if(subtotal1 == 0){
            subtotal.setText("");
            tax.setText("");
            total.setText("");
            return;
        }

        subtotal1 = Double.parseDouble(MainActivity.formatAmount(subtotal1));
        subtotal.setText("$ " + subtotal1);
        tax1 = subtotal1*taxAmount2;
        tax1 = Double.parseDouble(MainActivity.formatAmount(tax1));
        tax.setText("$ " + tax1);
        total.setText("$ " +MainActivity.formatAmount(tax1 +  subtotal1));
        total1 = tax1 + subtotal1;
    }

    //Displays success message on Toast when order is added
    private void displayMessage(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }
}