package com.project5;

import androidx.appcompat.app.AppCompatActivity;

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


public class OrderCart extends AppCompatActivity {
    private TextView Text;
    public static ArrayList<String> list = new ArrayList<>();
    public static ListView list1;
    public static ArrayAdapter<String> adapter = null;
    public static ListView select = null;
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
            String j =(String) b.get("number");
            Text.setText(j);
//            list = (List) b.get("arr");
        }
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
//        setListAdapter(adapter);
        list1 = (ListView) findViewById(R.id.listView);
        list1.setAdapter(adapter);
    }
//    @Override
//    protected void onResume(){
//
//    }
//    @Override
//    protected void onStart(){
//
//    }
    public void itemSelect(View view){
        select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                val =(String) adapterView.getItemAtPosition(position);
            }
//            @Override
//            public abstract void onNothingSelected(AdapterView<?> adapterView) {
//                // your stuff
//            }
        });
    }

//    @Override
//    public abstract void onItemClick(AdapterView<?> adapter1, View v, int position,
//                            long arg3) {
//        val = (String) adapter1.getItemAtPosition(position);
//        adapter.remove(value);
//        adapter.notifyDataSetChanged();
//    }
    public void removePizza(View view){
        final Button button = (Button) findViewById(R.id.removeButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!val.equals("")){
                    adapter.remove(val);
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

}