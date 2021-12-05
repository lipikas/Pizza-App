package com.project5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class OrderCart2 extends AppCompatActivity{
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
            pizzaList.setAdapter(adapter);
        }
    }


}