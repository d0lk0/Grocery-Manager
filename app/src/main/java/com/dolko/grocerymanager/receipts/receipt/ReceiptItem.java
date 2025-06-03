package com.dolko.grocerymanager.receipts.receipt;

public class ReceiptItem {
    private String name;
    private String addDate;
    private String price;
    private String receipt_id;

    // Constructor
    public ReceiptItem(String receipt_id, String addDate, String price, String name) {
        this.name = name;
        this.addDate = addDate;
        this.price = price;
        this.receipt_id = receipt_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReceiptId() {
        return receipt_id;
    }

    public void setReceiptId(String receiptId) {
        this.receipt_id = receipt_id;
    }
}