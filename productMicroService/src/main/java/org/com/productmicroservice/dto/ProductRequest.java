package org.com.productmicroservice.dto;

public record ProductRequest(
        String id,
        String name,
        double price,
        int quantity,
        Long categoryId
) {
}
