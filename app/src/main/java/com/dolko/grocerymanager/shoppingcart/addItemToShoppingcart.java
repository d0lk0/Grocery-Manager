package com.dolko.grocerymanager.shoppingcart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class addItemToShoppingcart extends Activity {
    DatabaseShoppingCart databaseShoppingCart;
    TextView name, quantity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_item_to_shoppingcart);

        databaseShoppingCart = new DatabaseShoppingCart(getApplicationContext());

        name = findViewById(R.id.add_item_product_name);
        quantity = findViewById(R.id.add_item_quantity);
        FloatingActionButton close = findViewById(R.id.add_item_close_button);
        FloatingActionButton confirm = findViewById(R.id.add_item_confirm_button);

        close.setOnClickListener(e -> {
            finish();
        });

        confirm.setOnClickListener(e -> {
            if(!name.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty()){
                databaseShoppingCart.addData(name.getText().toString(), quantity.getText().toString());
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Zadané chybné údaje!", Toast.LENGTH_LONG).show();
            }
        });
    }

}
