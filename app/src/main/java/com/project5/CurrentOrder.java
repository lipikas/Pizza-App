package com.project5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

//Features current order
public class CurrentOrder extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private Pizza currPizza;
    private Spinner spinner;
    private String type;
    private ListView selectedToppings;
    private ListView generalToppings;
    private Toppings selectedTopping;
    private Boolean isSelectedTopping;

    //Initializes the current order activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);
        Intent intent = getIntent();
        type = intent.getStringExtra(MainActivity.INTENT_MESSAGE);
        String pizzaType = Character.toUpperCase(type.charAt(0)) + type.substring(1);
        currPizza = PizzaMaker.createPizza(pizzaType);
        setImage();
        setLabel(pizzaType);
        setSizes();
        setToppings();
        setbackButton();
    }

    //Initialize Pizza Image
    private void setImage(){
        ImageView img = (ImageView) findViewById(R.id.imageView1);
        img.setImageResource(getResources().getIdentifier(type + "img" , "drawable", getPackageName()));
    }

    //Initialize text label for pizza
    private void setLabel(String textLabel){
        TextView textBox = (TextView) findViewById(R.id.pizzaType);
        textBox.setText(textLabel);
    }

    //Initializes back button in action bar
    private void setbackButton(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //Initializes toppings listview
    public void setToppings(){
        ArrayAdapter<Toppings> chosenToppings = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currPizza.toppings);
        List<Toppings> remainingToppings = new ArrayList<>();
        for(Toppings t : Toppings.values()){
            if(!currPizza.toppings.contains(t)) remainingToppings.add(t);
        }
        ArrayAdapter<Toppings> allToppings = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, remainingToppings);
        selectedToppings = findViewById(R.id.selectedToppings);
        selectedToppings.setOnItemClickListener(this);
        selectedToppings.setAdapter(chosenToppings);
        generalToppings = findViewById(R.id.allToppings);
        generalToppings.setOnItemClickListener(this);
        generalToppings.setAdapter(allToppings);
        updatePrice();
    }

    //Initializes size spinner
    private void setSizes(){
        ArrayAdapter<Size> sizes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Size.values());
        spinner = findViewById(R.id.sizeSpinner);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(sizes);
    }

    //Updates the price of currPizza when any changes are made
    private void updatePrice(){
        TextView price = findViewById(R.id.priceTextField);
        price.setText(MainActivity.formatAmount(currPizza.price()));
    }

    //Adds a topping to the current pizza
    public void addTopping(View view) {
        if(selectedToppings.getAdapter().getCount() == Pizza.MAX_TOPPINGS){
            displayMessage("Maximum toppings of 7 reached.");
            return;
        }
        if(selectedTopping == null || isSelectedTopping){
            selectedTopping = null;
            displayMessage("Please select a topping to add.");
            return;
        }
        ((ArrayAdapter) generalToppings.getAdapter()).remove(selectedTopping);
        currPizza.toppings.add(selectedTopping);
        selectedTopping = null;
        updatePrice();
    }

    //Removes a topping from the current pizza
    public void removeTopping(View view) {
        if(selectedToppings.getAdapter().getCount() == 0){
            displayMessage("All toppings removed.");
            return;
        }
        //Toppings selectedTopping = (Toppings) generalToppings.getSelectedItem();
        if(selectedTopping == null || !isSelectedTopping){
            selectedTopping = null;
            displayMessage("Please select a topping to remove.");
            return;
        }
        ((ArrayAdapter) generalToppings.getAdapter()).add(selectedTopping);
        ((ArrayAdapter) selectedToppings.getAdapter()).remove(selectedTopping);
        currPizza.toppings.remove(selectedTopping);
        selectedTopping = null;
        updatePrice();
    }

    //Sets the isSelectedTopping boolean value when back button in action bar is clicked
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

    //Selects the size when size spinner value is changed
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        currPizza.size = (Size) spinner.getSelectedItem();
        updatePrice();
    }

    //does nothing when nothing is selected in the Size spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    //Adds pizza to order
    public void addOrder(View view){
        Log.i("Pizza Added", "Pizza added to order:" + currPizza.toString());
        MainActivity.addPizza(currPizza);
        currPizza = PizzaMaker.createPizza(Character.toUpperCase(type.charAt(0)) + type.substring(1));
        setToppings();
        setSizes();
        displayMessage("Order Added!");
    }

    //When back button at the bottom the screen is pressed
    @Override
    public void onBackPressed(){
        finish();
    }

    //When back button in ActionBar is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    //Displays success message on Toast when order is added
    private void displayMessage(CharSequence text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();
    }

}