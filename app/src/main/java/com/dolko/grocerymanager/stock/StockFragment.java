package com.dolko.grocerymanager.stock;

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

import java.util.ArrayList;
import java.util.List;

public class StockFragment extends Fragment {
    private AdapterStock adapterStock;
    private RecyclerView recyclerView;

    public static List<String> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_stock, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapterStock = new AdapterStock();

        recyclerView = view.findViewById(R.id.recycler_view_shopping_cart_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterStock);

    }
    
}
