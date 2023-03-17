package com.dolko.grocerymanager.receipts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class AdapterReceipts extends RecyclerView.Adapter<ItemViewHolderReceipts> {

    @NonNull
    @Override
    public ItemViewHolderReceipts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_receipt, parent, false);
        return new ItemViewHolderReceipts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderReceipts holder, int position) {
        holder.name.setText(ReceiptsFragment.items.get(position)[0]);
        holder.date.setText(ReceiptsFragment.items.get(position)[1]);
        //holder.price.setText(ReceiptsFragment.items.get(position));
    };

    @Override
    public int getItemCount() {
        Log.e("size f", String.valueOf(ReceiptsFragment.items.size()));
        return ReceiptsFragment.items.size();
    }

}

