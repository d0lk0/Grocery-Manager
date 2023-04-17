package com.dolko.grocerymanager.stock;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

import java.util.List;

public class NestedAdapterStock extends RecyclerView.Adapter<NestedAdapterStock.NestedViewHolderStock>{
    private List<String> mList;

    public NestedAdapterStock(List<String> mList){
        this.mList = mList;
    }

    @NonNull
    @Override
    public NestedViewHolderStock onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nested_stock , parent , false);
        return new NestedViewHolderStock(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolderStock holder, int position) {
        holder.name.setText(mList.get(position));
        holder.quantity.setText("");
        //holder.image.setImageResource();
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
                //databaseShoppingCart.removeItem(ShoppingcartFragment.items.get(position)[2], ShoppingcartFragment.items.get(position)[0], ShoppingcartFragment.items.get(position)[1]);
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
        return mList.size();
    }

    public void removeItem(int position) {
        mList.remove(position);
        Log.e("pos", String.valueOf(position));
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size());
    }

    public class NestedViewHolderStock extends RecyclerView.ViewHolder{
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
