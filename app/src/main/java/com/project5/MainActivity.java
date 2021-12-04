package com.project5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String INTENT_MESSAGE = "com.project5.MESSAGE";
    public static final String number = "123";
    public ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list.add("A");
        list.add("B");
        list.add("B");
        list.add("B");
        list.add("B");
    }

    public void createDeluxeOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra("deluxe", INTENT_MESSAGE);
        startActivity(intent);
    }

    public void createHawaiianOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra("hawaiian", INTENT_MESSAGE);
        startActivity(intent);
    }

    public void createPepperoniOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra("pepperoni", INTENT_MESSAGE);
        startActivity(intent);
    }

    public void order(View view){
        Intent intent = new Intent(this, OrderCart.class);
        intent.putExtra("number", number);
        intent.putExtra("arr", list);
        startActivity(intent);
    }
    public void orderList(View view){
        Intent intent = new Intent(this, OrderView.class);
        intent.putExtra("arr", list);
        startActivity(intent);
    }
}