package com.dolko.grocerymanager.receipts;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Arrays;

public class ReceiptsFragment extends Fragment {
    DatabaseReceipts databaseReceipts;
    public AdapterReceipts adapter;
    static ArrayList<String[]> items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        return inflater.inflate(R.layout.fragment_receipts, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        items.clear();

        adapter = new AdapterReceipts();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_receipts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        databaseReceipts = new DatabaseReceipts(getContext());
        Cursor data = databaseReceipts.getAllReceipts();

        String[] tmp;

        while(data.moveToNext()) {
            tmp = new String[2];
            tmp[0] = data.getString(data.getColumnIndexOrThrow("receipt_id"));
            tmp[1] = data.getString(data.getColumnIndexOrThrow("add_date"));
            Log.e("data:", Arrays.toString(tmp));
            items.add(tmp);
        }

        data.close();
    }

}
