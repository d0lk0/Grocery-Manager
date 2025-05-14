package com.dolko.grocerymanager.shoppingcart;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseShoppingCart;

import java.util.List;
import java.util.Objects;

public class AdapterShoppingcart extends RecyclerView.Adapter<ItemViewHolderShoppingcart> {

    private List<String[]> items;
    private DatabaseShoppingCart databaseShoppingCart;

    public AdapterShoppingcart(List<String[]> items) {
        this.items = items;
    }

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
        String[] item = items.get(position);
        holder.name.setText(item[0]);
        holder.count.setText(String.format("%s ks", item[1]));

        boolean isChecked = Integer.parseInt(item[3]) == 1;
        updateItemAppearance(holder, isChecked);

        holder.item_h.setOnClickListener(view -> {
            int currentState = Integer.parseInt(items.get(position)[3]);

            if(currentState == 0) {databaseShoppingCart.updateItemState(Integer.parseInt(items.get(position)[2]),
                    holder.name.getText().toString(),1);

                items.get(position)[3] = "1";

                holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.count.setPaintFlags(holder.count.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.item_h.setCardBackgroundColor(Color.parseColor("#4a4a4a"));
                holder.item_h.getBackground().setAlpha(100);

                holder.name.getBackground().setAlpha(100);
                holder.count.getBackground().setAlpha(100);
                holder.more.getBackground().setAlpha(100);
            } else {
                databaseShoppingCart.updateItemState(
                        Integer.parseInt(items.get(position)[2]),
                        holder.name.getText().toString(),
                        0
                );

                items.get(position)[3] = "0";

                holder.name.setPaintFlags(holder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.count.setPaintFlags(holder.count.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                holder.item_h.setCardBackgroundColor(Color.parseColor("#D9D9D9"));
                holder.item_h.getBackground().setAlpha(255);

                holder.name.getBackground().setAlpha(255);
                holder.count.getBackground().setAlpha(255);
                holder.more.getBackground().setAlpha(255);
            }

            notifyItemChanged(position);

            databaseShoppingCart.close();
        });

        holder.more.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_layout);
            LinearLayout deleteLayout = dialog.findViewById(R.id.layoutDelete);
            LinearLayout editLayout = dialog.findViewById(R.id.layoutEdit);

            deleteLayout.setOnClickListener(v -> {
                databaseShoppingCart.removeItem(item[2], item[0], item[1]);
                removeItem(position);
                dialog.dismiss();
            });

            editLayout.setOnClickListener(v -> {
                EditItemFragmentShoppingcart fragment = new EditItemFragmentShoppingcart();
                Bundle args = new Bundle();
                args.putString("id", item[2]);
                args.putString("name", item[0]);
                args.putString("quantity", item[1]);
                fragment.setArguments(args);

                if (view.getContext() instanceof AppCompatActivity) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
                dialog.dismiss();
            });

            dialog.show();
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public void updateItems(List<String[]> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    private void updateItemAppearance(ItemViewHolderShoppingcart holder, boolean isChecked) {
        if (isChecked) {
            holder.name.setPaintFlags(holder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.count.setPaintFlags(holder.count.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.item_h.setCardBackgroundColor(Color.parseColor("#4a4a4a"));
            setAlpha(holder, 100);
        } else {
            holder.name.setPaintFlags(holder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.count.setPaintFlags(holder.count.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.item_h.setCardBackgroundColor(Color.parseColor("#D9D9D9"));
            setAlpha(holder, 255);
        }
    }

    private void setAlpha(ItemViewHolderShoppingcart holder, int alpha) {
        holder.item_h.getBackground().setAlpha(alpha);
        holder.name.getBackground().setAlpha(alpha);
        holder.count.getBackground().setAlpha(alpha);
        holder.more.getBackground().setAlpha(alpha);
    }
}


