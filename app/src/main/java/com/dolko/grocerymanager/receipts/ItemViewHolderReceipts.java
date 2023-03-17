package com.dolko.grocerymanager.receipts;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class ItemViewHolderReceipts extends RecyclerView.ViewHolder {

    TextView name, price, date;
    public ItemViewHolderReceipts(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.receipt_name);
        price = itemView.findViewById(R.id.receipt_price);
        date = itemView.findViewById(R.id.receipt_creation_date);
    }
}