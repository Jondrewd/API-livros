package com.apilivros.apilivros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apilivros.apilivros.Domain.Review;
import com.apilivros.apilivros.Domain.pk.ReviewID;

import jakarta.transaction.Transactional;

public interface ReviewRepository extends JpaRepository<Review, ReviewID>{

    @Query("SELECT r FROM Review r WHERE r.id.bookId = :bookId AND r.id.userId = :userId")
    Review findByBookAndUser(@Param("bookId") Integer bookId, @Param("userId") Integer userId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Review r WHERE r.id.bookId = :bookId AND r.id.userId = :userId")
    void deleteByBookAndUser(@Param("bookId") Integer bookId, @Param("userId") Integer userId);
}
