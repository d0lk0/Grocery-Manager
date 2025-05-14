package com.dolko.grocerymanager.overview;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseInStock;
import com.dolko.grocerymanager.database.DatabaseReceipts;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;

public class OverviewFragment extends Fragment {

    DatabaseShoppingCart databaseShoppingCart;
    DatabaseReceipts databaseReceipts;
    DatabaseInStock databaseInStock ;

    TextView shop1, shop2, shop3;
    TextView shopping1, shopping2, shopping3;
    TextView expire1, expire2, expire3;

    Button debug;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false );
        shop1 = view.findViewById(R.id.shopping_cart_1);
        shop2 = view.findViewById(R.id.shopping_cart_2);
        shop3 = view.findViewById(R.id.shopping_cart_3);

        shopping1 = view.findViewById(R.id.recent_shopping_1);
        shopping2 = view.findViewById(R.id.recent_shopping_2);
        shopping3 = view.findViewById(R.id.recent_shopping_3);

        expire1 = view.findViewById(R.id.nearest_to_expire_1);
        expire2 = view.findViewById(R.id.nearest_to_expire_2);
        expire3 = view.findViewById(R.id.nearest_to_expire_3);

        debug = view.findViewById(R.id.debug);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseReceipts = new DatabaseReceipts(getContext());

        Cursor data = databaseReceipts.getLimitedData(3);
        int i = 0;
        while(data.moveToNext()) {
            Log.e("data", String.valueOf(data.getString(data.getColumnIndexOrThrow("name"))));
            if(i == 0) shopping1.setText(data.getString(data.getColumnIndexOrThrow("name")));
            else if (i == 1) shopping2.setText(data.getString(data.getColumnIndexOrThrow("name")));
            else if (i == 2) shopping3.setText(data.getString(data.getColumnIndexOrThrow("name")));
            ++i;
        }
        data.close();
        databaseReceipts.close();

        databaseShoppingCart = new DatabaseShoppingCart(getContext());
        Cursor cart = databaseShoppingCart.getLimitedData(3);
        i = 0;
        while(cart.moveToNext()) {
            Log.e("cart", String.valueOf(cart.getString(cart.getColumnIndexOrThrow("product_name"))));
            if(i == 0) shop1.setText(cart.getString(cart.getColumnIndexOrThrow("product_name")));
            else if (i == 1) shop2.setText(cart.getString(cart.getColumnIndexOrThrow("product_name")));
            else if (i == 2) shop3.setText(cart.getString(cart.getColumnIndexOrThrow("product_name")));
            ++i;
        }
        cart.close();
        databaseShoppingCart.close();

        //TODO: Pridať zobrazovanie aj datumu a počet kusov

        databaseInStock = new DatabaseInStock(getContext());
        Cursor stock = databaseInStock.getLimitedData(3);
        i = 0;
        while(stock.moveToNext()) {
            Log.e("stock", String.valueOf(stock.getString(stock.getColumnIndexOrThrow("name"))));
            if(i == 0) expire1.setText(stock.getString(stock.getColumnIndexOrThrow("name")));
            else if (i == 1) expire2.setText(stock.getString(stock.getColumnIndexOrThrow("name")));
            else if (i == 2) expire3.setText(stock.getString(stock.getColumnIndexOrThrow("name")));
            ++i;
        }
        stock.close();
        databaseInStock.close();

        debug.setOnClickListener(v -> {
            databaseInStock.insertContent();
            //databaseReceipts.insertContent();
            databaseShoppingCart.insertDBContent();
        });
    }
}