package com.dolko.grocerymanager.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        databaseHelper = new DatabaseHelper(this);

        //Toast.makeText(this, getIntent().getStringExtra("key"), Toast.LENGTH_LONG).show();

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
}
