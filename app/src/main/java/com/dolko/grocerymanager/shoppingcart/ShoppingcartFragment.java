package com.dolko.grocerymanager.shoppingcart;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingcartFragment extends Fragment {

    DatabaseShoppingCart databaseShoppingCart;
    private AdapterShoppingcart adapterShoppingcart;
    private RecyclerView recyclerView;

    public static List<String[]> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        return inflater.inflate(R.layout.fragment_shoppingcart, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        items.clear();

        databaseShoppingCart = new DatabaseShoppingCart(getContext());
        adapterShoppingcart = new AdapterShoppingcart();

        recyclerView = view.findViewById(R.id.recycler_view_shopping_cart_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterShoppingcart);

        String[] tmp;
        Cursor data = databaseShoppingCart.getData();

        while(data.moveToNext()) {
            tmp = new String[3];
            tmp[0] = data.getString(data.getColumnIndexOrThrow("product_name"));
            tmp[1] = data.getString(data.getColumnIndexOrThrow("quantity"));
            tmp[2] = data.getString(data.getColumnIndexOrThrow("id"));
            items.add(tmp);
            Log.e("items", Arrays.toString(tmp));
        }

        data.close();
    }


}
