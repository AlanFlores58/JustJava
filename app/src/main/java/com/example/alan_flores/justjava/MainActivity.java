package com.example.alan_flores.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextView textView = new TextView(this);
        //setContentView(textView);

        setContentView(R.layout.activity_main);
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);

        //display(quantity);
        //displayPrice(quantity * 5);

        int price = calculatePrice(quantity,5);
        boolean whipped_cream = checkWhippedCream();
        boolean chocolate = checkChocolate();
        String name = getName();
        String priceMessage = createOrderSummary(quantity,price,whipped_cream,chocolate,name);
        //displayMessage(priceMessage);
        composeEmail(getString(R.string.Subject,name), priceMessage);
        //Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setData(Uri.parse("geo:47.6, -122.3"));
        //if (intent.resolveActivity(getPackageManager()) != null){
        //    startActivity(intent);
        //}

    }

    private void composeEmail(String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void increment(View viewr){
        if (quantity == 100) {
            Toast.makeText(this,"You cannot have more tham 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
        //displayPrice(quantity * 5);
    }

    public void decrement(View viewr){
        if (quantity == 1){
            Toast.makeText(this,"You cannot have less tham 1 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
        //displayPrice(quantity * 5);
    }

    /**private void displayMessage(String message){
        TextView orderSummaryTextView= (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }**/

    private int calculatePrice(int quantity,int priceCup){
        if (checkWhippedCream())
            priceCup = priceCup + 1;
        if (checkChocolate())
            priceCup = priceCup + 2;
        return  quantity * priceCup;
    }

    private String createOrderSummary(int quantity,int price, boolean whipped_cream, boolean chocolate, String name){
        String message = getString(R.string.sumary_order,name,whipped_cream,chocolate,quantity,price) + " \n" + getString(R.string.thank_you);
        return message;
    }

    private Boolean checkWhippedCream(){
        CheckBox whipped_cream_checkbox= (CheckBox) findViewById(R.id.Whipped_cream_checkbox);
        return whipped_cream_checkbox.isChecked();
    }

    private Boolean checkChocolate(){
        CheckBox chocolate_checkbox= (CheckBox) findViewById(R.id.chocolate_checkbox);
        return chocolate_checkbox.isChecked();
    }

    private String getName(){
        EditText name_edit_Text= (EditText) findViewById(R.id.name_edit_Text);
        return name_edit_Text.getText().toString();
    }
}
