package com.project5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    public static final String INTENT_MESSAGE = "com.project5.MESSAGE";
    private static List<Pizza> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("lifecycle", "on create invoked");
    }

    public void enterButton(View view){
        TextView phoneNum = (TextView) findViewById(R.id.textInput);
        if(phoneNum.getText().length() != 10){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Invalid Phone Number");
            builder.setMessage("Please Enter a Valid 10-digit Phone Number!");
            builder.setCancelable(false);
            builder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else isEnabled(true);
    }

    public void changeButton(View view){
        isEnabled(false);
    }

    public void isEnabled(boolean b){
        Button btn1 = (Button) findViewById(R.id.deluxe);
        btn1.setEnabled(b);
        Button btn2 = (Button) findViewById(R.id.hawaiian);
        btn2.setEnabled(b);
        Button btn3 = (Button) findViewById(R.id.pepperoni);
        btn3.setEnabled(b);
        Button btn4 = (Button) findViewById(R.id.change);
        btn4.setEnabled(b);
        Button btn5 = (Button) findViewById(R.id.enter);
        btn5.setEnabled(!b);
        TextView phoneNum = (TextView) findViewById(R.id.textInput);
        phoneNum.setEnabled(!b);
    }

    public void createDeluxeOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra(INTENT_MESSAGE, "deluxe");
        startActivity(intent);
    }

    public void createHawaiianOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra(INTENT_MESSAGE, "hawaiian");
        startActivity(intent);
    }

    public void createPepperoniOrder(View view) {
        Intent intent = new Intent(this, CurrentOrder.class);
        intent.putExtra(INTENT_MESSAGE, "pepperoni");
        startActivity(intent);
    }

    public static void addOrder(Pizza newPizza){
        orders.add(newPizza);
    }

    /**
     * formats the amount
     * @param amount is the amount given by user
     * @return string format of amount
     */
    public String formatAmount(double amount){
        int amountInInt = (int)amount;
        String intPart = Integer.toString(amountInInt);
        String formattedAmount = "";
        int len = intPart.length();
        if(len <= 3) {
            formattedAmount = intPart;
        }
        else if(len % 3 == 0) {
            int beg = 0, end = 3;
            for(int i = 1; i < len/3; i++){
                formattedAmount += intPart.substring(beg, end) + ",";
                beg = end;
                end += 3;
            }
            formattedAmount += intPart.substring(beg, len);
        }
        else{
            int remainder = len % 3;
            formattedAmount = intPart.substring(0, remainder) + ",";
            int beg = remainder, end = beg + 3;
            for(int i = 1; i < len/3; i++){
                formattedAmount += intPart.substring(beg, end) + ",";
                beg = end;
                end += 3;
            }
            formattedAmount += intPart.substring(beg, len);
        }
        String amountAsString = Double.toString(amount);
        amountAsString += "0";
        int indexOfDecimalPoint = amountAsString.indexOf(".");
        int endOfDecimal = Math.min(indexOfDecimalPoint + 3, amountAsString.length());
        formattedAmount += "." + amountAsString.substring(indexOfDecimalPoint + 1, endOfDecimal);
        return formattedAmount;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume invoked");
        System.out.println("orders: \n");
        for(Pizza o : orders){
            Log.d("Current Orders", o.toString());
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause invoked");
    }
}