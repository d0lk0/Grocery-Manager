package com.dolko.grocerymanager.shoppingcart;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ShoppingcartFragment extends Fragment {

    DatabaseHelper databaseHelper;
    private Adapter adapter;
    static List<String> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_shoppingcart, container, false );
        databaseHelper = new DatabaseHelper(getContext());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Cursor data = databaseHelper.getData();
        if (data.moveToFirst()) {
            do {
                int index = data.getColumnIndex("product_name");
                if (index != -1) {
                    items.add(data.getString(index));
                }
            } while (data.moveToNext());
        }
        data.close();

        adapter = new Adapter();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}
