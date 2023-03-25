package com.dolko.grocerymanager.receipts.receipt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseReceipts;
import com.dolko.grocerymanager.database.FetchData;
import com.dolko.grocerymanager.receipts.ReceiptsFragment;

public class Receipt extends Fragment {

    DatabaseReceipts databaseReceipts;
    TextView name, date, price;
    Button save;
    public AdapterReceipt adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);

        name = view.findViewById(R.id.receipt_name);
        date = view.findViewById(R.id.receipt_creation_date);
        price = view.findViewById(R.id.receipt_price);
        save = view.findViewById(R.id.button_save_receipt);

        save.setOnClickListener(e ->{
            databaseReceipts.addReceipt(FetchData.detail[3], (FetchData.detail[1]).replace("." ,"-"), FetchData.detail[2], FetchData.detail[0]);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReceiptsFragment()).commit();
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        databaseReceipts = new DatabaseReceipts(getContext());
        adapter = new AdapterReceipt();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        name.setText(FetchData.detail[0]);
        date.setText(FetchData.detail[1]);
        price.setText(FetchData.detail[2]);
    }
}
