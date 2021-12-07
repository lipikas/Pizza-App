package com.project5;

import android.content.Context;
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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
//Order View's features
public class OrderView extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    private static ArrayList<String> phoneList = new ArrayList<>();// pho
    private static ArrayAdapter<String> spinnerAdapter = null;
    private static ArrayAdapter<String> orderAdapter = null;
    private static ArrayList<String> orderList = new ArrayList<>();
    private static ListView pizzaOrder;
    private static String selectedNumber = null;
    private TextView total;
    private static Spinner spinner;

    //Initializes the order cart activity
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

//    //Creates an alert with the message and title passed as an argument
//    public void createAlert(String message, String title){
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setMessage(message);
//        alert.setTitle(title);
//        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        AlertDialog dialog = alert.create();
//        dialog.show();
//    }
    //remove order and associated Number
    public void removeOrderList(View view){
        if(selectedNumber == null){
            displayMessage("Please add an order!");
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

    //Updates selectedNumber when spinner is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        selectedNumber = (String) spinner.getSelectedItem();
        if(selectedNumber!= null){
            update();
        }
    }
    //updates total and orderList
    public void update(){
        orderList = MainActivity.printList(selectedNumber);
        total = findViewById(R.id.totalAmount1);
        String val = orderList.get(orderList.size() -1);
        total.setText("$ " + MainActivity.formatAmount(Double.parseDouble(val)));
        orderList.remove(orderList.size()-1);
        updateList();
    }
    //Updates listview
    public void updateList(){
        orderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orderList);
        pizzaOrder = (ListView) findViewById(R.id.listView2);
        pizzaOrder.setOnItemClickListener(this);
        pizzaOrder.setAdapter(orderAdapter);
    }
    //does nothing when no selectedNumber
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    //Does nothing when listview selected
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    //Displays success message on Toast when order is added
    private void displayMessage(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }
}