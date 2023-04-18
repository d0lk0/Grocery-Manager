package com.dolko.grocerymanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseInStock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends Activity implements AdapterView.OnItemSelectedListener {

    DatabaseInStock databaseInStock;
    Spinner spinnerUnit, spinnerCategory, spinnerCategorySpecific;
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
        spinnerCategorySpecific = findViewById(R.id.add_item_category_specific);

        spinnerUnit.setOnItemSelectedListener(this);
        spinnerCategory.setOnItemSelectedListener(this);
        spinnerCategorySpecific.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        buy_date.setText(intent.getStringExtra("date"));

        List<String> units = new ArrayList<>();
        units.add("Kg");
        units.add("Balenie");
        units.add("Ks");

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(unitAdapter);

        List<String> categories = new ArrayList<>();
        categories.add("Pečivo");
        categories.add("Mäsové výrobky");
        categories.add("Ovocie a Zelenia");
        categories.add("Mrazené výrobky");
        categories.add("Cestoviny");
        categories.add("Mliečne výrobky");
        categories.add("Trvanlivé potraviny");
        categories.add("Nápoje");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        List<String> nestedList1 = new ArrayList<>();
        nestedList1.add("Chlieb");
        nestedList1.add("Rohlík");
        nestedList1.add("Bageta");
        nestedList1.add("Kaiserka");
        nestedList1.add("Croissant");
        nestedList1.add("Donut");
        nestedList1.add("Tortila");

        List<String> nestedList2 = new ArrayList<>();
        nestedList2.add("Hydina");
        nestedList2.add("Bravčové");
        nestedList2.add("Hovädzie");
        nestedList2.add("Párky");
        nestedList2.add("Klobásy");
        nestedList2.add("Salámy");
        nestedList2.add("Paštety");
        nestedList2.add("Ryby");

        List<String> nestedList3 = new ArrayList<>();
        nestedList3.add("Jablko");
        nestedList3.add("Hruška");
        nestedList3.add("Hrozno");
        nestedList3.add("Jahody");
        nestedList3.add("Melón");
        nestedList3.add("Mrkva");
        nestedList3.add("Mandarinka");
        nestedList3.add("Kapusta");

        List<String> nestedList4 = new ArrayList<>();
        nestedList4.add("Pizza");
        nestedList4.add("Ryby");
        nestedList4.add("Zmrzlina");
        nestedList4.add("Ľad");
        nestedList4.add("Zemiakové výrobky");

        List<String> nestedList5 = new ArrayList<>();
        nestedList5.add("Vaječné");

        List<String> nestedList6 = new ArrayList<>();
        nestedList6.add("Maslo");
        nestedList6.add("Nátierka");
        nestedList6.add("Syr");
        nestedList6.add("Vajcia");
        nestedList6.add("Jogurty");
        nestedList6.add("Smotana");

        List<String> nestedList7 = new ArrayList<>();
        nestedList7.add("Bazový sirup");
        nestedList7.add("Kokosový koktejl");
        nestedList7.add("Mysli sypané");

        List<String> nestedList8 = new ArrayList<>();
        nestedList8.add("Kofola");
        nestedList8.add("Pepsi");
        nestedList8.add("Coca-Cola");
        nestedList8.add("Vinea");
        nestedList8.add("Fanta");

        ArrayAdapter<String> categorySpecificAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nestedList1);
        categorySpecificAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorySpecific.setAdapter(categorySpecificAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                ArrayAdapter<String> categorySpecificAdapter = null;
                if (position == 0) {
                    categorySpecificAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, nestedList1);
                } else if (position == 1) {
                    categorySpecificAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, nestedList2);
                } else if (position == 2) {
                    categorySpecificAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, nestedList3);
                } else if (position == 3) {
                    categorySpecificAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, nestedList4);
                } else if (position == 4) {
                    categorySpecificAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, nestedList5);
                } else if (position == 5) {
                   categorySpecificAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, nestedList6);
                } else if (position == 6) {
                    categorySpecificAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, nestedList7);
                } else if (position == 7) {
                    categorySpecificAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, nestedList8);
                }

                assert categorySpecificAdapter != null;
                categorySpecificAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategorySpecific.setAdapter(categorySpecificAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}

        });

        FloatingActionButton close = findViewById(R.id.add_item_close_button);
        FloatingActionButton confirm = findViewById(R.id.add_item_confirm_button);

        close.setOnClickListener(e -> {
            finish();
        });

        confirm.setOnClickListener(e -> {
            /*if(!Pattern.matches("(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})", exp_date.getText())) {
                Toast.makeText(getApplicationContext(), "Zlý formát dátum expiracie", Toast.LENGTH_LONG).show();
                return;
            } else if (!Pattern.matches("(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})", buy_date.getText())){
                Toast.makeText(getApplicationContext(), "Zlý formát dátumu nákupu", Toast.LENGTH_LONG).show();
                return;
            } else if (quantity.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Zlý počet kusov", Toast.LENGTH_LONG).show();
                return;
            }*/

            Log.e("itm",(spinnerCategorySpecific.getSelectedItem().toString() + " " + spinnerCategory.getSelectedItem().toString() + " " + Integer.parseInt(quantity.getText().toString())));
            databaseInStock.updateItem(spinnerCategorySpecific.getSelectedItem().toString(), Integer.parseInt(quantity.getText().toString()));

            /*Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 2023);
            calendar.set(Calendar.MONTH, 5); // +1
            calendar.set(Calendar.DAY_OF_MONTH, 18);
            calendar.set(Calendar.HOUR_OF_DAY, 21);
            calendar.set(Calendar.MINUTE, 45);

            Intent notificationIntent = new Intent(getApplicationContext(), MyNotificationReceiver.class);
            notificationIntent.putExtra("notificationMessage", "This is my notification message!");

            int notificationId = 1; // Unique id for the notification
            PendingIntent pendingIntent = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
            }
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }*/


            finish();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

