package com.dolko.grocerymanager.receipts;

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

    @NonNull
    @Override
    public ItemViewHolderReceipts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_receipt, parent, false);
        return new ItemViewHolderReceipts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolderReceipts holder, int position) {
        holder.name.setText(ReceiptsFragment.items.get(position)[0]);
        holder.date.setText(ReceiptsFragment.items.get(position)[1]);

        holder.itemView.setOnClickListener(view -> {
            if(Pattern.matches("[A-Z]-\\b[\\dA-F]{32}\\b", holder.name.getText())){
                try {
                    FetchData.getUrlContent(holder.name.getText().toString());
                    if(FetchData.detail != null){
                        ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Receipt()).commit();
                    }
                } catch (IOException | JSONException e) {
                    Log.e("Error: ", "Error scanning");
                    throw new RuntimeException(e);}
            }
        });
    }

    @Override
    public int getItemCount() {
        //Log.e("size f", String.valueOf(ReceiptsFragment.items.size()));
        return ReceiptsFragment.items.size();
    }

}

