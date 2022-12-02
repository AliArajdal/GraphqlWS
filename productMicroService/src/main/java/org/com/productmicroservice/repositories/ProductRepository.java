package org.com.productmicroservice.repositories;

import org.com.productmicroservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
}
