package com.dolko.grocerymanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import com.dolko.grocerymanager.activity.AddActivity;
import com.dolko.grocerymanager.database.DatabaseHelper;
import com.dolko.grocerymanager.overview.OverviewFragment;
import com.dolko.grocerymanager.scan.ScanFragment;
import com.dolko.grocerymanager.settings.SettingsFragment;
import com.dolko.grocerymanager.shoppingcart.ShoppingcartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        databaseHelper = new DatabaseHelper(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OverviewFragment()).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setBackground(null);
        bottomNavigation.getMenu().getItem(2).setEnabled(false);
        bottomNavigation.setSelectedItemId(R.id.mOverview);

        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mOverview:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OverviewFragment()).commit();
                    break;
                case R.id.mScan:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScanFragment()).commit();
                    break;
                case R.id.mShoppingcart:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShoppingcartFragment()).commit();
                    break;
                case R.id.mSettings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                    break;
            }
            return true;
        });

        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(e -> {
            Intent myIntent = new Intent(MainActivity.this, AddActivity.class);
            myIntent.putExtra("key", "kluč");
            MainActivity.this.startActivity(myIntent);
        });
    }
}