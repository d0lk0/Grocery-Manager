package com.dolko.grocerymanager.shoppingcart;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

public class AdapterShoppingcart extends RecyclerView.Adapter<ItemViewHolderShoppingcart> {

    @NonNull
    @Override
    public ItemViewHolderShoppingcart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_shoppingcart, parent, false);
        return new ItemViewHolderShoppingcart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderShoppingcart holder, int position) {
        holder.name.setText(ShoppingcartFragment.items.get(position));
        holder.count.setText(String.format("%s Ks", (Math.round(Math.random() * (7 - 1)) + 1)));

        holder.more.setOnClickListener(view ->{
            final Dialog dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_layout);

            LinearLayout editLayout = dialog.findViewById(R.id.layoutEdit);
            LinearLayout deleteLayout = dialog.findViewById(R.id.layoutDelete);

            editLayout.setOnClickListener(v -> {
                dialog.dismiss();
            });

            deleteLayout.setOnClickListener(v -> {
                removeItem(position);
                dialog.dismiss();
            });

            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        });
    }

    @Override
    public int getItemCount() {
        return ShoppingcartFragment.items.size();
    }

    public void removeItem(int position) {
        ShoppingcartFragment.items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ShoppingcartFragment.items.size());
    }

    public void restoreItem(String item, int position) {
        ShoppingcartFragment.items.add(position, item);
        notifyItemInserted(position);
    }
}

