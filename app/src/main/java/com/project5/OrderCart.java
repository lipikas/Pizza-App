package com.project5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


public class OrderCart extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    private TextView Text;
    public static ArrayList<String> list  = new ArrayList<>();
    public static ListView pizzaList;
    public static ArrayAdapter<String> adapter = null;
    public static String selectedPizza = null;
    public static List<List<String>> orderList = new ArrayList<>();
    String val = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart2);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        Text = findViewById(R.id.phone);
        if(b!=null) {
            list = (ArrayList<String>) iin.getSerializableExtra("arr");
            String j = (String) b.get("number");
            Text.setText(j);

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            pizzaList = (ListView) findViewById(R.id.listView);
            pizzaList.setOnItemClickListener(this);
            pizzaList.setAdapter(adapter);
        }
    }
//    @Override
//    protected void onResume(){
//
//    }
    @Override
    protected void onPause(){

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
        if(parent.equals(pizzaList)) selectedPizza = (String) pizzaList.getItemAtPosition(position);
        System.out.println("Selected Pizza: " + selectedPizza);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    public void removePizza(View view){
        if(list.size() == 0){
            createAlert("No pizzas in the list.", "Error!");
            return;
        }
        if(selectedPizza == null){
            createAlert("Please select a pizza to remove.", "Error!");
            return;
        }
        ((ArrayAdapter) pizzaList.getAdapter()).remove(selectedPizza);
        list.remove(selectedPizza);
        PizzaApplication app = (PizzaApplication) getApplicationContext();
        app.setList(list);
    }

    public void placeOrder(View view){
        if(list.size() == 0){
            createAlert("No pizzas in the list.", "Error!");
            return;
        }
        orderList.add(list);
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        pizzaList = (ListView) findViewById(R.id.listView);
        pizzaList.setAdapter(adapter);
//        PizzaApplication app = (PizzaApplication) getApplicationContext();
//        app.setList(list);
    }

}