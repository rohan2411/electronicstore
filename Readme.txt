# Electronics Store Checkout System

This repository contains the backend code for a web-based electronics store's checkout system. The system provides RESTful endpoints for Admin User Operations and Customer Operations.

Endpoints

Admin User Operations

1. Create a new product (POST)

Invoke-RestMethod -Uri 'http://localhost:8080/admin/products' -Method Post -ContentType 'application/json' -Body '{
  "name": "Fan",
  "price": 1000
}'


2. Remove a product (DELETE)

Invoke-RestMethod -Uri 'http://localhost:8080/admin/products/{productId}' -Method Delete

3. Add discount deals for products (PUT)

Invoke-RestMethod -Uri 'http://localhost:8080/admin/products/{productId}/add-discount' -Method Put -ContentType 'application/json' -Body '{
  "discountPercentage": 20
}'

Customer Operations

1. Add a product to the basket (POST)

Invoke-RestMethod -Uri 'http://localhost:8080/customer/basket/{customerId}/add' -Method Post -ContentType 'application/json' -Body '{
  "name": "Air Conditioner",
  "price": 35000
}'


2. Remove a product from the basket (POST)

Invoke-RestMethod -Uri 'http://localhost:8080/customer/basket/{customerId}/remove' -Method Post -ContentType 'application/json' -Body '{
  "id": {productId},
  "name": "Air Conditioner",
  "price": 35000
}'

3. Get the customer's basket (GET)

Invoke-RestMethod -Uri 'http://localhost:8080/customer/basket/{customerId}/basket' -Method Get

4. Calculate the receipt (GET)

Invoke-RestMethod -Uri 'http://localhost:8080/customer/basket/{customerId}/receipt' -Method Get


