package com.tuflex.web.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tuflex.web.user.model.ERole;
import com.tuflex.web.user.model.Hotel;
import com.tuflex.web.user.model.Review;
import com.tuflex.web.user.model.Role;
import com.tuflex.web.user.model.User;

// @Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserAndHotel(User user, Hotel hotel);

    List<Review> findByHotel(Hotel hotel);

    long countByHotelAndRate(Hotel hotel, Integer rate);
}