package com.dolko.grocerymanager.stock;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dolko.grocerymanager.R;
import com.dolko.grocerymanager.database.DatabaseInStock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<DataModelStock> items;
    private AdapterStock adapter;
    private DatabaseInStock databaseInStock;

    private String[] titles = {"Pečivo", "Mäsové výrobky", "Ovocie a Zelenia", "Mrazené výrobky", "Cestoviny", "Mliečne výrobky", "Trvanlivé potraviny", "Nápoje"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        return inflater.inflate(R.layout.fragment_stock, container, false );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseInStock = new DatabaseInStock(getContext());

        recyclerView = view.findViewById(R.id.recycler_view_stock);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        items = new ArrayList<>();

        List<String[]> temp_s = new ArrayList<>();

        for(String title : titles){
            Cursor data = databaseInStock.getCategoryItems(title);
            String[] tmp;

            while (data.moveToNext()) {
                tmp = new String[2];
                tmp[0] = data.getString(data.getColumnIndexOrThrow("name"));
                tmp[1] = data.getString(data.getColumnIndexOrThrow("quantity"));
                temp_s.add(tmp);
                Log.e("items", Arrays.toString(tmp));
            }
            data.close();

            items.add(new DataModelStock(temp_s, title));
            temp_s = new ArrayList<>();
        }


        /*List<String> nestedList1 = new ArrayList<>();
        nestedList1.add("Chlieb");
        nestedList1.add("Rohlík");
        nestedList1.add("Bageta");
        nestedList1.add("Kaiserka");
        nestedList1.add("Croissant");
        nestedList1.add("Donut");
        nestedList1.add("Tortila");

        List<String> nestedList2 = new ArrayList<>();
        nestedList2.add("Hydina");
        nestedList2.add("Bravčové");
        nestedList2.add("Hovädzie");
        nestedList2.add("Párky");
        nestedList2.add("Klobásy");
        nestedList2.add("Salámy");
        nestedList2.add("Paštety");
        nestedList2.add("Ryby");

        List<String> nestedList3 = new ArrayList<>();
        nestedList3.add("Jablko");
        nestedList3.add("Hruška");
        nestedList3.add("Hrozno");
        nestedList3.add("Jahody");
        nestedList3.add("Melón");
        nestedList3.add("Mrkva");
        nestedList3.add("Mandarinka");
        nestedList3.add("Kapusta");

        List<String> nestedList4 = new ArrayList<>();
        nestedList4.add("Pizza");
        nestedList4.add("Ryby");
        nestedList4.add("Zmrzlina");
        nestedList4.add("Ľad");
        nestedList4.add("Zemiakové výrobky");

        List<String> nestedList5 = new ArrayList<>();
        nestedList5.add("Vaječné");

        List<String> nestedList6 = new ArrayList<>();
        nestedList6.add("Maslo");
        nestedList6.add("Nátierka");
        nestedList6.add("Syr");
        nestedList6.add("Vajcia");
        nestedList6.add("Jogurty");
        nestedList6.add("Smotana");

        List<String> nestedList7 = new ArrayList<>();
        nestedList7.add("Bazový sirup");
        nestedList7.add("Kokosový koktejl");
        nestedList7.add("Mysli sypané");

        List<String> nestedList8 = new ArrayList<>();
        nestedList8.add("Kofola");
        nestedList8.add("Pepsi");
        nestedList8.add("Coca-Cola");
        nestedList8.add("Vinea");
        nestedList8.add("Fanta");

        items.add(new DataModelStock(, "Pečivo"));
        items.add(new DataModelStock(nestedList2, "Mäsové výrobky"));
        items.add(new DataModelStock(nestedList3, "Ovocie a Zelenia"));
        items.add(new DataModelStock(nestedList4, "Mrazené výrobky"));
        items.add(new DataModelStock(nestedList5, "Cestoviny"));
        items.add(new DataModelStock(nestedList6, "Mliečne výrobky"));
        items.add(new DataModelStock(nestedList7, "Trvanlivé potraviny"));
        items.add(new DataModelStock(nestedList8, "Nápoje"));*/


        adapter = new AdapterStock(items);
        recyclerView.setAdapter(adapter);
    }

}
