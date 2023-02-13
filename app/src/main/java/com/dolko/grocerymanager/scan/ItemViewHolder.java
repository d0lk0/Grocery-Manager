package com.dolko.grocerymanager.scan;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    TextView item;
    public ItemViewHolder(View itemView) {
        super(itemView);
        item = itemView.findViewById(R.id.item);
    }
}