package com.example.electronicstore;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptService {

    // Apply discount on every nth product (e.g., Buy 1 get 50% off the second)
    private static final int NTH_PRODUCT_DISCOUNT = 2;
    private static final double NTH_PRODUCT_DISCOUNT_PERCENTAGE = 50.0;

    public Receipt calculateReceipt(List<Product> products) {
        List<Product> purchases = new ArrayList<>(products);
        calculateTotalPrice(purchases);
        double totalPrice;

        // Apply discount for every Nth product
        int discountCount = purchases.size() / NTH_PRODUCT_DISCOUNT;
        for (int i = 1; i <= discountCount; i++) {
            int index = i * NTH_PRODUCT_DISCOUNT - 1;
            Product product = purchases.get(index);
            double discountAmount = product.getPrice() * (NTH_PRODUCT_DISCOUNT_PERCENTAGE / 100);
            product.setPrice(product.getPrice() - discountAmount);
        }

        totalPrice = calculateTotalPrice(purchases); // Recalculate the total price after applying discounts

        return new Receipt(purchases, totalPrice);
    }

    private double calculateTotalPrice(List<Product> products) {
        double totalPrice = 0;
        for (Product product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }
}

