package com.dolko.grocerymanager.receipts;

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
import com.dolko.grocerymanager.database.DatabaseReceipts;

import java.util.ArrayList;
import java.util.List;

public class ReceiptsFragment extends Fragment {
    DatabaseReceipts databaseReceipts;
    public AdapterReceipts adapter;
    static List<String[]> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        return inflater.inflate(R.layout.fragment_receipts, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        databaseReceipts = new DatabaseReceipts(getContext());
        adapter = new AdapterReceipts();

        items.clear();

        Cursor data = databaseReceipts.getAllReceipts();
        String[] tmp = new String[2];

        while (data.moveToNext()) {
            tmp[0] = data.getString(data.getColumnIndexOrThrow("receipt_id"));
            tmp[1] = data.getString(data.getColumnIndexOrThrow("add_date"));
            items.add(tmp);
        }

        data.close();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_receipts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

}
