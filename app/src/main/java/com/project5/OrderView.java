package com.project5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Button;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OrderView extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    public static ArrayList<String> phoneList = new ArrayList<>();// pho
    public static ArrayAdapter<String> spinnerAdapter = null;
    public static ArrayAdapter<String> orderAdapter = null;
    public static ArrayList<String> orderList = new ArrayList<>();
    public static ListView pizzaOrder;
    public static String selectedOrder = null;
    public static String selectedNumber = null;
    private TextView total;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null) {
            phoneList = (ArrayList<String>) iin.getSerializableExtra("numberList");
        }
        spinnerAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phoneList);
        spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        System.out.println("N:- " + selectedNumber);
        if(selectedNumber!= null){
            orderList = MainActivity.printList(selectedNumber);
            total = findViewById(R.id.totalAmount1);
            String val = orderList.get(orderList.size() -1);
            total.setText("$ " + MainActivity.formatAmount(Double.parseDouble(val)));
            orderList.remove(orderList.size()-1);

            orderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orderList);
            pizzaOrder = (ListView) findViewById(R.id.listView2);
            pizzaOrder.setOnItemClickListener(this);
            pizzaOrder.setAdapter(orderAdapter);
        }

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

    public void removeOrderList(View view){
        if(selectedNumber == null){
            createAlert("Please add an order!", "Error!");
            return;
        }
        MainActivity.removeOrderList(selectedNumber);
        phoneList.remove(selectedNumber);
        spinnerAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phoneList);
        spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(this);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        selectedNumber = null;
        orderList = new ArrayList<>();
        updateList();
        total.setText("");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        selectedNumber = (String) spinner.getSelectedItem();
        if(selectedNumber!= null){
            update();
        }
    }

    public void update(){
        orderList = MainActivity.printList(selectedNumber);
        total = findViewById(R.id.totalAmount1);
        String val = orderList.get(orderList.size() -1);
        total.setText("$ " + MainActivity.formatAmount(Double.parseDouble(val)));
        orderList.remove(orderList.size()-1);
        updateList();
    }

    public void updateList(){
        orderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orderList);
        pizzaOrder = (ListView) findViewById(R.id.listView2);
        pizzaOrder.setOnItemClickListener(this);
        pizzaOrder.setAdapter(orderAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        System.out.println("ID: " + parent.equals(selectedToppings));
        if(parent.equals(pizzaOrder)) selectedOrder = (String) pizzaOrder.getItemAtPosition(position);
//        System.out.println("Selected Pizza: " + selectedPizza);
    }
}