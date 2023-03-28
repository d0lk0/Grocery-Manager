package com.dolko.grocerymanager.shoppingcart;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class ItemViewHolderShoppingcart extends RecyclerView.ViewHolder {

    TextView name, count;
    ImageButton more;

    public ItemViewHolderShoppingcart(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        count = itemView.findViewById(R.id.quantity);
        more = itemView.findViewById(R.id.button_more_info);
    }
}