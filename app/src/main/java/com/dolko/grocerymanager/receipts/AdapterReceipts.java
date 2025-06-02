package com.dolko.grocerymanager.receipts;

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
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseReceipts;
import com.dolko.grocerymanager.FetchData;
import com.dolko.grocerymanager.receipts.receipt.Receipt;

import org.json.JSONException;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

public class AdapterReceipts extends RecyclerView.Adapter<ItemViewHolderReceipts> {

    DatabaseReceipts databaseReceipts;

    @NonNull
    @Override
    public ItemViewHolderReceipts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_receipt, parent, false);
        databaseReceipts = new DatabaseReceipts(view.getContext());
        return new ItemViewHolderReceipts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderReceipts holder, int position) {
        String tmp_name = ReceiptsFragment.items.get(position)[0].toLowerCase();
        if(tmp_name.contains("billa")) holder.logo.setImageResource(R.mipmap.billa);
        else if(tmp_name.contains("coop")) holder.logo.setImageResource(R.mipmap.coop);
        else if(tmp_name.contains("fresh")) holder.logo.setImageResource(R.mipmap.fresh);
        else if(tmp_name.contains("kaufland")) holder.logo.setImageResource(R.mipmap.kaufland);
        else if(tmp_name.contains("milk-agro")) holder.logo.setImageResource(R.mipmap.milkagro);
        else if(tmp_name.contains("tesco")) holder.logo.setImageResource(R.mipmap.tesco);
        else holder.logo.setImageResource(R.mipmap.default_shop);

        holder.name.setText(ReceiptsFragment.items.get(position)[0]);
        holder.date.setText(ReceiptsFragment.items.get(position)[1]);
        holder.price.setText(ReceiptsFragment.items.get(position)[2]);
        holder.receipt_id.setText(ReceiptsFragment.items.get(position)[3]);

        holder.receipt_more_info.setOnClickListener(view ->{
            final Dialog dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_layout);
            dialog.findViewById(R.id.layoutEdit).setVisibility(View.GONE);

            LinearLayout deleteLayout = dialog.findViewById(R.id.layoutDelete);

            deleteLayout.setOnClickListener(v -> {
                databaseReceipts.removeReceipt(ReceiptsFragment.items.get(position)[3]);
                removeItem(position);
                dialog.dismiss();
            });

            dialog.show();
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        });

        holder.itemView.setOnClickListener(view -> {
            if(Pattern.matches("[A-Z]-\\b[\\dA-F]{32}\\b", holder.receipt_id.getText())){
                try {
                    Log.d("receipt id", holder.receipt_id.getText().toString());
                    FetchData.getUrlContent(holder.receipt_id.getText().toString());
                    if(FetchData.detail != null){
                        Receipt fragment = new Receipt();
                        Bundle args = new Bundle();
                        args.putString("caller", "GONE");
                        fragment.setArguments(args);
                        ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, fragment).addToBackStack( null ).commit();
                    }
                } catch (IOException | JSONException e) {
                    Log.e("Error: ", "Error scanning");
                    throw new RuntimeException(e);}
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("receipts item count", String.valueOf(ReceiptsFragment.items.size()));
        return ReceiptsFragment.items.size();
    }

    public void removeItem(int position) {
        ReceiptsFragment.items.remove(position);
        Log.e("pos", String.valueOf(position));
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ReceiptsFragment.items.size());
    }
}

