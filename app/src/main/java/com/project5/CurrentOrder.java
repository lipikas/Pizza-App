package com.project5;

import androidx.appcompat.app.ActionBar;
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
import android.view.MenuItem;
import androidx.annotation.NonNull;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
//test
public class CurrentOrder extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private Pizza currPizza;
    Spinner spinner;
    String type;
    ListView selectedToppings;
    ListView generalToppings;
    Toppings selectedTopping;
    TextView price;
    ArrayAdapter<Toppings> allToppings;
    ArrayAdapter<Toppings> chosenToppings;
    ArrayAdapter<Size> sizes;
    static MainActivity mainPage;
    Boolean isSelectedTopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainPage = new MainActivity();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);
        Intent intent = getIntent();
        type = intent.getStringExtra(MainActivity.INTENT_MESSAGE);

        currPizza = PizzaMaker.createPizza(Character.toUpperCase(type.charAt(0)) + type.substring(1));

        //set image
        ImageView img = (ImageView) findViewById(R.id.imageView1);
        img.setImageResource(getResources().getIdentifier(type + "img" , "drawable", getPackageName()));

        //set text label
        TextView textBox = (TextView) findViewById(R.id.pizzaType);
        textBox.setText(Character.toUpperCase(type.charAt(0)) + type.substring(1) + " Pizza");

        setSizes();

        setToppings();

        //set back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Updates the price of currPizza when any changes are made
     */
    public void updatePrice(){
        price = findViewById(R.id.priceTextField);
        price.setText(mainPage.formatAmount(currPizza.price()));
    }

    /**
     * Adds a topping to the current pizza
     */
    public void addTopping(View view) {
        if(selectedToppings.getAdapter().getCount() == Pizza.MAX_TOPPINGS){
            createAlert("Maximum toppings of 7 reached.", "Error!");
            return;
        }
        if(selectedTopping == null || isSelectedTopping){
            selectedTopping = null;
            createAlert("Please select a topping to add.", "Error!");
            return;
        }
        ((ArrayAdapter) generalToppings.getAdapter()).remove(selectedTopping);
        currPizza.toppings.add(selectedTopping);
        selectedTopping = null;
        updatePrice();
    }

    /**
     * Removes a topping from the current pizza
     */
    public void removeTopping(View view) {
        if(selectedToppings.getAdapter().getCount() == 0){
            createAlert("All toppings removed.", "Error!");
            return;
        }
        //Toppings selectedTopping = (Toppings) generalToppings.getSelectedItem();
        if(selectedTopping == null || !isSelectedTopping){
            selectedTopping = null;
            createAlert("Please select a topping to remove.", "Error!");
            return;
        }
        ((ArrayAdapter) generalToppings.getAdapter()).add(selectedTopping);
        ((ArrayAdapter) selectedToppings.getAdapter()).remove(selectedTopping);
        currPizza.toppings.remove(selectedTopping);
        selectedTopping = null;
        updatePrice();
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
        System.out.println("ID: " + parent.equals(selectedToppings));
        if(parent.equals(selectedToppings)){
            selectedTopping = (Toppings) selectedToppings.getItemAtPosition(position);
            isSelectedTopping = true;
        }
        else{
            selectedTopping = (Toppings) generalToppings.getItemAtPosition(position);
            isSelectedTopping = false;
        }
        System.out.println("Selected Topping: " + selectedTopping);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        currPizza.size = (Size) spinner.getSelectedItem();
        updatePrice();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    public void addOrder(View view){
        System.out.println("curr pizza added: " + currPizza);
        MainActivity.addPizza(currPizza);
        currPizza = PizzaMaker.createPizza(Character.toUpperCase(type.charAt(0)) + type.substring(1));
        setToppings();
    }

    @Override
    public void onBackPressed(){
        System.out.println("on back pressed");
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("on AB pressed");
        finish();
        System.out.println("method finished");
        return true;
    }

    public void setToppings(){
        //set toppings listview
        chosenToppings = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currPizza.toppings);
        List<Toppings> remainingToppings = new ArrayList<>();
        for(Toppings t : Toppings.values()){
            if(!currPizza.toppings.contains(t)) remainingToppings.add(t);
        }
        allToppings = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, remainingToppings);
        selectedToppings = findViewById(R.id.selectedToppings);
        selectedToppings.setOnItemClickListener(this);
        selectedToppings.setAdapter(chosenToppings);
        generalToppings = findViewById(R.id.allToppings);
        generalToppings.setOnItemClickListener(this);
        generalToppings.setAdapter(allToppings);
        updatePrice();
    }

    public void setSizes(){
        sizes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Size.values());
        spinner = findViewById(R.id.sizeSpinner);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(sizes);
    }

}