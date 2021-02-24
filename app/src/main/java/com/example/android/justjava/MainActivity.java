package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (quantity == 50) {
            Toast.makeText(this, "Order Limit Reached", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity++;


        display(quantity);

    }

    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }



        quantity--;
        display(quantity);
    }

    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.cream_Check_Box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolatetoppings = (CheckBox) findViewById(R.id.chocolate_Check_Box);
        boolean haschocolate = chocolatetoppings.isChecked();

        EditText text = (EditText) findViewById(R.id.text_Input);
        String nameOfUser  = text.getText().toString();

        int totalPrice = calculatePrice(hasWhippedCream , haschocolate);
        String msg = orderSummary(totalPrice, hasWhippedCream , haschocolate , nameOfUser);

        Intent intentObj = new Intent(Intent.ACTION_SENDTO);
        intentObj.setData(Uri.parse("mailto:"));
        intentObj.putExtra(Intent.EXTRA_SUBJECT , "Just Java Order for "+nameOfUser);
        intentObj.putExtra(Intent.EXTRA_TEXT ,msg );
        if(intentObj.resolveActivity(getPackageManager()) != null){
            startActivity(intentObj);
        }

    }

    private String orderSummary(int price, boolean hasCream , boolean haschocolate , String input_Text) {

        String priceMesaage = "Name : " + input_Text;
        priceMesaage += "\nAdd whipped cream? " + hasCream;
        priceMesaage += "\nAdd chocolate? " + haschocolate;
        priceMesaage += "\n Quantity: " + quantity;
        priceMesaage += "\nTotal: $" + price;
        priceMesaage += "\nThankYou!!";
        return priceMesaage;


    }


    private int calculatePrice(boolean addCream , boolean addChocolate) {
        // price of 1 cup of coffee
        int basePrice = 5;

        // if whippedcream is checked then add 1 to baseprice of coffee
        if(addCream){
            basePrice += 1;
        }
        //if chocolate checked then add 2 to baseprice of coffee
        if(addChocolate){
            basePrice += 2;
        }
        // multiplying with quantity
        return quantity * basePrice;
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
