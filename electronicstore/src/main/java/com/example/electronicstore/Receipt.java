package com.example.electronicstore;

import java.util.List;

public class Receipt {
    private List<Product> purchases;
    private double totalPrice;

    public Receipt(List<Product> purchases, double totalPrice) {
        this.purchases = purchases;
        this.totalPrice = totalPrice;
    }

    public List<Product> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Product> purchases) {
        this.purchases = purchases;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "purchases=" + purchases +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
