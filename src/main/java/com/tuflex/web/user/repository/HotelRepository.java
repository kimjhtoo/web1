package com.tuflex.web.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tuflex.web.user.model.ERole;
import com.tuflex.web.user.model.Hotel;
import com.tuflex.web.user.model.Role;

// @Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query(value = "select *, (6371*acos(cos(radians(:lat))*cos(radians(latitude))*cos(radians(longitude)-radians(:lon))+sin(radians(:lat))*sin(radians(latitude)))) AS distance from hotel having distance < 3 order by distance DESC", nativeQuery = true)
    List<Hotel> findByLocation(@Param("lat") Double lat, @Param("lon") Double lon);
}