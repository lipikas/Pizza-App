package com.project5;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OrderView extends AppCompatActivity {
    public static ArrayList<String> list = new ArrayList<>();
    public static ArrayAdapter<String> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
//        Text = findViewById(R.id.phone);
        if(b!=null) {
            list = (ArrayList<String>) iin.getSerializableExtra("arr");
//            list = (List) b.get("arr");
        }
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        Spinner s = (Spinner) findViewById(R.id.spinner2);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }
}