package com.project5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class OrderCart extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    private TextView text;
    private TextView tax;
    private TextView total;
    private TextView subtotal;
    public static ArrayList<Pizza> pizza  = new ArrayList<>();
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
//            pizza = (ArrayList<Pizza>) iin.getSerializableExtra("order");
            pizzaList = (ArrayList<String>) iin.getSerializableExtra("list");
            number = (String) b.get("number");
            text.setText(number);

//            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pizzaList);
//            pizzaView = (ListView) findViewById(R.id.listView);
//            pizzaView.setOnItemClickListener(this);
//            pizzaView.setAdapter(adapter);

//            subtotal1 = getTotal(pizza);
//            subtotal1 = Double.parseDouble(MainActivity.formatAmount(subtotal1));
//
//            subtotal.setText("$ " + subtotal1);
//            tax1 = subtotal1*(6.625/100);
//            tax1 = Double.parseDouble(MainActivity.formatAmount(tax1));
//            tax.setText("$ " + tax1);
//            total.setText("$ " +MainActivity.formatAmount(tax1 +  subtotal1));
//            total1 = tax1 + subtotal1;
        }
    }
    /**
     * gets order total
     * @param pizza is pizza list
     * @return order total
     */
    public double getTotal(List<Pizza> pizza){
        double sum = 0;
        for(int i = 0; i < pizza.size(); i++){
            sum += pizza.get(i).price();
        }

        return sum;
    }

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

    public void removePizza(View view){
        if(pizza.size() == 0){
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

        pizza.toppings = toppingList;
        MainActivity.removePizza(pizza, false, toppingList, sum, size);
    }

    public void placeOrder(View view){
        if(pizza.size() == 0){
            createAlert("No pizzas in the list.", "Error!");
            return;
        }
        MainActivity.addOrder(new Order(pizza, number, total1));// add to order list
        MainActivity.setPizza(new ArrayList<>());
        pizza = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pizzaList);
        pizzaView = (ListView) findViewById(R.id.listView);
        pizzaView.setAdapter(adapter);
    }

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
}