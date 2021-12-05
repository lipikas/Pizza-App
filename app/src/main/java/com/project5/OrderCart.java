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


public class OrderCart extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private TextView Text;
    public static ArrayList<String> list  = new ArrayList<>();
    public static ListView pizzaList;
    public static ArrayAdapter<String> adapter = null;
    public static String selectedPizza = null;
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
//    @Override
//    protected void onStart(){
//
//    }
//    public void itemSelect(View view){
//        select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                val =(String) adapterView.getItemAtPosition(position);
//            }
////            @Override
////            public abstract void onNothingSelected(AdapterView<?> adapterView) {
////                // your stuff
////            }
//        });
//    }

//    @Override
//    public abstract void onItemClick(AdapterView<?> adapter1, View v, int position,
//                            long arg3) {
//        val = (String) adapter1.getItemAtPosition(position);
//        adapter.remove(value);
//        adapter.notifyDataSetChanged();
//    }
    public void removePizza(View view){
        if(selectedPizza == null){
            createAlert("Select a pizza order to remove", "Error!");
        } else{
            ((ArrayAdapter) pizzaList.getAdapter()).remove(selectedPizza);
            selectedPizza = null;
        }
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

}