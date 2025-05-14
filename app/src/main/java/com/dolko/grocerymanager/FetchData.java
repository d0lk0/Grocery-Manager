package com.dolko.grocerymanager;


import android.util.Log;

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
        detail = new String[4];

        if (receiptContent != null) {
            items.clear();
            try {
                detail[0] = receiptContent.getJSONObject("receipt").getJSONObject("organization").getString("name");
                detail[1] = receiptContent.getJSONObject("receipt").getString("createDate");
                detail[2] = receiptContent.getJSONObject("receipt").getString("totalPrice") + "€";
                detail[3] = receiptContent.getJSONObject("receipt").getString("receiptId");

                JSONArray itemsArray = receiptContent.getJSONObject("receipt").getJSONArray("items");

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject itemObject = itemsArray.getJSONObject(i);
                    String item_name = itemObject.getString("name");
                    if (item_name.contains("Zľava") || item_name.contains("zľava") || item_name.contains("VRATNÝ")) continue;
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
                System.out.print(items);
                Log.e("all ajtems", items.toString());
            } catch (JSONException e) {
                Log.e("Error: ", "Error scanning");
                throw new RuntimeException(e);
            }
        }
    }


}

