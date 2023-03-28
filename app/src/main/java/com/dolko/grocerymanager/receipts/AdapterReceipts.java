package com.dolko.grocerymanager.receipts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.FetchData;
import com.dolko.grocerymanager.receipts.receipt.Receipt;

import org.json.JSONException;

import java.io.IOException;
import java.util.regex.Pattern;

public class AdapterReceipts extends RecyclerView.Adapter<ItemViewHolderReceipts> {
    Receipt receipt;

    @NonNull
    @Override
    public ItemViewHolderReceipts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_receipt, parent, false);
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

        holder.name.setText(ReceiptsFragment.items.get(position)[0]);
        holder.date.setText(ReceiptsFragment.items.get(position)[1]);
        holder.price.setText(ReceiptsFragment.items.get(position)[2]);
        holder.receipt_id.setText(ReceiptsFragment.items.get(position)[3]);

        holder.itemView.setOnClickListener(view -> {
            if(Pattern.matches("[A-Z]-\\b[\\dA-F]{32}\\b", holder.receipt_id.getText())){
                try {
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

}

