package com.jumanji.capston.repository;

import com.jumanji.capston.data.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {


    List<Review> findAllByShopIdOrderByRegTimeDesc(String shopId);


    int countByIdStartingWith(String reviewId);
}
