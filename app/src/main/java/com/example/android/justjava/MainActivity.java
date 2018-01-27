
package com.example.android.justjava;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Creates a global quantity variable.
     */

    public int quantity = 0;
    public int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */

    public void decrement(View view) {
        if (quantity == 1) {
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * In String name, we save  user input given in the name field.
     */

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_box);
        String name = nameField.getText().toString();
//        Log.v("MainActivity", "Name: " + name);

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
//        Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
//        Log.v("MainActivity", "Has chocolate topping: " + hasChocolate);

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = (createOrderSummary(name, price, hasWhippedCream, hasChocolate));

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6,-122.3"));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }

        Intent sendEmail = new Intent(Intent.ACTION_SENDTO);
        sendEmail.setData(Uri.parse("mailto:"));
        sendEmail.setAction(Intent.ACTION_SEND);
        sendEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"dammit@gmail.com.com"});
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.JustJava_order_for) + name);
        sendEmail.putExtra(Intent.EXTRA_TEXT, priceMessage);
        sendEmail.setType("text/html");
// Verify that the intent will resolve to an activity
        if (sendEmail.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendEmail, "Send feedback"));
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream is whether or not the user wants whipped cream.
     * @param hasChocolate    is whether or not the user wants chocolate topping.
     * @return total price
     */

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if (hasWhippedCream)
            basePrice += 1;
        if (hasChocolate) {
            basePrice += 2;
        }
        price = basePrice * quantity;
        return price;
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
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
////        orderSummaryTextView.setText(message);
//    }

    /**
     * Create summary of order.
     *
     * @param price           of the order
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate    is whether or not the user wants chocolate topping
     * @return text summary
     */

    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {
        Resources res = getResources();
        String priceMessage = getString(R.string.JustJava_order_for, name);
        priceMessage += "\n" + getString(R.string.Add_whipped_cream, hasWhippedCream);
        priceMessage += "\n" + getString(R.string.Add_chocolate_topping, hasChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + res.getString(R.string.order_summary_price,price);
        priceMessage += "\n" + getString(R.string.Thank_you);
        return priceMessage;
    }
}

