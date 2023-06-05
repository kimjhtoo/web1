package com.tuflex.web.user.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tuflex.web.user.model.Hotel;
import com.tuflex.web.user.model.Payment;
import com.tuflex.web.user.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByHotel(Hotel hotel);
}