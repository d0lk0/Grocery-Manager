package com.dolko.grocerymanager.scan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.FetchData;

public class Adapter extends RecyclerView.Adapter<ItemViewHolder> {

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_receipt_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if(FetchData.items != null) holder.item.setText(FetchData.items.get(position));
    }

    @Override
    public int getItemCount() {
        Log.e("data: ", String.valueOf(FetchData.items.size()));
        if(FetchData.items != null) return FetchData.items.size();
        return 0;
    }
}

