package com.dolko.grocerymanager.scan;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class ItemViewHolderScan extends RecyclerView.ViewHolder {

    TextView item;
    public ItemViewHolderScan(View itemView) {
        super(itemView);
        item = itemView.findViewById(R.id.item_content);
    }
}