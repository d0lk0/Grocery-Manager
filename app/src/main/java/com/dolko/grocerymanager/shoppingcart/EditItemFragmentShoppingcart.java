package com.dolko.grocerymanager.shoppingcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;

public class EditItemFragmentShoppingcart extends Fragment {
    private DatabaseShoppingCart databaseShoppingCart;
    EditText ediText;
    Button save_nested_edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_nested_item_edit, container, false);
        ediText = view.findViewById(R.id.item_nested_edit_quantity);
        save_nested_edit = view.findViewById(R.id.button_save_stock_edit);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseShoppingCart = new DatabaseShoppingCart(getContext());

        save_nested_edit.setOnClickListener(e -> {
            if(!ediText.getText().toString().equals("")){
                Bundle args = getArguments();
                if (args != null) {
                    databaseShoppingCart.updateItem(args.getString("name"), Integer.parseInt(ediText.getText().toString()));
                    ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ShoppingcartFragment()).addToBackStack( null ).commit();
                }
            }
        });

    }
}
