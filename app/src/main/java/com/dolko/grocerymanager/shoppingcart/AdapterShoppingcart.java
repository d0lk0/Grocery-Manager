package com.dolko.grocerymanager.shoppingcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class AdapterShoppingcart extends RecyclerView.Adapter<ItemViewHolderShoppingcart> {

    @NonNull
    @Override
    public ItemViewHolderShoppingcart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ItemViewHolderShoppingcart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderShoppingcart holder, int position) {
        holder.name.setText(ShoppingcartFragment.items.get(position));
        holder.count.setText(String.format("%s Ks", (Math.round(Math.random() * (7 - 1)) + 1)));
    }

    @Override
    public int getItemCount() {
        return ShoppingcartFragment.items.size();
    }

    public void removeItem(int position) {
        ShoppingcartFragment.items.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(String item, int position) {
        ShoppingcartFragment.items.add(position, item);
        notifyItemInserted(position);
    }

}

