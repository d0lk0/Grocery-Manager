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

        Cursor getUnits = databaseInStock.getAllUnits();
        if (getUnits.moveToFirst()) {
            do {
                units.add(getUnits.getString(getUnits.getColumnIndexOrThrow("unit_name")));
                Log.d("unit", getUnits.getString(getUnits.getColumnIndexOrThrow("unit_name")));
            } while (getUnits.moveToNext());
        } else {
            Log.e("unit", "No unit found in database.");
        }
        getUnits.close();

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(unitAdapter);

        List<String> categories = new ArrayList<>();

        Cursor getCategories = databaseInStock.getAllCategories();
        if (getCategories.moveToFirst()) {
            do {
                categories.add(getCategories.getString(getCategories.getColumnIndexOrThrow("category_name")));
                Log.d("category", getCategories.getString(getCategories.getColumnIndexOrThrow("category_name")));
            } while (getCategories.moveToNext());
        } else {
            Log.e("category", "No categories found in database.");
        }
        getCategories.close();

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        FloatingActionButton close = findViewById(R.id.add_item_close_button);
        FloatingActionButton confirm = findViewById(R.id.add_item_confirm_button);

        close.setOnClickListener(e -> finish());

        confirm.setOnClickListener(e -> {
            if(name.getText().toString().isEmpty()){
                notification("Meno nemôže byť prázdne");
                return;
            } else if(!Pattern.matches("^(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[0-2])\\.(19|20)\\d{2}$", exp_date.getText())) {
                notification("Zlý formát dátumu expiracie (dd.mm.YYYY)");
                return;
            } else if (!Pattern.matches("^(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[0-2])\\.(19|20)\\d{2}$", buy_date.getText())){ //(\d{2}).(\d{2}).(\d{4}) (\d{2}):(\d{2}):(\d{2})
                notification("Zlý formát dátumu nákupu (dd.mm.YYYY hh:mm:ss)");
                return;
            } else if (quantity.getText().toString().isEmpty() && !Pattern.matches("(\\d)", quantity.getText()) && Integer.parseInt((String) quantity.getText()) > 0  ){
                notification("Zlý počet kusov");
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

    public void notification(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}

