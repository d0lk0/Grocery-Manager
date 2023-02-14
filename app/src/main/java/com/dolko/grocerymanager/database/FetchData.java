package com.dolko.grocerymanager.database;


import android.util.Log;

import com.dolko.grocerymanager.scan.AdapterScan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchData  {

    public static JSONObject data;
    public static  String[] detail;
    public static List<String> items = new ArrayList<>();

    static AdapterScan adapterScan;

    public static void getUrlContent(String receiptId) throws IOException, JSONException {
        try {
            URL url = new URL("https://ekasa.financnasprava.sk/mdu/api/v1/opd/receipt/find");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write("{\"receiptId\":\"" + receiptId + "\"}");
            wr.flush();
            wr.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                String response = sb.toString();

                conn.disconnect();
                data = new JSONObject(response);
                getData(data);
            }
        } catch (Exception ignored) {}
    }

    public static void getData(JSONObject receiptContent) {
        detail = new String[3];

        if (receiptContent != null) {
            try {
                String name = receiptContent.getJSONObject("receipt").getJSONObject("organization").getString("name");
                String date = receiptContent.getJSONObject("receipt").getString("createDate");
                String price = receiptContent.getJSONObject("receipt").getString("totalPrice") + "€";

                detail[0] = name;
                detail[1] = date;
                detail[2] = price;

                JSONArray itemsArray = receiptContent.getJSONObject("receipt").getJSONArray("items");
                int itemsArrayLength = itemsArray.length();
                for (int i = 0; i < itemsArrayLength; i++) {
                    JSONObject itemObject = itemsArray.getJSONObject(i);
                    String item_name = itemObject.getString("name");
                    int item_quantity = itemObject.getInt("quantity");
                    double item_price = itemObject.getDouble("price");
                    float pricePerItem = BigDecimal.valueOf(item_price)
                            .divide(BigDecimal.valueOf(item_quantity), 2, RoundingMode.HALF_UP)
                            .floatValue();
                    String itemString = item_name + "\n \t" +
                            item_quantity + "ks * " + pricePerItem + " | " + item_price + "€";

                    items.add(itemString);
                    Log.e("item",i + " " + itemString);
                }
                Log.e("all ajtems", items.toString());
            } catch (JSONException e) {
                Log.e("Error: ", "Error scanning");
                throw new RuntimeException(e);
            }
        }
    }


}

