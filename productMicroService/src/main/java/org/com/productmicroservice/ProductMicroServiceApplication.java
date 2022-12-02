package org.com.productmicroservice;

import org.com.productmicroservice.entities.Category;
import org.com.productmicroservice.entities.Product;
import org.com.productmicroservice.repositories.CategoryRepository;
import org.com.productmicroservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class ProductMicroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductMicroServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository, CategoryRepository categoryRepository){
        return args -> {
            List.of("Computer", "Printer", "Smartphone").forEach(
                    cat -> {
                        Category category = Category.builder().name(cat).build();
                        categoryRepository.save(category);
                    }
            );
            Random random = new Random();
            categoryRepository.findAll().forEach(cat -> {
                for (int i = 0; i < 10; i++) {
                    Product product = Product.builder()
                            .id(UUID.randomUUID().toString())
                            .name(cat.getName()+" "+i)
                            .price(Math.random() * 10000)
                            .quantity(random.nextInt(100))
                            .category(cat)
                            .build();
                    productRepository.save(product);
                }
            });
        };
    }
}
