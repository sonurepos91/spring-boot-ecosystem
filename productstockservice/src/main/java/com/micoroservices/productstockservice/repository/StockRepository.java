package com.micoroservices.productstockservice.repository;

import com.micoroservices.productstockservice.models.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<ProductStock, Long> {

    Optional<ProductStock> findById (Long userId);

    Long countByProductName (String vechicleType);
}
