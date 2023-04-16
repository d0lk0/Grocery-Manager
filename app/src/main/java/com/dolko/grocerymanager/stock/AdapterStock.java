package com.dolko.grocerymanager.stock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class AdapterStock extends RecyclerView.Adapter<ItemViewHolderStock>{

    @NonNull
    @Override
    public ItemViewHolderStock onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_shoppingcart, parent, false);
        return new ItemViewHolderStock(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderStock holder, int position) {
        holder.name.setText("");
        holder.count.setText("");
    }

    @Override
    public int getItemCount() {
        return StockFragment.items.size();
    }

    public void removeItem(int position) {
        StockFragment.items.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(String item, int position) {
        StockFragment.items.add(position, item);
        notifyItemInserted(position);
    }
}
