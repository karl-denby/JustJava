package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    /**
     * Global variables
     */
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Calculate the total price
     *
     * @return the total price
     */
    private int calculatePrice() {
        int price = 5;

        if (whipped()) {
            price += 1;
        }

        if (chocolate()) {
            price += 2;
        }
        return quantity * price;
    }


    /**
     *
     * @param name of person ordering
     * @param quantity how many coffees
     * @param price how much do they cost
     * @param whipped_cream do they want whipped cream
     * @param chocolate do they want chocolate
     * @return priceMessage
     */
    public String createOrderSummary(String name, int quantity, int price, boolean whipped_cream, boolean chocolate) {
        String priceMessage = getString(R.string.email_name, name);
        priceMessage += "\n" + getString(R.string.email_cream, "" + whipped_cream);
        priceMessage += "\n" + getString(R.string.email_chocolate, "" + chocolate);
        priceMessage += "\n" + getString(R.string.email_quantity, quantity);
        priceMessage += "\n" + getString(R.string.email_total, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.email_thanks);
        return priceMessage;
    }

    private boolean whipped() {
        boolean hasWhippedCream;
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);
        return hasWhippedCream;
    }

    private boolean chocolate() {
        boolean hasChocolate;
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        hasChocolate = chocolateCheckBox.isChecked();

        //Log.v("MainActivity", "Has chocolate: " + hasChocolate);
        return hasChocolate;
    }

    private String getOrderName() {
        EditText name_edittext = (EditText) findViewById(R.id.name_edittext);
        //Log.v("MainActivity", "Name of ordering person: " + name_edittext.getText());
        return name_edittext.getText().toString();
    }


    public void composeEmail(String address, String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        String priceMessage = createOrderSummary(getOrderName(), quantity, calculatePrice(), whipped(), chocolate());
        //displayMessage(priceMessage);
        composeEmail("", "JustJava order for: " + getOrderName(), priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     *
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
     */

    /**
     * These methods increment or decrement the quantity.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity += 1;
        } else {
            Toast.makeText(MainActivity.this, "Can't order more than 100 cups", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity -= 1;
        } else {
            Toast.makeText(MainActivity.this, "Can't order less than 1 cup", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given text on screen

    public void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.price_text_view);
        orderSummaryTextView.setText(message);
    }
     */
}