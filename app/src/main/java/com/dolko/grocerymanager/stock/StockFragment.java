package com.dolko.grocerymanager.stock;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.activity.AddActivity;
import com.dolko.grocerymanager.database.DatabaseInStock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<DataModelStock> items;
    private AdapterStock adapter;
    private DatabaseInStock databaseInStock;

    Button b_add_item, b_add_category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        return inflater.inflate(R.layout.layout_activity_stock, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseInStock = new DatabaseInStock(getContext());

        b_add_item = view.findViewById(R.id.addItem);
        b_add_category = view.findViewById(R.id.addCategory);

        b_add_item.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), AddActivity.class);
            startActivity(intent);
        });

        b_add_category.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Pridanie kategórie");
            View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.layout_popup_text, (ViewGroup) getView(), false);
            final EditText input = viewInflated.findViewById(R.id.input);
            builder.setView(viewInflated);

            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                if(!input.getText().toString().isEmpty()) {
                    dialog.dismiss();
                    databaseInStock.insertCategory(input.getText().toString());
                    Toast.makeText(getContext(), "Úspešne pridaná kategória: " + input.getText().toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Nie je možné pridať kategóriu", Toast.LENGTH_LONG).show();
                }

                requireActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragment_container, new StockFragment()).commit();

            });
            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
            builder.show();
        });

        recyclerView = view.findViewById(R.id.recycler_view_stock);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        items = new ArrayList<>();

        List<String> categories = new ArrayList<>();

        Cursor getCategories = databaseInStock.getAllCategories();
        if (getCategories.moveToFirst()) {
            do {
                categories.add(getCategories.getString(getCategories.getColumnIndexOrThrow("category_name")));
                Log.d("category", getCategories.getString(getCategories.getColumnIndexOrThrow("category_name")));
            } while (getCategories.moveToNext());
        } else {
            Log.e("category", "No categories found in database.");
        }
        getCategories.close();

        List<String[]> temp_s = new ArrayList<>();

        for(String title : categories){
            Cursor data = databaseInStock.getCategoryItems(title);
            String[] tmp;

            while (data.moveToNext()) {
                tmp = new String[3];
                tmp[0] = data.getString(data.getColumnIndexOrThrow("id"));
                tmp[1] = data.getString(data.getColumnIndexOrThrow("name"));
                tmp[2] = data.getString(data.getColumnIndexOrThrow("quantity"));
                temp_s.add(tmp);
                Log.e("items", Arrays.toString(tmp));
            }
            data.close();

            items.add(new DataModelStock(temp_s, title));
            temp_s = new ArrayList<>();
        }

        adapter = new AdapterStock(items);
        recyclerView.setAdapter(adapter);
    }



}
