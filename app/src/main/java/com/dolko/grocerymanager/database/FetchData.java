package com.dolko.grocerymanager.database;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchData {

    public static JSONObject getUrlContents(String receiptId) throws IOException, JSONException {
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

                return new JSONObject(response);
            }
        } catch (Exception ignored) {}

        return null;
    }

    private String[] parseJson(){






        return new String[0];
    }

}

