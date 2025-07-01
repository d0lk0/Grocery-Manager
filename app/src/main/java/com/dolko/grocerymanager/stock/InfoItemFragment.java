package com.dolko.grocerymanager.stock;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseInStock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class InfoItemFragment extends Fragment {
    Spinner spinnerUnit, spinnerCategory;
    TextView name, quantity, exp_date, buy_date, description;
    FloatingActionButton close, save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.layout_activity_add, container, false);

        name = view.findViewById(R.id.item_name);
        quantity = view.findViewById(R.id.item_quantity);
        exp_date = view.findViewById(R.id.item_expiration_date);
        buy_date = view.findViewById(R.id.item_buy_date);
        description = view.findViewById(R.id.add_item_description);

        spinnerUnit = view.findViewById(R.id.item_unit);
        spinnerCategory = view.findViewById(R.id.item_category);

        save = view.findViewById(R.id.confirm_button);
        close = view.findViewById(R.id.close_button);

        name.setClickable(false);
        quantity.setClickable(false);
        exp_date.setClickable(false);
        buy_date.setClickable(false);
        description.setClickable(false);

        spinnerUnit.setVisibility(View.INVISIBLE);
        spinnerCategory.setVisibility(View.INVISIBLE);

        save.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        assert args != null;

        DatabaseInStock databaseInStock = new DatabaseInStock(getContext());
        Cursor data = databaseInStock.getItemByID(args.getString("id"));

        if(data.getCount() > 0){
            data.moveToFirst();
            Log.d("data count", String.valueOf(data.getCount()));
            name.setText(data.getString(data.getColumnIndexOrThrow("name")));
            quantity.setText(data.getString(data.getColumnIndexOrThrow("quantity")));
            exp_date.setText(data.getString(data.getColumnIndexOrThrow("exp_date")));
            buy_date.setText(data.getString(data.getColumnIndexOrThrow("buy_date")));
            description.setText(data.getString(data.getColumnIndexOrThrow("description")));
        }

        databaseInStock.close();

        close.setOnClickListener(e -> requireActivity().getSupportFragmentManager().popBackStack(null, POP_BACK_STACK_INCLUSIVE));

    }
}
