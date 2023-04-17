package com.dolko.grocerymanager.stock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterStock extends RecyclerView.Adapter<AdapterStock.AdapterStockHolder>{

    private List<DataModelStock> mList;
    private List<String> list = new ArrayList<>();

    public AdapterStock(List<DataModelStock> mList){
        this.mList  = mList;
    }

    @NonNull
    @Override
    public AdapterStockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stock , parent , false);
        return new AdapterStockHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterStockHolder holder, int position) {
        DataModelStock model = mList.get(position);
        holder.mTextView.setText(model.getItemText());

        boolean isExpandable = model.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable){
            holder.mArrowImage.setImageResource(R.drawable.arrow_up);
        }else{
            holder.mArrowImage.setImageResource(R.drawable.arrow_down);
        }

        NestedAdapterStock adapter = new NestedAdapterStock(list);
        holder.nestedRecyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.nestedRecyclerView.setHasFixedSize(true);
        holder.nestedRecyclerView.setAdapter(adapter);
        holder.linearLayout.setOnClickListener(v -> {
            model.setExpandable(!model.isExpandable());
            list = model.getNestedList();
            notifyItemChanged(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class AdapterStockHolder extends RecyclerView.ViewHolder{
        private LinearLayout linearLayout;
        private RelativeLayout expandableLayout;
        private TextView mTextView;
        private ImageView mArrowImage;
        private RecyclerView nestedRecyclerView;


        public AdapterStockHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            mTextView = itemView.findViewById(R.id.item_stock_text_view);
            mArrowImage = itemView.findViewById(R.id.item_stock_arrow_image);
            nestedRecyclerView = itemView.findViewById(R.id.item_stock_child_recycler_view);
        }
    }
}
