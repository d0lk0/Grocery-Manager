package com.dolko.grocerymanager.receipts;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class ItemViewHolderReceipts extends RecyclerView.ViewHolder {

    TextView name, price, date, receipt_id;
    ImageView logo;
    public ItemViewHolderReceipts(View itemView) {
        super(itemView);
        logo = itemView.findViewById(R.id.receipt_logo);
        name = itemView.findViewById(R.id.receipt_name);
        price = itemView.findViewById(R.id.receipt_price);
        date = itemView.findViewById(R.id.receipt_creation_date);
        receipt_id = itemView.findViewById(R.id.receipt_id);
    }
}