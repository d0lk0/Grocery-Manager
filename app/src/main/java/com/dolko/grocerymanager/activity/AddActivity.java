package com.dolko.grocerymanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.dolko.grocerymanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerUnit, spinnerCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        spinnerUnit = findViewById(R.id.add_item_unit);
        spinnerCategory = findViewById(R.id.add_item_category);

        spinnerUnit.setOnItemSelectedListener(this);
        spinnerCategory.setOnItemSelectedListener(this);

        List<String> units = new ArrayList<>();
        units.add("Kg");
        units.add("Balenie");
        units.add("Ks");

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(unitAdapter);

        List<String> categories = new ArrayList<>();
        categories.add("Ovocie");
        categories.add("Mäso");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        FloatingActionButton close = findViewById(R.id.add_item_close_button);
        FloatingActionButton confirm = findViewById(R.id.add_item_confirm_button);

        close.setOnClickListener(e -> {
            finish();
        });

        confirm.setOnClickListener(e -> {
            //TODO: Save to DB
            finish();
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
