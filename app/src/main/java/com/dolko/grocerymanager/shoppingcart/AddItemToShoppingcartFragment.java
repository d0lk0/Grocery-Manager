package com.dolko.grocerymanager.shoppingcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddItemToShoppingcartFragment extends Fragment {
    DatabaseShoppingCart databaseShoppingCart;
    TextView name, quantity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_item_to_shoppingcart, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onCreate(savedInstanceState);

        databaseShoppingCart = new DatabaseShoppingCart(getContext());

        name = view.findViewById(R.id.item_name);
        quantity = view.findViewById(R.id.item_quantity);
        FloatingActionButton close = view.findViewById(R.id.close_button);
        FloatingActionButton confirm = view.findViewById(R.id.confirm_button);

        close.setOnClickListener(e -> {
            requireActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });

        confirm.setOnClickListener(e -> {
            if(!name.getText().toString().isEmpty() && !quantity.getText().toString().isEmpty()){
                databaseShoppingCart.addItem(name.getText().toString(), quantity.getText().toString(), 0);
                requireActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {
                Toast.makeText(getContext(), "Zadané chybné údaje!", Toast.LENGTH_LONG).show();
            }
        });

        /*view.setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                requireActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return true;
            }
            return false;
        });*/
    }

}
