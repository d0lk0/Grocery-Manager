package com.dolko.grocerymanager.overview;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseReceipts;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;

public class OverviewFragment extends Fragment {

    DatabaseShoppingCart databaseShoppingCart;
    DatabaseReceipts databaseReceipts;

    TextView shop1, shop2, shop3;
    TextView shopping1, shopping2, shopping3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false );
        shop1 = view.findViewById(R.id.shopping_cart_1);
        shop2 = view.findViewById(R.id.shopping_cart_2);
        shop3 = view.findViewById(R.id.shopping_cart_3);

        shopping1 = view.findViewById(R.id.recent_shopping_1);
        shopping2 = view.findViewById(R.id.recent_shopping_2);
        shopping3 = view.findViewById(R.id.recent_shopping_3);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseShoppingCart = new DatabaseShoppingCart(getContext());
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

    }
}