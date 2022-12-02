package org.com.productmicroservice.web;

import org.com.productmicroservice.dto.ProductRequest;
import org.com.productmicroservice.entities.Product;
import org.com.productmicroservice.repositories.CategoryRepository;
import org.com.productmicroservice.repositories.ProductRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ProductServiceGraphQLController {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceGraphQLController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @QueryMapping
    public List<Product> productList(){
        return productRepository.findAll();
    }
    @QueryMapping
    public Product productById(@Argument String id){
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("Product %s Not Found", id))
        );
    }
    @MutationMapping
    public Product saveProduct(@Argument ProductRequest productRequest){
        Product productToSave = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(productRequest.name())
                .price(productRequest.price())
                .quantity(productRequest.quantity())
                .category(categoryRepository.findById(productRequest.categoryId()).orElse(null))
                .build();
        productRepository.save(productToSave);
        return productToSave;
    }
    @MutationMapping
    public Product updateProduct(@Argument ProductRequest productRequest){
        Product productToSave = Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .price(productRequest.price())
                .quantity(productRequest.quantity())
                .category(categoryRepository.findById(productRequest.categoryId()).orElse(null))
                .build();
        productRepository.save(productToSave);
        return productToSave;
    }
    @MutationMapping
    public void deleteProduct(@Argument String id){
        productRepository.deleteById(id);
    }
}
