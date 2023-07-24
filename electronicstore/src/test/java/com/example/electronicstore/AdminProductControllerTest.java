package com.example.electronicstore;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminProductController.class)
public class AdminProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Map<Long, Product> products;

    @Test
    public void testCreateProduct() throws Exception {

        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100.0);

        String requestBody = "{\"name\":\"Test Product\",\"price\":100.0}";


        mockMvc.perform(post("/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.0));
    }

    @Test
    public void testRemoveProduct() throws Exception {

        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setPrice(100.0);

        when(products.remove(productId)).thenReturn(product);


        mockMvc.perform(delete("/admin/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().string("Product removed successfully"));
    }

    @Test
    public void testRemoveNonExistingProduct() throws Exception {

        long productId = 1L;

        when(products.remove(productId)).thenReturn(null);


        mockMvc.perform(delete("/admin/products/{id}", productId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddDiscountDeal() throws Exception {

        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setPrice(100.0);

        when(products.get(productId)).thenReturn(product);


        mockMvc.perform(post("/admin/products/{id}/add-discount?discountPercentage=50", productId))
                .andExpect(status().isOk())
                .andExpect(content().string("Discount added successfully"))
                .andExpect(jsonPath("$.price").value(50.0));
    }

    @Test
    public void testAddDiscountDealForNonExistingProduct() throws Exception {

        long productId = 1L;

        when(products.get(productId)).thenReturn(null);


        mockMvc.perform(post("/admin/products/{id}/add-discount?discountPercentage=50", productId))
                .andExpect(status().isNotFound());
    }
}
