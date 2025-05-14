package com.dolko.grocerymanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseInStock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddActivity extends Activity implements AdapterView.OnItemSelectedListener {

    DatabaseInStock databaseInStock;
    Spinner spinnerUnit, spinnerCategory;
    TextView name, quantity, exp_date, buy_date, description;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        databaseInStock = new DatabaseInStock(getApplicationContext());

        name = findViewById(R.id.add_item_product_name);
        quantity = findViewById(R.id.add_item_quantity);
        exp_date = findViewById(R.id.add_item_expiration_date);
        buy_date = findViewById(R.id.add_item_buy_date);
        description = findViewById(R.id.add_item_description);

        spinnerUnit = findViewById(R.id.add_item_unit);
        spinnerCategory = findViewById(R.id.add_item_category);

        spinnerUnit.setOnItemSelectedListener(this);
        spinnerCategory.setOnItemSelectedListener(this);

        Pattern pattern = Pattern.compile("\\s\\s\\s");
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            String temp_n = intent.getStringExtra("name");
            assert temp_n != null;
            Matcher matcher_n = pattern.matcher(temp_n);

            if (matcher_n.find()) name.setText(temp_n.substring(0, matcher_n.start()));
            else name.setText(temp_n);
            buy_date.setText(intent.getStringExtra("date"));
        }

        List<String> units = new ArrayList<>();
        units.add("Kg");
        units.add("Balenie");
        units.add("Ks");

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(unitAdapter);

        List<String> categories = new ArrayList<>();

        Cursor getCategories = databaseInStock.getCategories();
        if(getCategories.moveToFirst()){
            while(getCategories.moveToNext()) {
                categories.add(getCategories.getString(getCategories.getColumnIndexOrThrow("category_name")));
                Log.d("category", getCategories.getString(getCategories.getColumnIndexOrThrow("category_name")));
            }
        }

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        FloatingActionButton close = findViewById(R.id.add_item_close_button);
        FloatingActionButton confirm = findViewById(R.id.add_item_confirm_button);

        close.setOnClickListener(e -> {
            finish();
        });

        confirm.setOnClickListener(e -> {
            if(!Pattern.matches("(\\d{2})-(\\d{2})-(\\d{4})", exp_date.getText())) {
                Toast.makeText(getApplicationContext(), "Zlý formát dátumu expiracie (dd-mm-YYYY)", Toast.LENGTH_LONG).show();
                return;
            } else if (!Pattern.matches("(\\d{2})-(\\d{2})-(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})", buy_date.getText())){
                Toast.makeText(getApplicationContext(), "Zlý formát dátumu nákupu (dd-mm-YYYY hh:mm:ss)", Toast.LENGTH_LONG).show();
                return;
            } else if (quantity.getText().equals("") && !Pattern.matches("(\\d)", quantity.getText())){
                Toast.makeText(getApplicationContext(), "Zlý počet kusov", Toast.LENGTH_LONG).show();
                return;
            }

            Log.d("AddItem",(spinnerCategory.getSelectedItem().toString() + " " + Integer.parseInt(quantity.getText().toString())));
            databaseInStock.addItem(
                    name.getText().toString(),
                    quantity.getText().toString(),
                    spinnerUnit.getSelectedItem().toString(),
                    exp_date.getText().toString(),
                    buy_date.getText().toString(),
                    spinnerCategory.getSelectedItem().toString(),
                    description.getText().toString());

            finishAndRemoveTask();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}

