package com.dolko.grocerymanager.scan;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.ReceiptDetail;
import com.dolko.grocerymanager.database.FetchData;

import java.util.ArrayList;
import java.util.List;

public class AdapterScan extends RecyclerView.Adapter<ItemViewHolderScan> {

    private List<ReceiptDetail> items = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolderScan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_receipt_item, parent, false);
        return new ItemViewHolderScan(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderScan holder, int position) {
        Log.e("detail", FetchData.items.get(position).getDetail());
        holder.item.setText(FetchData.items.get(position).getDetail());
        holder.item.setText("text");
    };

    @Override
    public int getItemCount() {
        Log.e("size f", String.valueOf(FetchData.items.size()));
        return FetchData.items.size();
    }

    public void setData(List<ReceiptDetail> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}

