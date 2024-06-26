package com.apilivros.apilivros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilivros.apilivros.Domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
    
}
