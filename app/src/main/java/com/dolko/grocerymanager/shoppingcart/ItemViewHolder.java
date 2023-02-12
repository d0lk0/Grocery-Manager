package com.dolko.grocerymanager.shoppingcart;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    TextView name, count, price;

    public ItemViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        count = itemView.findViewById(R.id.count);
        price = itemView.findViewById(R.id.price);
    }
}