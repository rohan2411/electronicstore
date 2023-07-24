package com.example.electronicstore;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong productIdGenerator = new AtomicLong();

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        long newProductId = productIdGenerator.incrementAndGet();
        product.setId(newProductId);
        products.put(newProductId, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Long productId) {
        Product removedProduct = products.remove(productId);
        if (removedProduct != null) {
            return ResponseEntity.ok("Product removed successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{productId}/add-discount")
    public ResponseEntity<String> addDiscountDeal(@PathVariable Long productId, @RequestParam double discountPercentage) {
        Product product = products.get(productId);
        if (product != null) {
            double discountedPrice = product.getPrice() * (1 - (discountPercentage / 100));
            product.setPrice(discountedPrice);
            return ResponseEntity.ok("Discount added successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
