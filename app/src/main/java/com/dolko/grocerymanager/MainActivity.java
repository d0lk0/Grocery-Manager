package com.dolko.grocerymanager;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import com.dolko.grocerymanager.database.DatabaseShoppingCart;
import com.dolko.grocerymanager.database.DatabaseReceipts;
import com.dolko.grocerymanager.overview.OverviewFragment;
import com.dolko.grocerymanager.receipts.ReceiptsFragment;
import com.dolko.grocerymanager.scan.ScanFragment;
import com.dolko.grocerymanager.settings.SettingsFragment;
import com.dolko.grocerymanager.shoppingcart.ShoppingcartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    DatabaseShoppingCart databaseShoppingCart;
    DatabaseReceipts databaseReceipts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        databaseShoppingCart = new DatabaseShoppingCart(this);
        databaseReceipts = new DatabaseReceipts(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OverviewFragment()).commit();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setBackground(null);
        bottomNavigation.getMenu().getItem(2).setEnabled(false);
        bottomNavigation.setSelectedItemId(R.id.mOverview);

         /*String name = "Notifikácie";
        String description = "Dátum spotreby";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("id", name, importance);
        channel.setDescription(description);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

       NotificationCompat.Builder builder = new NotificationCompat
                .Builder(this, "id")
                .setSmallIcon(R.mipmap.ic_launcher_adaptive_fore)
                .setContentTitle("Upozornenie")
                .setContentText("Mlieko bude o 3 dni po záruke.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);*/

        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.mOverview:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OverviewFragment()).commit();
                    /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                        NotificationManagerCompat.from(this).notify(123, builder.build());
                    }*/
                    break;
                case R.id.mReceipts:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReceiptsFragment()).commit();
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

        FloatingActionButton scan = findViewById(R.id.menu_scan_button);
        scan.setOnClickListener(e -> getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScanFragment()).commit());
    }
}