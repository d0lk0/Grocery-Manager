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
    TextView expire1Date,expire2Date,expire3Date;

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

        expire1Date = view.findViewById(R.id.nearest_to_expire_1_date);
        expire2Date = view.findViewById(R.id.nearest_to_expire_2_date);
        expire3Date = view.findViewById(R.id.nearest_to_expire_3_date);

        debug = view.findViewById(R.id.debug);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseReceipts = new DatabaseReceipts(getContext());
        Cursor data = databaseReceipts.getLimitedData(3);

        for(int i=0;data.moveToNext();++i){
            Log.e("data", String.valueOf(data.getString(data.getColumnIndexOrThrow("name"))));
            if(i == 0) shopping1.setText(data.getString(data.getColumnIndexOrThrow("name")));
            else if (i == 1) shopping2.setText(data.getString(data.getColumnIndexOrThrow("name")));
            else if (i == 2) shopping3.setText(data.getString(data.getColumnIndexOrThrow("name")));
        }

        data.close();
        databaseReceipts.close();

        databaseShoppingCart = new DatabaseShoppingCart(getContext());
        Cursor cart = databaseShoppingCart.getLimitedData(3);

        for(int i=0;cart.moveToNext();++i){
            Log.e("cart", String.valueOf(cart.getString(cart.getColumnIndexOrThrow("product_name"))));
            if(i == 0) shop1.setText(cart.getString(cart.getColumnIndexOrThrow("product_name")));
            else if (i == 1) shop2.setText(cart.getString(cart.getColumnIndexOrThrow("product_name")));
            else if (i == 2) shop3.setText(cart.getString(cart.getColumnIndexOrThrow("product_name")));
        }

        cart.close();
        databaseShoppingCart.close();

        //TODO: Pridať zobrazovanie počet kusov

        databaseInStock = new DatabaseInStock(getContext());
        Cursor stock = databaseInStock.getTimeLimited(3);

        for(int i=0;stock.moveToNext();++i){
            Log.e("stock", String.valueOf(stock.getString(stock.getColumnIndexOrThrow("name"))));
            if(i == 0) {
                expire1.setText(stock.getString(stock.getColumnIndexOrThrow("name")));
                expire1Date.setText(stock.getString(stock.getColumnIndexOrThrow("exp_date")));
            }
            else if (i == 1) {
                expire2.setText(stock.getString(stock.getColumnIndexOrThrow("name")));
                expire2Date.setText(stock.getString(stock.getColumnIndexOrThrow("exp_date")));
            }
            else if (i == 2) {
                expire3.setText(stock.getString(stock.getColumnIndexOrThrow("name")));
                expire3Date.setText(stock.getString(stock.getColumnIndexOrThrow("exp_date")));
            }
        }

        if(expire1.getText().toString().equals("Zatiaľ žiadne")){
            expire1Date.setVisibility(View.GONE);
            expire2Date.setVisibility(View.GONE);
            expire3Date.setVisibility(View.GONE);
        } else if (expire2.getText().toString().equals("Zatiaľ žiadne")){
            expire2Date.setVisibility(View.GONE);
            expire3Date.setVisibility(View.GONE);
        } else if(expire3.getText().toString().equals("Zatiaľ žiadne")){
            expire3Date.setVisibility(View.GONE);
        } else {
            expire1Date.setVisibility(View.VISIBLE);
            expire2Date.setVisibility(View.VISIBLE);
            expire3Date.setVisibility(View.VISIBLE);
        }


        stock.close();
        databaseInStock.close();

        debug.setOnClickListener(v -> {
            databaseInStock.insertContent();
            databaseReceipts.insertContent();
            databaseShoppingCart.insertContent();
        });
    }
}