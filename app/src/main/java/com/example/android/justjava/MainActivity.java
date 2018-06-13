package com.example.android.justjava;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;
/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    int price = 0;
    int priceToppings = 0;
    int priceToppings2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    EditText name;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        name = (EditText) findViewById(R.id.NameEdit);
        String nameCustom = name.getText().toString();
        if(name.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Please enter your name before closing the order!",Toast.LENGTH_SHORT).show();
            return;

        }
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkbox2);
        if (checkBox.isChecked()) {
            priceToppings = 10*quantity;

            //whip cream costs 10 buck
        }
        else
            priceToppings = 0;
        if (checkBox2.isChecked()) {
            priceToppings2 = 5*quantity;

            // chocolate costs 5 bucks
        }
        else
            priceToppings2 = 0;
        price = quantity*20;

        // each cup of coffee costs Rs 20.

        String priceMessage=createOrderSummary(checkBox.isChecked(),checkBox2.isChecked(),nameCustom);

        //createOrderSummary() returns the summary and it is saved in String priceMessage

        displayMessage(priceMessage);

        // order summary obtained from createOrderSummary() is send to displayMessage to be printed.

        String namel= name.getText().toString();
        String mailId[] = {"suranjandaw@gmail.com"};
        Intent summary = new Intent(Intent.ACTION_SENDTO);
        summary.setData(Uri.parse("mailto:"));
        summary.putExtra(Intent.EXTRA_SUBJECT,"Coffee Order from "+namel);
        summary.putExtra(Intent.EXTRA_TEXT,priceMessage);
        summary.putExtra(Intent.EXTRA_EMAIL, mailId);
        if(summary.resolveActivity(getPackageManager()) != null)
            startActivity(summary);
    }
    public void increment(View view){
        if(quantity<10){
            quantity++;
        }
        else
            Toast.makeText(this, "Sorry cannot serve so many sups of coffee",Toast.LENGTH_SHORT).show();
        display(quantity);
    }
    public void decrement(View view){
        if(quantity>1) {
            quantity--;
        }
        else
            Toast.makeText(this, "You have to choose atleast one cup of Coffee",Toast.LENGTH_SHORT ).show();
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */
    String createOrderSummary(boolean whip, boolean choco, String name){
        String s = "";
        if(whip && choco)
            s="Name- "+name+"\nQuantity:"+quantity+" with Whip Cream and Chocolate\nPrice:Rs"+(price+priceToppings+priceToppings2)+"\nThank You!!";
        else if(whip && !choco)
            s="Name- "+name+"\nQuantity:"+quantity+" with Whip Cream\nPrice:Rs"+(price+priceToppings)+"\nThank You!!";
        else if(!whip && choco)
            s="Name- "+name+"\nQuantity:"+quantity+" with Chocolate\nPrice:Rs"+(price+priceToppings2)+"\nThank You!!";
        else
            s="Name- "+name+"\nQuantity:"+quantity+" Plain\nPrice:Rs"+(price)+"\nThank You!!";

        return s;
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }
}
