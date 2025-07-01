package com.dolko.grocerymanager.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;
import com.dolko.grocerymanager.database.DatabaseReceipts;
import com.dolko.grocerymanager.shoppingcart.ShoppingcartFragment;

public class SettingsFragment extends Fragment {

    DatabaseShoppingCart databaseShoppingCart;
    DatabaseReceipts databaseReceipts;
    ShoppingcartFragment shoppingcartFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.layout_activity_settings, container, false );

        databaseShoppingCart = new DatabaseShoppingCart(getContext());
        databaseReceipts = new DatabaseReceipts(getContext());
        shoppingcartFragment = (ShoppingcartFragment) getParentFragmentManager().findFragmentById(R.id.fragment_container);

        Button btn_insert = view.findViewById(R.id.insert_items);
        Button btn_delete = view.findViewById(R.id.delete_items);

        btn_insert.setOnClickListener(e -> {
            databaseShoppingCart.insertContent();
        });

        btn_delete.setOnClickListener(e -> {
            databaseShoppingCart.deleteContent();
            if (shoppingcartFragment != null) shoppingcartFragment.reloadData();
        });

        return view;
    }

}

