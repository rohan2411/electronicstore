package com.example.electronicstore;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerBasketController.class)
public class CustomerBasketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptService receiptService;

    @Test
    public void testAddToBasket() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(100.0);

        String requestBody = "{\"name\":\"Test Product\",\"price\":100.0}";

        mockMvc.perform(post("/customer/basket/{customerId}/add", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added to basket successfully"));
    }

    @Test
    public void testRemoveFromBasket() throws Exception {
        // Arrange
        long customerId = 100L;
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(100.0);

        List<Product> basket = new ArrayList<>();
        basket.add(product);

        when(receiptService.calculateReceipt(basket)).thenReturn(new Receipt(basket, 100.0));
        when(receiptService.calculateReceipt(new ArrayList<>())).thenReturn(new Receipt(new ArrayList<>(), 0.0));

        // Act & Assert
        mockMvc.perform(post("/customer/basket/{customerId}/remove", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"name\":\"Test Product\",\"price\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product removed from basket successfully"));

        mockMvc.perform(post("/customer/basket/{customerId}/receipt", customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchases", hasSize(0)))
                .andExpect(jsonPath("$.totalPrice").value(0.0));
    }
}

