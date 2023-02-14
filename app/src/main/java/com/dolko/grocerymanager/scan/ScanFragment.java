package com.dolko.grocerymanager.scan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.activity.CaptActivity;
import com.dolko.grocerymanager.database.DatabaseReceipts;
import com.dolko.grocerymanager.database.FetchData;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;

import java.io.IOException;

public class ScanFragment extends Fragment {

    DatabaseReceipts databaseReceipts;
    TextView name, date, price;
    AdapterScan adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false );
        databaseReceipts = new DatabaseReceipts(getContext());

        name = view.findViewById(R.id.receipt_name);
        date = view.findViewById(R.id.receipt_creation_date);
        price = view.findViewById(R.id.receipt_price);

        adapter = new AdapterScan();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_receipts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up / down for flashlight");
        options.setBeepEnabled(false);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptActivity.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result-> {
        if(result.getContents() != null) {
            /*if(Pattern.matches("[A-Z]-\b[\\dA-F]{32}\b", result.getContents())){
            } else {
                Toast.makeText(getContext(), "Error wrong id of receipt!", Toast.LENGTH_SHORT).show();
            }*/
            try {
                FetchData.getUrlContent(result.getContents());
                if(FetchData.detail != null){
                    name.setText(FetchData.detail[0]);
                    date.setText(FetchData.detail[1]);
                    price.setText(FetchData.detail[2]);
                    adapter.setData(FetchData.items);
                }
            } catch (IOException | JSONException e) {
                Log.e("Error: ", "Error scanning");
                throw new RuntimeException(e);}
        }
    });


}
