package com.dolko.grocerymanager;

import com.google.gson.annotations.SerializedName;

public class ReceiptDetail {

    @SerializedName("name")
    private String name;

    @SerializedName("createDate")
    private String date;

    @SerializedName("totalPrice")
    private String price;

    public String getName() {return name;}

    public void setName(long id) {this.name = name;}

    public String getDate() {
        return date;
    }

    public void setDate(String email) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
