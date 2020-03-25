package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("select prodName from Product where prodId=:prodId")
    public String findProdNameByProdId(Long prodId);
}
