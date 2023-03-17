package com.dolko.grocerymanager.receipts.receipt;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.FetchData;

public class AdapterReceipt extends RecyclerView.Adapter<ItemViewHolder> {

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_receipt_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Log.e("detail", FetchData.items.get(position));
        holder.item.setText(FetchData.items.get(position));
    };

    @Override
    public int getItemCount() {
        Log.e("size f", String.valueOf(FetchData.items.size()));
        return FetchData.items.size();
    }

}

