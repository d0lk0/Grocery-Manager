package com.dolko.grocerymanager.receipts;

import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseReceipts;

import java.util.ArrayList;

public class ReceiptsFragment extends Fragment {
    DatabaseReceipts databaseReceipts;
    public AdapterReceipts adapter;
    static ArrayList<String[]> items = new ArrayList<>();

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_receipts, container, false );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        items.clear();

        adapter = new AdapterReceipts();

        recyclerView = view.findViewById(R.id.recycler_view_receipts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        databaseReceipts = new DatabaseReceipts(getContext());
        Cursor data = databaseReceipts.getAllReceipts();

        while(data.moveToNext()) {
            String[] tmp = new String[4];
            tmp[0] = data.getString(data.getColumnIndexOrThrow("name"));
            tmp[1] = data.getString(data.getColumnIndexOrThrow("add_date"));
            tmp[2] = data.getString(data.getColumnIndexOrThrow("price"));
            tmp[3] = data.getString(data.getColumnIndexOrThrow("receipt_id"));
            items.add(tmp);
        }

        data.close();

        view.setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                requireActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                return true;
            }
            return false;
        });
    }

}
