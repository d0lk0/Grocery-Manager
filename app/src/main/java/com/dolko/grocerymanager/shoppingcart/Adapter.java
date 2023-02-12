package com.dolko.grocerymanager.shoppingcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class Adapter extends RecyclerView.Adapter<ItemViewHolder> {

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.name.setText(ShoppingcartFragment.items.get(position));
    }

    @Override
    public int getItemCount() {
        return ShoppingcartFragment.items.size();
    }
}

