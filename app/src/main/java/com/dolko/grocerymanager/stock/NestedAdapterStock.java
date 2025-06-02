package com.dolko.grocerymanager.stock;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseInStock;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NestedAdapterStock extends RecyclerView.Adapter<NestedAdapterStock.NestedViewHolderStock>{
    private List<String[]> mList;
    DatabaseInStock databaseInStock;

    public NestedAdapterStock(List<String[]> mList){
        this.mList = mList;
    }

    @NonNull
    @Override
    public NestedViewHolderStock onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nested_stock , parent , false);
        databaseInStock = new DatabaseInStock(parent.getContext());
        return new NestedViewHolderStock(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolderStock holder, int position) {
        holder.name.setText(mList.get(position)[1]);
        holder.quantity.setText(String.format("%s ks", mList.get(position)[2]));

        Log.d("mList content", mList.stream().map(Arrays::toString).collect(Collectors.joining("\n")));

        holder.more.setOnClickListener(view ->{
            final Dialog dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_layout);

            LinearLayout editLayout = dialog.findViewById(R.id.layoutEdit);
            LinearLayout deleteLayout = dialog.findViewById(R.id.layoutDelete);
            LinearLayout infoLayout = dialog.findViewById(R.id.layoutInfo);

            infoLayout.setOnClickListener(v -> {
                InfoItemFragment fragment = new InfoItemFragment();
                Bundle args = new Bundle();
                args.putString("id", mList.get(position)[0]);
                fragment.setArguments(args);
                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack( null ).commit();
                dialog.dismiss();
            });

            editLayout.setOnClickListener(v -> {
                EditItemFragment fragment = new EditItemFragment();
                Bundle args = new Bundle();
                args.putString("id", mList.get(position)[0]);
                args.putString("name", mList.get(position)[1]);
                args.putString("quantity", mList.get(position)[2]);
                fragment.setArguments(args);
                ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack( null ).commit();
                dialog.dismiss();
            });

            deleteLayout.setOnClickListener(v -> {
                databaseInStock.deleteItem(mList.get(position)[0], mList.get(position)[1]); //"id", "name"
                removeItem(position);
                dialog.dismiss();
            });

            dialog.show();
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void removeItem(int position) {
        mList.remove(position);
        Log.e("pos", String.valueOf(position));
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size());
    }

    public static class NestedViewHolderStock extends RecyclerView.ViewHolder{
        private TextView name, quantity;
        private ImageView image;
        private ImageButton more;
        public NestedViewHolderStock(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_nested_stock);
            quantity = itemView.findViewById(R.id.quantity_nested_stock);
            image = itemView.findViewById(R.id.item_stock_nested_image);
            more = itemView.findViewById(R.id.button_more_info_stock);
        }
    }
}
