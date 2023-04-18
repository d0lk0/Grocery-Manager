package com.dolko.grocerymanager.shoppingcart;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;

public class AdapterShoppingcart extends RecyclerView.Adapter<ItemViewHolderShoppingcart> {

    DatabaseShoppingCart databaseShoppingCart;

    @NonNull
    @Override
    public ItemViewHolderShoppingcart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_shoppingcart, parent, false);
        databaseShoppingCart = new DatabaseShoppingCart(parent.getContext());
        return new ItemViewHolderShoppingcart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderShoppingcart holder, int position) {
        holder.name.setText(ShoppingcartFragment.items.get(position)[0]);
        holder.count.setText(String.format("%s ks", ShoppingcartFragment.items.get(position)[1]));

        holder.item_h.setOnClickListener(view -> { //TODO: ked bude vyše ako 1 kus tak dať okno, kolko kusov treba odškrtnuť
            Log.e("hol:", String.valueOf(holder.name.getPaintFlags()));
            if(holder.name.getPaintFlags() != 1299){
                holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.count.setPaintFlags(holder.count.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.item_h.setCardBackgroundColor(Color.parseColor("#43a81e"));
                holder.item_h.getBackground().setAlpha(100);
            } else {
                holder.name.setPaintFlags(holder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.count.setPaintFlags(holder.count.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.item_h.setCardBackgroundColor(Color.parseColor("#D9D9D9"));
                holder.item_h.getBackground().setAlpha(255);
            }
        });

        holder.more.setOnClickListener(view ->{
            final Dialog dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_layout);

            LinearLayout deleteLayout = dialog.findViewById(R.id.layoutDelete);

            dialog.findViewById(R.id.layoutEdit).setVisibility(View.GONE);

            deleteLayout.setOnClickListener(v -> {
                holder.name.setPaintFlags(holder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.count.setPaintFlags(holder.count.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.item_h.setCardBackgroundColor(Color.parseColor("#D9D9D9"));
                holder.item_h.getBackground().setAlpha(255);

                databaseShoppingCart.removeItem(ShoppingcartFragment.items.get(position)[2], ShoppingcartFragment.items.get(position)[0], ShoppingcartFragment.items.get(position)[1]);
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
        Log.e("shoppingcartItemCount", String.valueOf(ShoppingcartFragment.items.size()));
        return ShoppingcartFragment.items.size();
    }

    public void removeItem(int position) {
        ShoppingcartFragment.items.remove(position);
        Log.e("pos", String.valueOf(position));
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ShoppingcartFragment.items.size());
    }


}

