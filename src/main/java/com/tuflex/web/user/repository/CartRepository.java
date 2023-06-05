package com.tuflex.web.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tuflex.web.user.model.Cart;
import com.tuflex.web.user.model.ERole;
import com.tuflex.web.user.model.Hotel;
import com.tuflex.web.user.model.Review;
import com.tuflex.web.user.model.Role;
import com.tuflex.web.user.model.User;

// @Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);

    void deleteByUser(User user);
}