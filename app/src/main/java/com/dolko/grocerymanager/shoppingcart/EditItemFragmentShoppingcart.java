package com.dolko.grocerymanager.shoppingcart;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class EditItemFragmentShoppingcart extends Fragment {
    private DatabaseShoppingCart databaseShoppingCart;
    EditText quantity, name;
    FloatingActionButton save_nested_edit, exit_nested_edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_nested_item_edit, container, false);
        quantity = view.findViewById(R.id.item_nested_edit_quantity);
        name = view.findViewById(R.id.item_nested_edit_name);
        save_nested_edit = view.findViewById(R.id.button_save_stock_edit);
        exit_nested_edit = view.findViewById(R.id.button_exit_stock_edit);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseShoppingCart = new DatabaseShoppingCart(getContext());
        Bundle args = getArguments();
        assert args != null;

        name.setText(args.getString("name"));
        quantity.setText(args.getString("quantity"));

        save_nested_edit.setOnClickListener(e -> {

            if(!name.getText().toString().isEmpty() && !name.getText().toString().equals(args.getString("name"))){
                databaseShoppingCart.updateItemName(Integer.parseInt(Objects.requireNonNull(args.getString("id"))), name.getText().toString());
                Toast.makeText(getContext(), "Zmena názvu úspešne uložená!", Toast.LENGTH_SHORT).show();
            }

            if(!quantity.getText().toString().isEmpty() && !quantity.getText().toString().equals(args.getString("quantity"))){
                databaseShoppingCart.updateItemQuantity(Integer.parseInt(Objects.requireNonNull(args.getString("id"))), Integer.parseInt(quantity.getText().toString()));
                Toast.makeText(getContext(), "Zmena množstva úspešne uložená!", Toast.LENGTH_SHORT).show();
            }

            databaseShoppingCart.close();
            requireActivity().getSupportFragmentManager().popBackStack(null, POP_BACK_STACK_INCLUSIVE);
        });

        exit_nested_edit.setOnClickListener(e -> {
            databaseShoppingCart.close();
            requireActivity().getSupportFragmentManager().popBackStack(null, POP_BACK_STACK_INCLUSIVE);
        });

    }
}
