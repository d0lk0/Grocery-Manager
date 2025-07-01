package com.dolko.grocerymanager.shoppingcart;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingcartFragment extends Fragment {

    private DatabaseShoppingCart databaseShoppingCart;
    private AdapterShoppingcart adapterShoppingcart;
    private RecyclerView recyclerView;
    private List<String[]> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        return inflater.inflate(R.layout.layout_activity_shoppingcart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view_shopping_cart_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        items = getDataFromDBtoItems(getContext());
        adapterShoppingcart = new AdapterShoppingcart(items);
        recyclerView.setAdapter(adapterShoppingcart);

        Button addItem = view.findViewById(R.id.addItemToCart);
        addItem.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new AddItemToShoppingcartFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    private List<String[]> getDataFromDBtoItems(Context context) {
        List<String[]> result = new ArrayList<>();
        databaseShoppingCart = new DatabaseShoppingCart(context);
        Cursor data = databaseShoppingCart.getData();

        while (data.moveToNext()) {
            String[] tmp = new String[4];
            tmp[0] = data.getString(data.getColumnIndexOrThrow("product_name"));
            tmp[1] = data.getString(data.getColumnIndexOrThrow("quantity"));
            tmp[2] = data.getString(data.getColumnIndexOrThrow("id"));
            tmp[3] = data.getString(data.getColumnIndexOrThrow("signed"));
            result.add(tmp);
        }

        data.close();
        databaseShoppingCart.close();
        return result;
    }

    public void reloadData() {
        List<String[]> newItems = getDataFromDBtoItems(getContext());
        adapterShoppingcart.updateItems(newItems);
    }
}

