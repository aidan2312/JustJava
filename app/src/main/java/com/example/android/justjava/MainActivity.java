package com.example.android.justjava;


/**
 * This app displays an order form to order coffee.
 */


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;


import java.net.URI;
import java.text.NumberFormat;
import java.util.Date;

import static android.content.Intent.ACTION_SENDTO;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**This Method is called when the + button is triggered.
     *
     * @param view
     */


    public void increment(View view){
        if(quantity < 15){
            quantity = quantity + 1;
            displayQuantity(quantity);
        } else{
            errorToast("Too many coffees!", Toast.LENGTH_LONG);
        }

    }
    /**
     * This method is called when the order button is clicked.
     */

    public void decrement(View view){
            if (quantity > 1) {
                quantity = quantity - 1;
                displayQuantity(quantity);
            }else{
                errorToast("Too few coffees.", Toast.LENGTH_LONG);
            }
    }
    public void submitOrder(View view) {


        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        EditText nameText = (EditText) findViewById(R.id.nameText);
        String name = nameText.getText().toString();
        hasChocolate = chocolate.isChecked();
        hasWhippedCream = whippedCream.isChecked();

        int coffeePrice = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(name, coffeePrice, hasWhippedCream, hasChocolate);

        displayMessage("Thank you for your order " + name + "!");

        Intent sendOrder = new Intent(ACTION_SENDTO);
        sendOrder.setData(Uri.parse("mailto:"));
        sendOrder.putExtra(Intent.EXTRA_TEXT, createOrderSummary(name, coffeePrice, hasWhippedCream, hasChocolate));
        sendOrder.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        if (sendOrder.resolveActivity(getPackageManager()) != null){
            startActivity(sendOrder);
        }else{
            errorToast("No email apps I guess.", Toast.LENGTH_LONG);
        }




    }





    /**
     * Calculates the price of the order.
     *
     *
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream){
            basePrice += 1;

        }

        if (addChocolate) {
            basePrice += 2;
        }
        return quantity * basePrice;


    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }


    /**
     * Creates the Order Summary.
     *
     * @param price the price of the coffee order.
     * @param addWhippedCream Whipped cream or no?
     *
     * @return the order summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        Date currentDate = Calendar.getInstance().getTime();
        String priceMessage = currentDate.toString();
        priceMessage += "\nName: " + name;
        priceMessage += "\nWhipped cream? " + addWhippedCream;
        priceMessage += "\nChocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;





    }

    /**
     * Generates a Toast to tell the user that there was an error with their input.
     * Method can be called with the String errorMessage passed to it.
     * @author Aidan Donnelly
     * @param errorMessage the cause of the error.
     */
    private void errorToast(String errorMessage, int duration){
       Context context = getApplicationContext();
        CharSequence text = "ERROR: " + errorMessage;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     *
     * @param toastMessage the message of the Toast notification.
     * @param duration the duration of the Toast notification.
     */

    private void coffeeToast(String toastMessage, int duration){
        Context context = getApplicationContext();

        CharSequence text = toastMessage;

        Toast toast = Toast.makeText(context,text, duration);
        toast.show();
        System.out.println(toastMessage);


    }





}