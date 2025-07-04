package com.dolko.grocerymanager.receipts.receipt;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseReceipts;
import com.dolko.grocerymanager.FetchData;
import com.dolko.grocerymanager.receipts.ReceiptsFragment;

public class Receipt extends Fragment {

    DatabaseReceipts databaseReceipts;
    TextView name, date, price;
    Button save;
    public AdapterReceipt adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.layout_activity_receipt, container, false);

        name = view.findViewById(R.id.receipt_name);
        date = view.findViewById(R.id.receipt_creation_date);
        price = view.findViewById(R.id.receipt_price);
        save = view.findViewById(R.id.button_save_receipt);

        save.setOnClickListener(e ->{
            if(!databaseReceipts.checkExistence(FetchData.detail[3])){
                databaseReceipts.addReceipt(FetchData.detail[3], (FetchData.detail[1]), FetchData.detail[2], FetchData.detail[0]);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReceiptsFragment()).commit();
            }
            else
                Toast.makeText(getContext(), "Tento bloček už je uložený!", Toast.LENGTH_SHORT).show();
        });

        Bundle args = getArguments();
        Log.e("Error: ", String.valueOf(args));
        if (args != null) {
            String caller = args.getString("caller");
            if ("VISIBLE".equals(caller)) save.setVisibility(View.VISIBLE);
            else if ("GONE".equals(caller)) save.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        databaseReceipts = new DatabaseReceipts(getContext());
        adapter = new AdapterReceipt();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Cursor data = databaseReceipts.getReceipt(FetchData.detail[3]);
        if(data.getCount() > 0){
            data.moveToFirst();
            Log.d("data count", String.valueOf(data.getCount()));
            if(data.getString(data.getColumnIndexOrThrow("name")).isEmpty()
                    || data.getString(data.getColumnIndexOrThrow("add_date")).isEmpty()
                    || data.getString(data.getColumnIndexOrThrow("price")).isEmpty())
                databaseReceipts.updateReceipt(FetchData.detail[3], FetchData.detail[1], FetchData.detail[2], FetchData.detail[0]);
        }


        data.close();

        name.setText(FetchData.detail[0]);
        date.setText(FetchData.detail[1]);
        price.setText(String.format("%s €", FetchData.detail[2]));
    }


}
