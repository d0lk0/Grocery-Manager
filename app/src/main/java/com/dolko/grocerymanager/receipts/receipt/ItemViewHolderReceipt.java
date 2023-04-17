package com.dolko.grocerymanager.receipts.receipt;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class ItemViewHolderReceipt extends RecyclerView.ViewHolder {

    TextView item;
    ImageButton add_item;
    public ItemViewHolderReceipt(View itemView) {
        super(itemView);
        item = itemView.findViewById(R.id.item_content);
        add_item = itemView.findViewById(R.id.button_add_item_stock);
    }
}