package com.dolko.grocerymanager.receipts.receipt;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.activity.AddActivity;
import com.dolko.grocerymanager.database.DatabaseInStock;
import com.dolko.grocerymanager.FetchData;

public class AdapterReceipt extends RecyclerView.Adapter<ItemViewHolderReceipt> {

    DatabaseInStock databaseInStock;

    @NonNull
    @Override
    public ItemViewHolderReceipt onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_receipt_item, parent, false);
        databaseInStock = new DatabaseInStock(view.getContext());
        return new ItemViewHolderReceipt(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderReceipt holder, int position) {
        Log.e("detail", FetchData.items.get(position));
        holder.item.setText(FetchData.items.get(position));
        holder.add_item.setOnClickListener(view ->{
            Intent myIntent = new Intent(view.getContext(), AddActivity.class);
            myIntent.putExtra("name", FetchData.items.get(position));
            myIntent.putExtra("date", (FetchData.detail[1]));
            view.getContext().startActivity(myIntent);
        });
    }

    @Override
    public int getItemCount() {
        Log.e("size f", String.valueOf(FetchData.items.size()));
        return FetchData.items.size();
    }

}

