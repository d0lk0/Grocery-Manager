package com.dolko.grocerymanager.scan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.activity.CaptActivity;
import com.dolko.grocerymanager.database.FetchData;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

public class ScanFragment extends Fragment {

    TextView text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false );
        //text = view.findViewById(R.id.text);
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

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->
    {
        if(result.getContents() != null) {
            if(Pattern.matches("[A-Z]-\b[\\dA-F]{32}\b", result.getContents())){
                try { // TODO: Rework
                    JSONObject json =  FetchData.getUrlContents(result.getContents());
                    assert json != null;
                    StringBuilder buildText = new StringBuilder("Name: " + json.getJSONObject("receipt").getJSONObject("organization").getString("name") + "\nTotal price: " + json.getJSONObject("receipt").getString("totalPrice") + "€ \nItems:");
                    for(int i = 0; json.getJSONObject("receipt").getJSONArray("items").length() > i;++i) {
                        buildText.append("\n").append(json.getJSONObject("receipt").getJSONArray("items").getJSONObject(i).getString("name"));
                        Log.println(Log.ERROR, "Text: ", json.getJSONObject("receipt").getJSONArray("items").getJSONObject(i).getString("name"));
                    }
                    text.setText(buildText.toString());
                } catch (IOException | JSONException e) {
                    Log.e("Error: ", "Error scanning");
                    throw new RuntimeException(e);}
            } else {
                Toast.makeText(getContext(), "Error wrong id of receipt!", Toast.LENGTH_SHORT).show();
            }
        }
    });


}
