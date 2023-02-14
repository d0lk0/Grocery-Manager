package com.dolko.grocerymanager.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseHelper;
import com.dolko.grocerymanager.shoppingcart.ShoppingcartFragment;

public class SettingsFragment extends Fragment {

    DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false );

        databaseHelper = new DatabaseHelper(getContext());

        Button btn_insert = view.findViewById(R.id.insert_items);
        Button btn_delete = view.findViewById(R.id.delete_items);

        btn_insert.setOnClickListener(e -> {
            databaseHelper.insertDBContent();
        });

        btn_delete.setOnClickListener(e -> {
            databaseHelper.deleteDBContent();
            ShoppingcartFragment.items.clear();
        });

        return view;
    }

}

