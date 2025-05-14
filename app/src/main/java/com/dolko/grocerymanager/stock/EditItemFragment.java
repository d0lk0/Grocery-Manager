package com.dolko.grocerymanager.stock;

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
import com.dolko.grocerymanager.database.DatabaseInStock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class EditItemFragment extends Fragment {
    private DatabaseInStock databaseInStock;

    EditText editQuantity;
    EditText editName;
    FloatingActionButton save_stock_edit, exit_stock_edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_nested_item_edit, container, false);

        editName = view.findViewById(R.id.item_nested_edit_name);
        editQuantity = view.findViewById(R.id.item_nested_edit_quantity);
        save_stock_edit = view.findViewById(R.id.button_save_stock_edit);
        exit_stock_edit = view.findViewById(R.id.button_exit_stock_edit);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        assert args != null;

        //TODO: Treba pridať všetky informacie aku su pri add_item, tak aby sa dalo ediťovať všetko

        editName.setText(args.getString("name"));
        editQuantity.setText(args.getString("quantity"));

        databaseInStock = new DatabaseInStock(getContext());

        save_stock_edit.setOnClickListener(e -> {

            if(!editName.getText().toString().isEmpty() && !editName.getText().toString().equals(args.getString("name"))){
                databaseInStock.updateItemName(Integer.parseInt(Objects.requireNonNull(args.getString("id"))), editName.getText().toString());
                Toast.makeText(getContext(), "Zmena názvu úspešne uložená!", Toast.LENGTH_SHORT).show();
            }

            if(!editQuantity.getText().toString().isEmpty() && !editQuantity.getText().toString().equals(args.getString("quantity"))){
                databaseInStock.updateItemQuantity(Integer.parseInt(Objects.requireNonNull(args.getString("id"))), Integer.parseInt(editQuantity.getText().toString()));
                Toast.makeText(getContext(), "Zmena množstva úspešne uložená!", Toast.LENGTH_SHORT).show();
            }

            databaseInStock.close();
            requireActivity().getSupportFragmentManager().popBackStack(null, POP_BACK_STACK_INCLUSIVE);
        });

        exit_stock_edit.setOnClickListener(e -> {
            databaseInStock.close();
            requireActivity().getSupportFragmentManager().popBackStack(null, POP_BACK_STACK_INCLUSIVE);
        });



    }
}
