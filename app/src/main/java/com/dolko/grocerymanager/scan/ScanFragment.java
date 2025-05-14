package com.dolko.grocerymanager.scan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.activity.CaptActivity;
import com.dolko.grocerymanager.database.DatabaseReceipts;
import com.dolko.grocerymanager.FetchData;
import com.dolko.grocerymanager.receipts.receipt.Receipt;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.regex.Pattern;

public class ScanFragment extends Fragment {

    DatabaseReceipts databaseReceipts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        databaseReceipts = new DatabaseReceipts(getContext());

        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up / down for flashlight");
        options.setBeepEnabled(false);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptActivity.class);
        barLauncher.launch(options);

        return inflater.inflate(R.layout.fragment_scan, container, false );
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result-> {
        if(result.getContents() == null) {
            Toast.makeText(getContext(), "Skúste znovu", Toast.LENGTH_SHORT).show();
            return;
        }

        if(Pattern.matches("[A-Z]-\\b[\\dA-F]{32}\\b", result.getContents())){
            try {
                FetchData.getUrlContent(result.getContents());
                if(FetchData.detail != null){
                    Receipt fragment = new Receipt();
                    Bundle args = new Bundle();
                    args.putString("caller", "VISIBLE");
                    fragment.setArguments(args);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }
            } catch (IOException | JSONException e) {
                Log.e("Error: ", "Error when fetching data");
                throw new RuntimeException(e);}
        } else {
            Toast.makeText(getContext(), "Chybný formát kódu bločku!", Toast.LENGTH_SHORT).show();
        }
    });
}
