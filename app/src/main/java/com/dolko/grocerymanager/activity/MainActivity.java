package com.dolko.grocerymanager.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseReceipts;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;
import com.dolko.grocerymanager.overview.OverviewFragment;
import com.dolko.grocerymanager.receipts.ReceiptsFragment;
import com.dolko.grocerymanager.scan.ScanFragment;
import com.dolko.grocerymanager.shoppingcart.ShoppingcartFragment;
import com.dolko.grocerymanager.stock.StockFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    DatabaseShoppingCart databaseShoppingCart;
    DatabaseReceipts databaseReceipts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.parseColor("#000000"));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        databaseShoppingCart = new DatabaseShoppingCart(this);
        databaseReceipts = new DatabaseReceipts(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OverviewFragment()).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setBackground(null);
        bottomNavigation.getMenu().getItem(2).setEnabled(false);
        bottomNavigation.setSelectedItemId(R.id.mOverview);

        bottomNavigation.setOnItemSelectedListener(item -> {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            int id = item.getItemId();
            if(id == R.id.mOverview)
                getSupportFragmentManager().beginTransaction().disallowAddToBackStack().replace(R.id.fragment_container, new OverviewFragment()).commit();
            else if(id == R.id.mReceipts)
                getSupportFragmentManager().beginTransaction().disallowAddToBackStack().replace(R.id.fragment_container, new ReceiptsFragment()).commit();
            else if(id == R.id.mShoppingcart)
                getSupportFragmentManager().beginTransaction().disallowAddToBackStack().replace(R.id.fragment_container, new ShoppingcartFragment()).commit();
            else if(id == R.id.mStockAtHome)
                getSupportFragmentManager().beginTransaction().disallowAddToBackStack().replace(R.id.fragment_container, new StockFragment()).commit();
            return true;
        });

        FloatingActionButton scan = findViewById(R.id.menu_scan_button);
        scan.setOnClickListener(e -> getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScanFragment()).commit());
    }

}