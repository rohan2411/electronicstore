package com.example.electronicstore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/customer/basket")
public class CustomerBasketController {
    private final Map<Long, List<Product>> customerBaskets = new ConcurrentHashMap<>();
    private final ReceiptService receiptService;

    public CustomerBasketController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("{customerId}/add")
    public ResponseEntity<String> addToBasket(@PathVariable Long customerId, @RequestBody Product product) {
        List<Product> basket = customerBaskets.computeIfAbsent(customerId, k -> new CopyOnWriteArrayList<>());
        basket.add(product);
        return ResponseEntity.ok("Product added to basket successfully");
    }

    @PostMapping("{customerId}/remove")
    public ResponseEntity<String> removeFromBasket(@PathVariable Long customerId, @RequestBody Product product) {
        List<Product> basket = customerBaskets.get(customerId);
        if (basket != null) {
            basket.remove(product);
            return ResponseEntity.ok("Product removed from basket successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{customerId}/receipt")
    public ResponseEntity<Receipt> calculateReceipt(@PathVariable Long customerId) {
        List<Product> basket = customerBaskets.get(customerId);
        if (basket != null) {
            Receipt receipt = receiptService.calculateReceipt(basket);
            return ResponseEntity.ok(receipt);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{customerId}/basket")
    public ResponseEntity<List<Product>> getBasket(@PathVariable Long customerId) {
        List<Product> basket = customerBaskets.get(customerId);
        if (basket != null) {
            return ResponseEntity.ok(basket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
